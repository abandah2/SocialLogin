package com.spartacus.sociallogin.sociallogin.twitter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.util.Log;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.AccountService;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

import io.fabric.sdk.android.Fabric;
import retrofit2.Call;

import static android.content.ContentValues.TAG;

public class TwitterHelper {
    private TwitterAuthClient mAuthClient;

    @NonNull
    private final Activity mActivity;

    @NonNull
    private final TwitterListener mListener;
    @NonNull
    private final String mTwitterApiKey;
    @NonNull
    private final String mTwitterSecreteKey;

    public TwitterHelper(@NonNull TwitterListener response, @NonNull Activity context,
                         @NonNull String twitterApiKey, @NonNull String twitterSecreteKey) {
        mActivity = context;
        mListener = response;
        mTwitterApiKey = twitterApiKey;
        mTwitterSecreteKey = twitterSecreteKey;
        TwitterAuthConfig authConfig = new TwitterAuthConfig(mTwitterApiKey, mTwitterSecreteKey);
        Fabric.with(context, new Twitter(authConfig));
        mAuthClient = new TwitterAuthClient();
    }

    private Callback<TwitterSession> mCallback = new Callback<TwitterSession>() {
        @Override
        public void success(Result<TwitterSession> result) {
            TwitterSession session = result.data;
            Log.e("tweet", session.getUserName() + session.getUserId());
            // The TwitterSession is also available through:
            // TWITTER.getInstance().core.getSessionManager().getActiveSession()
            final JSONObject object = new JSONObject();
            try {
                object.put("UserName", session.getUserName());
                object.put("UserId", session.getUserId());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final Bitmap[] bitmap = {null};
            AccountService _AccountService = Twitter.getApiClient(result.data).getAccountService();
            _AccountService.verifyCredentials(true, true).enqueue(new retrofit2.Callback<User>() {
                @Override
                public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                    Log.e(TAG, "Twitter user is: " + response.toString());
                    Log.e(TAG, "Twitter-Email" + response.body().email);
                    Log.e(TAG, "Twitter-profileImage" + response.body().profileImageUrl);
                    Log.e(TAG, "Twitter-ID" + response.body().id);
                    // twitterDetails = response.body().email + "," + response.body().profileImageUrl + "," + response.body().id;
                    try {
                        object.put("profileImage", response.body().profileImageUrlHttps);
                        object.put("createdAt", response.body().createdAt);
                        object.put("lang", response.body().lang);
                        object.put("name", response.body().name);
                        object.put("screenName", response.body().screenName);
                        object.put("timeZone", response.body().timeZone);
                        object.put("verified", response.body().verified);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    bitmap[0] = GetProfilePicFromurl(object);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.e(TAG, "verifyCredentials failed! " + t.getLocalizedMessage());
                }
            });

            final TwitterSession twitterSession = result.data;
            mAuthClient.requestEmail(twitterSession, new com.twitter.sdk.android.core.Callback<String>() {
                @Override
                public void success(Result<String> emailResult) {
                    String email = emailResult.data;
                    try {
                        object.put("Email", emailResult.data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    mListener.onTwitterSignIn(object, bitmap[0]);

                }

                @Override
                public void failure(TwitterException e) {
                    mListener.onTwitterSignIn(object, bitmap[0]);
                }
            });


        }

        @Override
        public void failure(TwitterException exception) {
            mListener.onTwitterError(exception.getMessage());
        }
    };

    public void performSignIn(Context context) {
        //

        TwitterAuthConfig authConfig = new TwitterAuthConfig(mTwitterApiKey, mTwitterSecreteKey);
        Fabric.with(context, new Twitter(authConfig));
        //Fabric.with(context, new TwitterCore(authConfig), new TweetUi());
        mAuthClient.authorize(mActivity, mCallback);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (mAuthClient != null) mAuthClient.onActivityResult(requestCode, resultCode, data);
    }

    public Bitmap GetProfilePicFromurl(JSONObject object) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Bitmap bitmap = null;
        try {
            URL imageURL = new URL(object.getString("profileImage").replace("_normal", "_bigger"));
            bitmap = BitmapFactory.decodeStream(imageURL.openConnection().getInputStream());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;//ResizeBitmap(bitmap,150,150);
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
}