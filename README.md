## SocialLogin

Its an easy utility to handle (Facebook Login , twitter Login ,google Login ) . 
Just recive data on listener as Json and Profile pic as Bitmap 

## How to integrate into your app?

* All three are in the same Way 
Step 1. :

```java
public class MainActivity extends AppCompatActivity {

    private FacebookHelper mFacebook;

```
Step 2. 
```java
  FacebookSdk.setApplicationId("*************");
        FacebookSdk.sdkInitialize(this);
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
```

Step 3. 
```java
   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mGoogle.onActivityResult(requestCode, resultCode, data);
    }

```
Step 3. 
perform this call whenever you want to signin (as in button)
```java
     mFacebook.performSignIn(MainActivity.this);
```
