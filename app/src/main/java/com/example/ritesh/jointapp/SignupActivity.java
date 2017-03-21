package com.example.ritesh.jointapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ritesh on 19/7/16.
 */
public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    Button register;

    EditText edt_Name, edt_Address, edt_Email, edt_Mobile, edt_Lineofwork,
            edt_Jointmeid, edt_Paypalid, edt_Repaypaldi, edt_Password, edt_Repassword;

    boolean iserror = false;

    String str_name = " ",
            result = " ",
            str_address = " ",
            str_email = " ",
            str_mobile = " ",
            str_lineofwork = " ",
            str_paypalid = " ",
            str_repaypalid = " ",
            str_jointmeid = " ",
            str_password = " ",
            str_repassword = " ",
            str_userprofileimage = " ",
            userid = "",
            username = "",
            useremail = "",
            error1 = "",
            useraddress = "",
            usermobile = "",
            userlineofwork = "",
            userprofileimage = "",
            islogin = "";


    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        context = getApplicationContext();

        register = (Button) findViewById(R.id.btn_register);
        edt_Name = (EditText) findViewById(R.id.et_name);
        edt_Address = (EditText) findViewById(R.id.et_address);
        edt_Email = (EditText) findViewById(R.id.et_email);
        edt_Mobile = (EditText) findViewById(R.id.et_mobile);
        edt_Lineofwork = (EditText) findViewById(R.id.et_lineofwork);
        edt_Paypalid = (EditText) findViewById(R.id.et_paypalid);
        edt_Repaypaldi = (EditText) findViewById(R.id.et_confirm_paypalid);
        edt_Password = (EditText) findViewById(R.id.et_password);
        edt_Repassword = (EditText) findViewById(R.id.et_repassword);

        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        boolean iserror = false;
        str_name = edt_Name.getText().toString().trim();
        str_address = edt_Address.getText().toString().trim();
        str_email = edt_Email.getText().toString().trim();
        str_mobile = edt_Mobile.getText().toString().trim();
        str_lineofwork = edt_Lineofwork.getText().toString().trim();
        str_paypalid = edt_Paypalid.getText().toString().trim();
        str_repaypalid = edt_Repaypaldi.getText().toString().trim();
        str_password = edt_Password.getText().toString().trim();
        str_repassword = edt_Repassword.getText().toString().trim();

        Log.e("str_name", str_name);
        Log.e("str_address", str_address);
        Log.e("str_email", str_email);
        Log.e("str_mobile", str_mobile);
        Log.e("str_lineofwork", str_lineofwork);
        Log.e("str_password", str_password);
        Log.e("str_repaypalid", str_repaypalid);

        if (str_name.equals("") || str_name.isEmpty() || str_name.length() == 0 || str_name == null) {
            iserror = true;

            /**************** Start Animation ****************/
        /*https://github.com/daimajia/AndroidViewAnimations/blob/master/README.md*/

            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(edt_Name);
            /**************** End Animation ****************/

            Toast.makeText(getApplicationContext(), "Please enter your Name",
                    Toast.LENGTH_SHORT).show();
        } else if (str_address.equals("")) {
            iserror = true;

            /**************** Start Animation ****************/
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(edt_Address);
            /**************** End Animation ****************/

            Toast.makeText(getApplicationContext(),
                    "Please enter your Address", Toast.LENGTH_SHORT).show();
        } else if (str_email.equals("")) {
            iserror = true;

            /**************** Start Animation ****************/
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(edt_Email);
            /**************** End Animation ****************/

            Toast.makeText(getApplicationContext(),
                    "Please enter your Email Id", Toast.LENGTH_SHORT).show();

        } else if (!isValidEmail(str_email)) {
            iserror = true;
            //	emailedit.requestFocus();
            /**************** Start Animation ****************/
            YoYo.with(Techniques.Shake)
                    .duration(700)
                    .playOn(edt_Email);
            /**************** End Animation ****************/
            Toast.makeText(getApplicationContext(),
                    "Please enter valid email address.", Toast.LENGTH_SHORT).show();

        } else if (str_mobile.equals("")) {
            iserror = true;

            /**************** Start Animation ****************/
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(edt_Mobile);
            /**************** End Animation ****************/

            Toast.makeText(getApplicationContext(),
                    "Please enter your Mobile Number", Toast.LENGTH_SHORT).show();

        } else if (str_lineofwork.equals("")) {
            iserror = true;

            /**************** Start Animation ****************/
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(edt_Lineofwork);
            /**************** End Animation ****************/

            Toast.makeText(getApplicationContext(),
                    "Please enter your Line of work", Toast.LENGTH_SHORT).show();

        } else if (str_paypalid.equals("")) {
            iserror = true;

            /**************** Start Animation ****************/
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(edt_Paypalid);
            /**************** End Animation ****************/

            Toast.makeText(getApplicationContext(),
                    "Please enter your PayPal ID", Toast.LENGTH_SHORT).show();

        }else if (!str_repaypalid.equals(str_paypalid)) {
            iserror = true;

            /**************** Start Animation ****************/
            YoYo.with(Techniques.Swing)
                    .duration(700)
                    .playOn(edt_Paypalid);

            YoYo.with(Techniques.Swing)
                    .duration(700)
                    .playOn(edt_Repaypaldi);
            /**************** End Animation ****************/

            Toast.makeText(getApplicationContext(),
                    "oopsss....\n PayPal ID not Match Please try again", Toast.LENGTH_SHORT).show();

        } else if (str_password.equals("")) {
            iserror = true;

            /**************** Start Animation ****************/
            YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(edt_Password);
            /**************** End Animation ****************/

            Toast.makeText(getApplicationContext(),
                    "Please enter your Password", Toast.LENGTH_SHORT).show();

        }  else if (str_password.length() < 5) {
            iserror = true;

            /**************** Start Animation ****************/
            YoYo.with(Techniques.Shake)
                    .duration(700)
                    .playOn(edt_Password);
            /**************** End Animation ****************/

            Toast.makeText(getApplicationContext(), "Please enter more than 5 character in password.", Toast.LENGTH_SHORT).show();
        } else if (!str_repassword.equals(str_password)) {
            iserror = true;

            /**************** Start Animation ****************/
            YoYo.with(Techniques.Swing)
                    .duration(700)
                    .playOn(edt_Password);

            YoYo.with(Techniques.Swing)
                    .duration(700)
                    .playOn(edt_Repassword);
            /**************** End Animation ****************/
            Toast.makeText(getApplicationContext(),
                    "oopsss....\n Password not Match Please try again", Toast.LENGTH_SHORT).show();
        }/*else if (str_password.equals(str_repassword))  {
            iserror = true;
            Toast.makeText(getApplicationContext(),
                    "Password not Match Please try again", Toast.LENGTH_SHORT).show();
        }*//* else {
            Intent signupss = new Intent(SignupActivity.this, MainActivity.class);
            startActivity(signupss);
            Toast.makeText(getApplicationContext(), "Registration successfull", Toast.LENGTH_SHORT).show();
        }*/
        if (!iserror) {
            if (Utils.isConnected(getApplicationContext())) {
                Registrationjsontask task = new Registrationjsontask();
                task.execute();
            } else {
                // Toast.makeText(getApplicationContext(), "Please Cheeck network conection..", Toast.LENGTH_SHORT).show();

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

    /*****************************
     * JSON TASK
     *************************************/

    public class Registrationjsontask extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            try {
                str_name = URLEncoder.encode(str_name,
                        "UTF-8");
                str_password = URLEncoder.encode(str_password,
                        "UTF-8");
                str_email = URLEncoder.encode(str_email,
                        "UTF-8");
                str_paypalid = URLEncoder.encode(str_paypalid,
                        "UTF-8");
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(HttpUrlPath.urlPath + "signup?");
            System.out.println("******************** object ********************="
                    + HttpUrlPath.urlPath + "signup?username=" + str_name +
                    "&register_id=123" + "&mobile=" + str_mobile + "&email=" + str_email +
                    "&line_work=" + str_lineofwork + "&password=" + str_password
                    + "&address=" + str_address + "&paypal_id=" + str_paypalid);

            try {

                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(
                        7);
                nameValuePairs.add(new BasicNameValuePair("username",
                        str_name));
                nameValuePairs.add(new BasicNameValuePair("email",
                        str_email));
                nameValuePairs.add(new BasicNameValuePair("password",
                        str_password));
                nameValuePairs.add(new BasicNameValuePair("mobile",
                        str_mobile));
                nameValuePairs.add(new BasicNameValuePair("line_work",
                        str_lineofwork));
                nameValuePairs.add(new BasicNameValuePair("address",
                        str_address));
                nameValuePairs.add(new BasicNameValuePair("paypal_id",
                        str_paypalid));
                post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = client.execute(post);
                String object = EntityUtils.toString(response.getEntity());
                System.out.println("************ object holds our data ************ =" + object);
                //JSONArray js = new JSONArray(object);


                JSONObject jobect = new JSONObject(object);
                result = jobect.getString("result");
                if (result.equalsIgnoreCase("successful")) {
                    username = jobect.getString("username");
                    useremail = jobect.getString("email");
                } else {
                    if (result.equalsIgnoreCase("unsuccessfully")) {
                        error1 = jobect.getString("error");
                    }
                }
            } catch (Exception e) {
                Log.e(" ******** Exception **********", "************ Exception ************" + e.getMessage());
                e.printStackTrace();
                iserror = true;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result1) {
            // TODO Auto-generated method stub
            super.onPostExecute(result1);

            //if (!iserror == false)
            if (!iserror) {
                if (result.equalsIgnoreCase("successful")) {
                    AppsConstant.editorjoint.putString("LoginSuccessfully", islogin);
                    AppsConstant.editorjoint.putString("id", userid);
                    /*AppsConstant.editorjoint.putString("username", username);
                    AppsConstant.editorjoint.putString("email", useremail);
                    AppsConstant.editorjoint.putString("useraddress", useraddress);
                    AppsConstant.editorjoint.putString("usermobile", usermobile);
                    AppsConstant.editorjoint.putString("userlineofwork", userlineofwork);*/
                    //AppsConstant.editor.putString("userprofileimage", userprofileimage);
                    AppsConstant.editorjoint.commit();
                    Toast.makeText(SignupActivity.this, "Signup Successfully ", Toast.LENGTH_SHORT).show();

                    Intent in = new Intent(SignupActivity.this, LoginActivity.class);
                    //in.putExtra("aa", "0");

                    //clear all previous running activity using FLAG_ACTIVITY_CLEAR_TOP
                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    SignupActivity.this.startActivity(in);
                    finish();

                } else {
                    Toast.makeText(getApplicationContext(), " User Already Exist with this Email\n Please try again ", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Oops!! Please check server connection .",
                        Toast.LENGTH_SHORT).show();
//
            }
        }

    }

}
