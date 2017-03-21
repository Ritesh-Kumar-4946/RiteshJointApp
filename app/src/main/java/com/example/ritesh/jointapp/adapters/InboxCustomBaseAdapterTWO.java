package com.example.ritesh.jointapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ritesh.jointapp.beans.InboxRowItemONE;
import com.example.ritesh.jointapp.R;

import java.util.List;

/**
 * Created by ritesh on 2/8/16.
 */
public class InboxCustomBaseAdapterTWO extends BaseAdapter {

    /*http://theopentutorials.com/post/uncategorized/android-custom-listview-with-image-and-text-using-baseadapter/*/

    Context context;
    List<InboxRowItemONE> rowItems;

    public InboxCustomBaseAdapterTWO(Context context, List<InboxRowItemONE> items) {
        this.context = context;
        this.rowItems = items;
    }

    /*private view holder class*/
    private class ViewHolder {
        ImageView imageView; // userimage
        ImageView rightarrow; // timerightarrow
        TextView txtTitle;  //username
        TextView txtDesc; // userstatus
        TextView usercard; // usercardtext
        TextView time; // timetext
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_inbox_item_customlistview, null);
            holder = new ViewHolder();

            holder.imageView = (ImageView) convertView.findViewById(R.id.icon);
            holder.rightarrow = (ImageView) convertView.findViewById(R.id.right_arrow);
            holder.txtDesc = (TextView) convertView.findViewById(R.id.status);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.member_name);
            holder.usercard = (TextView) convertView.findViewById(R.id.tv_usercard);
            holder.time = (TextView) convertView.findViewById(R.id.contact_type);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        InboxRowItemONE rowItem = (InboxRowItemONE) getItem(position);

        holder.imageView.setImageResource(rowItem.getImageId());
        holder.rightarrow.setImageResource(rowItem.getRightarrow());

        holder.txtDesc.setText(rowItem.getDesc());
        holder.txtTitle.setText(rowItem.getTitle());
        holder.usercard.setText(rowItem.getUsercard());
        holder.time.setText(rowItem.getTime());

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
