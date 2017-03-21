package com.example.ritesh.jointapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alimuzaffar.lib.widgets.AnimatedEditText;
import com.example.ritesh.jointapp.constant.AppsConstant;
import com.example.ritesh.jointapp.constant.HttpUrlPath;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by ritesh on 26/7/16.
 */
@SuppressWarnings("deprecation")
public class ProfileEditActivity extends AppCompatActivity {

    /*image capture declaration (Start) Butter Knife*/
    @BindView(R.id.profileimg)
    CircleImageView profileimg;
    @BindView(R.id.iv_editprofile)
    ImageView editprofile;
    @BindView(R.id.iv_coverbg)
    ImageView coverbgg;
    @BindView(R.id.iv_editcoverpage)
    ImageView editcoverpage;
    @BindView(R.id.menuViewButton)
    ImageView menuViewButton;
    @BindView(R.id.btn_browse)
    Button browse;
    @BindView(R.id.tv_jointid)
    TextView tv_jointid;
    @BindView(R.id.tv_mystory)
    TextView tv_mystory;
    @BindView(R.id.tv_mystory_type)
    TextView tv_mystory_type;
    @BindView(R.id.tv_updateprofile)
    TextView updateprofile;
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_mobilenumber)
    EditText et_mobilenumber;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.et_lineofwork)
    EditText et_lineofwork;
    @BindView(R.id.et_placeofwork)
    EditText et_placeofwork;
    @BindView(R.id.et_goal)
    EditText et_goal;
    @BindView(R.id.et_websites)
    EditText et_websites;
    /*@BindView(R.id.et_thought)
    AnimatedEditText et_thoughts;*/
    @BindView(R.id.profileprogressbar)
    ProgressBar profileimageprogressbar;
    @BindView(R.id.coverprogressbar)
    ProgressBar coverimageprogressbar;
    @BindView(R.id.alldataupdateprogressbar)
    ProgressBar alldataprogressbar;

    /*image capture declaration (End) Butter Knife*/
    File projectImage, projectCoverimage, resumefile, mystoryfile;
    /*Step two for filebody create file*/

    FileBody ProfileImagefilebody, CoverImagefilebody, Resumefilebody, MyStoryManualTypefilebody;

    Uri mResumeUri, mImageCaptureUri, mImageCaptureUrii, mStoryManualTypeUri;

    /*String filenameResume = "";*/
    private AnimatedEditText et_thoughts;
    private static final int MY_INTENT_BROWSE_RESUME = 70;   /*Step one for file body*/
    private static final int PICK_FROM_CAMERA_PROFILE = 1;
    private static final int CROP_FROM_CAMERA_PROFILE = 2;
    private static final int PICK_FROM_FILE_PROFILE = 3;

    private static final int PICK_FROM_CAMERA_COVER = 4;
    private static final int CROP_FROM_CAMERA_COVER = 5;
    private static final int PICK_FROM_FILE_COVER = 6;

    private AlertDialog dialogProfile, dialogCover;


    // Declare
    private LinearLayout slidingPanel;
    private boolean isExpanded;
    private DisplayMetrics metrics;
    private RelativeLayout headerPanel;
    private int panelWidth1;

    FrameLayout.LayoutParams slidingPanelParameters;
    LinearLayout.LayoutParams headerPanelParameters;

    boolean iserror = false;

    String str_getuserid = " ",
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
            str_getVideos = " ",
            str_getAddress = " ",
            str_getLineofwork = " ",
            result = " ",
            validusername = " ",
            validmobilenumber = " ",
            validaddress = " ",
            validlineofwork = " ",
            validplaceofwork = " ",
            validmystory = " ",
            validgoal = " ",
            validwebsites = " ",
            validthought = "",
            selectedResumefilenamepath = "",
            selectedImagepath = " ",
            selectedImagepathc = " ",
            filenameResume = "",
            filenameMyStroy = "",
            userlogid = " ",
            str_My_Story;


    String FILE = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_edit);
        ButterKnife.bind(this);

        Log.e("onCreate", "In the onCreate() event");
        Log.e("onCreate", "In the onCreate() event");
        Log.e("onCreate", "In the onCreate() event");
/*

        mystoryfile = new File(FILE);
        filenameMyStroy = FILE.substring(FILE.lastIndexOf("/") + 1);
//        tv_mystory.setText(filenameMyStroy);
        Log.e("************* My STory File *************", filenameMyStroy);
        Log.e("************* My STory File *************", filenameMyStroy);

*/

        et_thoughts = (AnimatedEditText) findViewById(R.id.et_thought);

         /*image settin by universal image loader */

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(ProfileEditActivity.this).defaultDisplayImageOptions(defaultOptions).build();
        ImageLoader.getInstance().init(config);

        /*image settin by universal image loader */

        captureImageInitialization();
        captureImageInitializations();

        // captureImageInitializations();
        AppsConstant.jointpreference = getSharedPreferences(AppsConstant.MyPREFERENCES, Context.MODE_PRIVATE);
        userlogid = AppsConstant.jointpreference.getString("id", "0");
        Log.e("**************USER LOGIN id***********:", "" + userlogid);

        if (Utils.isConnected(getApplicationContext())) {
            SignupData task = new SignupData();
            task.execute();
            Log.e("************** Signupdata Task running **************", "YES");
            Log.e("************** Signupdata Task running **************", "YES");
            Log.e("************** Signupdata Task running **************", "YES");
               /* Intent signupss = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(signupss);*/
        } else {
            //Toast.makeText(getApplicationContext(), "Please Cheeck network conection..", Toast.LENGTH_SHORT).show();

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
        browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent browsee = new Intent();
//                browsee.setType("pdf/*,txt/*,doc/*, docx/*");
//                browsee.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(Intent.createChooser(browsee, "Select File"), MY_INTENT_BROWSE_RESUME); /*Step three for file body*/


                final Dialog callFeeDialog = new Dialog(ProfileEditActivity.this);
//                callFeeDialog = new Dialog(MainActivity.this);
                callFeeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                callFeeDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                callFeeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                callFeeDialog.setContentView(R.layout.activity_story_dialog);


                final TextView Tv_TypeStory = (TextView) callFeeDialog.findViewById(R.id.tv_type_story);
                final TextView Tv_SelectedStory = (TextView) callFeeDialog.findViewById(R.id.tv_selected_story);
                final TextView Tv_SelectStory = (TextView) callFeeDialog.findViewById(R.id.tv_select_story);
                final TextView Tv_AutoFit_My_Story = (TextView) callFeeDialog.findViewById(R.id.af_my_story);
                final EditText Edt_Story_Type = (EditText) callFeeDialog.findViewById(R.id.et_story_type);
                final RelativeLayout Rl_SelectOptions = (RelativeLayout) callFeeDialog.findViewById(R.id.rl_selectoptions);
                final RelativeLayout Rl_Story_Manual = (RelativeLayout) callFeeDialog.findViewById(R.id.rl_story_manual);
                final RelativeLayout Rl_TypeStory = (RelativeLayout) callFeeDialog.findViewById(R.id.rl_typestory);
                final RelativeLayout Rl_Dialog_Main_Buttons = (RelativeLayout) callFeeDialog.findViewById(R.id.rl_dialog_main_buttons);
                final RelativeLayout Rl_Type_Story_Buttons = (RelativeLayout) callFeeDialog.findViewById(R.id.rl_type_story_buttons);


                FancyButton btn_dialog_cancel = (FancyButton) callFeeDialog.findViewById(R.id.btn_dialog_cancel);
                FancyButton btn_dialog_done = (FancyButton) callFeeDialog.findViewById(R.id.btn_dialog_done);

                FancyButton btn_type_story_cancel = (FancyButton) callFeeDialog.findViewById(R.id.btn_type_story_cancel);
                FancyButton btn_type_story_done = (FancyButton) callFeeDialog.findViewById(R.id.btn_type_story_done);


                btn_dialog_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callFeeDialog.dismiss();
                    }
                });

                btn_dialog_done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (!filenameResume.equalsIgnoreCase("")) {
                            Log.e("filenameResume :", "is not empty" + "\t" + "" + filenameResume);
                            callFeeDialog.dismiss();
                        } else {
                            Log.e("filenameResume :", "is empty" + "\t" + " :" + filenameResume);
                            Toast.makeText(ProfileEditActivity.this, "Please Choose Your Story : " + filenameResume,
                                    Toast.LENGTH_LONG).show();
                        }

                    }
                });


                /*main dialog buttons (Start)*/
                Tv_TypeStory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Rl_SelectOptions.setVisibility(View.GONE);
                        Rl_Dialog_Main_Buttons.setVisibility(View.GONE);

                        Rl_TypeStory.setVisibility(View.VISIBLE);
                        Rl_Type_Story_Buttons.setVisibility(View.VISIBLE);


                        FILE = Environment.getExternalStorageDirectory().toString()
                                + "/PDF/" + "MyStory.pdf";

                        mystoryfile = new File(FILE);
                        filenameMyStroy = FILE.substring(FILE.lastIndexOf("/") + 1);
//        tv_mystory.setText(filenameMyStroy);
                        Log.e("************* My STory File *************", filenameMyStroy);
                        Log.e("************* My STory File *************", filenameMyStroy);
                    }
                });


                Edt_Story_Type.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                        Tv_AutoFit_My_Story.setText(s);
                        tv_mystory_type.setText(s);
                        str_My_Story = Edt_Story_Type.getText().toString().trim();

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        str_My_Story = Edt_Story_Type.getText().toString().trim();
                        Log.e("str_My_Story text :", "" + str_My_Story);
                    }
                });


                Tv_SelectStory.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent browsee = new Intent(Intent.ACTION_GET_CONTENT);
                        browsee.setType("application/pdf/*,txt/*,doc/*, docx/*");
//                        browsee.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(browsee, "Select File"), MY_INTENT_BROWSE_RESUME); /*Step three for file body*/


                    }
                });
                /*main dialog buttons (End)*/


                /*My story type text confirm buttons (Start)*/
                btn_type_story_cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Rl_SelectOptions.setVisibility(View.VISIBLE);
                        Rl_Dialog_Main_Buttons.setVisibility(View.VISIBLE);

                        Rl_TypeStory.setVisibility(View.GONE);
                        Rl_Type_Story_Buttons.setVisibility(View.GONE);

                    }
                });

                btn_type_story_done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        new SweetAlertDialog(ProfileEditActivity.this, SweetAlertDialog.WARNING_TYPE)
                                .setTitleText("Are you sure?")
                                .setContentText("All Information is Correct..?")
                                .setCancelText("Cancel")
                                .setConfirmText("Confirm")
                                .showCancelButton(true)
                                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                    @Override
                                    public void onClick(SweetAlertDialog sDialog) {
                                        // reuse previous dialog instance, keep widget user state, reset them if you need
                                        sDialog.setTitleText("Cancelled!")
                                                .setContentText("My Story Not Updated..!!")
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

                                        // Create New Blank Document
                                        Document document = new Document(PageSize.A4);

                                        // Create Directory in External Storage
                                        String root = Environment.getExternalStorageDirectory().toString();
                                        File myDir = new File(root + "/PDF");
                                        myDir.mkdirs();

                                        // Create Pdf Writer for Writting into New Created Document
                                        try {
                                            PdfWriter.getInstance(document, new FileOutputStream(FILE));

                                            // Open Document for Writting into document
                                            document.open();

                                            // User Define Method
                                            addMetaData(document);
                                            addTitlePage(document);
                                        } catch (FileNotFoundException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        } catch (DocumentException e) {
                                            // TODO Auto-generated catch block
                                            e.printStackTrace();
                                        }
                                        // Close Document after writting all content
                                        document.close();

                                        Toast.makeText(ProfileEditActivity.this, "PDF File is Created. Location : " + FILE,
                                                Toast.LENGTH_LONG).show();


                                        callFeeDialog.dismiss();
                                        sDialog.dismiss();

                                    }
                                })
                                .show();


                    }
                /*My story type text confirm buttons (Start)*/

                });


                callFeeDialog.show();


            }
        });


        //coverbgg = (ImageView) findViewById(R.id.iv_coverbg);
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(" *********** CLICK FOR PROFILE PHOTO ***********", "YES");
                Log.e(" *********** CLICK FOR PROFILE PHOTO ***********", "YES");
                Log.e(" *********** CLICK FOR PROFILE PHOTO ***********", "YES");

                dialogProfile.show();
            }
        });

        /*editcoverpage = (ImageView) findViewById(R.id.iv_editcoverpage);*/
        editcoverpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.e(" *********** CLICK FOR COVER PHOTO ***********", "YES");
                Log.e(" *********** CLICK FOR COVER PHOTO ***********", "YES");
                Log.e(" *********** CLICK FOR COVER PHOTO ***********", "YES");

                dialogCover.show();
            }
        });

        updateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validusername = et_username.getText().toString();
                validmobilenumber = et_mobilenumber.getText().toString();
                validaddress = et_address.getText().toString();
                validlineofwork = et_lineofwork.getText().toString();
                validplaceofwork = et_placeofwork.getText().toString();
                validmystory = tv_mystory.getText().toString();
                validgoal = et_goal.getText().toString();
                validwebsites = et_websites.getText().toString();
                validthought = et_thoughts.getText().toString();

                if (Utils.isConnected(getApplicationContext())) {
                    UpdateData task = new UpdateData();
                    profileimageprogressbar.setVisibility(View.VISIBLE);
                    coverimageprogressbar.setVisibility(View.VISIBLE);
                    task.execute();
                    Log.e("************** UpdateData Task running **************", "YES");
                    Log.e("************** UpdateData Task running **************", "YES");
                    Log.e("************** UpdateData Task running **************", "YES");
               /* Intent signupss = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(signupss);*/

                } else {
                    //Toast.makeText(getApplicationContext(), "Please Cheeck network conection..", Toast.LENGTH_SHORT).show();

                    LayoutInflater layoutInflater = LayoutInflater.from(ProfileEditActivity.this);
                    View promptView = layoutInflater.inflate(R.layout.activity_wificheck_customalert, null);
                    final AlertDialog alertD = new AlertDialog.Builder(ProfileEditActivity.this).create();
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
        });

        final String[] items = new String[]{"Take from camera", "Select from gallery"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Initialize left menu open on click (Start)
        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        panelWidth1 = (int) ((metrics.widthPixels) * 0.75);

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

    }

    /**
     * Called when the activity has become visible.
     */
    @Override
    protected void onResume() {
        super.onResume();

        /*SignupData taskkk = new SignupData();
        taskkk.execute();*/
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

    private void captureImageInitialization() {
        /**
         * a selector dialog to display two image source options, from camera
         * �Take from camera� and from existing files �Select from gallery�
         */
        final String[] items = new String[]{"Take from camera", "Select from gallery"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select Image For Profile");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) { // pick from
                // camera
                if (item == 0) {
                    Log.e(" ********** IMAGE FOR PROFILE **********", "YES");
                    Log.e(" ********** IMAGE FOR PROFILE **********", "YES");
                    Log.e(" ********** IMAGE FOR PROFILE **********", "YES");


                    /*mImageCaptureUri = Uri.fromFile(new File(Environment
                            .getExternalStorageDirectory(), "tmp_avatar_"
                            + String.valueOf(System.currentTimeMillis())
                            + ".jpg"));*/


                    new PickerBuilder(ProfileEditActivity.this, PickerBuilder.SELECT_FROM_CAMERA)
                            .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                                @Override
                                public void onImageReceived(Uri imageUri) {
                                    profileimg.setImageURI(imageUri);
                                    Log.e("***** Profile image pathw ********", "" + imageUri);
                                    File f = new File(imageUri.getPath());
                                    selectedImagepath = imageUri.getPath();
                                    Log.e("selectedImagepath for profile :", "" + selectedImagepath);

                                    /******************* file for post the profile image to the server *******************/
                                    projectImage = new File(selectedImagepath);
                                    Log.e("profile File projectImage:", "" + projectImage);
                                    /******************* file for post the profile image to the server *******************/

//                                    if (f.exists())
//                                        f.delete();
                                    Log.e("***************** image pathwwwwww *****************", "" + selectedImagepath);
                                    Log.e("***************** image pathwwwwww *****************", "" + selectedImagepath);
                                    Log.e("***************** image pathwwwwww *****************", "" + selectedImagepath);
                                    Log.e("********************Image File Pathttttt ********************", "" + imageUri);
                                    Log.e("********************Image File Pathttttt ********************", "" + imageUri);
                                    Log.e("********************Image File Pathttttt ********************", "" + imageUri);

//                                    Toast.makeText(ProfileEditActivity.this, "Got image - " + imageUri, Toast.LENGTH_LONG).show();
                                    Log.e("***** Profile image pathw ********", "" + imageUri);


                                }
                            })
//                            .setImageName("testImage")

                            .setImageName("Joint_Cam_Profile" + String.valueOf(System.currentTimeMillis()))
//                            .setImageFolderName("testFolder")
                            .setImageFolderName("Joint")
                            .withTimeStamp(false)
                            .setCropScreenColor(Color.CYAN)
                            .start();


                } else {


                    new PickerBuilder(ProfileEditActivity.this, PickerBuilder.SELECT_FROM_GALLERY)
                            .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                                @Override
                                public void onImageReceived(Uri imageUri) {

                                    profileimg.setImageURI(imageUri);

                                    Log.e("***** Profile image pathw ********", "" + imageUri);
                                    File f = new File(imageUri.getPath());
//                                    selectedImagepath = getRealPathFromURI(imageUri);
                                    selectedImagepath = imageUri.getPath();
                                    Log.e("selectedImagepath for profile :", "" + selectedImagepath);

                                    /******************* file for post the profile image to the server *******************/
                                    projectImage = new File(selectedImagepath);
                                    Log.e("profile File projectImage:", "" + projectImage);
                                    /******************* file for post the profile image to the server *******************/

//                                    if (f.exists())
//                                        f.delete();
                                    Log.e("***************** image pathwwwwww *****************", "" + selectedImagepath);
                                    Log.e("***************** image pathwwwwww *****************", "" + selectedImagepath);
                                    Log.e("***************** image pathwwwwww *****************", "" + selectedImagepath);
                                    Log.e("********************Image File Pathttttt ********************", "" + imageUri);
                                    Log.e("********************Image File Pathttttt ********************", "" + imageUri);
                                    Log.e("********************Image File Pathttttt ********************", "" + imageUri);
//                                    Toast.makeText(ProfileEditActivity.this, "Got image - " + imageUri, Toast.LENGTH_LONG).show();
                                    Log.e("***** Profile image pathw ********", "" + imageUri);

                                }
                            })
                            .setImageName("Joint_Gall_Profile" + String.valueOf(System.currentTimeMillis()))
                            .setImageFolderName("Joint")
                            .setCropScreenColor(Color.CYAN)
                            .setOnPermissionRefusedListener(new PickerBuilder.onPermissionRefusedListener() {
                                @Override
                                public void onPermissionRefused() {

                                }
                            })
                            .start();


                    Log.e(" ********** PHOTO FROM GALLERY FOR PROFILE **********", "YES");
                    Log.e(" ********** PHOTO FROM GALLERY FOR PROFILE **********", "YES");
                    Log.e(" ********** PHOTO FROM GALLERY FOR PROFILE **********", "YES");
                }
            }
        });

        dialogProfile = builder.create();
    }

    private void captureImageInitializations() {
        /**
         * a selector dialog to display two image source options, from camera
         * �Take from camera� and from existing files �Select from gallery�
         */
        final String[] items = new String[]{"Take from camera", "Select from gallery"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.select_dialog_item, items);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Select Image For Cover");
        builder.setAdapter(adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item) { // pick from
                // camera
                if (item == 0) {
                    Log.e(" ********** IMAGE FOR COVER **********", "YES");
                    Log.e(" ********** IMAGE FOR COVER **********", "YES");
                    Log.e(" ********** IMAGE FOR COVER **********", "YES");


                    new PickerBuilder(ProfileEditActivity.this, PickerBuilder.SELECT_FROM_CAMERA)
                            .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                                @Override
                                public void onImageReceived(Uri imageUri) {
                                    coverbgg.setImageURI(imageUri);
                                    Log.e("***** Profile image pathw ********", "" + imageUri);
                                    File f = new File(imageUri.getPath());
                                    selectedImagepathc = imageUri.getPath();
                                    Log.e("selectedImagepath for cover :", "" + selectedImagepathc);

                                    /******************* file for post the profile image to the server *******************/
                                    projectCoverimage = new File(selectedImagepathc);
                                    Log.e("profile File projectCoverimage:", "" + projectCoverimage);
                                    /******************* file for post the profile image to the server *******************/

//                                    if (f.exists())
//                                        f.delete();
                                    Log.e("***************** image pathwwwwww for cover *****************", "" + selectedImagepathc);
                                    Log.e("***************** image pathwwwwww for cover *****************", "" + selectedImagepathc);
                                    Log.e("***************** image pathwwwwww for cover *****************", "" + selectedImagepathc);
                                    Log.e("********************Image File Pathttttt cover********************", "" + imageUri);
                                    Log.e("********************Image File Pathttttt cover********************", "" + imageUri);
                                    Log.e("********************Image File Pathttttt cover********************", "" + imageUri);

//                                    Toast.makeText(ProfileEditActivity.this, "Got image - " + imageUri, Toast.LENGTH_LONG).show();
                                    Log.e("***** Profile image pathw cover ********", "" + imageUri);


                                }
                            })
//                            .setImageName("testImage")

                            .setImageName("Joint_Cam_Cover" + String.valueOf(System.currentTimeMillis()))
//                            .setImageFolderName("testFolder")
                            .setImageFolderName("Joint")
                            .withTimeStamp(false)
                            .setCropScreenColor(Color.CYAN)
                            .start();




                } else {
                    new PickerBuilder(ProfileEditActivity.this, PickerBuilder.SELECT_FROM_GALLERY)
                            .setOnImageReceivedListener(new PickerBuilder.onImageReceivedListener() {
                                @Override
                                public void onImageReceived(Uri imageUriCover) {

                                    coverbgg.setImageURI(imageUriCover);

                                    Log.e("***** Cover image pathw ********", "" + imageUriCover);
                                    File f = new File(imageUriCover.getPath());
//                                    selectedImagepath = getRealPathFromURI(imageUri);
                                    selectedImagepathc = imageUriCover.getPath();
                                    Log.e("selectedImagepathc for profile :", "" + selectedImagepathc);

                                    /******************* file for post the profile image to the server *******************/
                                    projectCoverimage = new File(selectedImagepathc);
                                    Log.e("profile File projectCoverimage:", "" + projectCoverimage);
                                    /******************* file for post the profile image to the server *******************/

//                                    if (f.exists())
//                                        f.delete();
                                    Log.e("***************** image pathwwwwww Cover*****************", "" + selectedImagepathc);
                                    Log.e("***************** image pathwwwwww Cover*****************", "" + selectedImagepathc);
                                    Log.e("***************** image pathwwwwww Cover*****************", "" + selectedImagepathc);
                                    Log.e("********************Image File Pathttttt Cover********************", "" + imageUriCover);
                                    Log.e("********************Image File Pathttttt Cover********************", "" + imageUriCover);
                                    Log.e("********************Image File Pathttttt Cover********************", "" + imageUriCover);
//                                    Toast.makeText(ProfileEditActivity.this, "Got image - " + imageUri, Toast.LENGTH_LONG).show();
                                    Log.e("***** Cover image pathw ********", "" + imageUriCover);

                                }
                            })
                            .setImageName("Joint_Gall_Cover" + String.valueOf(System.currentTimeMillis()))
                            .setImageFolderName("Joint")
                            .setCropScreenColor(Color.CYAN)
                            .setOnPermissionRefusedListener(new PickerBuilder.onPermissionRefusedListener() {
                                @Override
                                public void onPermissionRefused() {

                                }
                            })
                            .start();


                    Log.e(" ********** PHOTO FROM GALLERY FOR COVER **********", "YES");
                    Log.e(" ********** PHOTO FROM GALLERY FOR COVER **********", "YES");
                    Log.e(" ********** PHOTO FROM GALLERY FOR COVER **********", "YES");
                }
            }
        });

        dialogCover = builder.create();
    }


    // Set PDF document Properties
    public void addMetaData(Document document) {
        document.addTitle("My Story");
        document.addSubject("My Person Info");
        document.addKeywords("Personal,	Education, Skills");
        document.addAuthor("User");
        document.addCreator("User");
    }

    public void addTitlePage(Document document) throws DocumentException {
        // Font Style for Document
        Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
        Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 22, Font.BOLD
                | Font.UNDERLINE, BaseColor.GRAY);
        Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
        Font normal = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);

        // Start New Paragraph
        Paragraph prHead = new Paragraph();
        // Set Font in this Paragraph
        prHead.setFont(titleFont);
        // Add item into Paragraph
        prHead.add("\n\nMy Story\n");

        // Create Table into Document with 1 Row
        PdfPTable myTable = new PdfPTable(1);
        // 100.0f mean width of table is same as Document size
        myTable.setWidthPercentage(100.0f);

        // Create New Cell into Table
        PdfPCell myCell = new PdfPCell(new Paragraph(""));
        myCell.setBorder(Rectangle.BOTTOM);

        // Add Cell into Table
        myTable.addCell(myCell);

        prHead.setFont(catFont);
        prHead.add("\n\n" + "");  //str_My_Story
        prHead.setAlignment(Element.ALIGN_CENTER);

        // Add all above details into Document
        document.add(prHead);
        document.add(myTable);

        document.add(myTable);

        // Now Start another New Paragraph
        Paragraph prPersinalInfo = new Paragraph();
        prPersinalInfo.setFont(smallBold);
        prPersinalInfo.add("\n\n" + str_My_Story + "\n\n");
        prPersinalInfo.setAlignment(Element.ALIGN_JUSTIFIED);

        document.add(prPersinalInfo);
        document.add(myTable);

        document.add(myTable);
        document.newPage();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case PICK_FROM_CAMERA_PROFILE:
                /**
                 * After taking a picture, do the crop
                 */

//                doCrop();
                break;

            case PICK_FROM_CAMERA_COVER:
//                /**
//                 * After taking a picture, do the crop
//                 */
//                doCropc();
//                break;

            case PICK_FROM_FILE_PROFILE:
                /**
                 * After selecting image from files, save the selected path
                 */

//                mImageCaptureUri = data.getData();
//
//                selectedImagepath = getRealPathFromURI(mImageCaptureUri);
//
//                Log.e("***************** image pathaaaaa *****************", "" + selectedImagepath);
//                Log.e("***************** image pathaaaaa *****************", "" + selectedImagepath);
//                Log.e("***************** image pathaaaaa *****************", "" + selectedImagepath);

//                doCrop();
                break;

            case PICK_FROM_FILE_COVER:
//                /**
//                 * After selecting image from files, save the selected path
//                 */
//                mImageCaptureUrii = data.getData();
//                selectedImagepathc = getRealPathFromURI(mImageCaptureUrii);
//                Log.e("***************** image pathdddddd *****************", "" + selectedImagepathc);
//                Log.e("***************** image pathdddddd *****************", "" + selectedImagepathc);
//                Log.e("***************** image pathdddddd *****************", "" + selectedImagepathc);
//                doCropc();
//                break;

            case CROP_FROM_CAMERA_PROFILE:
//                Bundle extras = data.getExtras();
//                selectedImagepath = getRealPathFromURI(mImageCaptureUri);
//                Log.e("***************** image pathsssss *****************", "" + selectedImagepath);
//                Log.e("***************** image pathsssss *****************", "" + selectedImagepath);
//                Log.e("***************** image pathsssss *****************", "" + selectedImagepath);
//                /**
//                 * After cropping the image, get the bitmap of the cropped image and
//                 * display it on imageview.
//                 */
//                if (extras != null) {
//                    Bitmap photo = extras.getParcelable("data");
//
//                    profileimg.setImageBitmap(photo);
//                }

//
//                File f = new File(mImageCaptureUri.getPath());
//                selectedImagepath = getRealPathFromURI(mImageCaptureUri);
//
//                /******************* file for post the profile image to the server *******************/
//                projectImage = new File(selectedImagepath);
//                /******************* file for post the profile image to the server *******************/
//
//                Log.e("***************** image pathwwwwww *****************", "" + selectedImagepath);
//                Log.e("***************** image pathwwwwww *****************", "" + selectedImagepath);
//                Log.e("***************** image pathwwwwww *****************", "" + selectedImagepath);
//                Log.e("********************Image File Pathttttt ********************", "" + mImageCaptureUri);
//                Log.e("********************Image File Pathttttt ********************", "" + mImageCaptureUri);
//                Log.e("********************Image File Pathttttt ********************", "" + mImageCaptureUri);
//                /**
//                 * Delete the temporary image
//                 */
//                if (f.exists())
//                    f.delete();

                break;

            case CROP_FROM_CAMERA_COVER:
//                Bundle extrass = data.getExtras();
//                selectedImagepathc = getRealPathFromURI(mImageCaptureUrii);
//
//                /******************* file for post the cover image to the server *******************/
//                projectCoverimage = new File(selectedImagepathc);
//                /******************* file for post the cover image to the server *******************/
//
//                Log.e("***************** image pathzzzzz *****************", "" + selectedImagepathc);
//                Log.e("***************** image pathzzzzz *****************", "" + selectedImagepathc);
//                Log.e("***************** image pathzzzzz *****************", "" + selectedImagepathc);
//                /**
//                 * After cropping the image, get the bitmap of the cropped image and
//                 * display it on imageview.
//                 */
//                if (extrass != null) {
//                    Bitmap photo = extrass.getParcelable("data");
//
//                    coverbgg.setImageBitmap(photo);
//                }
//
//                File ff = new File(mImageCaptureUrii.getPath());
//                Log.e("***************** image patheeeeeeee *****************", "" + selectedImagepathc);
//                Log.e("***************** image patheeeeeeee *****************", "" + selectedImagepathc);
//                Log.e("***************** image patheeeeeeee *****************", "" + selectedImagepathc);
//                Log.e("********************Image File Pathooooo ********************", "" + mImageCaptureUrii);
//                Log.e("********************Image File Pathooooo ********************", "" + mImageCaptureUrii);
//                Log.e("********************Image File Pathooooo ********************", "" + mImageCaptureUrii);
//
//                /**
//                 * Delete the temporary image
//                 */
//                if (ff.exists())
//                    ff.delete();

                break;

            case MY_INTENT_BROWSE_RESUME:
                if (requestCode == MY_INTENT_BROWSE_RESUME) {
                    if (null == data) return;

                    //String selectedResumePath;
                    mResumeUri = data.getData();

                    Log.e(" ********** URI Resume ********** :", "" + mResumeUri);
                    Log.e(" ********** URI Resume ********** :", "" + mResumeUri);
                    Log.e(" ********** URI Resume ********** :", "" + mResumeUri);

                    //MEDIA GALLERY
                    selectedResumefilenamepath = mResumeUri.getPath();
                    Log.e(" ********** Resume path ********** :", "" + selectedResumefilenamepath);
                    Log.e(" ********** Resume path ********** :", "" + selectedResumefilenamepath);
                    Log.e(" ********** Resume path ********** :", "" + selectedResumefilenamepath);


                    if (selectedResumefilenamepath == null) {

                        Toast.makeText(ProfileEditActivity.this, "Please Select from Device Storage", Toast.LENGTH_SHORT).show();

                    } else {
                        resumefile = new File(selectedResumefilenamepath);
                        filenameResume = selectedResumefilenamepath.substring(selectedResumefilenamepath.lastIndexOf("/") + 1);
                        tv_mystory.setText(filenameResume);


                        Log.e("************* Resumefile *************", filenameResume);
                        Log.e("************* Resumefile *************", filenameResume);
                        Log.e("************* Resumefile *************", filenameResume);
                    }

                }

                break;
        }
    }

    class SignupData extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            signuprogress2.setVisibility(View.VISIBLE);
            Log.e("******* NOW WEB SERVICE IS RUNNING *******", "YES");
            Log.e("******* NOW WEB SERVICE IS RUNNING *******", "YES");
        }

        @SuppressWarnings("deprecation")
        @Override
        protected String doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();
            Log.e("******* NOW BACKGROUND TASK IS RUNNING *******", "YES");
            Log.e("******* NOW BACKGROUND TASK IS RUNNING *******", "YES");

//            HttpPost post = new HttpPost(HttpUrlPath.urlPath + "get_user?");
            HttpPost post = new HttpPost(HttpUrlPath.urlPath + "get_user?user_id=" + userlogid);
            System.out.println("#####object=" + HttpUrlPath.urlPath +
                    "get_user?user_id=" + str_getuserid +
                    "id=" + userlogid +
                    " username=" + str_getName +
                    "joint_id=" + str_getJointid +
                    "email=" + str_getEmail +
                    "password=" + str_getPassword +
                    "line_work=" + str_getLineofwork +
                    "mobile=" + str_getMobile +
                    "place_work= " + str_getPlaceofWork +
                    "story=" + str_getMyStory +
                    "address=" + str_getAddress +
                    "goals=" + str_getGoalsAspiration +
                    "website=" + str_getWebSites +
                    "image=" + str_getProfileimage +
                    "thought" + str_getthought +
                    "cover_img = " + str_getCoverimage);


            try {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("id", userlogid));
                Log.e("******* ID TO SERVER FOR MACHING*******", "" + userlogid);
                Log.e("******* ID TO SERVER FOR MACHING*******", "" + userlogid);
                Log.e("******* ID TO SERVER FOR MACHING*******", "" + userlogid);


                post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = client.execute(post);
                String obj = EntityUtils.toString(response.getEntity());

                Log.e("************All Incoming Data*******", obj);

                JSONObject ParentObject = new JSONObject(obj);
                JSONObject alldataObject = ParentObject.getJSONObject("all_data");

                result = alldataObject.getString("result");

                if (result.equalsIgnoreCase("successful")) {
                    Log.e("************Json2*******************", String.valueOf(alldataObject));
                    str_getName = alldataObject.getString("username");
                    str_getMobile = alldataObject.getString("mobile");
                    str_getJointid = alldataObject.getString("joint_id");
                    str_getAddress = alldataObject.getString("address");
                    str_getLineofwork = alldataObject.getString("line_work");
                    str_getPlaceofWork = alldataObject.getString("place_work");
                    str_getMyStory = alldataObject.getString("story");
                    str_getGoalsAspiration = alldataObject.getString("goals");
                    str_getWebSites = alldataObject.getString("website");
                    str_getProfileimage = alldataObject.getString("image");
                    str_getCoverimage = alldataObject.getString("cover_img");
                    str_getthought = alldataObject.getString("thought");
//                    str_getVideos = ParentObject.getString("video");

                } else {
                    if (result.equalsIgnoreCase("unsuccessfully")) {
                        // error1 = jobect.getString("error");
                    }
                }
            } catch (Exception e) {
                System.out.println("Errror in gettting by ID" + e);

            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            et_username.setText(str_getName);
            Log.e("value of str_getName: ", "" + str_getName);

            et_mobilenumber.setText(str_getMobile);
            Log.e("value of str_getMobile: ", "" + str_getMobile);

            tv_jointid.setText(str_getJointid);
            Log.e("value of str_getJointid: ", "" + str_getJointid);

            et_address.setText(str_getAddress);
            Log.e("value of str_getAddress: ", "" + str_getAddress);

            et_lineofwork.setText(str_getLineofwork);
            Log.e("value of str_getLineofwork: ", "" + str_getLineofwork);

            et_placeofwork.setText(str_getPlaceofWork);
            Log.e("value of str_getPlaceofWork: ", "" + str_getPlaceofWork);

            tv_mystory.setText(str_getMyStory.substring(str_getMyStory.lastIndexOf("/") + 1));
            Log.e("value of str_getMyStory: ", "" + str_getMyStory);

            et_goal.setText(str_getGoalsAspiration);
            Log.e("value of str_getGoalsAspiration: ", "" + str_getGoalsAspiration);

            et_websites.setText(str_getWebSites);
            Log.e("value of str_getWebSites: ", "" + str_getWebSites);


            if (str_getthought.equalsIgnoreCase("")) {
                et_thoughts.setText("User Thought");
            } else {
                et_thoughts.setText(str_getthought);
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
                    //coverimageprogressbar.setVisibility(View.VISIBLE);
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

    class UpdateData extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alldataprogressbar.setVisibility(View.VISIBLE);
            Log.e("******* NOW WEB Update SERVICE IS RUNNING *******", "YES");
            Log.e("******* NOW WEB Update SERVICE IS RUNNING *******", "YES");

            Log.e("selectedImagepath :", "" + selectedImagepath + "\t" + "selectedImagepathc :" + "" + selectedImagepathc
                    + "\t" + "selectedResumefilenamepath :" + "" + selectedResumefilenamepath);
            Log.e("selectedImagepath :", "" + selectedImagepath + "\t" + "selectedImagepathc :" + "" + selectedImagepathc
                    + "\t" + "selectedResumefilenamepath :" + "" + selectedResumefilenamepath);

            if (selectedImagepath.equalsIgnoreCase(" ") && selectedImagepathc.equalsIgnoreCase("") &&
                    selectedResumefilenamepath.equalsIgnoreCase("") && FILE.equalsIgnoreCase("")) {

                selectedImagepath = "Ritesh";
                selectedImagepathc = "kumar";
                selectedResumefilenamepath = "king";
                FILE = "blank";

            } else {
                Log.e("ERRRRRRRRRRR :", "");
                Log.e("ERRRRRRRRRRR :", "");
                Log.e("ERRRRRRRRRRR :", "");

            }
        }

        @Override
        protected String doInBackground(String... params) {

            try {

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost postRequest = new HttpPost(HttpUrlPath.urlPath + "full_user_update?user_id=" + userlogid);
                MultipartEntity reqEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);

                reqEntity.addPart("username", new StringBody(validusername));
                reqEntity.addPart("mobile", new StringBody(validmobilenumber));
                reqEntity.addPart("address", new StringBody(validaddress));
                reqEntity.addPart("line_work", new StringBody(validlineofwork));
                reqEntity.addPart("place_work", new StringBody(validplaceofwork));
                //reqEntity.addPart("story", new StringBody(validmystory));
                reqEntity.addPart("goals", new StringBody(validgoal));
                reqEntity.addPart("website", new StringBody(validwebsites));
                reqEntity.addPart("mobile", new StringBody(validmobilenumber));
                reqEntity.addPart("thought", new StringBody(validthought));


                if (projectImage == null) {
                    Log.e("if true for projectImage ", "seem to be true");
                    selectedImagepath = "Ritesh";

                } else {
                    /******************* file for post the profile image to the server *******************/
                    ProfileImagefilebody = new FileBody(projectImage);
                    reqEntity.addPart("image", ProfileImagefilebody);
                    Log.e("****************Profile image ****************", "" + projectImage.toString());
                    Log.e("****************Profile image ****************", "" + projectImage.toString());
                    Log.e("****************Profile image ****************", "" + projectImage.toString());

                    Log.e("****************Profile File Body ****************", "" + projectImage.toString());
                    Log.e("****************Profile File Body ****************", "" + projectImage.toString());
                    Log.e("****************Profile File Body ****************", "" + projectImage.toString());
                    /******************* file for post the profile image to the server *******************/
                }

                if (projectCoverimage == null) {
                    Log.e("iftrue for projectCoverimage ", "seem to be true");
                    selectedImagepathc = "kumar";
                } else {
                    /******************* file for post the cover image to the server *******************/
                    CoverImagefilebody = new FileBody(projectCoverimage);
                    reqEntity.addPart("cover_img", CoverImagefilebody);
                    Log.e("****************Cover image ****************", "" + projectCoverimage.toString());
                    Log.e("****************Cover image ****************", "" + projectCoverimage.toString());
                    Log.e("****************Cover image ****************", "" + projectCoverimage.toString());


                    Log.e("****************Cover File Body ****************", "" + CoverImagefilebody);
                    Log.e("****************Cover File Body ****************", "" + CoverImagefilebody);
                    Log.e("****************Cover File Body ****************", "" + CoverImagefilebody);
                    /******************* file for post the cover image to the server *******************/
                }


                if (resumefile == null) {

                    Log.e("oohhoo Selected resumefile", "NULLLLLLL" + "so we are looking for manually type My Story");
                    selectedResumefilenamepath = "king";


                    if (mystoryfile == null) {

                        Log.e("manually type My Story : mystoryfile :", "Also NNNuuuLLL");
                        FILE = "blank";


                    } else {
                        Log.e(" yyeaaahhh :", " we found manually typed MyStoryFile");
                        MyStoryManualTypefilebody = new FileBody(mystoryfile);
                        reqEntity.addPart("story", MyStoryManualTypefilebody);
                        Log.e(" ******* story File send over server ******", "" + mystoryfile.toString());
                        Log.e(" ******* story File send over server ******", "" + mystoryfile.toString());
                        Log.e(" ******* story File send over server ******", "" + mystoryfile.toString());


                        Log.e("****************story File Body ****************", "" + MyStoryManualTypefilebody);
                        Log.e("****************story File Body ****************", "" + MyStoryManualTypefilebody);
                        Log.e("****************story File Body ****************", "" + MyStoryManualTypefilebody);
                    }


                } else {
                    Log.e(" Yoyo we found selected My STory file :", "wao");
                    Resumefilebody = new FileBody(resumefile);
                    reqEntity.addPart("story", Resumefilebody);
                    Log.e("****************Resume File ****************", "" + resumefile.toString());
                    Log.e("****************Resume File ****************", "" + resumefile.toString());
                    Log.e("****************Resume File ****************", "" + resumefile.toString());


                    Log.e("****************Resume File Body ****************", "" + Resumefilebody);
                    Log.e("****************Resume File Body ****************", "" + Resumefilebody);
                    Log.e("****************Resume File Body ****************", "" + Resumefilebody);


                }


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

            coverimageprogressbar.setVisibility(View.GONE);
            profileimageprogressbar.setVisibility(View.GONE);
            alldataprogressbar.setVisibility(View.GONE);

            if (!iserror) {
                if (result.equalsIgnoreCase("successful")) {

                    Log.e("xxxxxxxxxxxxxxx All Value : xxxxxxxxxxxxxxx :", " user name: " + validusername
                            + " \n mobile :" + validmobilenumber
                            + "\n address : " + validaddress
                            + "\n line_work : " + validlineofwork
                            + "\n place_work : " + validplaceofwork
                            + "\n goals : " + validgoal
                            + "\n website : " + validwebsites
                            + "\n mobile : " + validmobilenumber
                            + "\n thought : " + validthought
                            + "\n story : " + Resumefilebody
                            + "\n image : " + ProfileImagefilebody
                            + "\n cover_img : " + CoverImagefilebody);

                    Log.e("*********** RESULT *********** : ", result);
                    Log.e("*********** RESULT *********** : ", result);
                    Log.e("*********** RESULT *********** : ", result);
                    //Toast.makeText(ProfileEditActivity.this, "Profile Updated Successfully", Toast.LENGTH_SHORT).show();
                    /*SignupData taskkk = new SignupData();
                    taskkk.execute();*/

                     /*sweet alert dialog*/
           /*         new SweetAlertDialog(ProfileEditActivity.this, SweetAlertDialog.SUCCESS_TYPE)
                            .setTitleText("Good job!")
                            .setContentText("Profile Updated Successfully.!!")
                            .show();*/

                    Intent intentImage = new Intent(ProfileEditActivity.this, MainActivity.class);
                    intentImage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                            Intent.FLAG_ACTIVITY_CLEAR_TASK |
                            Intent.FLAG_ACTIVITY_NO_ANIMATION |
                            Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intentImage);
                    finish();

                } else if (result.equalsIgnoreCase("unsuccessful")) {
                    //Toast.makeText(ProfileEditActivity.this, "Profile is not updated.... \n please try again", Toast.LENGTH_SHORT).show();

                    Log.e("xxxxxxxxxxxxxxx All Value : xxxxxxxxxxxxxxx :", " user name: " + validusername
                            + " \n mobile :" + validmobilenumber
                            + "\n address : " + validaddress
                            + "\n line_work : " + validlineofwork
                            + "\n place_work : " + validplaceofwork
                            + "\n goals : " + validgoal
                            + "\n website : " + validwebsites
                            + "\n mobile : " + validmobilenumber
                            + "\n thought : " + validthought
                            + "\n story : " + Resumefilebody
                            + "\n image : " + ProfileImagefilebody
                            + "\n cover_img : " + CoverImagefilebody);

                    new SweetAlertDialog(ProfileEditActivity.this, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops!!")
                            .setContentText("Profile not updated..!! Try again")
                            .show();
                }
            }
        }

    }


}
