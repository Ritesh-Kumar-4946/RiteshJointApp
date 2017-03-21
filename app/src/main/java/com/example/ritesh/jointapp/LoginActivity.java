package com.example.ritesh.jointapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.applozic.mobicomkit.Applozic;
import com.applozic.mobicomkit.api.account.register.RegistrationResponse;
import com.applozic.mobicomkit.api.account.user.PushNotificationTask;
import com.applozic.mobicomkit.api.account.user.User;
import com.applozic.mobicomkit.api.account.user.UserLoginTask;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.ritesh.jointapp.constant.AppsConstant;
import com.example.ritesh.jointapp.constant.HttpUrlPath;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import fr.castorflex.android.circularprogressbar.CircularProgressBar;
import fr.castorflex.android.circularprogressbar.CircularProgressDrawable;

/**
 * Created by ritesh on 19/7/16.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button login;
    TextView signup;
    SharedPreferences sharedPreferences;
    CircularProgressBar mCircularProgressBar;
    RelativeLayout loginprogress;

    // ProgressBar loginprogressbar;

    SharedPreferences.Editor editor;
    String result = "";
    String str_UserEmail = "", str_Password = "";
    String userid = "", useremail = "", userpassword = "", username = "",
            usermobile = "", useraddress = "", str_joint_id = "";

    EditText edt_UEmail, edt_Password;
    // CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        //callbackManager = CallbackManager.Factory.create();

        // sharedPreferences = getSharedPreferences(islogin, MODE_PRIVATE);

        AppsConstant.jointpreference = getSharedPreferences(AppsConstant.MyPREFERENCES, Context.MODE_PRIVATE);

        signup = (TextView) findViewById(R.id.tv_signup);
        login = (Button) findViewById(R.id.btn_login);
        edt_UEmail = (EditText) findViewById(R.id.et_suseremail);
        edt_Password = (EditText) findViewById(R.id.et_setpassword);
        mCircularProgressBar = (CircularProgressBar) findViewById(R.id.progressbar_circular);
        loginprogress = (RelativeLayout) findViewById(R.id.rl_login_progress);
        loginprogress.setVisibility(View.GONE);
        //    loginprogressbar = (ProgressBar) findViewById(R.id.loginprogressbar);

        ((CircularProgressDrawable) mCircularProgressBar.getIndeterminateDrawable()).start();
        updateValues();

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signups = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(signups);
               /* Toast.makeText(getApplicationContext(), "WelCome to Signup",
                        Toast.LENGTH_SHORT).show();*/

            }
        });
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        boolean iserror = false;

        str_UserEmail = edt_UEmail.getText().toString().trim();
        str_Password = edt_Password.getText().toString().trim();

        /* make edittext condition for empty, input etc match */

        if (str_UserEmail.equals("")) {
            iserror = true;
            /**************** Start Animation ****************/
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(edt_UEmail);
            /**************** End Animation ****************/

            Toast.makeText(getApplicationContext(), "Please enter email address.", Toast.LENGTH_LONG).show();
        } else if (!isValidEmail(str_UserEmail)) {
            iserror = true;
            edt_UEmail.requestFocus();

            /**************** Start Animation ****************/
            YoYo.with(Techniques.Shake)
                    .duration(700)
                    .playOn(edt_UEmail);
            /**************** End Animation ****************/

            Toast.makeText(getApplicationContext(), "Enter valid email.", Toast.LENGTH_SHORT).show();

        } else if (str_Password.equals("")) {
            iserror = true;

            /**************** Start Animation ****************/
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(edt_Password);
            /**************** End Animation ****************/

            Toast.makeText(getApplicationContext(), "Please enter your Password", Toast.LENGTH_LONG).show();
        }
        if (!iserror) {

            if (Utils.isConnected(getApplicationContext())) {
                Loginjsontask task = new Loginjsontask();
                task.execute();
            } else {
                //Toast.makeText(getApplicationContext(), "Please Cheeck network conection..", Toast.LENGTH_SHORT).show();
                //  TODO Auto-generated method stub

                LayoutInflater layoutInflater = LayoutInflater.from(this);
                View promptView = layoutInflater.inflate(R.layout.activity_wificheck_customalert, null);
                final AlertDialog alertD = new AlertDialog.Builder(this).create();
            /*prevent alert dialog from outside click and back button click (Star)*/
                alertD.setCanceledOnTouchOutside(false);
                alertD.setCancelable(false);
            /*prevent alert dialog from outside click and back button click (Star)*/
                Button Tryagain = (Button) promptView.findViewById(R.id.btn_tryagain);
                Tryagain.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = getIntent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                    }
                });
                alertD.setView(promptView);
                alertD.show();

            }
        }

    }

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        }
    }

    public class Loginjsontask extends AsyncTask<String, Void, String> {

        boolean iserror = false;

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            //  loginprogressbar.setVisibility(View.VISIBLE);
            Log.e("******* NOW WEB SERVICE IS RUNNING *******", "YES");
            Log.e("******* NOW WEB SERVICE IS RUNNING *******", "YES");
            loginprogress.setVisibility(View.VISIBLE);

        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            Log.e("******* NOW BACKGROUND TASK IS RUNNING *******", "YES");
            Log.e("******* NOW BACKGROUND TASK IS RUNNING *******", "YES");

            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(HttpUrlPath.urlPath + "login?");

            try {
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(7);
                nameValuePairs.add(new BasicNameValuePair("email", str_UserEmail));
                nameValuePairs.add(new BasicNameValuePair("password", str_Password));

                post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = client.execute(post);
                String object = EntityUtils.toString(response.getEntity());
                System.out.println("****************object*******************=" + object);
                System.out.println("****************object*******************=" + object);
                System.out.println("****************object*******************=" + object);

                //JSONArray js = new JSONArray(object);
                JSONObject jobect = new JSONObject(object);
                result = jobect.getString("result");
                if (result.equalsIgnoreCase("successful")) {
                    userid = jobect.getString("id");
                    Log.e("******* ID RETURN BY SERVER *******", "" + userid);
                    Log.e("******* ID RETURN BY SERVER *******", "" + userid);
                    Log.e("******* ID RETURN BY SERVER *******", "" + userid);

                    useremail = jobect.getString("email");
                    Log.e("******* EMAIL RETURN BY SERVER *******", "" + useremail);
                    Log.e("******* EMAIL RETURN BY SERVER *******", "" + useremail);
                    Log.e("******* EMAIL RETURN BY SERVER *******", "" + useremail);

                    userpassword = jobect.getString("password");
                    str_joint_id = jobect.getString("joint_id");
                    Log.e("******* PASSWORD RETURN BY SERVER *******", "" + userpassword);
                    Log.e("******* PASSWORD RETURN BY SERVER *******", "" + userpassword);
                    Log.e("******* PASSWORD RETURN BY SERVER *******", "" + userpassword);
                }

            } catch (Exception e) {
                Log.v("22", "22" + e.getMessage());
                e.printStackTrace();
                iserror = true;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result1) {
            // TODO Auto-generated method stub
            super.onPostExecute(result1);

            if (!iserror) {
                if (result.equalsIgnoreCase("successful")) {

                    //  loginprogressbar.setVisibility(View.GONE);

                    AppsConstant.jointpreference = getSharedPreferences(AppsConstant.MyPREFERENCES, Context.MODE_PRIVATE);
                    AppsConstant.editorjoint = AppsConstant.jointpreference.edit();
                    AppsConstant.editorjoint.putString("LoginSuccessfully", AppsConstant.islogin);
                    AppsConstant.editorjoint.putString("id", userid);
                    AppsConstant.editorjoint.putString("loginTest", "true");

                    Log.e("********* GET USER ID *********", "" + userid + "\nSuccess USERNAME PASSWORD MATCH");
                    Log.e("********* GET USER ID *********", "" + userid + "\nSuccess USERNAME PASSWORD MATCH");
                    Log.e("********* GET USER ID *********", "" + userid + "\nSuccess USERNAME PASSWORD MATCH");
                    AppsConstant.editorjoint.commit();


                    UserLoginTask.TaskListener listener = new UserLoginTask.TaskListener() {

                        @Override
                        public void onSuccess(RegistrationResponse registrationResponse, Context context) {
                            //After successful registration with Applozic server the callback will come here

                            PushNotificationTask pushNotificationTask = null;
                            PushNotificationTask.TaskListener listener=  new PushNotificationTask.TaskListener() {
                                @Override
                                public void onSuccess(RegistrationResponse registrationResponse) {

                                }
                                @Override
                                public void onFailure(RegistrationResponse registrationResponse, Exception exception) {

                                }

                            };
                            pushNotificationTask = new PushNotificationTask(Applozic.getInstance(context).getDeviceRegistrationId(),listener,context);
                            pushNotificationTask.execute((Void)null);

                            /*Intent mainActvity = new Intent(context, MainActivity.class);
                            startActivity(mainActvity);
                            Intent intent = new Intent(context, ConversationActivity.class);
                            if (ApplozicClient.getInstance(LoginActivity.this).isContextBasedChat()) {
                                intent.putExtra(ConversationUIService.CONTEXT_BASED_CHAT, true);
                            }
                            startActivity(intent);
                            finish();*/

                        }

                        @Override
                        public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
                            //If any failure in registration the callback  will come here
                        }};

                    User user = new User();
                    user.setUserId(str_joint_id); //userId it can be any unique user identifier
                    user.setDisplayName(username); //displayName is the name of the user which will be shown in chat messages
                    user.setEmail(useremail); //optional
                    user.setAuthenticationTypeId(User.AuthenticationType.APPLOZIC.getValue());  //User.AuthenticationType.APPLOZIC.getValue() for password verification from Applozic server and User.AuthenticationType.CLIENT.getValue() for access Token verification from your server set access token as password
                    user.setPassword(""); //optional, leave it blank for testing purpose, read this if you want to add additional security by verifying password from your server https://www.applozic.com/docs/configuration.html#access-token-url
                    user.setImageLink("");//optional,pass your image link
                    new UserLoginTask(user, listener, LoginActivity.this).execute((Void) null);



                    Log.e("********* Login Detail SEND *********", "Success USERNAME PASSWORD MATCH GOTO MAINPAGE");
                    Log.e("********* Login Detail SEND *********", "Success USERNAME PASSWORD MATCH GOTO MAINPAGE");
                    Log.e("********* Login Detail SEND *********", "Success USERNAME PASSWORD MATCH GOTO MAINPAGE");

                    Toast.makeText(LoginActivity.this, "Login Successfully ", Toast.LENGTH_SHORT).show();
                    Intent in = new Intent(LoginActivity.this, MainActivity.class);
                    in.putExtra("EXIT", "0");
                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    LoginActivity.this.startActivity(in);
                    finish();
//                    ((CircularProgressDrawable) mCircularProgressBar.getIndeterminateDrawable()).stop();

//                    ((CircularProgressDrawable) mCircularProgressBar.getIndeterminateDrawable()).progressiveStop();
                    loginprogress.setVisibility(View.GONE);

                } else {

                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Swing)
                            .duration(700)
                            .playOn(edt_UEmail);

                    YoYo.with(Techniques.Swing)
                            .duration(700)
                            .playOn(edt_Password);
                    /**************** End Animation ****************/

                    Log.e("********* Login Detail SEND *********", "NOT Success USERNAME PASSWORD ERROR");
                    Log.e("********* Login Detail SEND *********", "NOT Success USERNAME PASSWORD ERROR");
                    Log.e("********* Login Detail SEND *********", "NOT Success USERNAME PASSWORD ERROR");
//                    ((CircularProgressDrawable) mCircularProgressBar.getIndeterminateDrawable()).stop();

//                    ((CircularProgressDrawable) mCircularProgressBar.getIndeterminateDrawable()).progressiveStop();

                    loginprogress.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Username or Password is wrong",
                            Toast.LENGTH_SHORT).show();
//									// TODO Auto-generated method stub
                }
            } else {
                Log.e("********* Login Detail SEND *********", "NOT Success");
                Log.e("********* Login Detail SEND *********", "NOT Success");
                Log.e("********* Login Detail SEND *********", "NOT Success");

                Toast.makeText(getApplicationContext(), "Oops!! Please check server connection .",
                        Toast.LENGTH_SHORT).show();
//                ((CircularProgressDrawable) mCircularProgressBar.getIndeterminateDrawable()).stop();
//								// TODO Auto-generated method stub
//                ((CircularProgressDrawable) mCircularProgressBar.getIndeterminateDrawable()).progressiveStop();
                loginprogress.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        super.onBackPressed();
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }

    private void updateValues() {
        CircularProgressDrawable circularProgressDrawable;
        CircularProgressDrawable.Builder b = new CircularProgressDrawable.Builder(this)
                .colors(getResources().getIntArray(R.array.gplus_colors))
                /*.sweepSpeed(mSpeed)
                .rotationSpeed(mSpeed)
                .strokeWidth(dpToPx(mStrokeWidth))*/
                .style(CircularProgressDrawable.STYLE_ROUNDED);
       /* if (mCurrentInterpolator != null) {
            b.sweepInterpolator(mCurrentInterpolator);
        }*/
        mCircularProgressBar.setIndeterminateDrawable(circularProgressDrawable = b.build());

        // /!\ Terrible hack, do not do this at home!
        circularProgressDrawable.setBounds(0,
                0,
                mCircularProgressBar.getWidth(),
                mCircularProgressBar.getHeight());
        mCircularProgressBar.setVisibility(View.INVISIBLE);
        mCircularProgressBar.setVisibility(View.VISIBLE);
    }
}
