package com.spartacus.sociallogin.sociallogin.twitter;

import android.graphics.Bitmap;

import org.json.JSONObject;

public interface TwitterListener {
  void onTwitterError(String errorMessage);

  void onTwitterSignIn(JSONObject object, Bitmap bitmap);
}
