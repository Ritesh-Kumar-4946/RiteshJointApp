package com.example.ritesh.jointapp;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.sdsmdg.tastytoast.TastyToast;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ritesh on 27/8/16.
 */
public class WebViewCoverImage extends AppCompatActivity {

    // ImageView web;
    private ProgressDialog dialog;

    @BindView(R.id.imgcover)
    TouchImageView imageCover;
    String UrlC = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading Image");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_coverimage);
        ButterKnife.bind(this);

        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).defaultDisplayImageOptions(defaultOptions).build();
        ImageLoader.getInstance().init(config);

        //  web=(ImageView)findViewById(R.id.webPhotoscover);

        if (UrlC.contains("Empty")) {
            TastyToast.makeText(getApplicationContext(), "Image Not Found", TastyToast.LENGTH_LONG,
                    TastyToast.INFO);
        } else {
            Bundle extra = getIntent().getExtras();
            UrlC = extra.get("URLC").toString();   // URL for cover image

            Log.e(" ********** profile image URL **********", "" + UrlC);
            Log.e(" ********** profile image URL **********", "" + UrlC);
            Log.e(" ********** profile image URL **********", "" + UrlC);
        }


        /**********************************
         * for performing Touchimage ZOOM IN ZOOM OUT TouchimageView.java file is required */

        imageCover.setOnTouchImageViewListener(new TouchImageView.OnTouchImageViewListener() {
            @Override
            public void onMove() {
                PointF point = imageCover.getScrollPosition();
                RectF rect = imageCover.getZoomedRect();
                float currentZoom = imageCover.getCurrentZoom();
                boolean isZoomed = imageCover.isZoomed();
            }
        });

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.getInstance().displayImage(UrlC, imageCover, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                dialog.show();
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                dialog.dismiss();
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                dialog.dismiss();
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                dialog.dismiss();
            }
        }); // Default options will be used
    }
}
