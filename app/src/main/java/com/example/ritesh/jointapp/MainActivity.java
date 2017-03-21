package com.example.ritesh.jointapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.multidex.MultiDex;
import android.support.v7.widget.CardView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.applozic.mobicomkit.Applozic;
import com.applozic.mobicomkit.api.account.register.RegistrationResponse;
import com.applozic.mobicomkit.api.account.user.PushNotificationTask;
import com.applozic.mobicomkit.api.account.user.User;
import com.applozic.mobicomkit.api.account.user.UserLoginTask;
import com.blackcat.currencyedittext.CurrencyEditText;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.ritesh.jointapp.constant.AppsConstant;
import com.example.ritesh.jointapp.constant.HttpUrlPath;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.rey.material.widget.ProgressView;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import mehdi.sakout.fancybuttons.FancyButton;

@SuppressWarnings("deprecation")
public class MainActivity extends Activity {

    private static final int PAYPAL_ACTIVITY_RESULT_CODE = 0;

    final Context mContext = this;
    /*left menu slider (Start)*/
    private LinearLayout slidingPanel;
    private boolean isExpanded;
    private int panelWidth1;
    FrameLayout.LayoutParams slidingPanelParameters;
    LinearLayout.LayoutParams headerPanelParameters;
    /*left menu slider (End)*/

    boolean iserror = false;
    String str_getuserid = " ",
            str_getTotalFriendsCount = "",
            str_getTotalFriendRequestCount = "",
            str_getName = " ",
            str_getProfileimage = " ",
            str_getCoverimage = " ",
            str_getthought = " ",
            str_getJointid = " ",
            str_getPlaceofWork = " ",
            str_getPassword = " ",
            str_getEmail = " ",
            str_getMobile = " ",
            str_getMyStory = " ",
            str_getGoalsAspiration = " ",
            str_getWebSites = " ",
            str_getAddress = " ",
            str_getLineofwork = " ",
            resumelUrl = "",
            profilephotoUrl = "",
            coverphotoUrl = "",
            result = "",
            resultPrint = "",
            singleVideoId = "",
            VideoCount = "",
            userlogid = "";
    String paypalID = "", JointlID = "", dialogJointID = "", str_joint_to_paypal = "";
    String paypalAMOUNT;
    String paypalRAmount;
    String paypalStringAMOUNT;
    //TextView username, thought, jointsid, placeofwork, goalsaspirations, websites;

    Dialog QuickTipDialog;


    //    CurrencyEditText edtpaypalAMOUNT;
    /*Butter Knife (Start)*/
    @BindView(R.id.tv_mystory)
    TextView mystory;
    @BindView(R.id.iv_profileimg)
    CircleImageView profileimg;
    @BindView(R.id.iv_coverbg)
    ImageView coverbgg;

    @BindView(R.id.profileprogressbar)
    ProgressBar profileimageprogressbar;
    @BindView(R.id.coverprogressbar)
    ProgressBar coverimageprogressbar;
    @BindView(R.id.videouploadprogressbar)
    ProgressView videouploadprogressbar;

    @BindView(R.id.tv_username)
    TextView username;
    @BindView(R.id.tv_thought)
    TextView thought;
    @BindView(R.id.tv_jointidd)
    TextView jointsid;
    @BindView(R.id.tv_placeofwork)
    TextView placeofwork;
    @BindView(R.id.tv_goal)
    TextView goalsaspirations;
    @BindView(R.id.tv_websites)
    TextView websites;

    /* this is used for showing pending friend request (Start)*/
    @BindView(R.id.friendrequesting)
    ImageView friendrquestsimage;
    @BindView(R.id.rl_rightmenu)
    RelativeLayout rlfriendrquestsimage;
    /* this is used for showing pending friend request (End)*/

    /* this is used for showinf total no. of friends (Start)*/
    @BindView(R.id.totalfriends)
    ImageView Tfriends;
    @BindView(R.id.rl_rightmenusss)
    RelativeLayout rlfriendrequest;
    /* this is used for showinf total no. of friends (End)*/

    @BindView(R.id.tv_totalfriends)
    TextView totalfriendsCount;

    @BindView(R.id.tv_totalfriends_request)
    TextView totalfriendRequestCount;

    @BindView(R.id.tv_tipme)
    TextView tipme;


    /*Butter Knife (End)*/

    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    private static final int PICK_VIDEO_ACTIVITY_REQUEST_CODE = 300;
    public static MainActivity ActivityContext = null;
    File projectVideo;
    Uri fileUri, uri;
    private AlertDialog dialogCover;

    /************************************************************************************************/
    ExpandableHeightGridView gridView;
    RelativeLayout rlgrid;
    ArrayList<String> listvideourl;
    ArrayList<String> listvideoid;
    ImageView uploadvideo;
    MainActivityCustomGridviewAdapter videoadapter;
    private int i = -1;
//    Dialog callFeeDialog;

    /************************************************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_mainactivity_profile);
        ButterKnife.bind(this);
        MultiDex.install(this);

        ActivityContext = this;
        captureVideoInitializations();

        /************************* Girdview data (Start)**************************/
//        addvideo = (ImageView) findViewById(R.id.iv_addvideo);
        rlgrid = (RelativeLayout) findViewById(R.id.rl_grid);
        gridView = (ExpandableHeightGridView) findViewById(R.id.grid);
        gridView.setAdapter(videoadapter);
        gridView.setExpanded(true);
        uploadvideo = (ImageView) findViewById(R.id.iv_videos1);

        /*videouploadprogressbar = (ProgressBar) findViewById(R.id.videouploadprogressbar);*/
        /**************************girdview data (Ends)**************************/

         /*image setting by universal image loader */
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(MainActivity.this).defaultDisplayImageOptions(defaultOptions).build();
        ImageLoader.getInstance().init(config);
        /*image settin by universal image loader */


        quickpayment();

        mystory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URLResume = resumelUrl;
                Intent intentresume = new Intent(MainActivity.this, WebviewResume.class);
                intentresume.putExtra("URL", URLResume);

                Log.e("********** URLResume ********** :", " " + resumelUrl);
                Log.e("********** URLResume ********** :", " " + resumelUrl);
                Log.e("********** URLResume ********** :", " " + resumelUrl);

                startActivity(intentresume);
            }
        });

        profileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URLProfileimage = profilephotoUrl;
                Intent intentprofileimage = new Intent(MainActivity.this, WebViewProfileImage.class);
                intentprofileimage.putExtra("URLP", URLProfileimage);

                Log.e("********** URLProfileimage ********** :", " " + profilephotoUrl);
                Log.e("********** URLProfileimage ********** :", " " + profilephotoUrl);
                Log.e("********** URLProfileimage ********** :", " " + profilephotoUrl);

                startActivity(intentprofileimage);
            }
        });

        coverbgg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URLCoverimage = coverphotoUrl;
                Intent intentcoverimage = new Intent(MainActivity.this, WebViewCoverImage.class);
                intentcoverimage.putExtra("URLC", URLCoverimage);

                Log.e("********** URLCoverimage ********** :", " " + coverphotoUrl);
                Log.e("********** URLCoverimage ********** :", " " + coverphotoUrl);
                Log.e("********** URLCoverimage ********** :", " " + coverphotoUrl);

                startActivity(intentcoverimage);
            }
        });


        Tfriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signups = new Intent(MainActivity.this, FriendsList.class);
                Log.e(" TOTAL FRIENDS image view clicked ", "Yes");
                Log.e(" TOTAL FRIENDS image view clicked ", "Yes");
                Log.e(" TOTAL FRIENDS image view clicked ", "Yes");
                startActivity(signups);
            }
        });

        rlfriendrequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signups = new Intent(MainActivity.this, FriendsList.class);
                Log.e(" TOTAL FRIENDS inner relative layout clicked ", "Yes");
                Log.e(" TOTAL FRIENDS inner relative layout clicked ", "Yes");
                Log.e(" TOTAL FRIENDS inner relative layout clicked ", "Yes");
                startActivity(signups);
            }
        });


        friendrquestsimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signups = new Intent(MainActivity.this, NotificationUserList.class);
                Log.e(" TOTAL FRIENDS request image view clicked ", "Yes");
                Log.e(" TOTAL FRIENDS request image view clicked ", "Yes");
                Log.e(" TOTAL FRIENDS request image view clicked ", "Yes");
                startActivity(signups);
            }
        });
        rlfriendrquestsimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signups = new Intent(MainActivity.this, NotificationUserList.class);
                Log.e(" TOTAL FRIENDS request inner relative layout clicked ", "Yes");
                Log.e(" TOTAL FRIENDS request inner relative layout clicked ", "Yes");
                Log.e(" TOTAL FRIENDS request    inner relative layout clicked ", "Yes");
                startActivity(signups);
            }
        });

        tipme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog callFeeDialog = new Dialog(MainActivity.this);
//                callFeeDialog = new Dialog(MainActivity.this);
                callFeeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                callFeeDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                callFeeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                callFeeDialog.setContentView(R.layout.activity_paypal_dialog);
//                final EditText edtPayPalID = new EditText(MainActivity.this);
                final EditText edtPayPalID = (EditText) callFeeDialog.findViewById(R.id.edt_paypalid);
                final CurrencyEditText edtpaypalAMOUNT = (CurrencyEditText) callFeeDialog.findViewById(R.id.edt_paypalamount);
//                paypalID = edtPayPalID.getText().toString().trim();
//                paypalAMOUNT = edtpaypalAMOUNT.formatCurrency(Long.toString(edtpaypalAMOUNT.getRawValue()));

                FancyButton btncancel = (FancyButton) callFeeDialog.findViewById(R.id.btn_paypalcancel);
                FancyButton btndone = (FancyButton) callFeeDialog.findViewById(R.id.btn_paypaldone);


//                Button btncancel = (Button) callFeeDialog.findViewById(R.id.btn_paypalcancel);
//                Button btndone = (Button) callFeeDialog.findViewById(R.id.btn_paypaldone);


                btncancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callFeeDialog.dismiss();
                    }
                });


                btndone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        paypalID = edtPayPalID.getText().toString().trim();
                        paypalAMOUNT = edtpaypalAMOUNT.formatCurrency(Long.toString(edtpaypalAMOUNT.getRawValue()));

                        Log.e("paypalID :", "\t" + "" + paypalID
                                + "\n" + "paypalAMOUNT :" + "\t" + "" + paypalAMOUNT);

                        Log.e("paypalAMOUNT None replaced Amount :", "" + paypalAMOUNT);
                        Log.e("paypalAMOUNT None replaced Amount :", "" + paypalAMOUNT);
                        Log.e("paypalAMOUNT None replaced Amount :", "" + paypalAMOUNT);

                        if (paypalID.equals("")) {
                            /**************** Start Animation ****************/
                            YoYo.with(Techniques.Tada)
                                    .duration(700)
                                    .playOn(edtPayPalID);
                            /**************** End Animation ****************/

                            Toast.makeText(MainActivity.this, "Please Enter PayPal ID", Toast.LENGTH_SHORT).show();
                        } /*else if (!isValidEmail(paypalID)) {
                            //	emailedit.requestFocus();
//                            **************** Start Animation ****************
                            YoYo.with(Techniques.Shake)
                                    .duration(700)
                                    .playOn(edtPayPalID);
//                            **************** End Animation ****************
                            Toast.makeText(getApplicationContext(),
                                    "Please enter valid PayPal ID", Toast.LENGTH_SHORT).show();

                        }*/ else if (paypalAMOUNT.equals("$0.00")) {

                            Toast.makeText(MainActivity.this, "Amount Should be greater than 0", Toast.LENGTH_SHORT).show();

                        } else if (paypalAMOUNT.equals("£0.00")) {

                            Toast.makeText(MainActivity.this, "Amount Should be greater than 0", Toast.LENGTH_SHORT).show();

                        } else if (paypalAMOUNT.equals("¥0.00")) {

                            Toast.makeText(MainActivity.this, "Amount Should be greater than 0", Toast.LENGTH_SHORT).show();

                        } else if (paypalAMOUNT.equals("€0.00")) {

                            Toast.makeText(MainActivity.this, "Amount Should be greater than 0", Toast.LENGTH_SHORT).show();

                        } else if (paypalAMOUNT.equals("₹0.00")) {

                            Toast.makeText(MainActivity.this, "Amount Should be greater than 0", Toast.LENGTH_SHORT).show();

                        } else {

                            new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                                    .setTitleText("Are you sure?")
                                    .setContentText("We are not responsible for any wrong Payment.")
                                    .setCancelText("Cancel")
                                    .setConfirmText("Confirm")
                                    .showCancelButton(true)
                                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {
                                            // reuse previous dialog instance, keep widget user state, reset them if you need
                                            sDialog.setTitleText("Cancelled!")
                                                    .setContentText("Your Money is safe :)")
                                                    .setConfirmText("OK")
                                                    .showCancelButton(false)
                                                    .setCancelClickListener(null)
                                                    .setConfirmClickListener(null)
                                                    .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                            callFeeDialog.dismiss();


                                        }

                                    })
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sDialog) {

                                            paypalRAmount = paypalAMOUNT.replaceAll("[\\£\\¥\\€\\₹\\$,]", "");
                                            Log.e("Replace Amount :", "" + paypalRAmount);
                                            Log.e("Replace Amount :", "" + paypalRAmount);
                                            Log.e("Replace Amount :", "" + paypalRAmount);

                                            Log.e(" ConfirmClick paypalID :", "\t" + "" + paypalID
                                                    + "\n" + " ConfirmClick AMOUNT :" + "\t" + "" + paypalRAmount);
                                            Log.e(" ConfirmClick paypalID :", "\t" + "" + paypalID
                                                    + "\n" + " ConfirmClick AMOUNT :" + "\t" + "" + paypalRAmount);
                                            Log.e(" ConfirmClick paypalID :", "\t" + "" + paypalID
                                                    + "\n" + " ConfirmClick AMOUNT :" + "\t" + "" + paypalRAmount);
                                            Log.e(" ConfirmClick paypalID :", "\t" + "" + paypalID
                                                    + "\n" + " ConfirmClick AMOUNT :" + "\t" + "" + paypalRAmount);

                                            Intent payintent = new Intent(MainActivity.this, PaypalWebviewActivity.class);
                                            Bundle paypalbundle = new Bundle();
//                                            paypalbundle.putString("paypalID", paypalID);
                                            paypalbundle.putString("paypalID", "test@gmail.com");
                                            paypalbundle.putString("PaymentAmount", paypalRAmount);
                                            payintent.putExtras(paypalbundle);
                                            startActivity(payintent);
                                            callFeeDialog.dismiss();
                                            sDialog.dismiss();

                                        }
                                    })
                                    .show();
                        }


                    }
                });

                callFeeDialog.show();

            }
        });


        AppsConstant.jointpreference = getSharedPreferences(AppsConstant.MyPREFERENCES, Context.MODE_PRIVATE);
        userlogid = AppsConstant.jointpreference.getString("id", "");
        Log.e("**************USER LOGIN id***********:", "" + userlogid);

        if (Utils.isConnected(getApplicationContext())) {
            SignupData task = new SignupData();
            task.execute();
            Log.e("Signupdata Task running", "YES");
            Log.e("Signupdata Task running", "YES");
            Log.e("Signupdata Task running", "YES");
        } else {
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

        if (Utils.isConnected(getApplicationContext())) {
            TotalFriendsCount task = new TotalFriendsCount();
            task.execute();
            Log.e("FriendsCount Task running", "YES");
            Log.e("FriendsCount Task running", "YES");
            Log.e("FriendsCount Task running", "YES");
        } else {
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


        if (Utils.isConnected(getApplicationContext())) {
            TotalFriendsRequestCount task = new TotalFriendsRequestCount();
            task.execute();
            Log.e("FriendsCount Task running", "YES");
            Log.e("FriendsCount Task running", "YES");
            Log.e("FriendsCount Task running", "YES");
        } else {
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


        /* ** ** ** ** ** ** ** ** **left menu slider (Start)** ** ** ** ** ** ** ** ** */
        DisplayMetrics metrics;
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        panelWidth1 = (int) ((metrics.widthPixels) * 0.75);

        RelativeLayout headerPanel;
        headerPanel = (RelativeLayout) findViewById(R.id.header);
        headerPanelParameters = (LinearLayout.LayoutParams) headerPanel
                .getLayoutParams();
        headerPanelParameters.width = metrics.widthPixels;
        headerPanel.setLayoutParams(headerPanelParameters);

        slidingPanel = (LinearLayout) findViewById(R.id.slidingPanel);
        slidingPanelParameters = (FrameLayout.LayoutParams) slidingPanel
                .getLayoutParams();
        slidingPanelParameters.width = metrics.widthPixels;
        slidingPanel.setLayoutParams(slidingPanelParameters);

        // Slide the Panel
        ImageView menuViewButton;
        menuViewButton = (ImageView) findViewById(R.id.menuViewButton);
        menuViewButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!isExpanded) {
                    isExpanded = true;
                    // Expand

                    FragmentManager fragmentManager = getFragmentManager();

                    FragmentTransaction fragmentTransaction = fragmentManager
                            .beginTransaction();
                    fragmentTransaction.replace(R.id.menuPanel,
                            new LeftMenuFragment());
                    fragmentTransaction.commit();
                    new OpenAnimation(slidingPanel, panelWidth1,
                            Animation.RELATIVE_TO_SELF, 0.0f,
                            Animation.RELATIVE_TO_SELF, 0.75f, 0, 0.0f, 0, 0.0f);

                } else {
                    isExpanded = false;
                    // Collapse

                    new ClosedAnimation(slidingPanel, panelWidth1,
                            TranslateAnimation.RELATIVE_TO_SELF, 0.75f,
                            TranslateAnimation.RELATIVE_TO_SELF, 0.0f, 0, 0.0f,
                            0, 0.0f);
                }
            }
        });
        /* ** ** ** ** ** ** ** ** ** left menu slider (End) ** ** ** ** ** ** ** ** ** */

    }

    private void quickpayment() {
        QuickTipDialog = new Dialog(MainActivity.this);
//                callFeeDialog = new Dialog(MainActivity.this);
        QuickTipDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        QuickTipDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        QuickTipDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        QuickTipDialog.setContentView(R.layout.activity_quicktip_dialog);
        final EditText edtJointID = (EditText) QuickTipDialog.findViewById(R.id.edt_joint_id);
        final CurrencyEditText edtpaypalAMOUNT = (CurrencyEditText) QuickTipDialog.findViewById(R.id.edt_tip_amout);
        CardView btncancel = (CardView) QuickTipDialog.findViewById(R.id.btn_quick_tip_cancel);
        CardView btndone = (CardView) QuickTipDialog.findViewById(R.id.btn_quick_tip_done);


        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QuickTipDialog.dismiss();
            }
        });


        btndone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JointlID = edtJointID.getText().toString().trim();
                paypalAMOUNT = edtpaypalAMOUNT.formatCurrency(Long.toString(edtpaypalAMOUNT.getRawValue()));

                Log.e("paypalID :", "\t" + "" + JointlID
                        + "\n" + "paypalAMOUNT :" + "\t" + "" + paypalAMOUNT);

                Log.e("paypalAMOUNT None replaced Amount :", "" + paypalAMOUNT);
                Log.e("paypalAMOUNT None replaced Amount :", "" + paypalAMOUNT);
                Log.e("paypalAMOUNT None replaced Amount :", "" + paypalAMOUNT);

                if (JointlID.equals("")) {
                    /**************** Start Animation ****************/
                    YoYo.with(Techniques.Tada)
                            .duration(700)
                            .playOn(edtJointID);
                    /**************** End Animation ****************/

                    Toast.makeText(MainActivity.this, "Please Enter TipMe ID", Toast.LENGTH_SHORT).show();
                } else if (paypalAMOUNT.equals("$0.00")) {

                    Toast.makeText(MainActivity.this, "Amount Should be greater than 0", Toast.LENGTH_SHORT).show();

                } else if (paypalAMOUNT.equals("£0.00")) {

                    Toast.makeText(MainActivity.this, "Amount Should be greater than 0", Toast.LENGTH_SHORT).show();

                } else if (paypalAMOUNT.equals("¥0.00")) {

                    Toast.makeText(MainActivity.this, "Amount Should be greater than 0", Toast.LENGTH_SHORT).show();

                } else if (paypalAMOUNT.equals("€0.00")) {

                    Toast.makeText(MainActivity.this, "Amount Should be greater than 0", Toast.LENGTH_SHORT).show();

                } else if (paypalAMOUNT.equals("₹0.00")) {

                    Toast.makeText(MainActivity.this, "Amount Should be greater than 0", Toast.LENGTH_SHORT).show();

                } else {

                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Are you sure?")
                            .setContentText("We are not responsible for any wrong Payment.")
                            .setCancelText("Cancel")
                            .setConfirmText("Confirm")
                            .showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog quickDialog) {
                                    // reuse previous dialog instance, keep widget user state, reset them if you need
                                    quickDialog.setTitleText("Cancelled!")
                                            .setContentText("Your Money is safe :)")
                                            .setConfirmText("OK")
                                            .showCancelButton(false)
                                            .setCancelClickListener(null)
                                            .setConfirmClickListener(null)
                                            .changeAlertType(SweetAlertDialog.ERROR_TYPE);
                                    QuickTipDialog.dismiss();


                                }

                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog quickDialog) {
                                    paypalRAmount = paypalAMOUNT.replaceAll("[\\£\\¥\\€\\₹\\$,]", "");
                                    Log.e("Replace Amount :", "" + paypalRAmount);
                                    Log.e("Replace Amount :", "" + paypalRAmount);
                                    Log.e("Replace Amount :", "" + paypalRAmount);

                                    Log.e(" ConfirmClick paypalID :", "\t" + "" + paypalID
                                            + "\n" + " ConfirmClick AMOUNT :" + "\t" + "" + paypalRAmount);
                                    Log.e(" ConfirmClick paypalID :", "\t" + "" + paypalID
                                            + "\n" + " ConfirmClick AMOUNT :" + "\t" + "" + paypalRAmount);
                                    Log.e(" ConfirmClick paypalID :", "\t" + "" + paypalID
                                            + "\n" + " ConfirmClick AMOUNT :" + "\t" + "" + paypalRAmount);
                                    Log.e(" ConfirmClick paypalID :", "\t" + "" + paypalID
                                            + "\n" + " ConfirmClick AMOUNT :" + "\t" + "" + paypalRAmount);

                                    if (Utils.isConnected(getApplicationContext())) {
                                        JointidTOPayPalid task = new JointidTOPayPalid();
                                        task.execute();
                                        Log.e("Joint To PayPal ID Task running", "YES");
                                        Log.e("Joint To PayPal ID Task running", "YES");
                                        Log.e("Joint To PayPal ID Task running", "YES");

                                    } else {
                                        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                                        View promptView = layoutInflater.inflate(R.layout.activity_wificheck_customalert, null);
                                        final AlertDialog alertD = new AlertDialog.Builder(MainActivity.this).create();
                                        alertD.setCanceledOnTouchOutside(false);
                                        alertD.setCancelable(false);
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


                                    paypalRAmount = paypalAMOUNT.replaceAll("[\\£\\¥\\€\\₹\\$,]", "");
                                    Log.e("Replace Amount :", "" + paypalRAmount);
                                    Log.e("Replace Amount :", "" + paypalRAmount);
                                    Log.e("Replace Amount :", "" + paypalRAmount);

                                    Log.e(" ConfirmClick paypalID :", "\t" + "" + paypalID
                                            + "\n" + " ConfirmClick AMOUNT :" + "\t" + "" + paypalRAmount);
                                    Log.e(" ConfirmClick paypalID :", "\t" + "" + paypalID
                                            + "\n" + " ConfirmClick AMOUNT :" + "\t" + "" + paypalRAmount);
                                    Log.e(" ConfirmClick paypalID :", "\t" + "" + paypalID
                                            + "\n" + " ConfirmClick AMOUNT :" + "\t" + "" + paypalRAmount);
                                    Log.e(" ConfirmClick paypalID :", "\t" + "" + paypalID
                                            + "\n" + " ConfirmClick AMOUNT :" + "\t" + "" + paypalRAmount);

                                    Intent payintent = new Intent(MainActivity.this, PaypalWebviewActivity.class);
                                    Bundle paypalbundle = new Bundle();
                                    paypalbundle.putString("paypalID", paypalID);
                                    paypalbundle.putString("PaymentAmount", paypalRAmount);
                                    payintent.putExtras(paypalbundle);
                                    startActivity(payintent);
                                    QuickTipDialog.dismiss();
                                    quickDialog.dismiss();

                                }
                            })
                            .show();
                }


            }
        });

        QuickTipDialog.show();
    }

    /**
     * Called when the activity has become visible.
     */
    @Override
    protected void onResume() {
        super.onResume();

        SignupData taskkk = new SignupData();
        taskkk.execute();
        Log.e("onResume called", "YES");
        Log.e("onResume called", "YES");
        Log.e("onResume called", "YES");
    }

    /**
     * Called when another activity is taking focus.
     */
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("onPause called", "YES");
        Log.e("onPause called", "YES");
        Log.e("onPause called", "YES");
    }

    /*********************
     * gridview adapter (Start)
     *********************/
    public class MainActivityCustomGridviewAdapter extends BaseAdapter {
        Context c;
        // int positionSelected = 0;
        private int positionSelected;
        private int position = 1;

        public MainActivityCustomGridviewAdapter(Context con) {
            c = con;
        }

        @Override
        public int getCount() {
            return listvideoid.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = getLayoutInflater();
            View row;
            row = inflater.inflate(R.layout.activity_userprofile_videogridview_items, parent, false);
            final VideoView videoView = (VideoView) row.findViewById(R.id.grid_video);
            final ImageView imageView = (ImageView) row.findViewById(R.id.grid_image);

            uri = Uri.parse(listvideourl.get(position));
            // create an object of media controller
            final MediaController mediaController = new MediaController(MainActivity.this);
            // set media controller object for a video view
            mediaController.setAnchorView(videoView);

            /*gridview video count and display on toast (Start)*/
            if (gridView != null) {
                gridView.getAdapter().getCount();
                /*Toast.makeText(getApplicationContext(), "Total number of Videos are:" +
                        gridView.getAdapter().getCount(), Toast.LENGTH_SHORT).show();*/

                /*friends.setText(gridView.getAdapter().getCount());*/

                Log.e(" ************** Video Count **************", "" + gridView.getAdapter().getCount());
                Log.e(" ************** Video Count **************", "" + gridView.getAdapter().getCount());
                Log.e(" ************** Video Count **************", "" + gridView.getAdapter().getCount());

            } else {
                Toast.makeText(getApplicationContext(), "Total number of Videos are: 0"
                        , Toast.LENGTH_SHORT).show();
                Log.e(" ************** Video Count **************", "" + " 0 " + gridView.getAdapter().getCount());
                Log.e(" ************** Video Count **************", "" + " 0 " + gridView.getAdapter().getCount());
                Log.e(" ************** Video Count **************", "" + " 0 " + gridView.getAdapter().getCount());
            }

            /*gridview video count and display on toast (End)*/

            /* handle can't play this video dialog (Start)*/
            videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {

                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    Log.e("#################### videoView ####################", "setOnErrorListener can't play this video ");
                    Log.e("#################### videoView ####################", "setOnErrorListener can't play this video ");
                    Log.e("#################### videoView ####################", "setOnErrorListener can't play this video ");
                    return true;
                }
            });
            /* handle can't play this video dialog (End)*/

            videoView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(listvideourl.get(position)));
                    intent.setDataAndType(Uri.parse(listvideourl.get(position)), "video/*");
                    /*Toast.makeText(MainActivity.this, "You clicked on position : "
                            + position + "\n", Toast.LENGTH_SHORT).show();*/
                    Log.e(" **************** ITEM CLICKED ****************", "" + position);
                    Log.e(" **************** ITEM CLICKED ****************", "" + position);
                    Log.e(" **************** ITEM CLICKED ****************", "" + position);
                    Log.e(" **************** ITEM CLICKED ****************", "" + listvideourl.get(position));
                    Log.e(" **************** ITEM CLICKED ****************", "" + listvideourl.get(position));
                    Log.e(" **************** ITEM CLICKED ****************", "" + listvideourl.get(position));
                    Log.e(" **************** ITEM CLICKED with id****************", "" + listvideoid.get(position));
                    Log.e(" **************** ITEM CLICKED with id****************", "" + listvideoid.get(position));
                    Log.e(" **************** ITEM CLICKED with id****************", "" + listvideoid.get(position));

                    startActivity(intent);
                    return false;
                }
            });

            if (position == 0) {
                videoView.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);

                Log.e(" ********** SET imageView IN 0 POSITION **********", "YES");
                Log.e(" ********** SET imageView IN 0 POSITION **********", "YES");
                Log.e(" ********** SET imageView IN 0 POSITION **********", "YES");

            } else {
                videoView.setVisibility(View.VISIBLE);
                imageView.setVisibility(View.GONE);

                Log.e(" ********** SET imageView IN 0 POSITION **********", "NO");
                Log.e(" ********** SET imageView IN 0 POSITION **********", "NO");
                Log.e(" ********** SET imageView IN 0 POSITION **********", "NO");
            }

            videoView.setVideoURI(uri);
            Log.e(" ********** VideoURI ********** ", " " + uri);
            videoView.setFocusable(false);
            videoView.seekTo(1);

            /********************* CONDITION FOR GRIDE VIEW ON CLICK EVENT ********************/
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (position) {
                        case 0:
                            if (position == 0) {
                                dialogCover.show();
                                gridView.setLongClickable(false);
                                /*Toast.makeText(MainActivity.this, "You clicked on position : "
                                        + position + "\n", Toast.LENGTH_SHORT).show();*/
                                Log.e(" **************** ITEM CLICKED ****************", "" + position);
                            } else {
                                /*Toast.makeText(MainActivity.this, "You clicked on position : "
                                        + position, Toast.LENGTH_SHORT).show();*/
                                Log.e("**************** ITEM CLICKED ****************", "" + position);
                            }

                            break;
                    }
                }
            });

            gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    singleVideoId = listvideoid.get(position);
                    gridView.setClickable(false);

                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Are you sure?")
                            .setContentText("Won't be able to recover this Video!")
                            .setCancelText("No,cancel !")
                            .setConfirmText("Yes,delete it!")
                            .showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    // reuse previous dialog instance, keep widget user state, reset them if you need
                                    sDialog.setTitleText("Cancelled!")
                                            .setContentText("Your Video is safe :)")
                                            .setConfirmText("OK")
                                            .showCancelButton(false)
                                            .setCancelClickListener(null)
                                            .setConfirmClickListener(null)
                                            .changeAlertType(SweetAlertDialog.ERROR_TYPE);

                                    // or you can new a SweetAlertDialog to show
                               /* sDialog.dismiss();
                                new SweetAlertDialog(SampleActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Cancelled!")
                                        .setContentText("Your imaginary file is safe :)")
                                        .setConfirmText("OK")
                                        .show();*/
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {


                                @Override
                                public void onClick(SweetAlertDialog sDialog) {

                                    if (Utils.isConnected(getApplicationContext())) {
                                        DeleteVideo task = new DeleteVideo();
                                        task.execute();
                                        Log.e("Signupdata Task running", "YES");
                                        Log.e("Signupdata Task running", "YES");
                                    } else {
                                        LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                                        View promptView = layoutInflater.inflate(R.layout.activity_wificheck_customalert, null);
                                        final AlertDialog alertD = new AlertDialog.Builder(MainActivity.this).create();
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
                                    sDialog.setTitleText("Deleted!")
                                            .setContentText("Your Video has been deleted!")
                                            .setConfirmText("OK")
                                            .showCancelButton(false)
                                            .setCancelClickListener(null)
                                            .setConfirmClickListener(null)
                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                }
                            })
                            .show();

                    /*listvideoid.remove(position);
                    videoadapter.notifyDataSetChanged();*/
                    Log.e("****************Item Deleted id ****************:", "" + singleVideoId);
                    Log.e("****************Item Deleted id ****************:", "" + singleVideoId);
                    Log.e("****************Item Deleted id ****************:", "" + singleVideoId);
                    Log.e("****************Item Deleted id ****************:", "" + singleVideoId);
         /*           Log.e("Item Deleted id :", "" + listvideoid.get(position));
                    Log.e("Item Deleted id :", "" + listvideoid.get(position));*/

                    /*Toast.makeText(MainActivity.this, "Item Deleted", Toast.LENGTH_SHORT).show();*/
                    return true;
                }
            });
            /********************* CONDITION FOR GRIDE VIEW ON CLICK EVENT ********************/

            rlgrid.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });

            return row;
        }
    }

    /*********************
     * gridview adapter (End)
     **********************/

    public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
                    .matches();
        }
    }

    class SignupData extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // videouploadprogressbar.setVisibility(View.VISIBLE);
            videouploadprogressbar.start();
            listvideourl = new ArrayList<String>();
            listvideoid = new ArrayList<String>();


        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(HttpUrlPath.urlPath + "get_user?user_id=" + userlogid);

            try {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("id", userlogid));
                post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = client.execute(post);
                String obj = EntityUtils.toString(response.getEntity());

/* ********** ********** ********** ********** ********** PRINT ALL INCOMING DATA (START)********** ********** ********** ********** ********** */
                System.out.println("########## OBJECT VALUE ##########" + obj);
                Log.e("************ All INCOMING DATA VALUE ************", "" + obj);
/* ********** ********** ********** ********** ********** PRINT ALL INCOMING DATA (END)********** ********** ********** ********** ********** */

/* ********** ********** ********** ********** ********** ALL INCOMING VIDEO DATA (START) ********** ********** ********** ********** ********** */
                JSONObject ParentObject = new JSONObject(obj);
                String alldata = ParentObject.getString("all_data");
                String videodata = ParentObject.getString("video_data");
/* ********** ********** ********** ********** ********** ALL INCOMING VIDEO DATA (END) ********** ********** ********** ********** ********** */

                Log.e("************ All INCOMING VIDEO DATA + ALL DATA ************", " " + videodata + "\n\n   " + alldata);

/* ********** ********** ********** ********** ********** SET ALL INCOMING VIDEO DATA (START) ********** ********** ********** ********** ********** */
                JSONArray jaaray = new JSONArray(videodata);
                Log.e("************Json Video data*******************", " " + videodata);
                for (int i = 0; i < jaaray.length(); i++) {
                    String videoresult = jaaray.getJSONObject(i).getString("video_result");
                    if (videoresult.equalsIgnoreCase("success")) {
                        String videoid = jaaray.getJSONObject(i).getString("video_id");
                        String videourl = jaaray.getJSONObject(i).getString("video");
                        listvideoid.add(videoid);
                        // String videoidlist = listvideoid.get(i);
                        Log.e(" ********** listvideoid **********", "" + videoid);
                        listvideourl.add(videourl);
                        // String videourllist = listvideourl.get(i);
                        Log.e(" ********** listvideourl **********", "" + videourl);
                    }
                }
/* ********** ********** ********** ********** ********** SET ALL INCOMING VIDEO DATA (END) ********** ********** ********** ********** ********** */

                JSONObject Object1 = new JSONObject(alldata);
                result = Object1.getString("result");
                Log.e("************Json All data*******************", "" + alldata + " " + videodata);
                if (result.equalsIgnoreCase("successful")) {
                    str_getuserid = Object1.getString("id");
                    str_getName = Object1.getString("username");
                    str_getMobile = Object1.getString("mobile");
                    str_getJointid = Object1.getString("joint_id");
                    str_getAddress = Object1.getString("address");
                    str_getLineofwork = Object1.getString("line_work");
                    str_getPlaceofWork = Object1.getString("place_work");
                    str_getPassword = Object1.getString("password");
                    str_getEmail = Object1.getString("email");
                    str_getMyStory = Object1.getString("story");
                    str_getthought = Object1.getString("thought");
                    resumelUrl = Object1.getString("story");
                    str_getGoalsAspiration = Object1.getString("goals");
                    str_getWebSites = Object1.getString("website");
                    str_getProfileimage = Object1.getString("image");
                    profilephotoUrl = Object1.getString("image");
                    str_getCoverimage = Object1.getString("cover_img");
                    coverphotoUrl = Object1.getString("cover_img");
                } else {
                    if (result.equalsIgnoreCase("unsuccessfully")) {
                        Toast.makeText(MainActivity.this, "unsuccessfully  ", Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                System.out.println("Errror in gettting by ID" + e);
                Log.e("22", "22" + e.getMessage());
                e.printStackTrace();
                iserror = true;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String mystring) {
            super.onPostExecute(mystring);
            //  videouploadprogressbar.setVisibility(View.GONE);
            videouploadprogressbar.stop();

            if (result.equalsIgnoreCase("successful")) {


                Log.e("MainActivity applozic user login for ONSUCCESS :", "OK");
                Log.e(" Joint ID :", "" + str_getJointid +
                "\t" + "User Name :" + "" + str_getName + "\t" + "User Email Id :" + "" + str_getEmail);
                Log.e(" Joint ID :", "" + str_getJointid +
                "\t" + "User Name :" + "" + str_getName + "\t" + "User Email Id :" + "" + str_getEmail);

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


                    }

                    @Override
                    public void onFailure(RegistrationResponse registrationResponse, Exception exception) {
                        //If any failure in registration the callback  will come here
                    }};

                User user = new User();
                user.setUserId(str_getJointid); //userId it can be any unique user identifier
                user.setDisplayName(str_getName); //displayName is the name of the user which will be shown in chat messages
                user.setEmail(str_getEmail); //optional
                user.setAuthenticationTypeId(User.AuthenticationType.APPLOZIC.getValue());  //User.AuthenticationType.APPLOZIC.getValue() for password verification from Applozic server and User.AuthenticationType.CLIENT.getValue() for access Token verification from your server set access token as password
                user.setPassword(""); //optional, leave it blank for testing purpose, read this if you want to add additional security by verifying password from your server https://www.applozic.com/docs/configuration.html#access-token-url
                user.setImageLink(profilephotoUrl);//optional,pass your image link
                new UserLoginTask(user, listener, MainActivity.this).execute((Void) null);







                if (listvideoid.size() > 0) {
                    Log.e(" ********** listsize ********** ", "" + listvideoid);
                    uploadvideo.setVisibility(View.GONE);
                    videoadapter = new MainActivityCustomGridviewAdapter(MainActivity.this);
                    videoadapter.notifyDataSetChanged();
                    gridView.invalidateViews();
                    videoadapter.notifyDataSetInvalidated();
                    gridView.setAdapter(videoadapter);


                    Log.e(" ********** listvideoid.size() > 0 **********", "YES");
                    Log.e(" ********** listvideoid.size() > 0 **********", "YES");
                    Log.e(" ********** listvideoid.size() > 0 **********", "YES");
                } else {
                    uploadvideo.setVisibility(View.VISIBLE);
                    uploadvideo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Log.e(" ********** UPLOADVIDEO CLICKED **********", "YES");
                            Log.e(" ********** UPLOADVIDEO CLICKED **********", "YES");
                            Log.e(" ********** UPLOADVIDEO CLICKED **********", "YES");

                            dialogCover.show();
                            Log.e(" ********** listvideoid.size() == 0 **********", "YES");
                            Log.e(" ********** listvideoid.size() == 0 **********", "YES");
                            Log.e(" ********** listvideoid.size() == 0 **********", "YES");
                        }
                    });
                }

                username.setText(str_getName);
                Log.e("value of str_getName: ", "" + str_getName);

                jointsid.setText(str_getJointid);
                Log.e("value of str_getJointid: ", "" + str_getJointid);

                placeofwork.setText(str_getPlaceofWork);
                Log.e("value of str_getPlaceofWork: ", "" + str_getPlaceofWork);

                mystory.setText(str_getMyStory.substring(str_getMyStory.lastIndexOf("/") + 1));
                Log.e("value of str_getMyStory: ", "" + str_getMyStory);

                goalsaspirations.setText(str_getGoalsAspiration);
                Log.e("value of str_getGoalsAspiration: ", "" + str_getGoalsAspiration);

                websites.setText(str_getWebSites);
                Log.e("value of str_getWebSites: ", "" + str_getWebSites);

                if (str_getthought.equalsIgnoreCase("")) {
                    thought.setText("User Thought");
                } else {
                    thought.setText(str_getthought);
                    Log.e("value of str_getthought: ", "" + str_getthought);

                }


            /*set profile image*/
                ImageLoader imageLoader = ImageLoader.getInstance();
                imageLoader.getInstance().displayImage(str_getProfileimage, profileimg, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        profileimageprogressbar.setVisibility(View.VISIBLE);

                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        profileimageprogressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        profileimageprogressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        profileimageprogressbar.setVisibility(View.GONE);

                    }
                });

                imageLoader.getInstance().displayImage(str_getCoverimage, coverbgg, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String imageUri, View view) {
                        coverimageprogressbar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                        coverimageprogressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                        coverimageprogressbar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingCancelled(String imageUri, View view) {
                        coverimageprogressbar.setVisibility(View.GONE);
                    }
                });
            /*set profile image*/

            }
        }

    }

    class TotalFriendsCount extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.e("  +++++++++++ TotalFriendsCount AsyncTask START +++++++++++:", "YES");
            Log.e("  +++++++++++ TotalFriendsCount AsyncTask START +++++++++++:", "YES");
            Log.e("  +++++++++++ TotalFriendsCount AsyncTask START +++++++++++:", "YES");
        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(HttpUrlPath.urlPath + "friend_count?user_id=" + userlogid);

            try {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("id", userlogid));
                post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = client.execute(post);
                String objfriend = EntityUtils.toString(response.getEntity());

                Log.e("************Friends count Data*******", objfriend);
                Log.e("************Friends count Data*******", objfriend);
                Log.e("************Friends count Data*******", objfriend);

                JSONObject ParentObject = new JSONObject(objfriend);

                result = ParentObject.getString("result");

                if (result.equalsIgnoreCase("successful")) {
                    Log.e("************ Friends JSON DATA*******************", String.valueOf(ParentObject));
                    Log.e("************ Friends JSON DATA*******************", String.valueOf(ParentObject));
                    Log.e("************ Friends JSON DATA*******************", String.valueOf(ParentObject));
                    str_getTotalFriendsCount = ParentObject.getString("count");
                    Log.e(" ++++++++++++ Friends count value ++++++++++++ ", "" + str_getTotalFriendsCount);
                    Log.e(" ++++++++++++ Friends count value ++++++++++++ ", "" + str_getTotalFriendsCount);
                    Log.e(" ++++++++++++ Friends count value ++++++++++++ ", "" + str_getTotalFriendsCount);
//                    str_getVideos = ParentObject.getString("video");

                } else {
                    Log.e(" ???????????????? Error getting friend count ????????????????:", "" + result);
                    Log.e(" ???????????????? Error getting friend count ????????????????:", "" + result);
                    Log.e(" ???????????????? Error getting friend count ????????????????:", "" + result);
                }
            } catch (Exception e) {
                System.out.println("Errror in gettting by ID" + e);

            }
            return result;
        }

        @Override
        protected void onPostExecute(String mystring) {
            super.onPostExecute(mystring);

            totalfriendsCount.setText(str_getTotalFriendsCount);
            Log.e("value of str_getFriendsCount: ", "" + str_getTotalFriendsCount);
        }

    }


    class JointidTOPayPalid extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.e(" JointlID ; ", "" + JointlID);
            Log.e(" JointlID ; ", "" + JointlID);
            Log.e("  +++++++++++ JointidTOPayPalid AsyncTask START +++++++++++:", "YES");
            Log.e("  +++++++++++ JointidTOPayPalid AsyncTask START +++++++++++:", "YES");
        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(HttpUrlPath.urlPath + "/get_user_paypal_id?joint_id=" + JointlID);

            try {
                /*List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("joint_id", "1472"));
                post.setEntity(new UrlEncodedFormEntity(nameValuePairs));*/
                HttpResponse response = client.execute(post);
                String objfriend = EntityUtils.toString(response.getEntity());

                Log.e("************JointidTOPayPalid  Data*******", objfriend);
                Log.e("************JointidTOPayPalid  Data*******", objfriend);

                JSONObject ParentObject = new JSONObject(objfriend);

                result = ParentObject.getString("result");

                if (result.equalsIgnoreCase("successful")) {
                    Log.e("************ JointidTOPayPalid JSON DATA*******************", String.valueOf(ParentObject));
                    Log.e("************ JointidTOPayPalid JSON DATA*******************", String.valueOf(ParentObject));
                    str_joint_to_paypal = ParentObject.getString("paypal_id");
                    Log.e(" ++++++++++++ JointidTOPayPalid count value ++++++++++++ ", "" + str_joint_to_paypal);
                    Log.e(" ++++++++++++ JointidTOPayPalid count value ++++++++++++ ", "" + str_joint_to_paypal);


                } else {
                    Log.e(" ???????????????? Error getting friend count ????????????????:", "" + result);
                    Log.e(" ???????????????? Error getting friend count ????????????????:", "" + result);
                    Log.e(" ???????????????? Error getting friend count ????????????????:", "" + result);
                }
            } catch (Exception e) {
                System.out.println("Errror in gettting by ID" + e);

            }
            return result;
        }

        @Override
        protected void onPostExecute(String mystring) {
            super.onPostExecute(mystring);


            if (result.equalsIgnoreCase("successful")) {

                Intent payintent = new Intent(MainActivity.this, PaypalWebviewActivity.class);
                Bundle paypalbundle = new Bundle();
                paypalbundle.putString("paypalID", paypalID);
                paypalbundle.putString("PaymentAmount", paypalRAmount);
                payintent.putExtras(paypalbundle);
                startActivity(payintent);
                QuickTipDialog.dismiss();


            } else {
                Log.e(" ???????????????? Error getting friend count ????????????????:", "" + result);
                Log.e(" ???????????????? Error getting friend count ????????????????:", "" + result);
                Log.e(" ???????????????? Error getting friend count ????????????????:", "" + result);

                Toast.makeText(ActivityContext, "Joint ID is incorrrect.",
                        Toast.LENGTH_LONG).show();
            }


        }

    }

    class TotalFriendsRequestCount extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.e("  +++++++++++ TotalFriendsRequestCount AsyncTask START +++++++++++:", "YES");
            Log.e("  +++++++++++ TotalFriendsRequestCount AsyncTask START +++++++++++:", "YES");
            Log.e("  +++++++++++ TotalFriendsRequestCount AsyncTask START +++++++++++:", "YES");
        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(HttpUrlPath.urlPath + "send_request_count?user_id=" + userlogid);

            try {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("id", userlogid));
                post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = client.execute(post);
                String objfriendrequest = EntityUtils.toString(response.getEntity());

                Log.e("************Friend Request Count Data*******", objfriendrequest);
                Log.e("************Friend Request Count Data*******", objfriendrequest);
                Log.e("************Friend Request Count Data*******", objfriendrequest);

                JSONObject ParentObject = new JSONObject(objfriendrequest);

                result = ParentObject.getString("result");

                if (result.equalsIgnoreCase("successful")) {
                    Log.e("************ Friend Request JSON DATA*******************", String.valueOf(ParentObject));
                    Log.e("************ Friend Request JSON DATA*******************", String.valueOf(ParentObject));
                    Log.e("************ Friend Request JSON DATA*******************", String.valueOf(ParentObject));
                    str_getTotalFriendRequestCount = ParentObject.getString("count");
                    Log.e(" ++++++++++++ Friend Request count value ++++++++++++ ", "" + str_getTotalFriendRequestCount);
                    Log.e(" ++++++++++++ Friend Request count value ++++++++++++ ", "" + str_getTotalFriendRequestCount);
                    Log.e(" ++++++++++++ Friend Request count value ++++++++++++ ", "" + str_getTotalFriendRequestCount);
//                    str_getVideos = ParentObject.getString("video");

                } else {
                    Log.e(" ???????????????? Error getting Friend Request ????????????????:", "" + result);
                    Log.e(" ???????????????? Error getting Friend Request ????????????????:", "" + result);
                    Log.e(" ???????????????? Error getting Friend Request ????????????????:", "" + result);
                }
            } catch (Exception e) {
                System.out.println("Errror in gettting by ID" + e);

            }
            return result;
        }

        @Override
        protected void onPostExecute(String mystring) {
            super.onPostExecute(mystring);

            totalfriendRequestCount.setText(str_getTotalFriendRequestCount);
            Log.e("value of str_getTotalFriendRequestCount: ", "" + str_getTotalFriendRequestCount);
            Log.e("value of str_getTotalFriendRequestCount: ", "" + str_getTotalFriendRequestCount);
            Log.e("value of str_getTotalFriendRequestCount: ", "" + str_getTotalFriendRequestCount);
        }

    }

    private void captureVideoInitializations() {
        /**
         * a selector dialog to display two image source options, from camera
         * �Take from camera� and from existing files �Select from gallery�
         */
        final String[] items = new String[]{"Take from camera", "Select from gallery"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select Video");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) { // pick from
                // camera
                if (item == 0) {
                    Log.e(" ********** VIDEO **********", "YES");
                    Log.e(" ********** VIDEO **********", "YES");
                    Log.e(" ********** VIDEO **********", "YES");

                    /**
                     * To take a photo from camera, pass intent action
                     * �MediaStore.ACTION_IMAGE_CAPTURE� to open the camera app.
                     */
                    // create new Intentwith with Standard Intent action that can be
                    // sent to have the camera application capture an video and return it.

/* *********************** CAPTURE VIDEO FROM CAMERA **************************/
                    Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

                    Log.e(" ********** START CAMERA FOR VIDEO **********", "YES");
                    Log.e(" ********** START CAMERA FOR VIDEO **********", "YES");
                    Log.e(" ********** START CAMERA FOR VIDEO **********", "YES");

                    // create a file to save the video
                    fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);

                    intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, fileUri);
                    Log.e(" ********** VIDEO CAPTURE BY CAMERA **********", "YES");
                    Log.e(" ********** VIDEO CAPTURE BY CAMERA **********", "YES");
                    Log.e(" ********** VIDEO CAPTURE BY CAMERA **********", "YES");
                    // set the image file name
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

                    Log.e(" ********** VIDEO NAME **********", "" + fileUri);
                    Log.e(" ********** VIDEO NAME **********", "" + fileUri);
                    Log.e(" ********** VIDEO NAME **********", "" + fileUri);

                    // set the video image quality to high
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    // start the Video Capture Intent
                    startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);

/* *********************** CAPTURE VIDEO FROM CAMERA **************************/
                    try {
                        intent.putExtra("return-data", true);

                        startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);

                    } catch (ActivityNotFoundException e) {
                        e.printStackTrace();
                    }
                } else {
                    // pick from file
                    /**
                     * To select an image from existing files, use
                     * Intent.createChooser to open image chooser. Android will
                     * automatically display a list of supported applications,
                     * such as image gallery or file manager.
                     */
                    Intent intent = new Intent();
                    intent.setType("video/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);

                    startActivityForResult(Intent.createChooser(intent,
                            "Complete action using"), PICK_VIDEO_ACTIVITY_REQUEST_CODE);

                    Log.e(" ********** VIDEO FROM GALLERY **********", "YES");
                    Log.e(" ********** VIDEO FROM GALLERY **********", "YES");
                    Log.e(" ********** VIDEO FROM GALLERY **********", "YES");
                }
            }
        });

        dialogCover = builder.create();
    }

    /**
     * Create a file Uri for saving an image or video
     */
    private static Uri getOutputMediaFileUri(int type) {

        return Uri.fromFile(getOutputMediaFile(type));
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(int type) {

        // Check that the SDCard is mounted
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraVideo");

        Log.e(" ********** VIDEO File for saving **********", "" + mediaStorageDir);
        Log.e(" ********** VIDEO File for saving **********", "" + mediaStorageDir);
        Log.e(" ********** VIDEO File for saving **********", "" + mediaStorageDir);

        Log.e(" ********** CREATE FOLDER ON PICTURE FOR SAVING VIDEO **********", "MyCameraVideo" + "YES");
        Log.e(" ********** CREATE FOLDER ON PICTURE FOR SAVING VIDEO **********", "MyCameraVideo" + "YES");
        Log.e(" ********** CREATE FOLDER ON PICTURE FOR SAVING VIDEO **********", "MyCameraVideo" + "YES");

        // Create the storage directory(MyCameraVideo) if it does not exist
        if (!mediaStorageDir.exists()) {

            if (!mediaStorageDir.mkdirs()) {

                // output.setText("Failed to create directory MyCameraVideo.");

                Toast.makeText(ActivityContext, "Failed to create directory MyCameraVideo.",
                        Toast.LENGTH_LONG).show();

                Log.e("MyCameraVideo", "Failed to create directory MyCameraVideo.");
                return null;
            }
        }


        // Create a media file name

        // For unique file name appending current timeStamp with file name
        java.util.Date date = new java.util.Date();
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(date.getTime());

        File mediaFile;

        if (type == MEDIA_TYPE_VIDEO) {

            // For unique video file name appending current timeStamp with file name
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "AppVideo_" + timeStamp + ".mp4");

            Log.e(" ********** SAVED VIDEO NAME **********", "" + mediaFile);
            Log.e(" ********** SAVED VIDEO NAME **********", "" + mediaFile);
            Log.e(" ********** SAVED VIDEO NAME **********", "" + mediaFile);

        } else {
            return null;
        }

//        Toast.makeText(ActivityContext, "Video saved to:\n" + mediaFile, Toast.LENGTH_LONG).show();
        Log.e(" ********** SAVED VIDEO NAME **********", "" + mediaFile);
        Log.e(" ********** SAVED VIDEO NAME **********", "" + mediaFile);
        Log.e(" ********** SAVED VIDEO NAME **********", "" + mediaFile);

        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // After camera screen this code will excuted
        if (resultCode != RESULT_OK)

            return;
        switch (requestCode) {
            case CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE:

                String selectedImagepathc;
                fileUri = data.getData();
                selectedImagepathc = getRealPathFromURI(fileUri);
                /******************* file for post the profile image to the server *******************/
                projectVideo = new File(selectedImagepathc);
                /******************* file for post the profile image to the server *******************/
                //      output.setText("Video File : " +data.getData());

                // Video captured and saved to fileUri specified in the Intent
               /* Toast.makeText(this, "Video saved to:\n" + data.getData(), Toast.LENGTH_LONG).show();
*/
                if (Utils.isConnected(getApplicationContext())) {
                    UploadVideo taskk = new UploadVideo();
                    taskk.execute();
                    Log.e("************** UpdateData Task running **************", "YES");
                    Log.e("************** UpdateData Task running **************", "YES");
                    Log.e("************** UpdateData Task running **************", "YES");
                } else {
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
                //  previewVideo();
                Log.e(" ********** SAVED VIDEO PATH **********", "" + data.getData());
                Log.e(" ********** SAVED VIDEO PATH **********", "" + data.getData());
                Log.e(" ********** SAVED VIDEO PATH **********", "" + data.getData());

                break;

            case PICK_VIDEO_ACTIVITY_REQUEST_CODE:
                /**
                 * After selecting image from files, save the selected path
                 */
                fileUri = data.getData();
                /*selectedImagepathc = getRealPathFromURI(fileUri);*/
                selectedImagepathc = fileUri.getPath();
                /******************* file for post the profile image to the server *******************/
                projectVideo = new File(selectedImagepathc);
                /******************* file for post the profile image to the server *******************/

                Log.e(" ********** VIDEO PATH **********", "" + selectedImagepathc);
                Log.e(" ********** VIDEO PATH **********", "" + selectedImagepathc);
                Log.e(" ********** VIDEO PATH **********", "" + selectedImagepathc);
               /* Toast.makeText(this, "Video saved to:\n" + selectedImagepathc, Toast.LENGTH_LONG).show();*/

                if (Utils.isConnected(getApplicationContext())) {
                    UploadVideo task = new UploadVideo();
                    task.execute();
                    Log.e("************** UpdateData Task running **************", "YES");
                    Log.e("************** UpdateData Task running **************", "YES");
                    Log.e("************** UpdateData Task running **************", "YES");
               /* Intent signupss = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(signupss);*/

                } else {
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
                //   previewVideo();
                Log.e(" ********** SAVED VIDEO PATH **********", "" + selectedImagepathc);
                Log.e(" ********** SAVED VIDEO PATH **********", "" + selectedImagepathc);
                Log.e(" ********** SAVED VIDEO PATH **********", "" + selectedImagepathc);
                break;
        }

        // check that it is the SecondActivity with an OK result
        if (requestCode == PAYPAL_ACTIVITY_RESULT_CODE) {
            if (resultCode == RESULT_OK) {

                // get String data from Intent
                String returnString = data.getStringExtra("keyName");


            }
        }

    }

    public String getRealPathFromURI(Uri contentUri) {
        try {
            String[] proj = {MediaStore.Video.Media.DATA};
            Cursor cursor = managedQuery(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            return contentUri.getPath();
        }
    }

    class UploadVideo extends AsyncTask<String, Void, String> {
        Context context;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //videouploadprogressbar.setVisibility(View.VISIBLE);
            videouploadprogressbar.start();
            Log.e("******* NOW WEB SERVICE IS RUNNING *******", "YES");
            Log.e("******* NOW WEB SERVICE IS RUNNING *******", "YES");
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {

            /******************* file for post the profile image to the server *******************/
            FileBody ProjectVideofilebody = new FileBody(projectVideo);
            Log.e("****************Profile image ****************", "" + projectVideo.toString());
            Log.e("****************Profile image ****************", "" + projectVideo.toString());
            Log.e("****************Profile image ****************", "" + projectVideo.toString());
            /******************* file for post the profile image to the server *******************/
            try {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost postRequest = new HttpPost(HttpUrlPath.urlPath + "user_video_upload?user_id=" + userlogid);
                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
                reqEntity.addPart("video", ProjectVideofilebody);

                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("id", userlogid));
                Log.e("******* ID TO SERVER FOR MACHING*******", "" + userlogid);
                Log.e("******* ID TO SERVER FOR MACHING*******", "" + userlogid);
                Log.e("******* ID TO SERVER FOR MACHING*******", "" + userlogid);

                postRequest.setEntity(reqEntity);

                HttpResponse response = httpClient.execute(postRequest);

                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));

                String sResponse;

                StringBuilder s = new StringBuilder();

                while ((sResponse = reader.readLine()) != null) {
                    s = s.append(sResponse);
                }

                String Jsondata = s.toString();
                Log.e("Jsondata : ", Jsondata);
                JSONObject parentObject = new JSONObject(Jsondata);
                result = parentObject.getString("result");
                Log.e("*********** RESULT *********** : ", result);
                Log.e("*********** RESULT *********** : ", result);
                Log.e("*********** RESULT *********** : ", result);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            // videouploadprogressbar.setVisibility(View.GONE);
            videouploadprogressbar.stop();
            if (!iserror) {
                if (result.equalsIgnoreCase("successful")) {
                    SignupData taskk = new SignupData();
                    taskk.execute();

                    /*this Promtdialog is also working */
                    /*new PromptDialog(MainActivity.this)
                            .setDialogType(PromptDialog.DIALOG_TYPE_SUCCESS)
                            .setAnimationEnable(true)
                            .setTitleText("Success").setContentText("Video Uploaded Successfully")
                            .setPositiveListener("OK", new PromptDialog.OnPositiveListener() {
                                @Override
                                public void onClick(PromptDialog dialog) {
                                    dialog.dismiss();
                                }
                            }).show();*/
                    /*this Promtdialog is also working */

                    /*sweet alert dialog*/
                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Good job!")
                            .setContentText("Video Uploaded Successfully.!!")
                            .show();

                } else {
                    // Toast.makeText(MainActivity.this, "Video is not uploaded.... \n please try again", Toast.LENGTH_SHORT).show();

                     /*sweet alert dialog*/
                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops!!")
                            .setContentText("Video is not uploaded..!! Try again")
                            .show();


                }
            } else {
               /* Toast.makeText(getApplicationContext(), "Oops!! Please check server connection .",
                        Toast.LENGTH_SHORT).show();*/

                /*sweet alert dialog*/
                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops!! Please check server connection .")
                        .setContentText("Something went wrong!")
                        .show();
            }
        }

    }

    class DeleteVideo extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(HttpUrlPath.urlPath + "user_delete_video?");
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("video_id", singleVideoId));

                post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = client.execute(post);
                String object = EntityUtils.toString(response.getEntity());
/* ********** ********** ********** ********** ********** PRINT ALL INCOMING DATA (START)********** ********** ********** ********** ********** */
                System.out.println("########## OBJECT VALUE ##########" + object);
                System.out.println("****************object*******************=" + object);
                System.out.println("****************object*******************=" + object);

                Log.e("************ All INCOMING DATA VALUE ************", "" + object);
                Log.e("************ All INCOMING DATA VALUE ************", "" + object);
                Log.e("************ All INCOMING DATA VALUE ************", "" + object);
/* ********** ********** ********** ********** ********** PRINT ALL INCOMING DATA (END)********** ********** ********** ********** ********** */

                //JSONArray js = new JSONArray(object);
                JSONObject jobect = new JSONObject(object);
                result = jobect.getString("result");
                if (result.equalsIgnoreCase("successful")) {
                    resultPrint = jobect.getString("result");
                    Log.e("******* result RETURN BY SERVER *******", "" + resultPrint);
                    Log.e("******* result RETURN BY SERVER *******", "" + resultPrint);
                    Log.e("******* result RETURN BY SERVER *******", "" + resultPrint);
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
            super.onPostExecute(result1);

            if (!iserror) {
                if (result.equalsIgnoreCase("successful")) {
                    SignupData task = new SignupData();
                    task.execute();

                    Log.e(" ************ VIDEO DELETED ID ************ :", " " + singleVideoId);
                    Log.e(" ************ VIDEO DELETED ID ************ :", " " + singleVideoId);
                    Log.e(" ************ VIDEO DELETED ID ************ :", " " + singleVideoId);

                     /*sweet alert dialog*/
                   /* new SweetAlertDialog(MainActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Good job!")
                            .setContentText("Video Deleted Successfully.!!")
                            .show();
*/
                } else {
                   /* Toast.makeText(getApplicationContext(), "Friend Request Already Send...!", Toast.LENGTH_SHORT).show();*/

                    SignupData task = new SignupData();
                    task.execute();
                    /*sweet alert dialog*/
                    new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops!!")
                            .setContentText("Video is not Deleted..!! Try again")
                            .show();

                }
            } else {
                 /*sweet alert dialog*/
                new SweetAlertDialog(MainActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Oops!! Please check server connection .")
                        .setContentText("Something went wrong!")
                        .show();

                Log.e("********* Video deleted *********", "NOT Success");
                Log.e("********* Video deleted *********", "NOT Success");
                Log.e("********* Video deleted *********", "NOT Success");

        /*        Toast.makeText(getApplicationContext(), "Oops!! Please check server connection .",
                        Toast.LENGTH_SHORT).show();*/
            }
        }

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        moveTaskToBack(true);
                        MainActivity.this.finish();

                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }


}