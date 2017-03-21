package com.example.ritesh.jointapp;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

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
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import xyz.hanks.library.SmallBang;
import xyz.hanks.library.SmallBangListener;

/**
 * Created by ritesh on 15/8/16.
 */
@SuppressWarnings("deprecation")
public class BrowseJointsSingleUser extends AppCompatActivity implements RatingBar.OnRatingBarChangeListener {

    @BindView(R.id.tv_mystory)
    TextView mystory;
    @BindView(R.id.iv_profileimg_browse_single_user)
    CircleImageView profileimg;
    @BindView(R.id.iv_coverbg)
    ImageView coverbgg;

    @BindView(R.id.profileprogressbar)
    ProgressBar profileimageprogressbar;
    @BindView(R.id.coverprogressbar)
    ProgressBar coverimageprogressbar;

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
    @BindView(R.id.likecounterbrowsesingleuser)
    TextView likeCount;
    @BindView(R.id.likecountertotal)
    TextView likeCountTotal;

    @BindView(R.id.ratingBar1)
    RatingBar ratingBar;
    @BindView(R.id.tv_rating)
    TextView ratingText;

    @BindView(R.id.iv_like_btn)
    ImageView btnlike;

    @BindView(R.id.iv_addfriend)
    ImageView btnaddfriends;

    @BindView(R.id.iv_add_faverate)
    ImageView addFaverate;

   /* @BindView(R.id.tv_addfriend)
    TextView tvaddfriend;*/

    private SmallBang mSmallBang;


    /*left menu slider (Start)*/
    private LinearLayout slidingPanel;
    private boolean isExpanded;
    private int panelWidth1;
    FrameLayout.LayoutParams slidingPanelParameters;
    LinearLayout.LayoutParams headerPanelParameters;
    ImageView menuViewButton;
    /*left menu slider (End)*/

    String singleuserid = "";
    String userlogid = "";
    String resumelUrl = "";
    String profilephotoUrl = "";
    String coverphotoUrl = "";
    String result = "";
    String resultF = "";

    String str_prelike = "Like ", str_friend_request_status = "", str_friend_request_status_NO = "0",
            str_friend_request_status_pending = "2", str_friend_request_status_accept = "1",
            str_faverate_other_userid = "", str_faverate_status = "", str_faverate_added = "1",
            str_faverate_remove = "0", str_faverate_check = "",
            getStr_getUserLikeCount = "", str_getUserLikeStatus = "", str_getUserLikeCount = "", str_getUserId = "",
            str_getOtherUserID = "", str_getFriendUserID = "", str_getFriendCount = "", str_getFriendRequestDate = "";

    String str_getuserid = "", str_getName = "", str_getProfileimage = "", str_getCoverimage = "", str_getthought = "",
            str_getJointid = "", str_getPlaceofWork = "", str_getPassword = "", str_getEmail = "", str_getMobile = "",
            str_getSeflLikeStatus = "", str_YESlike = "1", str_NOlike = "0", str_getPaypalID = "", str_getMyStory = "", str_getGoalsAspiration = "",
            str_getWebSites = "", str_getAddress = "", str_getLineofwork = "";

    private Uri uri;
    ExpandableHeightGridView gridView;
    RelativeLayout rlgrid;
    ArrayList<String> listvideourl;
    ArrayList<String> listvideoid;
    BrowseJointsSingleUserCustomGridviewAdapter videoadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_joints_singleuser);
        ButterKnife.bind(this);
        mSmallBang = SmallBang.attach2Window(this);

        ratingBar.setOnRatingBarChangeListener(this);

        /*Intent for getting the extra string value ("id" for single user from list of users)
        from BrowseJointsUserList.java class    (Start)*/
        Intent intent = getIntent();
        singleuserid = intent.getStringExtra("id");
        Log.e(" ********** Singleuserid **********", "" + singleuserid);
        Log.e(" ********** Singleuserid **********", "" + singleuserid);
        Log.e(" ********** Singleuserid **********", "" + singleuserid);

        AppsConstant.jointpreference = getSharedPreferences(AppsConstant.MyPREFERENCES, Context.MODE_PRIVATE);
        userlogid = AppsConstant.jointpreference.getString("id", "");
        Log.e("**************USER LOGIN id***********:", "" + userlogid);


        /*webserivce Call for get single user data (Start)*/
        if (Utils.isConnected(getApplicationContext())) {
            SingleUserData task = new SingleUserData();
            task.execute();
//            new SingleUserData().execute();        // another way to call asynktask
            Log.e("SingleUserData Task running", "YES");
            Log.e("SingleUserData Task running", "YES");
        } else {

            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View promptView = layoutInflater.inflate(R.layout.activity_wificheck_customalert, null);
            final AlertDialog alertD = new AlertDialog.Builder(BrowseJointsSingleUser.this).create();
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
        /*webserivce Call for get single user data (End)*/


        btnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View lv) {
                like(lv);
            }
        });


        btnaddfriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addfriendtask(v);

            }
        });

        addFaverate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                likeFaverate(v);
            }
        });

        mystory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URLResume = resumelUrl;
                Intent intentresume = new Intent(BrowseJointsSingleUser.this, WebviewResume.class);
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
                Intent intentprofileimage = new Intent(BrowseJointsSingleUser.this, WebViewProfileImage.class);
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
                Intent intentcoverimage = new Intent(BrowseJointsSingleUser.this, WebViewCoverImage.class);
                intentcoverimage.putExtra("URLC", URLCoverimage);

                Log.e("********** URLCoverimage ********** :", " " + coverphotoUrl);
                Log.e("********** URLCoverimage ********** :", " " + coverphotoUrl);
                Log.e("********** URLCoverimage ********** :", " " + coverphotoUrl);

                startActivity(intentcoverimage);
            }
        });

        /*image setting by universal image loader */
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(BrowseJointsSingleUser.this).defaultDisplayImageOptions(defaultOptions).build();
        ImageLoader.getInstance().init(config);
        /*image settin by universal image loader */

        /************************* Girdview data (Start)**************************/
        rlgrid = (RelativeLayout) findViewById(R.id.rl_grid);
        gridView = (ExpandableHeightGridView) findViewById(R.id.grid);
        gridView.setAdapter(videoadapter);
        gridView.setExpanded(true);
        /**************************girdview data (Ends)**************************/


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

    /*add friend (Start)*/
    public void like(View view) {
        mSmallBang.bang(view);
        mSmallBang.setmListener(new SmallBangListener() {

            @Override
            public void onAnimationStart() {
                btnlike.setImageResource(R.drawable.ic_unlikee);
            }

            @Override
            public void onAnimationEnd() {
                if (Utils.isConnected(getApplicationContext())) {
                    UserClickLike task = new UserClickLike();
                    task.execute();
                    Log.e("UserClickLike Task running", "YES");
                    Log.e("UserClickLike Task running", "YES");
                } else {
                    LayoutInflater layoutInflater = LayoutInflater.from(BrowseJointsSingleUser.this);
                    View promptView = layoutInflater.inflate(R.layout.activity_wificheck_customalert, null);
                    final AlertDialog alertD = new AlertDialog.Builder(BrowseJointsSingleUser.this).create();
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
    }

    public void addfriendtask(View view) {
        mSmallBang.bang(view);
        mSmallBang.setmListener(new SmallBangListener() {

            @Override
            public void onAnimationStart() {
                btnaddfriends.setImageResource(R.drawable.ic_add_friend);

            }

            @Override
            public void onAnimationEnd() {
                /*Toast.makeText(BrowseJointsSingleUser.this, "Friend Request Send!", Toast.LENGTH_SHORT).show();*/
                if (Utils.isConnected(getApplicationContext())) {
                    FriendRequestSend task = new FriendRequestSend();
                    task.execute();
                    Log.e("FriendRequestSend Task running", "YES");
                    Log.e("FriendRequestSend Task running", "YES");
                } else {

                    LayoutInflater layoutInflater = LayoutInflater.from(BrowseJointsSingleUser.this);
                    View promptView = layoutInflater.inflate(R.layout.activity_wificheck_customalert, null);
                    final AlertDialog alertD = new AlertDialog.Builder(BrowseJointsSingleUser.this).create();
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
    }


    public void likeFaverate(View view) {
        mSmallBang.bang(view);
        mSmallBang.setmListener(new SmallBangListener() {

            @Override
            public void onAnimationStart() {
                addFaverate.setImageResource(R.drawable.ic_heart_off);

            }

            @Override
            public void onAnimationEnd() {
                /*Toast.makeText(BrowseJointsSingleUser.this, "Friend Request Send!", Toast.LENGTH_SHORT).show();*/
                if (Utils.isConnected(getApplicationContext())) {
                    AddFaverate task = new AddFaverate();
                    task.execute();
                    Log.e("AddFaverate Task running", "YES");
                    Log.e("AddFaverate Task running", "YES");
                } else {

                    LayoutInflater layoutInflater = LayoutInflater.from(BrowseJointsSingleUser.this);
                    View promptView = layoutInflater.inflate(R.layout.activity_wificheck_customalert, null);
                    final AlertDialog alertD = new AlertDialog.Builder(BrowseJointsSingleUser.this).create();
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
    }
    /*add friend (End)*/

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

        final int numStars = ratingBar.getNumStars();
        ratingText.setText(rating + "/" + numStars);
        //    ratingText.setText(""+this.ratingBar.getRating());

        Log.e("********** Rating Value **********:", "" + rating + "/" + numStars);
        Log.e("********** Rating Value **********:", "" + rating + "/" + numStars);
        Log.e("********** Rating Value **********:", "" + rating + "/" + numStars);
        /*Toast.makeText(BrowseJointsSingleUser.this, rating + "/" + numStars, Toast.LENGTH_LONG).show();
        Toast.makeText(BrowseJointsSingleUser.this,String.valueOf(ratingBar.getRating()),Toast.LENGTH_SHORT).show();*/

    }


    /*webserivce Calling for get single user Data Show (Start)*/
    class SingleUserData extends AsyncTask<String, Void, String> {

        boolean iserror = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            listvideourl = new ArrayList<String>();
            listvideoid = new ArrayList<String>();
            Log.e("SingleUserData task running :", "yes");
            Log.e("SingleUserData" + "singleuserid :", "" + singleuserid + "\t\t" + "userlogid :" + "" + userlogid);
            Log.e("SingleUserData" + "singleuserid :", "" + singleuserid + "\t\t" + "userlogid :" + "" + userlogid);

        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(HttpUrlPath.urlPath + "get_user?");

            try {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("user_id", singleuserid));
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
                    resumelUrl = Object1.getString("story");
                    str_getGoalsAspiration = Object1.getString("goals");
                    str_getWebSites = Object1.getString("website");
                    str_getProfileimage = Object1.getString("image");
                    profilephotoUrl = Object1.getString("image");
                    str_getCoverimage = Object1.getString("cover_img");
                    coverphotoUrl = Object1.getString("cover_img");
                    getStr_getUserLikeCount = Object1.getString("like_count");
                    str_getSeflLikeStatus = Object1.getString("like");
                    str_getPaypalID = Object1.getString("paypal_id");

                } else {
                    if (result.equalsIgnoreCase("unsuccessfully")) {
                        Toast.makeText(BrowseJointsSingleUser.this, "unsuccessfully  ", Toast.LENGTH_SHORT).show();
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
            Log.e("SingleUserData"
                    + "\n" + "str_getuserid :", "" + str_getuserid
                    + "\n" + "str_getName :" + "" + str_getName
                    + "\n" + "str_getMobile :" + "" + str_getMobile
                    + "\n" + "str_getJointid :" + "" + str_getJointid
                    + "\n" + "str_getAddress :" + "" + str_getAddress
                    + "\n" + "str_getLineofwork :" + "" + str_getLineofwork
                    + "\n" + "str_getPlaceofWork :" + "" + str_getPlaceofWork
                    + "\n" + "str_getPassword :" + "" + str_getPassword
                    + "\n" + "str_getEmail :" + "" + str_getEmail
                    + "\n" + "str_getMyStory :" + "" + str_getMyStory
                    + "\n" + "resumelUrl :" + "" + resumelUrl
                    + "\n" + "str_getGoalsAspiration :" + "" + str_getGoalsAspiration
                    + "\n" + "str_getWebSites :" + "" + str_getWebSites
                    + "\n" + "str_getProfileimage :" + "" + str_getProfileimage
                    + "\n" + "profilephotoUrl :" + "" + profilephotoUrl
                    + "\n" + "str_getCoverimage :" + "" + str_getCoverimage
                    + "\n" + "coverphotoUrl :" + "" + coverphotoUrl
                    + "\n" + "getStr_getUserLikeCount :" + "" + getStr_getUserLikeCount
                    + "\n" + "str_getSeflLikeStatus :" + "" + str_getSeflLikeStatus
                    + "\n" + "str_getPaypalID :" + "" + str_getPaypalID);

            /*Toast.makeText(BrowseJointsSingleUser.this, "getStr_getUserLikeCount :" + "" + getStr_getUserLikeCount,
                    Toast.LENGTH_SHORT).show();*/

            Log.e("getStr_getUserLikeCount :", "" + getStr_getUserLikeCount);
            Log.e("getStr_getUserLikeCount :", "" + getStr_getUserLikeCount);
            likeCountTotal.setText(getStr_getUserLikeCount);
            Log.e("getStr_getUserLikeCount :", "" + getStr_getUserLikeCount);
            Log.e("getStr_getUserLikeCount :", "" + getStr_getUserLikeCount);

            if (listvideoid.size() > 0) {
                Log.e(" ********** listsize ********** ", "" + listvideoid);
                videoadapter = new BrowseJointsSingleUserCustomGridviewAdapter(BrowseJointsSingleUser.this);
                videoadapter.notifyDataSetChanged();
                gridView.invalidateViews();
                videoadapter.notifyDataSetInvalidated();
                gridView.setAdapter(videoadapter);


                Log.e(" ********** listvideoid.size() > 0 **********", "YES");
                Log.e(" ********** listvideoid.size() > 0 **********", "YES");
                Log.e(" ********** listvideoid.size() > 0 **********", "YES");
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

            if (Utils.isConnected(getApplicationContext())) {
                SingleUserAllStatusData task = new SingleUserAllStatusData();
                task.execute();
//            new SingleUserData().execute();        // another way to call asynktask
                Log.e("SingleUserLikeStatusData Task running", "YES");
                Log.e("SingleUserLikeStatusData Task running", "YES");
            } else {

                LayoutInflater layoutInflater = LayoutInflater.from(BrowseJointsSingleUser.this);
                View promptView = layoutInflater.inflate(R.layout.activity_wificheck_customalert, null);
                final AlertDialog alertD = new AlertDialog.Builder(BrowseJointsSingleUser.this).create();
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


             /*webserivce Call for Status of Friend Request Send To The single user (Start)*/
            if (Utils.isConnected(getApplicationContext())) {
                FriendRequestSendCheck task = new FriendRequestSendCheck();
                task.execute();
                Log.e("FriendRequestSend Task running", "YES");
                Log.e("FriendRequestSend Task running", "YES");
            } else {

                LayoutInflater layoutInflater = LayoutInflater.from(BrowseJointsSingleUser.this);
                View promptView = layoutInflater.inflate(R.layout.activity_wificheck_customalert, null);
                final AlertDialog alertD = new AlertDialog.Builder(BrowseJointsSingleUser.this).create();
           /* prevent alert dialog from outside click and back button click (Star)*/
                alertD.setCanceledOnTouchOutside(false);
                alertD.setCancelable(false);
           /* prevent alert dialog from outside click and back button click (Star)*/
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
        /*webserivce Call for Status of Friend Request Send To The single user (End)*/


        }


    }
    /*webserivce Calling for get single user Data Show (End)*/


    class SingleUserAllStatusData extends AsyncTask<String, Void, String> {

        boolean iserror = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("SingleUserLikeStatusData task running :", "yes");
            Log.e("SingleUserLikeStatusData  " + "singleuserid :", "" + singleuserid + "\t\t" + "  userlogid :" + "" + userlogid);
            Log.e("SingleUserLikeStatusData  " + "singleuserid :", "" + singleuserid + "\t\t" + "  userlogid :" + "" + userlogid);

        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(HttpUrlPath.urlPath + "get_user?");

            try {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("user_id", singleuserid));
                nameValuePairs.add(new BasicNameValuePair("my_user_id", userlogid));
                post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = client.execute(post);
                String obj = EntityUtils.toString(response.getEntity());

                Log.e("************ All SingleUserLikeData DATA VALUE ************", "" + obj);
/* ********** ********** ********** ********** ********** PRINT ALL INCOMING DATA (END)********** ********** ********** ********** ********** */

/* ********** ********** ********** ********** ********** ALL INCOMING VIDEO DATA (START) ********** ********** ********** ********** ********** */
                JSONObject ParentObject = new JSONObject(obj);
                String likedata = ParentObject.getString("all_data");
/* ********** ********** ********** ********** ********** ALL INCOMING VIDEO DATA (END) ********** ********** ********** ********** ********** */

                Log.e("************ All INCOMING SingleUserLikeData ************", " " + likedata);

                JSONObject Object1 = new JSONObject(likedata);
                result = Object1.getString("result");
                Log.e("************Json All data*******************", "" + likedata);
                if (result.equalsIgnoreCase("successful")) {
                    getStr_getUserLikeCount = Object1.getString("like_count");
                    str_getSeflLikeStatus = Object1.getString("like");
                    str_friend_request_status = Object1.getString("friend");
                    str_faverate_check = Object1.getString("favorite");

                } else {
                    if (result.equalsIgnoreCase("unsuccessfully")) {
                        Toast.makeText(BrowseJointsSingleUser.this, "unsuccessfully  ", Toast.LENGTH_SHORT).show();
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
            Log.e("SingleUserLikeData with counter"
                    + "\n" + "getStr_getUserLikeCount :", "" + getStr_getUserLikeCount
                    + "\n" + "str_getSeflLikeStatus :" + "" + str_getSeflLikeStatus
                    + "\n" + "str_faverate_check :" + "" + str_faverate_check
                    + "\n" + "str_friend_request_status :" + "" + str_friend_request_status);


            if (!iserror) {
                if (str_getSeflLikeStatus.equals(str_YESlike)) {
                    btnlike.setImageResource(R.drawable.ic_like);
                    Log.e("check on start like str_getSeflLikeStatus :", "Yes Like  " + "" + str_YESlike + " 1 ");
                    Log.e("check on start like str_getSeflLikeStatus :", "Yes Like  " + "" + str_YESlike + " 1 ");

                } else if (str_getSeflLikeStatus.equals(str_NOlike)) {
                    btnlike.setImageResource(R.drawable.ic_unlikee);
                    Log.e("check on start like str_getSeflLikeStatus :", "No Like  " + "" + str_NOlike + " 0 ");
                    Log.e("check on start like str_getSeflLikeStatus :", "No Like  " + "" + str_NOlike + " 0 ");

                }

                if (str_friend_request_status.equals(str_friend_request_status_NO)) {

                    Log.e("str_friend_request_status :", "" + str_friend_request_status_NO);
                    btnaddfriends.setImageResource(R.drawable.ic_add_friend);

                } else if (str_friend_request_status.equals(str_friend_request_status_pending)) {

                    Log.e("str_friend_request_status :", "" + str_friend_request_status_pending);
                    btnaddfriends.setImageResource(R.drawable.ic_add_friendrequestpending);

                } else if (str_friend_request_status.equals(str_friend_request_status_accept)) {

                    Log.e("str_friend_request_status :", "" + str_friend_request_status_accept);
                    btnaddfriends.setImageResource(R.drawable.ic_add_friendrequestaccept);

                }

                if (str_faverate_check.equalsIgnoreCase(str_faverate_added)) {

                    Log.e("str_faverate_check :", "" + str_faverate_added);
                    addFaverate.setImageResource(R.drawable.ic_heart_on);


                } else if (str_faverate_check.equalsIgnoreCase(str_faverate_remove)) {

                    Log.e("str_faverate_check :", "" + str_faverate_remove);
                    addFaverate.setImageResource(R.drawable.ic_heart_off);

                }

            } else {
                Log.e("********* User id and Other User id *********", "NOT Success");
                Log.e("********* User id and Other User id *********", "NOT Success");
                Log.e("********* User id and Other User id *********", "NOT Success");
                btnlike.setImageResource(R.drawable.ic_unlikee);
                Toast.makeText(getApplicationContext(), "Oops!! Please check server connection .",
                        Toast.LENGTH_SHORT).show();
            }

        }


    }


    /*webserivce Calling for get single user Like Count After Click (Start)*/
    class UserClickLike extends AsyncTask<String, Void, String> {

        boolean iserror = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.e("UserLike task running :", "Yes");
            Log.e("UserLike task running :", "Yes");
            Log.e("userlogid :", "" + userlogid);
            Log.e("Liked singleuserid :", "" + singleuserid);
        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(HttpUrlPath.urlPath + "add_update_like?");
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("user_id", userlogid));
                nameValuePairs.add(new BasicNameValuePair("other_user_id", singleuserid));

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
                    str_getUserId = jobect.getString("user_id");
                    str_getOtherUserID = jobect.getString("other_user_id");
                    str_getUserLikeCount = jobect.getString("like_count");
                    str_getUserLikeStatus = jobect.getString("like");
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
            Log.e("******* ID RETURN BY SERVER *******", "" + str_getUserId);
            Log.e("******* OtherUserID RETURN BY SERVER *******", "" + str_getOtherUserID);
            Log.e("******* UserLikeCount RETURN BY SERVER *******", "" + str_getUserLikeCount);
            Log.e("******* UserLikeStatus RETURN BY SERVER *******", "" + str_getUserLikeStatus);

            if (!iserror) {
                if (str_getUserLikeStatus.equals("1")) {
                    Log.e("check on click like str_getUserLikeStatus :", "Yes Like  " + "1");
                    Log.e("check on click like str_getUserLikeStatus :", "Yes Like  " + "1");
                    likeCountTotal.setText(str_getUserLikeCount);
                    btnlike.setImageResource(R.drawable.ic_like);
                    Toast.makeText(BrowseJointsSingleUser.this, "Thanks for Your FeedBack", Toast.LENGTH_SHORT).show();

                } else {
                    Log.e("check on click like str_getUserLikeStatus :", "Yes DislikeLike  " + "0");
                    likeCountTotal.setText(str_getUserLikeCount);
                    btnlike.setImageResource(R.drawable.ic_unlikee);
                    Toast.makeText(getApplicationContext(), "Thanks for Your FeedBack", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.e("********* User id and Other User id *********", "NOT Success");
                Log.e("********* User id and Other User id *********", "NOT Success");
                Log.e("********* User id and Other User id *********", "NOT Success");
                btnlike.setImageResource(R.drawable.ic_unlikee);
                Toast.makeText(getApplicationContext(), "Oops!! Please check server connection .",
                        Toast.LENGTH_SHORT).show();
            }

        }

    }
    /*webserivce Calling for get single user Like Count After Click (End)*/

    /*webserivce Calling for get single user Like Count Onstart (Start)*/
    class UserLikeCount extends AsyncTask<String, Void, String> {

        boolean iserror = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.e("UserLikeCount task running :", "Yes");
            Log.e("UserLikeCount task running :", "Yes");
            Log.e("userlogid :", "" + userlogid);
            Log.e("Liked singleuserid :", "" + singleuserid);


        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(HttpUrlPath.urlPath + "add_update_like?");
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("user_id", userlogid));
                nameValuePairs.add(new BasicNameValuePair("my_user_id", singleuserid));

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
                    str_getUserId = jobect.getString("user_id");
                    Log.e("******* ID RETURN BY SERVER *******", "" + str_getUserId);
                    str_getOtherUserID = jobect.getString("other_user_id");
                    Log.e("******* OtherUserID RETURN BY SERVER *******", "" + str_getOtherUserID);
                    str_getUserLikeCount = jobect.getString("like_count");
                    Log.e("******* UserLikeCount RETURN BY SERVER *******", "" + str_getUserLikeCount);
                    getStr_getUserLikeCount = jobect.getString("like");
                    Log.e("******* UserLikeCount RETURN BY SERVER *******", "" + getStr_getUserLikeCount);
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

                    likeCountTotal.setText(str_getUserLikeCount);
                    Log.e("value of str_getGoalsAspiration: ", "" + str_getUserLikeCount);
//                        Toast.makeText(BrowseJointsSingleUser.this, "Thanks for Your FeedBack ", Toast.LENGTH_SHORT).show();

                } else {

                    Toast.makeText(getApplicationContext(), "Ooops Error", Toast.LENGTH_SHORT).show();
                }
            } else {
                Log.e("********* User id and Other User id *********", "NOT Success");
                Log.e("********* User id and Other User id *********", "NOT Success");
                Log.e("********* User id and Other User id *********", "NOT Success");

                Toast.makeText(getApplicationContext(), "Oops!! Please check server connection .",
                        Toast.LENGTH_SHORT).show();
            }


            /*webserivce Call for Status of Friend Request Send To The single user (Start)*/
            if (Utils.isConnected(getApplicationContext())) {
                FriendRequestSendCheck task = new FriendRequestSendCheck();
                task.execute();
                Log.e("FriendRequestSend Task running", "YES");
                Log.e("FriendRequestSend Task running", "YES");
            } else {

                LayoutInflater layoutInflater = LayoutInflater.from(BrowseJointsSingleUser.this);
                View promptView = layoutInflater.inflate(R.layout.activity_wificheck_customalert, null);
                final AlertDialog alertD = new AlertDialog.Builder(BrowseJointsSingleUser.this).create();
           /* prevent alert dialog from outside click and back button click (Star)*/
                alertD.setCanceledOnTouchOutside(false);
                alertD.setCancelable(false);
           /* prevent alert dialog from outside click and back button click (Star)*/
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
        /*webserivce Call for Status of Friend Request Send To The single user (End)*/

        }

    }
    /*webserivce Calling for get single user Like Count Onstart (End)*/

    /*webserivce Calling for Send Friend Request to the single user (Start)*/
    class FriendRequestSend extends AsyncTask<String, Void, String> {

        boolean iserror = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.e("FriendRequestSend task running :", "Yes");
            Log.e("FriendRequestSend task running :", "Yes");

            Log.e("userlogid :", "" + userlogid);
            Log.e("Liked singleuserid :", "" + singleuserid);

        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(HttpUrlPath.urlPath + "add_friend?");
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("user_id", userlogid));
                nameValuePairs.add(new BasicNameValuePair("friend_id", singleuserid));

                post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = client.execute(post);
                String object = EntityUtils.toString(response.getEntity());

                Log.e("************ All INCOMING Friend DATA VALUE ************", "" + object);
                Log.e("************ All INCOMING Friend DATA VALUE ************", "" + object);
                Log.e("************ All INCOMING Friend DATA VALUE ************", "" + object);

                //JSONArray js = new JSONArray(object);
                JSONObject jobect = new JSONObject(object);
                resultF = jobect.getString("result");
                if (resultF.equalsIgnoreCase("successful")) {
                    str_getUserId = jobect.getString("user_id");
                    Log.e("******* ID RETURN BY SERVER *******", "" + str_getUserId);

                    str_getFriendUserID = jobect.getString("friend_id");
                    Log.e("******* str_getFriendUserID RETURN BY SERVER *******", "" + str_getFriendUserID);

                    str_getFriendCount = jobect.getString("friend");
                    Log.e("******* FriendCount RETURN BY SERVER *******", "" + str_getFriendCount);
                } else if (resultF.equalsIgnoreCase("already friend")) {
                    Log.e("******* FriendCount already send****** :", "" + resultF);
                }

            } catch (Exception e) {
                Log.v("22", "22" + e.getMessage());
                e.printStackTrace();
                iserror = true;
            }
            return resultF;
        }

        @Override
        protected void onPostExecute(String result1) {
            super.onPostExecute(result1);

            if (!iserror) {
                if (resultF.equalsIgnoreCase("successful")) {
                    Toast.makeText(BrowseJointsSingleUser.this, "Friend Request Send...! ", Toast.LENGTH_SHORT).show();

//                    tvaddfriend.setText("Friend Request Send ");
                    btnaddfriends.setImageResource(R.drawable.ic_add_friendrequestpending);
                    Log.e(" Friend Request Send :", "Success");


                } else if (resultF.equalsIgnoreCase("already send")) {

                    btnaddfriends.setImageResource(R.drawable.ic_add_friendrequestpending);
                    Toast.makeText(getApplicationContext(), "Friend Request Already Send...!", Toast.LENGTH_SHORT).show();

//                    tvaddfriend.setText("Friend Request Send ");
                    Log.e(" Friend Request :", "Already Send");
                    Log.e(" Friend Request Status :", "" + resultF);
                    Log.e(" Friend Request Send :", "Already Send");

                }
            } else {
                Log.e("********* User id and Friend User id *********", "NOT Success");
                Log.e("********* User id and Friend User id *********", "NOT Success");
                Log.e("********* User id and Friend User id *********", "NOT Success");

//                tvaddfriend.setText("Add Friend");
                btnaddfriends.setImageResource(R.drawable.ic_add_friend);
                Toast.makeText(getApplicationContext(), "Oops!! Please check server connection .",
                        Toast.LENGTH_SHORT).show();
            }

//            tvaddfriend.setText("Friend Request Send ");
            Log.e("value of result: ", "" + resultF);
            Log.e("value of result: ", "" + resultF);
            Log.e("value of result: ", "" + resultF);


        }

    }


    class AddFaverate extends AsyncTask<String, Void, String> {

        boolean iserror = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.e("AddFaverate task running :", "Yes");
            Log.e("AddFaverate task running :", "Yes");

            Log.e("userlogid :", "" + userlogid);
            Log.e("singleuserid :", "" + singleuserid);

        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(HttpUrlPath.urlPath + "add_update_favorite?");
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("user_id", userlogid));
                nameValuePairs.add(new BasicNameValuePair("other_user_id", singleuserid));

                post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = client.execute(post);
                String object = EntityUtils.toString(response.getEntity());
/* ********** ********** ********** ********** ********** PRINT ALL INCOMING DATA (START)********** ********** ********** ********** ********** */
                System.out.println("########## favorite OBJECT VALUE ##########" + object);
                System.out.println("******favorite  object ***** :" + object);

                Log.e("**** favorite INCOMING  VALUE ****", "" + object);
                Log.e("**** favorite INCOMING  VALUE ****", "" + object);
                Log.e("**** favorite INCOMING  VALUE ****", "" + object);
/* ********** ********** ********** ********** ********** PRINT ALL INCOMING DATA (END)********** ********** ********** ********** ********** */

                //JSONArray js = new JSONArray(object);
                JSONObject jobect = new JSONObject(object);
                result = jobect.getString("result");
                if (result.equalsIgnoreCase("successful")) {
                    str_getUserId = jobect.getString("user_id");
                    Log.e("******* user ID RETURN BY SERVER *******", "" + str_getUserId);

                    str_faverate_other_userid = jobect.getString("other_user_id");
                    Log.e("******* Faverate Other Userid RETURN BY SERVER *******", "" + str_faverate_other_userid);

                    str_faverate_status = jobect.getString("favorite");
                    Log.e("******* str_faverate_status RETURN BY SERVER *******", "" + str_faverate_status);

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
                if (str_faverate_status.equalsIgnoreCase(str_faverate_added)) {
                    Toast.makeText(BrowseJointsSingleUser.this, "Friend Added Faverate...! ", Toast.LENGTH_SHORT).show();

//                    tvaddfriend.setText("Friend Request Send ");
                    addFaverate.setImageResource(R.drawable.ic_heart_on);
                    Log.e(" Friend Added Faverate :", "Success");
                    Log.e(" Friend Added Faverate :", "Success");


                } else if (str_faverate_status.equalsIgnoreCase(str_faverate_remove)) {
                    Toast.makeText(getApplicationContext(), "Friend Faverate Removed..!", Toast.LENGTH_SHORT).show();

//                    tvaddfriend.setText("Friend Request Send ");
                    addFaverate.setImageResource(R.drawable.ic_heart_off);
                    Log.e(" Friend Removed Faverate :", "Success");
                    Log.e(" Friend Removed Faverate :", "Success");

                }
            } else {
                Log.e("********* User id and Friend User id *********", "NOT Success");
                Log.e("********* User id and Friend User id *********", "NOT Success");
                Log.e("********* User id and Friend User id *********", "NOT Success");

//                tvaddfriend.setText("Add Friend");
                addFaverate.setImageResource(R.drawable.ic_heart_off);
                Toast.makeText(getApplicationContext(), "Oops!! Please check server connection .",
                        Toast.LENGTH_SHORT).show();
            }

//            tvaddfriend.setText("Friend Request Send ");
            Log.e("value of result: ", "" + result);
            Log.e("value of result: ", "" + result);
            Log.e("value of result: ", "" + result);


        }

    }
    /*webserivce Calling for Send Friend Request to the single user (End)*/

    /*webserivce Calling for Send Friend Request to the single user (Start)*/
    class FriendRequestSendCheck extends AsyncTask<String, Void, String> {

        boolean iserror = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(HttpUrlPath.urlPath + "add_friend?");
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("user_id", userlogid));
                nameValuePairs.add(new BasicNameValuePair("friend_id", singleuserid));

                post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                HttpResponse response = client.execute(post);
                String object = EntityUtils.toString(response.getEntity());
/* ********** ********** ********** ********** ********** PRINT ALL INCOMING DATA (START)********** ********** ********** ********** ********** */
                Log.e("************ All INCOMING DATA VALUE ************", "" + object);
                Log.e("************ All INCOMING DATA VALUE ************", "" + object);
                Log.e("************ All INCOMING DATA VALUE ************", "" + object);
/* ********** ********** ********** ********** ********** PRINT ALL INCOMING DATA (END)********** ********** ********** ********** ********** */

                //JSONArray js = new JSONArray(object);
                JSONObject jobect = new JSONObject(object);
                result = jobect.getString("result");
                if (result.equalsIgnoreCase("already friend")) {

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
                    btnaddfriends.setBackgroundResource(R.drawable.ic_add_friendrequestpending);

                } else if (result.equalsIgnoreCase("already friend")) {
                    // Toast.makeText(getApplicationContext(), "Friend Request Already Send...!", Toast.LENGTH_SHORT).show();

//                    tvaddfriend.setText("Friend Request Send ");
                    btnaddfriends.setBackgroundResource(R.drawable.ic_add_friendrequestpending);
                    Log.e(" Friend Request Send :", "Already Send");
                    Log.e(" Friend Request Send :", "Already Send");
                    Log.e(" Friend Request Send :", "Already Send");

                }
            } else {
                Log.e("********* User id and Friend User id *********", "NOT Success");
                Log.e("********* User id and Friend User id *********", "NOT Success");
                Log.e("********* User id and Friend User id *********", "NOT Success");

//                tvaddfriend.setText("Add Friend");
                Toast.makeText(getApplicationContext(), "Oops!! Please check server connection .",
                        Toast.LENGTH_SHORT).show();
            }

        }

    }
    /*webserivce Calling for Send Friend Request to the single user (End)*/

    /*********************
     * gridview adapter (Start)
     *********************/
    public class BrowseJointsSingleUserCustomGridviewAdapter extends BaseAdapter {
        Context c;

        public BrowseJointsSingleUserCustomGridviewAdapter(Context con) {
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
            row = inflater.inflate(R.layout.activity_singleuser_videogridview_items, parent, false);
            final VideoView videoView = (VideoView) row.findViewById(R.id.grid_video);
            uri = Uri.parse(listvideourl.get(position));

            // create an object of media controller
            final MediaController mediaController = new MediaController(BrowseJointsSingleUser.this);

            // set media controller object for a video view
            mediaController.setAnchorView(videoView);

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
                    Log.e(" **************** ITEM CLICKED ****************", "" + position);
                    startActivity(intent);
                    return false;
                }
            });

            videoView.setVideoURI(uri);
            Log.e(" ********** VideoURI ********** ", " " + uri);
            videoView.setFocusable(false);
            videoView.seekTo(1000);

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
    /********************* gridview adapter (End)     **********************/
}
