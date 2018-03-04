package com.spartacus.sociallogin;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.spartacus.sociallogin.sociallogin.facebook.FacebookHelper;
import com.spartacus.sociallogin.sociallogin.facebook.FacebookListener;
import com.spartacus.sociallogin.sociallogin.google.GoogleHelper;
import com.spartacus.sociallogin.sociallogin.google.GoogleListener;
import com.spartacus.sociallogin.sociallogin.twitter.TwitterHelper;
import com.spartacus.sociallogin.sociallogin.twitter.TwitterListener;

import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    private Button mFacebookButton, mTwitterButton, mGoogleButton, mInstagramButton;

    private FacebookHelper mFacebook;
    private TwitterHelper mTwitter;
    private GoogleHelper mGoogle;


    private ImageView imageView;
    private TextView mDataTextView;
    private CallbackManager mCallbackManager;

    public MainActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
        facebookinitialize();
        twitterinitialize();
        googleinitializa();

        printKeyHash(this);

    }

    private void initialize() {
        mDataTextView = findViewById(R.id.data_received_text_view);
        imageView = findViewById(R.id.imageView);
    }

    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (android.content.pm.Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    private void facebookinitialize() {


        FacebookSdk.setApplicationId("199981650555518");
        FacebookSdk.sdkInitialize(this);
        mFacebookButton = (Button) findViewById(R.id.facebook_button);
        mFacebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFacebook.performSignIn(MainActivity.this);

            }
        });

        mFacebook = new FacebookHelper(new FacebookListener() {
            @Override
            public void onFbSignInFail(String errorMessage) {
                mDataTextView.setText(errorMessage);
            }

            @Override
            public void onFbSignInSuccess(JSONObject object, Bitmap bitmap) throws IOException {
                Log.e("TAG", object.toString());
                imageView.setImageBitmap(bitmap);
                mDataTextView.setText(object.toString());
            }

            @Override
            public void onFBSignOut() {
                mDataTextView.setText("Signed out of Facebook");

            }
        });


    }

    private void twitterinitialize() {


        mTwitterButton = (Button) findViewById(R.id.twitter_button);
        mTwitterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTwitter.performSignIn(MainActivity.this);

            }
        });

        mTwitter = new TwitterHelper(new TwitterListener() {
            @Override
            public void onTwitterError(String errorMessage) {

            }

            @Override
            public void onTwitterSignIn(JSONObject object, Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
                mDataTextView.setText(object.toString());
            }
        }, this, "p29SnVJ1XSBGDPivO93uANxO5", "f82uMWyAhwy2hxD3PacWrNIjYsn0WiUVk7lG8QzpbWr2RQNBQW");


    }

    private void googleinitializa() {
        mGoogleButton = findViewById(R.id.googleli);
        mGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGoogle.performSignIn(MainActivity.this);

            }
        });
        mGoogle = new GoogleHelper(new GoogleListener() {
            @Override
            public void onGoogleAuthSignIn(String authToken, GoogleSignInAccount acc) {
                mDataTextView.setText(acc.toString());


            }

            @Override
            public void onGoogleAuthSignInFailed(String errorMessage) {
                // Log.e("TAGTAG",errorMessage);

            }

            @Override
            public void onGoogleAuthSignOut() {

            }
        }, this, "530750722928-ev6tpms4mba58ip9piofb093cmh75fog.apps.googleusercontent.com");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFacebook.onActivityResult(requestCode, resultCode, data);
        mTwitter.onActivityResult(requestCode, resultCode, data);


    }


}
