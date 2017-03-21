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
import com.example.ritesh.jointapp.beans.TestimoniesRowItemONE;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by ritesh on 2/8/16.
 */
public class TestimoniesCustomBaseAdapterTWO extends BaseAdapter {

    /*http://theopentutorials.com/post/uncategorized/android-custom-listview-with-image-and-text-using-baseadapter/*/

    Context context;
    List<TestimoniesRowItemONE> rowItems;

    public TestimoniesCustomBaseAdapterTWO(Context context, List<TestimoniesRowItemONE> items) {
        this.context = context;
        this.rowItems = items;
    }

    /*private view holder class*/
    private class ViewHolder {
        CircleImageView imageView;
        TextView txtTitle;
        TextView txtDesc;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        LayoutInflater mInflater = (LayoutInflater)
                context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_testimonies_item_customlistview, null);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.desc);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
            holder.imageView = (CircleImageView) convertView.findViewById(R.id.icon);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        TestimoniesRowItemONE rowItem = (TestimoniesRowItemONE) getItem(position);

        holder.txtDesc.setText(rowItem.getDesc());
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
