package com.example.ritesh.jointapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.ritesh.jointapp.adapters.InboxCustomBaseAdapterTWO;
import com.example.ritesh.jointapp.beans.InboxRowItemONE;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ritesh on 16/7/16.
 */
public class InboxTHREE extends AppCompatActivity implements AdapterView.OnItemClickListener {

    /*http://www.tutorialsbuzz.com/2014/03/watsapp-custom-listview-imageview-textview-baseadapter.html*/

    public static final String[] titles = new String[]{
            "Ritesh", "Kumar", "Sachin", "Tendulkar",
            "Ritesh", "Kumar", "Sachin", "Tendulkar",
            "Ritesh", "Kumar", "Sachin", "Tendulkar",
            "Ritesh", "Kumar", "Sachin", "Tendulkar",
            "Ritesh", "Kumar", "Sachin", "Tendulkar",
            "Ritesh", "Kumar", "Sachin", "Tendulkar",
            "Ritesh", "Kumar", "Sachin", "Tendulkar",
            "Ritesh", "Kumar", "Sachin", "Tendulkar",
            "Ritesh", "Kumar", "Sachin", "Tendulkar",
            "Ritesh", "Kumar", "Sachin", "Tendulkar",
            "Ritesh", "Kumar", "Sachin", "Tendulkar",
            "Ritesh", "Kumar", "Sachin", "Tendulkar",
            "Ritesh", "Kumar", "Sachin", "Tendulkar",
            "Ritesh", "Kumar", "Sachin", "Tendulkar",
            "Ritesh", "Kumar", "Sachin", "Tendulkar",
            "Ritesh", "Kumar", "Sachin", "Tendulkar"};

    public static final String[] usercardtext = new String[]{
            "It is an aggregate accessory fruit", "It is the largest herbaceous flowering plant", "Citrus Fruit", "Mixed Fruits",
            "It is an aggregate accessory fruit", "It is the largest herbaceous flowering plant", "Citrus Fruit", "Mixed Fruits",
            "It is an aggregate accessory fruit", "It is the largest herbaceous flowering plant", "Citrus Fruit", "Mixed Fruits",
            "It is an aggregate accessory fruit", "It is the largest herbaceous flowering plant", "Citrus Fruit", "Mixed Fruits",
            "It is an aggregate accessory fruit", "It is the largest herbaceous flowering plant", "Citrus Fruit", "Mixed Fruits",
            "It is an aggregate accessory fruit", "It is the largest herbaceous flowering plant", "Citrus Fruit", "Mixed Fruits",
            "It is an aggregate accessory fruit", "It is the largest herbaceous flowering plant", "Citrus Fruit", "Mixed Fruits",
            "It is an aggregate accessory fruit", "It is the largest herbaceous flowering plant", "Citrus Fruit", "Mixed Fruits",
            "It is an aggregate accessory fruit", "It is the largest herbaceous flowering plant", "Citrus Fruit", "Mixed Fruits",
            "It is an aggregate accessory fruit", "It is the largest herbaceous flowering plant", "Citrus Fruit", "Mixed Fruits",
            "It is an aggregate accessory fruit", "It is the largest herbaceous flowering plant", "Citrus Fruit", "Mixed Fruits",
            "It is an aggregate accessory fruit", "It is the largest herbaceous flowering plant", "Citrus Fruit", "Mixed Fruits",
            "It is an aggregate accessory fruit", "It is the largest herbaceous flowering plant", "Citrus Fruit", "Mixed Fruits",
            "It is an aggregate accessory fruit", "It is the largest herbaceous flowering plant", "Citrus Fruit", "Mixed Fruits",
            "It is an aggregate accessory fruit", "It is the largest herbaceous flowering plant", "Citrus Fruit", "Mixed Fruits",
            "It is an aggregate accessory fruit", "It is the largest herbaceous flowering plant", "Citrus Fruit", "Mixed Fruits"};

    public static final String[] timetext = new String[]{
            "Xmas Card", "Diwali Card", "New Year Card", "Holi Card",
            "Xmas Card", "Diwali Card", "New Year Card", "Holi Card",
            "Xmas Card", "Diwali Card", "New Year Card", "Holi Card",
            "Xmas Card", "Diwali Card", "New Year Card", "Holi Card",
            "Xmas Card", "Diwali Card", "New Year Card", "Holi Card",
            "Xmas Card", "Diwali Card", "New Year Card", "Holi Card",
            "Xmas Card", "Diwali Card", "New Year Card", "Holi Card",
            "Xmas Card", "Diwali Card", "New Year Card", "Holi Card",
            "Xmas Card", "Diwali Card", "New Year Card", "Holi Card",
            "Xmas Card", "Diwali Card", "New Year Card", "Holi Card",
            "Xmas Card", "Diwali Card", "New Year Card", "Holi Card",
            "Xmas Card", "Diwali Card", "New Year Card", "Holi Card",
            "Xmas Card", "Diwali Card", "New Year Card", "Holi Card",
            "Xmas Card", "Diwali Card", "New Year Card", "Holi Card",
            "Xmas Card", "Diwali Card", "New Year Card", "Holi Card",
            "Xmas Card", "Diwali Card", "New Year Card", "Holi Card"};

    public static final String[] descriptions = new String[]{
            "12:45 AM", " Monday", "08/15/2016", "04:28 PM",
            "12:45 AM", " Monday", "08/15/2016", "04:28 PM",
            "12:45 AM", " Monday", "08/15/2016", "04:28 PM",
            "12:45 AM", " Monday", "08/15/2016", "04:28 PM",
            "12:45 AM", " Monday", "08/15/2016", "04:28 PM",
            "12:45 AM", " Monday", "08/15/2016", "04:28 PM",
            "12:45 AM", " Monday", "08/15/2016", "04:28 PM",
            "12:45 AM", " Monday", "08/15/2016", "04:28 PM",
            "12:45 AM", " Monday", "08/15/2016", "04:28 PM",
            "12:45 AM", " Monday", "08/15/2016", "04:28 PM",
            "12:45 AM", " Monday", "08/15/2016", "04:28 PM",
            "12:45 AM", " Monday", "08/15/2016", "04:28 PM",
            "12:45 AM", " Monday", "08/15/2016", "04:28 PM",
            "12:45 AM", " Monday", "08/15/2016", "04:28 PM",
            "12:45 AM", " Monday", "08/15/2016", "04:28 PM",
            "12:45 AM", " Monday", "08/15/2016", "04:28 PM"};


    public static final Integer[] images = {
            R.drawable.ic_f, R.drawable.ic_c, R.drawable.ic_c, R.drawable.ic_avtar_f,
            R.drawable.ic_f, R.drawable.ic_c, R.drawable.ic_avtar_f, R.drawable.ic_avtar_f,
            R.drawable.ic_f, R.drawable.ic_c, R.drawable.ic_c, R.drawable.ic_c,
            R.drawable.ic_f, R.drawable.ic_c, R.drawable.ic_avtar_f, R.drawable.ic_avtar_f,
            R.drawable.ic_f, R.drawable.ic_c, R.drawable.ic_c, R.drawable.ic_avtar_f,
            R.drawable.ic_f, R.drawable.ic_c, R.drawable.ic_avtar_f, R.drawable.ic_avtar_f,
            R.drawable.ic_f, R.drawable.ic_avtar_f, R.drawable.ic_c, R.drawable.ic_c,
            R.drawable.ic_f, R.drawable.ic_c, R.drawable.ic_avtar_f, R.drawable.ic_avtar_f,
            R.drawable.ic_f, R.drawable.ic_avtar_f, R.drawable.ic_avtar_f, R.drawable.ic_avtar_f,
            R.drawable.ic_f, R.drawable.ic_c, R.drawable.ic_c, R.drawable.ic_c,
            R.drawable.ic_f, R.drawable.ic_avtar_f, R.drawable.ic_avtar_f, R.drawable.ic_avtar_f,
            R.drawable.ic_f, R.drawable.ic_c, R.drawable.ic_c, R.drawable.ic_c,
            R.drawable.ic_f, R.drawable.ic_avtar_f, R.drawable.ic_avtar_f, R.drawable.ic_avtar_f,
            R.drawable.ic_f, R.drawable.ic_c, R.drawable.ic_c, R.drawable.ic_avtar_f,
            R.drawable.ic_f, R.drawable.ic_c, R.drawable.ic_avtar_f, R.drawable.ic_avtar_f,
            R.drawable.ic_f, R.drawable.ic_avtar_f, R.drawable.ic_avtar_f, R.drawable.ic_c};

    public static final Integer[] rightarrowimages = {
            R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign,
            R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign,
            R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign,
            R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign,
            R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign,
            R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign,
            R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign,
            R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign,
            R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign,
            R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign,
            R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign,
            R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign,
            R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign,
            R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign,
            R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign,
            R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign, R.drawable.ic_rightsign};

    ListView listView;
    List<InboxRowItemONE> rowItems;


    /*left menu slider (Start)*/
    private LinearLayout slidingPanel;
    private boolean isExpanded;
    private DisplayMetrics metrics;
    private RelativeLayout headerPanel;
    private int panelWidth1;
    private ImageView menuViewButton;
    FrameLayout.LayoutParams slidingPanelParameters;
    LinearLayout.LayoutParams headerPanelParameters;
    /*left menu slider (End)*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        rowItems = new ArrayList<InboxRowItemONE>();
        for (int i = 0; i < titles.length; i++) {
            InboxRowItemONE item = new InboxRowItemONE(images[i], rightarrowimages[i], titles[i], usercardtext[i], timetext[i], descriptions[i]);
            rowItems.add(item);
        }

        listView = (ListView) findViewById(R.id.list);
        InboxCustomBaseAdapterTWO adapter = new InboxCustomBaseAdapterTWO(this, rowItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);



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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position,
                            long id) {
        Toast toast = Toast.makeText(getApplicationContext(),
                "Item " + (position + 1) + ": " + rowItems.get(position),
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }
}
