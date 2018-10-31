package com.lenvosoft.offers.social.interfaces;

import android.graphics.Bitmap;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface GoogleListener {
  void onGoogleAuthSignIn(String authToken, GoogleSignInAccount userId, Bitmap bitmap);

  void onGoogleAuthSignInFailed(String errorMessage);

  void onGoogleAuthSignOut();
}
