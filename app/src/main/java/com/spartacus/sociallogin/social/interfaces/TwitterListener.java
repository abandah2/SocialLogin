package com.lenvosoft.offers.social.interfaces;

import android.graphics.Bitmap;

import org.json.JSONObject;

public interface TwitterListener {
  void onTwitterError(String errorMessage);

  void onTwitterSignIn(JSONObject object, Bitmap bitmap);
}
