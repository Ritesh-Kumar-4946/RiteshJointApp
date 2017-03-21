package com.example.ritesh.jointapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ritesh.jointapp.R;
import com.example.ritesh.jointapp.beans.FavoritiesRowItemONE;

import java.util.List;

/**
 * Created by ritesh on 2/8/16.
 */
public class FavoritiesCustomBaseAdapterTWO extends BaseAdapter {

    Context context;
    List<FavoritiesRowItemONE> rowItems;

    public FavoritiesCustomBaseAdapterTWO(Context context, List<FavoritiesRowItemONE> items) {
        this.context = context;
        this.rowItems = items;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        TextView txtTitle;
        ImageView txtDesc;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_favorities_userlist_item, null);
            holder = new ViewHolder();
            holder.txtDesc = (ImageView) convertView.findViewById(R.id.desc);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        FavoritiesRowItemONE rowItem = (FavoritiesRowItemONE) getItem(position);

        holder.txtDesc.setImageResource(rowItem.getDesc());
        holder.txtTitle.setText(rowItem.getTitle());
        holder.imageView.setImageResource(rowItem.getImageId());

        return convertView;
    }

    @Override
    public int getCount() {
        return rowItems.size();
    }

    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }

}
