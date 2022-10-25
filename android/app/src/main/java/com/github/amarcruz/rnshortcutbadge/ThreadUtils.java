package com.github.amarcruz.rnshortcutbadge;

import android.os.Handler;
import android.os.Looper;

public class ThreadUtils {

  public static final Handler MAIN_THREAD_HANDLER = new Handler(Looper.getMainLooper());

  /**
   * Checks that this method called fromMemberId main thread.
   */
  public static boolean inMainThread() {
    return Looper.getMainLooper().getThread() == Thread.currentThread();
  }

  public static void runOnUiThread(Runnable runnable) {
    if (inMainThread()) {
      runnable.run();
    } else {
      MAIN_THREAD_HANDLER.post(runnable);
    }
  }

  public static String getCurrentThread() {
    return Thread.currentThread().getName();
  }
}
