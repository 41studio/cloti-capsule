package com.fourtyonestudio.cloticapsuleloopback;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.common.collect.ImmutableMap;
import com.strongloop.android.loopback.Model;
import com.strongloop.android.loopback.ModelRepository;
import com.strongloop.android.loopback.RestAdapter;
import com.strongloop.android.remoting.adapters.Adapter;

import org.json.JSONObject;

import java.util.Arrays;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @Bind(R.id.login_progress)
    ProgressBar loginProgress;
    @Bind(R.id.login_facebook)
    Button loginFacebook;
    @Bind(R.id.email)
    AutoCompleteTextView email;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.email_sign_in_button)
    Button emailSignInButton;
    @Bind(R.id.email_login_form)
    LinearLayout emailLoginForm;
    @Bind(R.id.login_form)
    ScrollView loginForm;
    @Bind(R.id.register_now)
    TextView registerNow;

    private ProgressDialog pDialog;

    // Facebook
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Facebook configuration
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        // Facebook login onclick
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Log.d("Success", "Login");

                        AccessToken accessToken = loginResult.getAccessToken();

                        final String token = accessToken.getToken();
                        Log.d("Success", token);

                        // App code
                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.v("LoginActivity", response.toString());

                                        Log.d("Success", object.optString("first_name"));
                                        Log.d("Success", object.optString("last_name"));
                                        Log.d("Success", object.optString("id"));
                                        Log.d("Success", object.optString("gender"));
                                        Log.d("Success", object.optString("email"));

                                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                        startActivity(i);
                                        finish();

//                                       //String access_token, String email, String first_name, String last_name, String platform, String uid, String username
                                        //setLoginSocmed(token, object.optString("email"), object.optString("first_name"), object.optString("last_name"), "facebook", object.optString("id"), "", object.optString("gender"));
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender, birthday, first_name, last_name");
                        request.setParameters(parameters);
                        request.executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        //Toast.makeText(MainActivity.this, "Login Cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(LoginActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result Facebook
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onResume() {
        super.onResume();

        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
        super.onPause();
    }

    @OnClick(R.id.login_facebook)
    void btnFacebookClick() {
        LoginManager.getInstance().logOut();
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile, email, user_birthday"));

    }

    @OnClick(R.id.email_sign_in_button)
    void btnEmailSignIn() {
        if (!email.getText().toString().trim().equals("") && !password.getText().toString().trim().equals("")) {
            login();
        } else {
            Toast.makeText(this, "Please fill correctly", Toast.LENGTH_LONG).show();
        }

    }

    @OnClick(R.id.register_now)
    void registerClick() {
        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(i);
    }

    private void login() {

        // 1. Grab the shared RestAdapter instance.
        final Apps app = new Apps();
        final RestAdapter adapter = app.getLoopBackAdapter();

        // 2. Instantiate our ModelRepository. Rather than create a subclass
        // this time, we'll use the base classes to show off their off-the-shelf
        // super-powers.
        final ModelRepository<Model> repository = adapter.createRepository("Users");

        // 3. The meat of the lesson: custom behaviour. Here, we're invoking
        // a custom, static method on the Location model type called "nearby".
        // As you might expect, it does a geo-based query ordered by the closeness
        // to the provided latitude and longitude. Rather than go through
        // LocationClient, we've plugged in the coordinates of our favorite noodle
        // shop here in San Mateo, California.
        //
        // Once we've successfully loaded the models, we pass them to our
        // `displayLocations` method to be converted to MarkerOptions and
        // added to the map as clickable pins!
        final Map<String, ?> parameters = ImmutableMap.of(
                "email", email.getText().toString(),
                "password", password.getText().toString());


        repository.invokeStaticMethod(
                "login",
                parameters,
                new Adapter.JsonObjectCallback() {
                    @Override
                    public void onSuccess(final JSONObject response) {
                        Toast.makeText(LoginActivity.this, "Success", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(final Throwable t) {
                       Toast.makeText(LoginActivity.this, t.toString(), Toast.LENGTH_LONG).show();
                    }
                });

    }



}
