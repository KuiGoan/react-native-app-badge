package com.github.amarcruz.rnshortcutbadge;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashMap;
import java.util.Map;

public class RNAppBadgeModule extends ReactContextBaseJavaModule {

    private ReactApplicationContext mReactContext;
    private int lastBadgeCount = -1;

    RNAppBadgeModule(ReactApplicationContext reactContext) {
        super(reactContext);
        mReactContext = reactContext;
    }


    /**
     * Get the current position. This can return almost immediately if the location is cached or
     * request an update, which might take a while.
     */
    @ReactMethod
    public void setCount(final int count) {
        if (lastBadgeCount == count) {
            return;
        }
        lastBadgeCount = count;
        NotificationBadge.applyCount(mReactContext, count);
    }
}