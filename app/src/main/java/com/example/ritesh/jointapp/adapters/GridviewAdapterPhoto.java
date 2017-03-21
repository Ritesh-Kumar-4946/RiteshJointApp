package com.example.ritesh.jointapp.adapters;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.ritesh.jointapp.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by user on 27/05/2016.
 */
public class GridviewAdapterPhoto extends BaseAdapter
{

    private ArrayList<String> listimage;
    private Activity activity;


    public GridviewAdapterPhoto(Activity activity, ArrayList<String> listimage) {
        super();

        this.listimage = listimage;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return listimage.size();
    }

    @Override
/*
    public String getItem(int position) {
        // TODO Auto-generated method stub
        return listimage.get(position);
    }
*/

    public String getItem(int position) {
        // TODO Auto-generated method stub
        return listimage.get(position);
    }


    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public static class ViewHolder
    {
        public ImageView imgViewFlag;
        /*public TextView txtViewTitle;*/
        public ProgressBar progressBar11;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisk(true).build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(activity).defaultDisplayImageOptions(defaultOptions).build();
        ImageLoader.getInstance().init(config);

        // TODO Auto-generated method stub
        final ViewHolder viewholder;
        LayoutInflater inflator = activity.getLayoutInflater();

        if(convertView==null)
        {
            viewholder = new ViewHolder();
            convertView = inflator.inflate(R.layout.image_gallery_list, null);

            /*viewholder.txtViewTitle = (TextView) convertView.findViewById(R.id.image_textview);*/
            viewholder.imgViewFlag = (ImageView) convertView.findViewById(R.id.photo_gallery_IV);
            viewholder.progressBar11=(ProgressBar) convertView.findViewById(R.id.progressBar11);


            convertView.setTag(viewholder);
        }
        else
        {
            viewholder = (ViewHolder) convertView.getTag();
        }

        /*viewholder.txtViewTitle.setText(listfolder.get(position));*/
/*        viewholder.imgViewFlag.setImageBitmap(listimage.get(position));*/

        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.getInstance().displayImage("http://" + listimage.get(position), viewholder.imgViewFlag, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                viewholder.progressBar11.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                viewholder.progressBar11.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                viewholder.progressBar11.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                viewholder.progressBar11.setVisibility(View.GONE);
            }
        }); // Default options will be used



        return convertView;
    }
}