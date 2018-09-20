package com.coeuz.pyscustomer;


import android.annotation.SuppressLint;
import android.annotation.TargetApi;

import com.facebook.FacebookSdk;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import com.coeuz.pyscustomer.Requiredclass.Constant;
import com.coeuz.pyscustomer.Requiredclass.TinyDB;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;

import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;

import com.google.android.gms.common.api.GoogleApiClient;

import com.onesignal.OneSignal;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    String  muserId, mcustomerName, mmobileNumber, memailId;

    Context context;
    private TinyDB tinyDB;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "MyPrefs";
    String  mToken;
    LoginButton faceBookLoginbtn;
    CallbackManager callbackManager;


    private static final int RC_SIGN_IN = 420;
    private GoogleApiClient mGoogleApiClient;
    private SignInButton btnSignIn;


    private LinearLayout noInternetLayout;
    private LinearLayout allViewLayout;
    private String onsignalUserId;


    @SuppressWarnings("deprecation")
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable(String userId, String registrationId) {
                onsignalUserId=userId;
                Log.d("debug", "User:" + userId);
                if (registrationId != null)
                    Log.d("debug", "registrationId:" + registrationId);

            }
        });
        setContentView(R.layout.activity_login);

        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tinyDB = new TinyDB(getApplicationContext());
        btnSignIn = findViewById(R.id.GoogleLoginbtn);
        initializeGPlusSetting();
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
      /*  btn = (Button) findViewById(R.id.btnss);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                disconnectFromFacebook();
            }
        });*/
        printKeyHash(getApplicationContext());
        noInternetLayout =findViewById(R.id.NoInternetLayout);
        allViewLayout =findViewById(R.id.email_login_form);

        faceBookLoginbtn = findViewById(R.id.FacebookLoginbtn);
        faceBookLoginbtn.setReadPermissions("public_profile", "email", "user_friends");

    /*    faceBookLoginbtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            private ProfileTracker mProfileTracker;

            @Override
            public void onSuccess(LoginResult loginResult) {
                if (Profile.getCurrentProfile() == null) {
                    mProfileTracker = new ProfileTracker() {
                        @Override
                        protected void onCurrentProfileChanged(Profile oldProfile, Profile currentProfile) {
                            Log.d("ffwrfewfew12", "ffwrfewfew");
                            Log.d("facebook - profile", currentProfile.getFirstName());
                            mProfileTracker.stopTracking();
                        }
                    };
                    // no need to call startTracking() on mProfileTracker
                    // because it is called by its constructor, internally.
                } else {
                    Profile profile = Profile.getCurrentProfile();
                    Log.d("ffwrfewfew342", "ffwrfewfew");
                    Log.d("frewfrewf322", profile.getFirstName());
                }
            }

            @Override
            public void onCancel() {
                Log.d("ffwrfewfew12", "ffwrfewfew");
                Log.d("facebook - onCancel", "cancelled");
            }

            @Override
            public void onError(FacebookException e) {
                Log.d("facebook - onError", e.getMessage());
            }
        });*/
       callbackManager=CallbackManager.Factory.create();
        faceBookLoginbtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {


               /* Toast.makeText(LoginActivity.this, "Login Success \n"+loginResult.getAccessToken().getUserId()+
                        "\n"+loginResult.getAccessToken().getToken(), Toast.LENGTH_SHORT).show();*/
                Log.d("yuwriyiwuwe",loginResult.getAccessToken().getToken());
                Log.d("yuwriyiwuwe1", String.valueOf(loginResult));

                mToken=loginResult.getAccessToken().getToken();
                tinyDB.putString(Constant.TOKEN,mToken);
                Log.d("fuiwrfuwiewfeww",mToken);


                SharedPreferences.Editor editor = sharedpreferences.edit();

                  /*     editor.putString(Name, UserName);

                editor.putString(Email, UserPassword);*/

                editor.clear();
                editor.apply();
                Intent intent=new Intent(getApplicationContext(),SlotPages.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Login Cancel", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onError(FacebookException error) {
            Log.d("fhewuhfu8eiw",error.toString());

            }
        });
        mEmailView = findViewById(R.id.email);

       // View mProgressView = findViewById(R.id.login_progress);

        mPasswordView = findViewById(R.id.password);


        Button mEmailSignInButton = findViewById(R.id.email_sign_in_button);
        TextView memail_Forgot_button = findViewById(R.id.email_Forgot_button);
        memail_Forgot_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ForgotPassordActivity.class);
                startActivity(intent);
            }
        });

        TextView SignUp_button = findViewById(R.id.SignUp);
        SignUp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });


        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    if (imm != null) {
                        imm.hideSoftInputFromWindow(Objects.requireNonNull(getCurrentFocus()).getWindowToken(), 0);
                    }
                } catch (Exception e) {
                    // TODO: handle exception
                }
                attemptLogin();
            }
        });
    }

    @SuppressWarnings("deprecation")
    private void initializeGPlusSetting() {

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        btnSignIn.setSize(SignInButton.SIZE_ICON_ONLY);
        btnSignIn.setScopes(gso.getScopeArray());
    }

    private void signIn() {
        Intent signIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        Log.d("jhuwri2", String.valueOf(mGoogleApiClient));
        startActivityForResult(signIntent, RC_SIGN_IN);
    }

    private void attemptLogin() {


        mEmailView.setError(null);
        mPasswordView.setError(null);

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String MobilePattern = "[0-9]{10}";


        final String emails = mEmailView.getText().toString();
        final String passwords = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(passwords) && !isPasswordValid(passwords)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }


        if (TextUtils.isEmpty(emails)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!mEmailView.getText().toString().trim().matches(emailPattern) && !mEmailView.getText().toString().matches(MobilePattern)) {
            mEmailView.setError("Please enter valid input ");
            mEmailView.requestFocus();
            return;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            String URL = Constant.APIONE +"/user/getCurrentUser";
            StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    Log.d("fnruingior",response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        muserId = jsonObject.getString("userId");
                        mcustomerName = jsonObject.getString("customerName");
                        mmobileNumber = jsonObject.getString("mobileNumber");
                        memailId = jsonObject.getString("emailId");
                        tinyDB.putString(Constant.CUSTOMERNAME, mcustomerName);
                        tinyDB.putString(Constant.USERID, muserId);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.clear();
                    editor.apply();
                    Intent intent=new Intent(getApplicationContext(),PaymentActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                    Log.d("jfiojeio", String.valueOf(response));


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("ppppppppppp", String.valueOf(error));
                    if (String.valueOf(error).equals("com.android.volley.AuthFailureError")) {
                        Toast.makeText(getApplicationContext(), "Couldn't find Your Account! Please SignUp!!", Toast.LENGTH_SHORT).show();
                    }

                    if (error instanceof NetworkError) {
                        noInternetLayout.setVisibility(View.VISIBLE);
                        allViewLayout.setVisibility(View.GONE);
                        Button button =  findViewById(R.id.TryAgain);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                recreate();
                            }
                        });
                    } else if (error instanceof ServerError) {

                        Log.d("heuiwirhu1", String.valueOf(error));
                    } else if (error instanceof ParseError) {
                        Toast.makeText(getApplicationContext(), "Parsing error! Please try again after some time!!", Toast.LENGTH_SHORT).show();

                    }  else if (error instanceof TimeoutError) {
                        noInternetLayout.setVisibility(View.VISIBLE);
                        allViewLayout.setVisibility(View.GONE);
                        Button button =  findViewById(R.id.TryAgain);
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                recreate();
                            }
                        });

                    }
                }
            })

            {
                @Override
                protected Response<String> parseNetworkResponse(final NetworkResponse response) {
                    try {
                        Log.d("mfiwoeiomifm", response.headers.get("x-auth-token"));
                        tinyDB.putString(Constant.TOKEN, response.headers.get("x-auth-token"));
                        return Response.success(
                                new String(
                                        response.data,
                                        HttpHeaderParser.parseCharset(response.headers, "")),
                                HttpHeaderParser.parseCacheHeaders(response));
                    } catch (UnsupportedEncodingException e) {
                        return Response.error(new ParseError(e));
                    }
                }


                @Override
                public Map<String, String> getHeaders() {
                    Map<String, String> headers = new HashMap<>();

                    String credentials = emails + ':' + passwords;
                    Log.d("hfrwuihi", emails);
                    Log.d("hfrwuihi", passwords);
                    String auth = "Basic "
                            + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
                    headers.put("Authorization", auth);
                    headers.put("X-Notify-Token", onsignalUserId);
                    Log.d("jfiwejetr", onsignalUserId);
                    Log.d("jfiwej", String.valueOf(headers.get("x-auth-token")));
                    return headers;

                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            requestQueue.add(request);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            String a = result.getStatus().toString();
            Log.d("iortrewutrt", a);

            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();

                if (acct != null) {
                    Log.d("iortrewutrt2", acct.getIdToken());
                    mToken = acct.getIdToken();
                    tinyDB.putString(Constant.TOKEN, mToken);
                    Log.d("fuiwrfuwiewfeww", mToken);


                    SharedPreferences.Editor editor = sharedpreferences.edit();

                  /*     editor.putString(Name, UserName);

                editor.putString(Email, UserPassword);*/

                    editor.clear();
                    editor.apply();
                    Intent intent = new Intent(getApplicationContext(), SlotPages.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();

                }
                assert acct != null;
                Log.d("iortrewutrt4", acct.getEmail());

            }




            //handleGPlusSignInResult(result);


                 /*   GoogleSignInResult result1 = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                    GoogleSignInAccount acct = result1.getSignInAccount();

                    if(acct!=null){
                        String authCode = acct.getServerAuthCode();
                       *//* String personName = acct.getDisplayName();
                        String personEmail = acct.getEmail();
                        String personPhotoUrl = acct.getPhotoUrl().toString();
                        String personKey = acct.getIdToken().toString();*//*
                        Log.d("fewfwe",authCode);
                    }*/


        }
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }


   /* private void handleGPlusSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {

            GoogleSignInAccount acct = result.getSignInAccount();


            if (acct != null) {
                Log.d("riueeire1", String.valueOf(acct));
                GoogleSignInAccount acct1 = result.getSignInAccount();
                String acct12 = acct1.getIdToken();
                if (acct12 != null) {
                    Log.d("riueeire12", acct12);
                }
                Log.d("riueeire12", String.valueOf(acct1));
                String idTokenString = acct1.getIdToken();
                if (idTokenString != null) {
                    Log.d("riueeire", idTokenString);
                }
                 *//* mToken=idTokenString.getAccessToken().getToken();
                tinyDB.putString(TOKEN,mToken);
                Log.d("fuiwrfuwiew",mToken);*//*
                //getCurrentUser();

                SharedPreferences.Editor editor = sharedpreferences.edit();

                  *//*     editor.putString(Name, UserName);

                editor.putString(Email, UserPassword);*//*

                editor.clear();
                editor.commit();
                Intent intent = new Intent(getApplicationContext(), SlotPages.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
              *//*  mHandler.postDelayed(new Runnable() {
                public void run() {
                    mEmailView.setText("");
                    mPasswordView.setText("");
                    mPasswordView.clearFocus();
                }
            }, 1000);*//*
            }
        }


    }
*/
   /* private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }*/

    private boolean isPasswordValid(String password) {
        //checkSelfPermissionTODO: Replace this with your own logic
        return password.length() > 4;
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("PackageManagerGetSignatures")
    public static void printKeyHash(Context context) {


        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.d("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }
        Log.d("ewrt43t", key);
    }

   /* public void disconnectFromFacebook() {

        if (AccessToken.getCurrentAccessToken() == null) {
            Log.d("jriotjio3", "greuthn4uire");
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {
                Log.d("jriotjio31", "greuthn4uire");
                LoginManager.getInstance().logOut();

            }
        }).executeAsync();
    }*/


}
