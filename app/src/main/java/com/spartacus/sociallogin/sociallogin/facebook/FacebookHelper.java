package com.spartacus.sociallogin.sociallogin.facebook;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class FacebookHelper {
    private FacebookListener mListener;
    private CallbackManager mCallBackManager;

    public FacebookHelper(@NonNull FacebookListener facebookListener) {
        mListener = facebookListener;
        mCallBackManager = CallbackManager.Factory.create();
        FacebookCallback<LoginResult> mCallBack = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //  mListener.onFbSignInSuccess(loginResult.getAccessToken().getToken(),
                // loginResult.getAccessToken().getUserId());
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Bitmap bitmap = null;
                                try {
                                    bitmap = GetProfilePicFromid(object);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    bitmap = GetProfilePicFromLink(object);
                                }
                                if (bitmap == null) bitmap = GetProfilePicFromid(object);

                                try {
                                    mListener.onFbSignInSuccess(object, bitmap);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday,first_name,last_name,location,locale,timezone" +
                        ",updated_time,verified,link,picture");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                mListener.onFbSignInFail("User cancelled operation");
                performSignOut();

            }

            @Override
            public void onError(FacebookException e) {
                mListener.onFbSignInFail(e.getMessage());
                performSignOut();
            }
        };
        LoginManager.getInstance().registerCallback(mCallBackManager, mCallBack);
    }

    @NonNull
    @CheckResult
    public CallbackManager getCallbackManager() {
        return mCallBackManager;
    }

    public void performSignIn(Activity activity) {
        LoginManager.getInstance()
                .logInWithReadPermissions(activity,
                        Arrays.asList("public_profile", "user_friends", "email"));
    }

    public void performSignIn(Fragment fragment) {
        LoginManager.getInstance()
                .logInWithReadPermissions(fragment,
                        Arrays.asList("public_profile", "user_friends", "email"));
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallBackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void performSignOut() {
        LoginManager.getInstance().logOut();
        mListener.onFBSignOut();
    }

    public Bitmap GetProfilePicFromLink(JSONObject object) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Bitmap myBitmap = null;
        try {
            JSONObject ob2 = (JSONObject) object.get("picture");
            JSONObject ob = (JSONObject) ob2.get("data");
            URL url = new URL((String) ob.get("url"));
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            myBitmap = BitmapFactory.decodeStream(input);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myBitmap;
    }

    private Bitmap ResizeBitmap(Bitmap myBitmap, int NewWigth, int Newheight) {
        int width = myBitmap.getWidth();
        int height = myBitmap.getHeight();
        float scaleWidth = ((float) NewWigth) / width;
        float scaleHeight = ((float) Newheight) / height;
        // CREATE A MATRIX FOR THE MANIPULATION
        Matrix matrix = new Matrix();
        // RESIZE THE BIT MAP
        matrix.postScale(scaleWidth, scaleHeight);

        // "RECREATE" THE NEW BITMAP
        Bitmap resizedBitmap = Bitmap.createBitmap(
                myBitmap, 0, 0, width, height, matrix, false);
        myBitmap.recycle();
        return myBitmap;
    }

    public Bitmap GetProfilePicFromid(JSONObject object) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Bitmap bitmap = null;
        try {
            URL imageURL = new URL("https://graph.facebook.com/" + object.getString("id") + "/picture?type=large");
            bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}
