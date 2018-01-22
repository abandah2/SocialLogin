package com.spartacus.sociallogin.sociallogin.facebook;

import android.graphics.Bitmap;

import org.json.JSONObject;

import java.io.IOException;

public interface FacebookListener {
  void onFbSignInFail(String errorMessage);

  void onFbSignInSuccess(JSONObject object, Bitmap bitmap) throws IOException;

  void onFBSignOut();
}
