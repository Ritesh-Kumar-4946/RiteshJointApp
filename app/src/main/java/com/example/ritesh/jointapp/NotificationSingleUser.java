package com.example.ritesh.jointapp;

/**
 * Created by ritesh on 27/10/16.
 */

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
import com.like.LikeButton;
import com.like.OnLikeListener;
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


public class NotificationSingleUser extends AppCompatActivity implements RatingBar.OnRatingBarChangeListener {

    @BindView(R.id.tv_mystory)
    TextView mystory;
    @BindView(R.id.profileimg)
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
    @BindView(R.id.likecounternotificationuser)
    TextView likeCount;

    @BindView(R.id.ratingBar1)
    RatingBar ratingBar;
    @BindView(R.id.tv_rating)
    TextView ratingText;

    @BindView(R.id.thumb_button)
    LikeButton thumbButton;

/*    @BindView(R.id.addfriend)
    ImageView addfriends;

    @BindView(R.id.tv_addfriend)
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

    String str_getUserLike = "", str_getUserLikeCount = "", str_getUserId = "",
            str_getOtherUserID = "", str_getFriendUserID = "", str_getFriendCount = "", str_getFriendRequestDate = "";

    String str_getuserid = "", str_getName = "", str_getProfileimage = "", str_getCoverimage = "", str_getthought = "",
            str_getJointid = "", str_getPlaceofWork = "", str_getPassword = "", str_getEmail = "", str_getMobile = "",
            str_getMyStory = "", str_getGoalsAspiration = "", str_getWebSites = "", str_getAddress = "", str_getLineofwork = "";

//    final int like = 1;
//    final int dislike = 0;

    private Uri uri;

    /************************************************************************************************/
    ExpandableHeightGridView gridView;
    RelativeLayout rlgrid;
    ArrayList<String> listvideourl;
    ArrayList<String> listvideoid;
    BrowseJointsSingleUserCustomGridviewAdapter videoadapter;

    /************************************************************************************************/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_single_user);
        ButterKnife.bind(this);
/*        mSmallBang = SmallBang.attach2Window(this);*/

        ratingBar.setOnRatingBarChangeListener(this);

        /*Intent for getting the extra string value ("id" for single user from list of users)
        from BrowseJointsUserList.java class    (Start)*/
        Intent intent = getIntent();
        singleuserid = intent.getStringExtra("id");
        Log.e(" ********** NotificationSingleuserid **********", "" + singleuserid);
        Log.e(" ********** NotificationSingleuserid **********", "" + singleuserid);
        Log.e(" ********** NotificationSingleuserid **********", "" + singleuserid);

        AppsConstant.jointpreference = getSharedPreferences(AppsConstant.MyPREFERENCES, Context.MODE_PRIVATE);
        userlogid = AppsConstant.jointpreference.getString("id", "");
        Log.e("**************USER LOGIN id***********:", "" + userlogid);

        thumbButton.setLiked(false);  // set by default dislike
        thumbButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {

                if (Utils.isConnected(getApplicationContext())) {
                    UserLike task = new UserLike();
                    task.execute();
                    Log.e("UserRating Task running", "YES");
                    Log.e("UserRating Task running", "YES");
                } else {
                    LayoutInflater layoutInflater = LayoutInflater.from(NotificationSingleUser.this);
                    View promptView = layoutInflater.inflate(R.layout.activity_wificheck_customalert, null);
                    final AlertDialog alertD = new AlertDialog.Builder(NotificationSingleUser.this).create();
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

                Toast.makeText(NotificationSingleUser.this, "Liked!", Toast.LENGTH_SHORT).show();

            }


            @Override
            public void unLiked(LikeButton likeButton) {

                if (Utils.isConnected(getApplicationContext())) {
                    UserLike task = new UserLike();
                    task.execute();
                    Log.e("UserRating Task running", "YES");
                    Log.e("UserRating Task running", "YES");
                } else {
                    LayoutInflater layoutInflater = LayoutInflater.from(NotificationSingleUser.this);
                    View promptView = layoutInflater.inflate(R.layout.activity_wificheck_customalert, null);
                    final AlertDialog alertD = new AlertDialog.Builder(NotificationSingleUser.this).create();
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

                Toast.makeText(NotificationSingleUser.this, "Disliked!", Toast.LENGTH_SHORT).show();
            }
        });

        /*Intent for getting the extra string value ("id" for single user from list of users)
        from BrowseJointsUserList.java class    (End)*/


/*        addfriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                like(v);
            }
        });*/

        mystory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String URLResume = resumelUrl;
                Intent intentresume = new Intent(NotificationSingleUser.this, WebviewResume.class);
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
                Intent intentprofileimage = new Intent(NotificationSingleUser.this, WebViewProfileImage.class);
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
                Intent intentcoverimage = new Intent(NotificationSingleUser.this, WebViewCoverImage.class);
                intentcoverimage.putExtra("URLC", URLCoverimage);

                Log.e("********** URLCoverimage ********** :", " " + coverphotoUrl);
                Log.e("********** URLCoverimage ********** :", " " + coverphotoUrl);
                Log.e("********** URLCoverimage ********** :", " " + coverphotoUrl);

                startActivity(intentcoverimage);
            }
        });


        /*webserivce Call for get single user data (Start)*/
        if (Utils.isConnected(getApplicationContext())) {
            SingleUserData task = new SingleUserData();
            task.execute();
            Log.e("Signupdata Task running", "YES");
            Log.e("Signupdata Task running", "YES");
        } else {

            LayoutInflater layoutInflater = LayoutInflater.from(this);
            View promptView = layoutInflater.inflate(R.layout.activity_wificheck_customalert, null);
            final AlertDialog alertD = new AlertDialog.Builder(NotificationSingleUser.this).create();
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


        /*webserivce Call for get single user Like Count Show (Start)*/
        if (Utils.isConnected(getApplicationContext())) {
            UserLikeCount task = new UserLikeCount();
            task.execute();
            Log.e("UserRating Task running", "YES");
            Log.e("UserRating Task running", "YES");
        } else {
            LayoutInflater layoutInflater = LayoutInflater.from(NotificationSingleUser.this);
            View promptView = layoutInflater.inflate(R.layout.activity_wificheck_customalert, null);
            final AlertDialog alertD = new AlertDialog.Builder(NotificationSingleUser.this).create();
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
        /*webserivce Call for get single user Like Count Show (End)*/

        /*webserivce Call for Status of Friend Request Send To The single user (Start)*/
       /* if (Utils.isConnected(getApplicationContext())) {
            FriendRequestSendCheck task = new FriendRequestSendCheck();
            task.execute();
            Log.e("FriendRequestSend Task running", "YES");
            Log.e("FriendRequestSend Task running", "YES");
        } else {

            LayoutInflater layoutInflater = LayoutInflater.from(NotificationSingleUser.this);
            View promptView = layoutInflater.inflate(R.layout.activity_wificheck_customalert, null);
            final AlertDialog alertD = new AlertDialog.Builder(NotificationSingleUser.this).create();
           *//* prevent alert dialog from outside click and back button click (Star)*//*
            alertD.setCanceledOnTouchOutside(false);
            alertD.setCancelable(false);
           *//* prevent alert dialog from outside click and back button click (Star)*//*
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
        }*/
        /*webserivce Call for Status of Friend Request Send To The single user (End)*/

        /*image setting by universal image loader */
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(NotificationSingleUser.this).defaultDisplayImageOptions(defaultOptions).build();
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
/*    public void like(View view) {
        mSmallBang.bang(view);
        mSmallBang.setmListener(new SmallBangListener() {

            @Override
            public void onAnimationStart() {
                addfriends.setImageResource(R.drawable.ic_add_friend);

            }

            @Override
            public void onAnimationEnd() {
                *//*Toast.makeText(BrowseJointsSingleUser.this, "Friend Request Send!", Toast.LENGTH_SHORT).show();*//*
                if (Utils.isConnected(getApplicationContext())) {
                    FriendRequestSend task = new FriendRequestSend();
                    task.execute();
                    Log.e("FriendRequestSend Task running", "YES");
                    Log.e("FriendRequestSend Task running", "YES");
                } else {

                    LayoutInflater layoutInflater = LayoutInflater.from(NotificationSingleUser.this);
                    View promptView = layoutInflater.inflate(R.layout.activity_wificheck_customalert, null);
                    final AlertDialog alertD = new AlertDialog.Builder(NotificationSingleUser.this).create();
            *//*prevent alert dialog from outside click and back button click (Star)*//*
                    alertD.setCanceledOnTouchOutside(false);
                    alertD.setCancelable(false);
            *//*prevent alert dialog from outside click and back button click (Star)*//*
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

                addfriends.setImageResource(R.drawable.ic_add_friendrequestpending);
            }

        });
    }*/
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
            //   videouploadprogressbar.setVisibility(View.VISIBLE);
            listvideourl = new ArrayList<String>();
            listvideoid = new ArrayList<String>();

        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(HttpUrlPath.urlPath + "get_user?user_id=" + singleuserid);

            try {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("id", singleuserid));
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
                    str_getUserLike = Object1.getString("like_count");

                } else {
                    if (result.equalsIgnoreCase("unsuccessfully")) {
                        Toast.makeText(NotificationSingleUser.this, "unsuccessfully  ", Toast.LENGTH_SHORT).show();
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
            likeCount.setVisibility(View.VISIBLE);
            // videouploadprogressbar.setVisibility(View.GONE);

            if (listvideoid.size() > 0) {
                Log.e(" ********** listsize ********** ", "" + listvideoid);
                videoadapter = new BrowseJointsSingleUserCustomGridviewAdapter(NotificationSingleUser.this);
                videoadapter.notifyDataSetChanged();
                gridView.invalidateViews();
                videoadapter.notifyDataSetInvalidated();
                gridView.setAdapter(videoadapter);


                Log.e(" ********** listvideoid.size() > 0 **********", "YES");
                Log.e(" ********** listvideoid.size() > 0 **********", "YES");
                Log.e(" ********** listvideoid.size() > 0 **********", "YES");
            }

            likeCount.setText("Like " + str_getUserLikeCount);
            Log.e("value of str_getGoalsAspiration: ", "" + str_getUserLikeCount);

            username.setText(str_getName);
            Log.e("value of str_getName: ", "" + str_getName);

            jointsid.setText(str_getJointid);
            Log.e("value of str_getJointid: ", "" + str_getJointid);

            placeofwork.setText(str_getPlaceofWork);
            Log.e("value of str_getPlaceofWork: ", "" + str_getPlaceofWork);

            /*mystory.setText(str_getMyStory);
            Log.e("value of str_getMyStory: ", "" + str_getMyStory);*/
            mystory.setText(str_getMyStory.substring(str_getMyStory.lastIndexOf("/") + 1));
            Log.e("value of str_getMyStory: ", "" + str_getMyStory);

            goalsaspirations.setText(str_getGoalsAspiration);
            Log.e("value of str_getGoalsAspiration: ", "" + str_getGoalsAspiration);

            websites.setText(str_getWebSites);
            Log.e("value of str_getWebSites: ", "" + str_getWebSites);


            /*if (str_getUserLikeCount == null)
            {
                likeCount.setText("Like " + "0");
                Log.e("value of str_getGoalsAspiration: ", "" + str_getUserLikeCount);
            }
            else if (str_getUserLikeCount != null) {
                likeCount.setText("Like " + str_getUserLikeCount);
                Log.e("value of str_getGoalsAspiration: ", "" + str_getUserLikeCount);
            }*/

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
    /*webserivce Calling for get single user Data Show (End)*/

    /*webserivce Calling for get single user Like Count After Click (Start)*/
    class UserLike extends AsyncTask<String, Void, String> {

        boolean iserror = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
                    Log.e("******* ID RETURN BY SERVER *******", "" + str_getUserId);
                    Log.e("******* ID RETURN BY SERVER *******", "" + str_getUserId);
                    Log.e("******* ID RETURN BY SERVER *******", "" + str_getUserId);

                    str_getOtherUserID = jobect.getString("other_user_id");
                    Log.e("******* OtherUserID RETURN BY SERVER *******", "" + str_getOtherUserID);
                    Log.e("******* OtherUserID RETURN BY SERVER *******", "" + str_getOtherUserID);
                    Log.e("******* OtherUserID RETURN BY SERVER *******", "" + str_getOtherUserID);

                    str_getUserLikeCount = jobect.getString("like_count");
                    Log.e("******* UserLikeCount RETURN BY SERVER *******", "" + str_getUserLikeCount);
                    Log.e("******* UserLikeCount RETURN BY SERVER *******", "" + str_getUserLikeCount);
                    Log.e("******* UserLikeCount RETURN BY SERVER *******", "" + str_getUserLikeCount);

                    str_getUserLike = jobect.getString("like");
                    Log.e("******* UserLike RETURN BY SERVER *******", "" + str_getUserLike);
                    Log.e("******* UserLike RETURN BY SERVER *******", "" + str_getUserLike);
                    Log.e("******* UserLike RETURN BY SERVER *******", "" + str_getUserLike);
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
            likeCount.setVisibility(View.VISIBLE);

            if (!iserror) {
                if (result.equalsIgnoreCase("successful")) {


                    Toast.makeText(NotificationSingleUser.this, "Thanks for Your FeedBack ", Toast.LENGTH_SHORT).show();

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

            /*mystory.setText(str_getMyStory);
            Log.e("value of str_getMyStory: ", "" + str_getMyStory);*/
            //mystory.setText(str_getMyStory.substring(str_getMyStory.lastIndexOf("/") + 1));
            Log.e("value of str_getMyStory: ", "" + str_getUserLike);

            likeCount.setText("Like " + str_getUserLikeCount);
            Log.e("value of str_getGoalsAspiration: ", "" + str_getUserLikeCount);


        }

    }
    /*webserivce Calling for get single user Like Count After Click (End)*/

    /*webserivce Calling for get single user Like Count Onstart (Start)*/
    class UserLikeCount extends AsyncTask<String, Void, String> {

        boolean iserror = false;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
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
                    Log.e("******* ID RETURN BY SERVER *******", "" + str_getUserId);
                    Log.e("******* ID RETURN BY SERVER *******", "" + str_getUserId);
                    Log.e("******* ID RETURN BY SERVER *******", "" + str_getUserId);

                    str_getOtherUserID = jobect.getString("other_user_id");
                    Log.e("******* OtherUserID RETURN BY SERVER *******", "" + str_getOtherUserID);
                    Log.e("******* OtherUserID RETURN BY SERVER *******", "" + str_getOtherUserID);
                    Log.e("******* OtherUserID RETURN BY SERVER *******", "" + str_getOtherUserID);

                    str_getUserLikeCount = jobect.getString("like_count");
                    Log.e("******* UserLikeCount RETURN BY SERVER *******", "" + str_getUserLikeCount);
                    Log.e("******* UserLikeCount RETURN BY SERVER *******", "" + str_getUserLikeCount);
                    Log.e("******* UserLikeCount RETURN BY SERVER *******", "" + str_getUserLikeCount);

                    str_getUserLike = jobect.getString("like");
                    Log.e("******* UserLike RETURN BY SERVER *******", "" + str_getUserLike);
                    Log.e("******* UserLike RETURN BY SERVER *******", "" + str_getUserLike);
                    Log.e("******* UserLike RETURN BY SERVER *******", "" + str_getUserLike);
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
            likeCount.setVisibility(View.VISIBLE);

            if (!iserror) {
                if (result.equalsIgnoreCase("successful")) {


                    //    Toast.makeText(BrowseJointsSingleUser.this, "Thanks for Your FeedBack ", Toast.LENGTH_SHORT).show();

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

            /*mystory.setText(str_getMyStory);
            Log.e("value of str_getMyStory: ", "" + str_getMyStory);*/
            //mystory.setText(str_getMyStory.substring(str_getMyStory.lastIndexOf("/") + 1));
            Log.e("value of str_getMyStory: ", "" + str_getUserLike);

            likeCount.setText("Like " + str_getUserLikeCount);
            Log.e("value of str_getGoalsAspiration: ", "" + str_getUserLikeCount);


        }

    }
    /*webserivce Calling for get single user Like Count Onstart (End)*/

    /*webserivce Calling for Send Friend Request to the single user (Start)*/
   /* class FriendRequestSend extends AsyncTask<String, Void, String> {

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
*//* ********** ********** ********** ********** ********** PRINT ALL INCOMING DATA (START)********** ********** ********** ********** ********** *//*
                System.out.println("########## OBJECT VALUE ##########" + object);
                System.out.println("****************object*******************=" + object);
                System.out.println("****************object*******************=" + object);

                Log.e("************ All INCOMING DATA VALUE ************", "" + object);
                Log.e("************ All INCOMING DATA VALUE ************", "" + object);
                Log.e("************ All INCOMING DATA VALUE ************", "" + object);
*//* ********** ********** ********** ********** ********** PRINT ALL INCOMING DATA (END)********** ********** ********** ********** ********** *//*

                //JSONArray js = new JSONArray(object);
                JSONObject jobect = new JSONObject(object);
                result = jobect.getString("result");
                if (result.equalsIgnoreCase("successful")) {
                    str_getUserId = jobect.getString("user_id");
                    Log.e("******* ID RETURN BY SERVER *******", "" + str_getUserId);
                    Log.e("******* ID RETURN BY SERVER *******", "" + str_getUserId);
                    Log.e("******* ID RETURN BY SERVER *******", "" + str_getUserId);

                    str_getFriendUserID = jobect.getString("friend_id");
                    Log.e("******* str_getFriendUserID RETURN BY SERVER *******", "" + str_getFriendUserID);
                    Log.e("******* str_getFriendUserID RETURN BY SERVER *******", "" + str_getFriendUserID);
                    Log.e("******* str_getFriendUserID RETURN BY SERVER *******", "" + str_getFriendUserID);

                    str_getFriendCount = jobect.getString("friend");
                    Log.e("******* FriendCount RETURN BY SERVER *******", "" + str_getFriendCount);
                    Log.e("******* FriendCount RETURN BY SERVER *******", "" + str_getFriendCount);
                    Log.e("******* FriendCount RETURN BY SERVER *******", "" + str_getFriendCount);

                    str_getFriendRequestDate = jobect.getString("date");
                    Log.e("******* FriendRequestDate RETURN BY SERVER *******", "" + str_getFriendRequestDate);
                    Log.e("******* FriendRequestDate RETURN BY SERVER *******", "" + str_getFriendRequestDate);
                    Log.e("******* FriendRequestDate RETURN BY SERVER *******", "" + str_getFriendRequestDate);
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
            likeCount.setVisibility(View.VISIBLE);

            if (!iserror) {
                if (result.equalsIgnoreCase("successful")) {
                    Toast.makeText(NotificationSingleUser.this, "Friend Request Send...! ", Toast.LENGTH_SHORT).show();

                    tvaddfriend.setText("Friend Request Send ");
                    Log.e(" Friend Request Send :", "Success");
                    Log.e(" Friend Request Send :", "Success");
                    Log.e(" Friend Request Send :", "Success");


                } else if (result.equalsIgnoreCase("already friend")) {
                    Toast.makeText(getApplicationContext(), "Friend Request Already Send...!", Toast.LENGTH_SHORT).show();

                    tvaddfriend.setText("Friend Request Send ");
                    Log.e(" Friend Request Send :", "Already Send");
                    Log.e(" Friend Request Send :", "Already Send");
                    Log.e(" Friend Request Send :", "Already Send");

                }
            } else {
                Log.e("********* User id and Friend User id *********", "NOT Success");
                Log.e("********* User id and Friend User id *********", "NOT Success");
                Log.e("********* User id and Friend User id *********", "NOT Success");

                tvaddfriend.setText("Add Friend");
                Toast.makeText(getApplicationContext(), "Oops!! Please check server connection .",
                        Toast.LENGTH_SHORT).show();
            }

            tvaddfriend.setText("Friend Request Send ");
            Log.e("value of result: ", "" + result);
            Log.e("value of result: ", "" + result);
            Log.e("value of result: ", "" + result);


        }

    }*/
    /*webserivce Calling for Send Friend Request to the single user (End)*/

    /*webserivce Calling for Send Friend Request to the single user (Start)*/
    /*class FriendRequestSendCheck extends AsyncTask<String, Void, String> {

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
*//* ********** ********** ********** ********** ********** PRINT ALL INCOMING DATA (START)********** ********** ********** ********** ********** *//*
                Log.e("************ All INCOMING DATA VALUE ************", "" + object);
                Log.e("************ All INCOMING DATA VALUE ************", "" + object);
                Log.e("************ All INCOMING DATA VALUE ************", "" + object);
*//* ********** ********** ********** ********** ********** PRINT ALL INCOMING DATA (END)********** ********** ********** ********** ********** *//*

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
            likeCount.setVisibility(View.VISIBLE);

            if (!iserror) {
                if (result.equalsIgnoreCase("successful")) {


                } else if (result.equalsIgnoreCase("already friend")) {
                    // Toast.makeText(getApplicationContext(), "Friend Request Already Send...!", Toast.LENGTH_SHORT).show();

                    tvaddfriend.setText("Friend Request Send ");
                    addfriends.setBackgroundResource(R.drawable.ic_add_friendrequestpending);
                    Log.e(" Friend Request Send :", "Already Send");
                    Log.e(" Friend Request Send :", "Already Send");
                    Log.e(" Friend Request Send :", "Already Send");

                }
            } else {
                Log.e("********* User id and Friend User id *********", "NOT Success");
                Log.e("********* User id and Friend User id *********", "NOT Success");
                Log.e("********* User id and Friend User id *********", "NOT Success");

                tvaddfriend.setText("Add Friend");
                Toast.makeText(getApplicationContext(), "Oops!! Please check server connection .",
                        Toast.LENGTH_SHORT).show();
            }

        }

    }*/
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
            final MediaController mediaController = new MediaController(NotificationSingleUser.this);

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
