package com.example.ritesh.jointapp;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ritesh.jointapp.constant.AppsConstant;
import com.example.ritesh.jointapp.constant.HttpUrlPath;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

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

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ritesh on 21/7/16.
 */
public class ProfilePreviewActivity extends AppCompatActivity {

    /*image capture declaration (Start)*/
    RelativeLayout editprofile;
    private CircleImageView profileimg;  // set image for profile
    private ImageView coverbgg;
    private ImageView menuViewButton;
    ProgressBar profileimageprogressbar, coverimageprogressbar;

    // Declare
    private LinearLayout slidingPanel;
    private boolean isExpanded;
    private DisplayMetrics metrics;
    private RelativeLayout headerPanel;
    private int panelWidth1;

    FrameLayout.LayoutParams slidingPanelParameters;
    LinearLayout.LayoutParams headerPanelParameters;

    TextView username, jointsid, mobilenumber, address, lineofwork,
            placeofwork, mystory, goalapirations, websites, thought;

    boolean iserror = false;

    String str_getuserid = " ",
            result = " ",
            resumelUrl = " ",
            profilephotoUrl = " ",
            userlogid = " ",
            coverphotoUrl = " ",
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
            str_getLineofwork = " ";

    /*oncreate (start)*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_preview);

        AppsConstant.jointpreference = getSharedPreferences(AppsConstant.MyPREFERENCES, Context.MODE_PRIVATE);
        userlogid = AppsConstant.jointpreference.getString("id", null);
        Log.e("**************USER LOGIN id ***********:", "" + userlogid);

        profileimageprogressbar = (ProgressBar) findViewById(R.id.profileprogressbar);
        coverimageprogressbar = (ProgressBar) findViewById(R.id.coverprogressbar);

        /*image settin by universal image loader */
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(ProfilePreviewActivity.this).defaultDisplayImageOptions(defaultOptions).build();
        ImageLoader.getInstance().init(config);
        /*image settin by universal image loader */


        if (Utils.isConnected(getApplicationContext())) {
            SignupData task = new SignupData();
            task.execute();
            Log.e("Signupdata Task running", "YES");
            Log.e("Signupdata Task running", "YES");
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

        username = (TextView) findViewById(R.id.tv_username);
        jointsid = (TextView) findViewById(R.id.tv_jointsid);
        mobilenumber = (TextView) findViewById(R.id.tv_mobilenumber);
        address = (TextView) findViewById(R.id.tv_address);
        lineofwork = (TextView) findViewById(R.id.tv_lineofwork);
        placeofwork = (TextView) findViewById(R.id.tv_placeofwork);
        mystory = (TextView) findViewById(R.id.tv_mystory);
        goalapirations = (TextView) findViewById(R.id.tv_goal);
        websites = (TextView) findViewById(R.id.tv_websites);
        thought = (TextView) findViewById(R.id.tv_thought);
        /*get profile information from sharedpreferences (End)*/

        profileimg = (CircleImageView) findViewById(R.id.profileimg);
        coverbgg = (ImageView) findViewById(R.id.iv_coverbg);


        editprofile = (RelativeLayout) findViewById(R.id.rl_editprofiletext);

        /*image capture event from imageview click (Start)*/
        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editprofile = new Intent(ProfilePreviewActivity.this, ProfileEditActivity.class);
                startActivity(editprofile);
                Log.e("   GO TO EDIT PROFILE  ", "      *****  YES  *****      ");
                /*Toast.makeText(getApplicationContext(), "WelCome to Edit Profile",
                        Toast.LENGTH_SHORT).show();*/
            }
        });

        mystory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URLResume = resumelUrl;
                Intent intentresume = new Intent(ProfilePreviewActivity.this, WebviewResume.class);
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
                Intent intentprofileimage = new Intent(ProfilePreviewActivity.this, WebViewProfileImage.class);
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
                Intent intentcoverimage = new Intent(ProfilePreviewActivity.this, WebViewCoverImage.class);
                intentcoverimage.putExtra("URLC", URLCoverimage);

                Log.e("********** URLCoverimage ********** :", " " + coverphotoUrl);
                Log.e("********** URLCoverimage ********** :", " " + coverphotoUrl);
                Log.e("********** URLCoverimage ********** :", " " + coverphotoUrl);

                startActivity(intentcoverimage);
            }
        });

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

    }

    class SignupData extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            signuprogress2.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();
//            HttpPost post = new HttpPost(HttpUrlPath.urlPath + "get_user?");
            HttpPost post = new HttpPost(HttpUrlPath.urlPath + "get_user?user_id=" + userlogid);
            System.out.println("#####object=" + HttpUrlPath.urlPath +
                    "get_user?" +
                    "user_id=" + str_getuserid +
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
                    str_getthought = alldataObject.getString("thought");

                    str_getMyStory = alldataObject.getString("story");
                    resumelUrl = alldataObject.getString("story");

                    str_getGoalsAspiration = alldataObject.getString("goals");
                    str_getWebSites = alldataObject.getString("website");

                    str_getProfileimage = alldataObject.getString("image");
                    profilephotoUrl = alldataObject.getString("image");

                    str_getCoverimage = alldataObject.getString("cover_img");
                    coverphotoUrl = alldataObject.getString("cover_img");
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

            username.setText(str_getName);
            Log.e("value of str_getName: ", "" + str_getName);

            mobilenumber.setText(str_getMobile);
            Log.e("value of str_getMobile: ", "" + str_getMobile);

            jointsid.setText(str_getJointid);
            Log.e("value of str_getJointid: ", "" + str_getJointid);

            address.setText(str_getAddress);
            Log.e("value of str_getAddress: ", "" + str_getAddress);

            lineofwork.setText(str_getLineofwork);
            Log.e("value of str_getLineofwork: ", "" + str_getLineofwork);

            placeofwork.setText(str_getPlaceofWork);
            Log.e("value of str_getPlaceofWork: ", "" + str_getPlaceofWork);

            mystory.setText(str_getMyStory.substring(str_getMyStory.lastIndexOf("/") + 1));
            Log.e("value of str_getMyStory: ", "" + str_getMyStory);

            goalapirations.setText(str_getGoalsAspiration);
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