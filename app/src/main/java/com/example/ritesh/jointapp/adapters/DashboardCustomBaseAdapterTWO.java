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
import com.example.ritesh.jointapp.beans.DashboardRowItemONE;
import com.example.ritesh.jointapp.beans.FavoritiesRowItemONE;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ritesh on 3/8/16.
 */
public class DashboardCustomBaseAdapterTWO extends BaseAdapter {

    Context context;
    ArrayList<DashboardRowItemONE> rowItems;

    public DashboardCustomBaseAdapterTWO(Context context, ArrayList<DashboardRowItemONE> items) {
        this.context = context;
        this.rowItems = items;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView;
        ImageView txtDesc;
        ImageView sentinbox;
        TextView txtTitle;
        TextView money;
        TextView date;

    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_dashboard_item_customlistview, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            holder.txtDesc = (ImageView) convertView.findViewById(R.id.desc);
            holder.sentinbox = (ImageView) convertView.findViewById(R.id.arrow);

            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.money = (TextView) convertView.findViewById(R.id.tv_money);
            holder.date = (TextView) convertView.findViewById(R.id.tv_month);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        DashboardRowItemONE rowItem = (DashboardRowItemONE) getItem(position);

        holder.txtDesc.setImageResource(rowItem.getDesc());
        holder.imageView.setImageResource(rowItem.getImageId());
        holder.sentinbox.setImageResource(rowItem.getSentinbox());

        holder.txtTitle.setText(rowItem.getTitle());
        holder.money.setText(rowItem.getMoney());
        holder.date.setText(rowItem.getDate());

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
