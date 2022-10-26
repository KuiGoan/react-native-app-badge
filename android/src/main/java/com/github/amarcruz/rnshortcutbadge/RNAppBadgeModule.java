package com.github.amarcruz.rnshortcutbadge;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.util.Log;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import java.util.HashMap;
import java.util.Map;

public class RNAppBadgeModule extends ReactContextBaseJavaModule {

  private static final String TAG = "RNAppBadge";
  private static final String BADGE_FILE = "BadgeCountFile";
  private static final String BADGE_KEY = "BadgeCount";

  private final ReactApplicationContext mReactContext;
  private final SharedPreferences mPrefs;
  private boolean mSupported = false;

  public RNAppBadgeModule(final ReactApplicationContext reactContext) {
    super(reactContext);
    mReactContext = reactContext;
    mPrefs = reactContext.getSharedPreferences(BADGE_FILE, Context.MODE_PRIVATE);
  }

  @Override
  public String getName() {
    return TAG;
  }

  @Override
  public Map<String, Object> getConstants() {
    final HashMap<String, Object> constants = new HashMap<>();
    boolean supported = false;

    try {
      Context context = getCurrentActivity();
      if (context == null) {
        context = mReactContext.getApplicationContext();
      }
      int counter = mPrefs.getInt(BADGE_KEY, 0);
      supported = NotificationBadge.applyCount(context, counter);

      if (!supported && Build.MANUFACTURER.equalsIgnoreCase("Xiaomi")) {
        supported = true;
      }
    } catch (Exception e) {
      Log.e(TAG, "Cannot initialize ShortcutBadger", e);
    }

    mSupported = supported;

    constants.put("launcher", getLauncherName());
    constants.put("supported", supported);

    return constants;
  }

  /**
   * Get the current position. This can return almost immediately if the location is cached or
   * request an update, which might take a while.
   */
  @ReactMethod
  public void setCount(final int count, final Promise promise) {
    try {
      // Save the counter unconditionally
      mPrefs.edit().putInt(BADGE_KEY, count).apply();

      if (!mSupported) {
        promise.resolve(false);
        return;
      }

      Context context = getCurrentActivity();
      if (context == null) {
        context = mReactContext.getApplicationContext();
      }
      boolean ok = NotificationBadge.applyCount(context, count);

      if (ok) {
        promise.resolve(true);
      } else {
        Log.d(TAG, "Cannot set badge.");
        promise.resolve(false);
      }
    } catch (Exception ex) {
      Log.e(TAG, "Error setting the badge", ex);
      promise.reject(ex);
    }
  }

  /**
   * Get the badge from the storage.
   */
  @ReactMethod
  public void getCount(final Promise promise) {
    promise.resolve(mPrefs.getInt(BADGE_KEY, 0));
  }

  /**
   * Find the package name of the current launcher
   */
  private String getLauncherName() {
    String name = null;

    try {
      final Intent intent = new Intent(Intent.ACTION_MAIN);
      intent.addCategory(Intent.CATEGORY_HOME);
      final ResolveInfo resolveInfo = mReactContext.getPackageManager()
          .resolveActivity(intent, PackageManager.MATCH_DEFAULT_ONLY);
      name = resolveInfo.activityInfo.packageName;
    } catch (Exception ignore) {
    }

    return name;
  }
}