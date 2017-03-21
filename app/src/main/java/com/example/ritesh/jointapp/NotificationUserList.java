package com.example.ritesh.jointapp;

import android.app.Activity;
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
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ritesh.jointapp.constant.AppsConstant;
import com.example.ritesh.jointapp.constant.HttpUrlPath;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.sdsmdg.tastytoast.TastyToast;

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

import de.hdodenhof.circleimageview.CircleImageView;
import mehdi.sakout.fancybuttons.FancyButton;

/**
 * Created by ritesh on 2/8/16.
 */
public class NotificationUserList extends AppCompatActivity {

    /*left menu slider (Start)*/
    private LinearLayout slidingPanel;
    private boolean isExpanded;
    private DisplayMetrics metrics;
    private RelativeLayout headerPanel;
    private int panelWidth1;
    private ImageView menuViewButton;
    FrameLayout.LayoutParams slidingPanelParameters;
    LinearLayout.LayoutParams headerPanelParameters;
    public static NotificationUserList ActivityContext = null;
    /*left menu slider (End)*/

    String userlogid = "";
    String UserID = "";
    String Userlineofwork = "";
    String Username = "";
    String Userimage = "";
    String userlistresult = "";
    String profilephotoUrl = "";
    String useraddress = "";
    String UserFriendrequestID = "",
            listcount = "";

    String str_getUserId = "", str_getFriendID = "";


    ListView listView;
    List<BrowseJointsUserList> rowItems;
    private ArrayList<String> Uname;
    private ArrayList<String> Ulineofwork;
    private ArrayList<String> Uimage;
    private ArrayList<String> Uid;
    private ArrayList<String> Uaddress;
    NotificationUserListCustomBaseAdapterTwo adapterTwo;
    private ProgressBar alldatalistupdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

         /*Toast.makeText(getApplicationContext(), "WelCome to Joints \n Please Update Your Profile",
                Toast.LENGTH_SHORT).show();*/
        ActivityContext = this;
        rowItems = new ArrayList<>();
        listView = (ListView) findViewById(R.id.list);

        /*Uimage = new ArrayList<>();
        Uname = new ArrayList<>();
        Ulineofwork = new ArrayList<>();
        Uid = new ArrayList<>();
        Uaddress = new ArrayList<>();*/
        alldatalistupdate = (ProgressBar) findViewById(R.id.alldataupdateprogressbar);

         /*image settin by universal image loader */
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(NotificationUserList.this).defaultDisplayImageOptions(defaultOptions).build();
        ImageLoader.getInstance().init(config);
        /*image settin by universal image loader */


        AppsConstant.jointpreference = getSharedPreferences(AppsConstant.MyPREFERENCES, Context.MODE_PRIVATE);
        userlogid = AppsConstant.jointpreference.getString("id", "");
        Log.e("**************USER LOGIN id***********:", "" + userlogid);


        Log.e("FriendRequestList Task running", "YES");
        if (Utils.isConnected(getApplicationContext())) {
            FriendRequestList task = new FriendRequestList();
            task.execute();
            Log.e("FriendRequestList Task running", "YES");
        } else {
            Toast.makeText(getApplicationContext(), "Please Cheeck network conection..", Toast.LENGTH_SHORT).show();
        }


       /* ** ** ** ** ** ** ** ** **left menu slider (Start)** ** ** ** ** ** ** ** ** */
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
        /* ** ** ** ** ** ** ** ** ** left menu slider (End) ** ** ** ** ** ** ** ** ** */

    }

    /* friend request list synctask (Start)*/
    class FriendRequestList extends AsyncTask<String, Void, String> {

        boolean iserror = false;
        String result = " ";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alldatalistupdate.setVisibility(View.VISIBLE);
            Log.e(" ************ UserListData AsyncTask Start ************ :", "yes");
        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(HttpUrlPath.urlPath + "send_request_list?user_id=" + userlogid);
            Log.e(" ************ userlogid :", " " + userlogid);
            Log.e(" ************ send_request_list doInBackground AsyncTask Start ************ :", "yes");


            try {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("id", userlogid)); // check  name paire value
                post.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                HttpResponse response = client.execute(post);
                String obj = EntityUtils.toString(response.getEntity());

                System.out.println("########## OBJECT VALUE ##########" + obj);
                Log.e("************ All INCOMING USER VALUE ************", "" + obj);


                Uimage = new ArrayList<>();
                Uname = new ArrayList<>();
                Ulineofwork = new ArrayList<>();
                Uid = new ArrayList<>();
                Uaddress = new ArrayList<>();


                JSONArray jaaray = new JSONArray(obj);
                Log.e("************Json User List data*******************", " " + obj);
                for (int i = 0; i < jaaray.length(); i++) {
                    userlistresult = jaaray.getJSONObject(i).getString("result");
                    if (userlistresult.equalsIgnoreCase("successful")) {
                        Userlineofwork = jaaray.getJSONObject(i).getString("line_work");
                        Log.e("*********** line of work ***********", "" + Userlineofwork);

                        Username = jaaray.getJSONObject(i).getString("username");
                        Log.e("*********** Username ***********", "" + Username);

                        Userimage = jaaray.getJSONObject(i).getString("image");
                        Log.e("*********** Userimage ***********", "" + Userimage);

                        profilephotoUrl = jaaray.getJSONObject(i).getString("image");
                        Log.e("*********** profilephotoUrl ***********", "" + profilephotoUrl);

                        UserID = jaaray.getJSONObject(i).getString("id");
                        Log.e("*********** UserID ***********", "" + UserID);

                        useraddress = jaaray.getJSONObject(i).getString("address");
                        Log.e("*********** useraddress ***********", "" + useraddress);

                        //String Userid = jaaray.getJSONObject(i).getString("id");
                        Uname.add(Userimage);
                        Ulineofwork.add(Userlineofwork);
                        Uimage.add(Username);
                        Uid.add(UserID);
                        Uaddress.add(useraddress);
                        // Uid.add(Userid);

                        String Usernamelist = Uname.get(i);
                        Log.e(" ********** Usrname_list **********", "" + Usernamelist);
                        Log.e(" ********** Usrname **********", "" + Uname);

                        String Userlinofworklist = Ulineofwork.get(i);
                        Log.e(" ********** User_lineofwork_list **********", "" + Userlinofworklist);
                        Log.e(" ********** User_lineofwork **********", "" + Ulineofwork);

                        String Userimagelist = Uimage.get(i);
                        Log.e(" ********** User_image_list **********", "" + Userimagelist);
                        Log.e(" ********** User_image **********", "" + Uimage);

                        String UserIDlist = Uid.get(i);
                        Log.e(" ********** User_id_list **********", "" + UserIDlist);
                        Log.e(" ********** User_id **********", "" + Uid);

                        String UserAddresslist = Uaddress.get(i);
                        Log.e(" ********** User_address_list **********", "" + UserAddresslist);
                        Log.e(" ********** User_address **********", "" + Uaddress);

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

            alldatalistupdate.setVisibility(View.GONE);
            Log.e(" ************ UserListData onPostExecute AsyncTask Start ************ :", "yes");

            if (Uimage.size() > 0) {
                Log.e(" ********** listsize ********** ", "" + Uimage);
                Log.e(" ********** user id listsize ********** ", "" + Uid);
                Log.e(" ********** user id listsize ********** ", "" + Uid);
                Log.e(" ********** user id listsize ********** ", "" + Uid);
                adapterTwo = new NotificationUserListCustomBaseAdapterTwo(NotificationUserList.
                        this, Uname, Uimage, Ulineofwork, Uid, Uaddress);
                listView.setAdapter(adapterTwo);
//                adapterTwo.notifyDataSetInvalidated();
                adapterTwo.notifyDataSetChanged();
                listView.getAdapter().getCount();

                Toast.makeText(getApplicationContext(),
                        "Total number of Items are:" + listView.getAdapter().getCount(), Toast.LENGTH_SHORT).show();
            } else {
                adapterTwo = new NotificationUserListCustomBaseAdapterTwo(NotificationUserList.
                        this, null, null, null, null, null);
                listView.setAdapter(adapterTwo);
                TastyToast.makeText(getApplicationContext(), "You have no Friend Request", TastyToast.LENGTH_LONG,
                        TastyToast.INFO);
            }

        }

    }
    /* friend request list synctask (End)*/

    /* list view adapter (Start)*/
    public class NotificationUserListCustomBaseAdapterTwo extends BaseAdapter {

        private ArrayList<String> Userimage;
        private ArrayList<String> username;
        private ArrayList<String> lineofwork;
        private ArrayList<String> Userid;
        private ArrayList<String> Uaddress;
        private ArrayList<String> fancyaccept;
        private ArrayList<String> fancycancel;
        private Activity activity;

        public NotificationUserListCustomBaseAdapterTwo(Activity activity, ArrayList<String> Userimage,
                                                        ArrayList<String> username, ArrayList<String> lineofwork,
                                                        ArrayList<String> Userid, ArrayList<String> Useraddress) {
            super();
            this.Userimage = Userimage;
            this.username = username;
            this.lineofwork = lineofwork;
            this.Userid = Userid;
            this.activity = activity;
            this.Uaddress = Useraddress;
            this.fancyaccept = fancyaccept;
            this.fancycancel = fancycancel;

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
//            return Userimage.size();

            return Userimage == null ?
                    0 : Userimage.size();
        }

        @Override
        public String getItem(int position) {
            // TODO Auto-generated method stub
            return Userimage.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

        /*private view holder class*/
        private class ViewHolder {
            CircleImageView Userimages;
            TextView username;
            TextView lineofwork;
            TextView address;
            FancyButton accept;
            FancyButton cancel;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            // TODO Auto-generated method stub
            final ViewHolder viewholder;
            LayoutInflater inflator = activity.getLayoutInflater();

            if (convertView == null) {
                viewholder = new ViewHolder();
                convertView = inflator.inflate(R.layout.activity_notification_listitems, null);

                viewholder.Userimages = (CircleImageView) convertView.findViewById(R.id.icon);
                viewholder.username = (TextView) convertView.findViewById(R.id.tv_username);
                viewholder.lineofwork = (TextView) convertView.findViewById(R.id.tv_lineofwork);
                viewholder.address = (TextView) convertView.findViewById(R.id.tv_address);
                viewholder.accept = (FancyButton) convertView.findViewById(R.id.btn_accept);
                viewholder.cancel = (FancyButton) convertView.findViewById(R.id.btn_cancel);

                convertView.setTag(viewholder);

            } else {
                viewholder = (ViewHolder) convertView.getTag();
            }

            UserFriendrequestID = Uid.get(position);
            Log.e("UserFriendrequestID******************* :", "" + UserFriendrequestID);
            Log.e("UserFriendrequestID******************* :", "" + UserFriendrequestID);
            Log.e("UserFriendrequestID******************* :", "" + UserFriendrequestID);
            // viewholder.Userimage.setImageResource(position);

            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.getInstance().displayImage(Userimage.get(position), viewholder.Userimages, new ImageLoadingListener() {
                @Override
                public void onLoadingStarted(String imageUri, View view) {
                    // profileimageprogressbar.setVisibility(View.VISIBLE);

                }

                @Override
                public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                    //    profileimageprogressbar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    //   profileimageprogressbar.setVisibility(View.GONE);
                }

                @Override
                public void onLoadingCancelled(String imageUri, View view) {
                    //   profileimageprogressbar.setVisibility(View.GONE);

                }
            });

            viewholder.username.setText(username.get(position));
            viewholder.lineofwork.setText(lineofwork.get(position));
            viewholder.address.setText(Uaddress.get(position));

            /*singleuser listview item click (Start)*/
            convertView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View arg0) {
                    Log.e("id******************* :", "" + Uid.get(position));
                    Log.e("id******************* :", "" + Uid.get(position));
                    Log.e("id******************* :", "" + Uid.get(position));

                    Intent intent = new Intent(NotificationUserList.this, NotificationSingleUser.class);
                    intent.putExtra("id", Uid.get(position));

                   /* Toast.makeText(BrowseJointsUserList.this,
                            "Item in position " + position + "\n"+ "id :"+ Uid.get(position)  + " clicked", Toast.LENGTH_LONG).show();*/
                    startActivity(intent);
                }
            });
            /*singleuser listview item click (End)*/



            /*show simgle user images onclick on their image in list (Start)*/
            viewholder.Userimages.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* Toast.makeText(BrowseJointsUserList.this, "" + position, Toast.LENGTH_SHORT).show();*/
                    Log.e("id******************* :", "" + Uname.get(position));
                    Log.e("id******************* :", "" + Uname.get(position));
                    Log.e("id******************* :", "" + Uname.get(position));

                    String URLProfileimage = Uname.get(position);
                    Intent intent = new Intent(NotificationUserList.this, WebViewProfileImage.class);
                    intent.putExtra("URLP", Uname.get(position));

                    Log.e("********** URLProfileimage ********** :", " " + URLProfileimage);
                    Log.e("********** URLProfileimage ********** :", " " + URLProfileimage);
                    Log.e("********** URLProfileimage ********** :", " " + URLProfileimage);

                   /* Toast.makeText(BrowseJointsUserList.this,
                            "Item in position " + position + "\n"+ "id :"+ Uid.get(position)  + " clicked", Toast.LENGTH_LONG).show();*/
                    startActivity(intent);
                }
            });
            /*show simgle user images onclick on their image in list (End)*/


            viewholder.accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.e("id******************* :", "" + Uid.get(position));
                    Log.e("id******************* :", "" + Uid.get(position));
                    Log.e("id******************* :", "" + Uid.get(position));

//                    notifyDataSetChanged();
                    listView.invalidateViews();


                    if (Utils.isConnected(getApplicationContext())) {
                        FriendRequestAccept task = new FriendRequestAccept();
                        task.execute();
                        Log.e("FriendRequestAccept Task running", "YES");
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Cheeck network connection..", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            return convertView;
        }

    }
    /* list view adapter (End)*/

    class FriendRequestAccept extends AsyncTask<String, Void, String> {

        boolean iserror = false;
        String result = " ";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            alldatalistupdate.setVisibility(View.VISIBLE);
            Log.e(" ************ FriendRequestAccept AsyncTask Start ************ :", "yes");
        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient client = new DefaultHttpClient();
            HttpPost post = new HttpPost(HttpUrlPath.urlPath + "accept_friend?");
            try {
                List<NameValuePair> nameValuePairs = new ArrayList<>();
                nameValuePairs.add(new BasicNameValuePair("friend_id", UserFriendrequestID));
                nameValuePairs.add(new BasicNameValuePair("user_id", userlogid));

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

                    str_getFriendID = jobect.getString("friend_id");
                    Log.e("******* FriendID RETURN BY SERVER *******", "" + str_getFriendID);
                    Log.e("******* FriendID RETURN BY SERVER *******", "" + str_getFriendID);
                    Log.e("******* FriendID RETURN BY SERVER *******", "" + str_getFriendID);
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
                    FriendRequestList task = new FriendRequestList();
                    task.execute();

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
        }
    }

}
