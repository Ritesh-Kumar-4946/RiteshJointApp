/* Created by Srikanth gr.
 */

package com.example.ritesh.jointapp;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

//Left Menu
public class LeftMenuFragment extends Fragment {

    RelativeLayout lir2_dashboard, lir3_inbox, lir4_notification, lir5_favorities,
            lir6_browseJoints, lir7_testimonies, lir8_profile, lir9_currentproject, lir10_logout;

    TextView litv_dashboard, litv_inbox;

    private CircleImageView profileimg;  // set image for profile
    private ImageView coverbgg;
    String userlogid = "";
    String result = " ";
    String profilechecksum = "Empty";

    String str_getuserid = "", str_getName= "", str_getProfileimage= "";
    TextView username;

    ProgressBar profileimageprogressbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_leftmenu, container, false);

        /*image settin by universal image loader */
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getActivity()).defaultDisplayImageOptions(defaultOptions).build();
        ImageLoader.getInstance().init(config);
        /*image settin by universal image loader */

        // AppsConstant.jointpreference = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        AppsConstant.jointpreference = getActivity().getSharedPreferences(AppsConstant.MyPREFERENCES, Context.MODE_PRIVATE);
        userlogid = AppsConstant.jointpreference.getString("id", "");
        Log.e("**************USER LOGIN id***********:", "" + userlogid);
        //  AppsConstant.editorjoint.commit();


        new SignupData().execute();    // asynk task
        Log.e("Signupdata Task running", "YES");
        if (Utils.isConnected(getActivity().getApplicationContext())) {
            SignupData task = new SignupData();
            task.execute();
            Log.e("Signupdata Task running", "YES");
            Log.e("Signupdata Task running", "YES");
               /* Intent signupss = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(signupss);*/
        } else {
            LayoutInflater layoutInflater = LayoutInflater.from(LeftMenuFragment.this.getActivity());
            View promptView = layoutInflater.inflate(R.layout.activity_wificheck_customalert, null);
            final AlertDialog alertD = new AlertDialog.Builder(LeftMenuFragment.this.getActivity()).create();
            /*prevent alert dialog from outside click and back button click (Star)*/
            alertD.setCanceledOnTouchOutside(false);
            alertD.setCancelable(false);
            /*prevent alert dialog from outside click and back button click (Star)*/
            Button Tryagain = (Button) promptView.findViewById(R.id.btn_tryagain);
            Tryagain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = getActivity().getIntent();
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    getActivity().finish();
                    getActivity().overridePendingTransition(0, 0);
                    startActivity(getActivity().getIntent());
                    getActivity().overridePendingTransition(0, 0);
                }
            });
            alertD.setView(promptView);
            alertD.show();
            Toast.makeText(getActivity().getApplicationContext(), "Please Cheeck network conection..", Toast.LENGTH_SHORT).show();
        }


		/*relative layout declation for clickevent (Start)*/
        lir2_dashboard = (RelativeLayout) view.findViewById(R.id.r2_dashboard);
        lir3_inbox = (RelativeLayout) view.findViewById(R.id.r3_inbox);
        lir4_notification = (RelativeLayout) view.findViewById(R.id.r4_notification);
        lir5_favorities = (RelativeLayout) view.findViewById(R.id.r5_favorities);
        lir6_browseJoints = (RelativeLayout) view.findViewById(R.id.r6_browsejoints);
        lir7_testimonies = (RelativeLayout) view.findViewById(R.id.r7_testimonies);
        lir8_profile = (RelativeLayout) view.findViewById(R.id.r8_profile);
        lir9_currentproject = (RelativeLayout) view.findViewById(R.id.r9_currentproject);
        lir10_logout = (RelativeLayout) view.findViewById(R.id.r10_logout);
        /*relative layout declation for clickevent (End)*/

		/*text view declation for clickevent (Start)*/
        litv_dashboard = (TextView) view.findViewById(R.id.tv_dashboard);
        litv_inbox = (TextView) view.findViewById(R.id.tv_inbox);
        profileimg = (CircleImageView) view.findViewById(R.id.profileimg);
        coverbgg = (ImageView) view.findViewById(R.id.iv_coverbg);
        username = (TextView) view.findViewById(R.id.tv_username);

        profileimageprogressbar = (ProgressBar) view.findViewById(R.id.profileprogressbar);

        /*text view declation for clickevent (End)*/

        lir2_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Onclick", "Onclick is success on lir2_dashboard");
                Intent dashboard = new Intent(LeftMenuFragment.this.getActivity(), DashboardTHREE.class);
                startActivity(dashboard);
                 /*layout animation when user click on this layout */
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                /*layout animation when user click on this layout */
                Toast.makeText(getActivity(), "DashBoard Comming Soon", Toast.LENGTH_SHORT).show();
            }
        });


        lir3_inbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Onclick", "Onclick is success on lir3_inbox");
                Intent inbox = new Intent(LeftMenuFragment.this.getActivity(), InboxTHREE.class);
                startActivity(inbox);
                 /*layout animation when user click on this layout */
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                /*layout animation when user click on this layout */
                Toast.makeText(getActivity(), "InboxTHREE Comming Soon", Toast.LENGTH_SHORT).show();
            }
        });

        lir4_notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Onclick", "Onclick is success on lir3_inbox");
                Intent notification = new Intent(LeftMenuFragment.this.getActivity(), NotificationUserList.class);
                startActivity(notification);
                 /*layout animation when user click on this layout */
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                /*layout animation when user click on this layout */
                Toast.makeText(getActivity(), "Notification Comming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        lir5_favorities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Onclick", "Onclick is success on lir3_inbox");
                Intent favorities = new Intent(LeftMenuFragment.this.getActivity(), FavoritiesTHREE.class);
                startActivity(favorities);
                 /*layout animation when user click on this layout */
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                /*layout animation when user click on this layout */
                Toast.makeText(getActivity(), "Favorities Comming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        lir6_browseJoints.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Onclick", "Onclick is success on lir3_inbox");
                Log.e("Onclick", "Onclick is success on lir3_inbox");
                Log.e("Onclick", "Onclick is success on lir3_inbox");

                if (str_getProfileimage.contains(profilechecksum)){
                    //Toast.makeText(getActivity(), "Please Update Your Profile First..!!", Toast.LENGTH_SHORT).show();

                    Log.e(" Profileimage Link Contains :", "" + profilechecksum);
                    Log.e(" Profileimage Link Contains :", "" + profilechecksum);
                    Log.e(" Profileimage Link Contains :", "" + profilechecksum);


                    /*new SweetAlertDialog(LeftMenuFragment.this.getActivity(), SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("Are you sure?")
                            .setContentText("Won't be able to recover this file!")
                            .setCancelText("No,cancel plx!")
                            .setConfirmText("Yes,delete it!")
                            .showCancelButton(true)
                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    // reuse previous dialog instance, keep widget user state, reset them if you need
                                    sDialog.setTitleText("Cancelled!")
                                            .setContentText("Your imaginary file is safe :)")
                                            .setConfirmText("OK")
                                            .showCancelButton(false)
                                            .setCancelClickListener(null)
                                            .setConfirmClickListener(null)
                                            .changeAlertType(SweetAlertDialog.ERROR_TYPE);

                                    // or you can new a SweetAlertDialog to show
                                sDialog.dismiss();
                                new SweetAlertDialog(LeftMenuFragment.this.getActivity(), SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Cancelled!")
                                        .setContentText("Your imaginary file is safe :)")
                                        .setConfirmText("OK")
                                        .show();
                                    // or you can new a SweetAlertDialog to show
                               *//* sDialog.dismiss();
                                new SweetAlertDialog(SampleActivity.this, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("Cancelled!")
                                        .setContentText("Your imaginary file is safe :)")
                                        .setConfirmText("OK")
                                        .show();*//*
                                }
                            })
                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                @Override
                                public void onClick(SweetAlertDialog sDialog) {
                                    sDialog.setTitleText("Deleted!")
                                            .setContentText("Your imaginary file has been deleted!")
                                            .setConfirmText("OK")
                                            .showCancelButton(false)
                                            .setCancelClickListener(null)
                                            .setConfirmClickListener(null)
                                            .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                }
                            })
                            .show();*/

                    new SweetAlertDialog(LeftMenuFragment.this.getActivity(), SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Deny Access..!!")
                            .setContentText("Please Complete Your Profile With Profile Image.")
                            .show();
                }
                else {
                    Log.e(" Profileimage Link Contains :", "IMAGE");
                    Log.e(" Profileimage Link Contains :", "IMAGE");
                    Log.e(" Profileimage Link Contains :", "IMAGE");
                    Intent browsejoints = new Intent(LeftMenuFragment.this.getActivity(), BrowseJointsUserList.class);
                    startActivity(browsejoints);
                 /*layout animation when user click on this layout */
                    getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                /*layout animation when user click on this layout */
                /*Toast.makeText(getActivity(), "BrowseJointsUserList Comming Soon", Toast.LENGTH_SHORT).show();*/
                }
            }
        });
        lir7_testimonies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Onclick", "Onclick is success on lir3_inbox");
                Intent testimonies = new Intent(LeftMenuFragment.this.getActivity(), TestimoniesTHREE.class);
                startActivity(testimonies);
                 /*layout animation when user click on this layout */
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                /*layout animation when user click on this layout */
                Toast.makeText(getActivity(), "Testimonies Comming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        lir8_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Onclick", "Onclick is success on lir3_inbox");
                Intent profile = new Intent(LeftMenuFragment.this.getActivity(), ProfilePreviewActivity.class);
                startActivity(profile);
                /*layout animation when user click on this layout */
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                /*layout animation when user click on this layout */
                /*Toast.makeText(getActivity(), "WelCome to User Profile", Toast.LENGTH_SHORT).show();*/
            }
        });
        lir9_currentproject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Onclick", "Onclick is success on lir3_inbox");
				/*Intent inbox = new Intent(LeftMenuFragment.this.getActivity(), InboxTHREE.class);
				startActivity(inbox);*/
                Toast.makeText(getActivity(), "Current Project Comming Soon", Toast.LENGTH_SHORT).show();
            }
        });
        lir10_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Onclick", "Onclick is success on lir3_inbox");
                Toast.makeText(getActivity(), "Logout Successfull", Toast.LENGTH_SHORT).show();
                AppsConstant.jointpreference = getActivity().getSharedPreferences(AppsConstant.MyPREFERENCES, Context.MODE_PRIVATE);
                AppsConstant.editorjoint.remove("loginTest").commit();
                AppsConstant.editorjoint.remove("id").commit();
                AppsConstant.editorjoint.remove("islogin").commit();
                AppsConstant.editorjoint.clear().commit();
                AppsConstant.editorjoint.commit();

                Log.e("************ VALUE OF USER ID AFTER LOGOUT ************", "" + userlogid);
                Log.e("************ VALUE OF USER ID AFTER LOGOUT ************", "" + userlogid);
                Log.e("************ VALUE OF USER ID AFTER LOGOUT ************", "" + userlogid);


                Log.e("************ LOGOUT SUCCESSFULL ************", "YES GOTO LOGIN PAGE");
                Log.e("************ LOGOUT SUCCESSFULL ************", "YES GOTO LOGIN PAGE");
                Log.e("************ LOGOUT SUCCESSFULL ************", "YES GOTO LOGIN PAGE");


                Toast.makeText(getActivity(), "You have successfully logout", Toast.LENGTH_LONG).show();


                Intent sendToLoginandRegistration = new Intent(getActivity(), LoginActivity.class);
                sendToLoginandRegistration.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                //sendToLoginandRegistration.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(sendToLoginandRegistration);
                //finish();
            }
        });

        return view;

        //return inflater.inflate(R.layout.activity_leftmenu, container, false);

    }

    class SignupData extends AsyncTask<String, Void, String> {

        boolean iserror = false;


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
                    "get_user?user_id=" + str_getuserid + " username=" + str_getName + "image=" + str_getProfileimage);

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
                    Log.e("************Json2*******************", String.valueOf(ParentObject));
                    str_getName = alldataObject.getString("username");
                    str_getProfileimage = alldataObject.getString("image");

                    Log.e("*************** username ***************", "" + str_getName);
                    Log.e("*************** username ***************", "" + str_getName);
                    Log.e("*************** username ***************", "" + str_getName);

                    Log.e("*************** image ***************", "" + str_getProfileimage);
                    Log.e("*************** image ***************", "" + str_getProfileimage);
                    Log.e("*************** image ***************", "" + str_getProfileimage);

                } else {
                    if (result.equalsIgnoreCase("unsuccessfully")) {
                        // error1 = jobect.getString("error");
                    }
                }

               /* Log.e("************Json2*******************", String.valueOf(ParentObject));
                str_getName = ParentObject.getString("username");
                //str_getMobile = ParentObject.getString("mobile");
                str_getJointid = ParentObject.getString("joint_id");
*/
            } catch (Exception e) {
                System.out.println("Errror in gettting by ID" + e);

            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            username.setText(str_getName);
            Log.e("*************** username eeeeee***************", "" + str_getName);
            Log.e("*************** username eeeeee***************", "" + str_getName);
            Log.e("*************** username eeeeee***************", "" + str_getName);

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

        }

    }
}
