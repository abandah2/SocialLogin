package com.spartacus.sociallogin.sociallogin.google;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface GoogleListener {
  void onGoogleAuthSignIn(String authToken, GoogleSignInAccount userId);

  void onGoogleAuthSignInFailed(String errorMessage);

  void onGoogleAuthSignOut();
}
