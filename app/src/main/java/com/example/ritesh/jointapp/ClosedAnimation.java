/* Created by Srikanth gr.
 */

package com.example.ritesh.jointapp;

import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;

public class ClosedAnimation extends TranslateAnimation implements
        TranslateAnimation.AnimationListener {

    private LinearLayout mainLayout;
    int panelWidth;

    public ClosedAnimation(LinearLayout layout, int width, int fromXType,
                           float fromXValue, int toXType, float toXValue, int fromYType,
                           float fromYValue, int toYType, float toYValue) {

        super(fromXType, fromXValue, toXType, toXValue, fromYType, fromYValue,
                toYType, toYValue);

        // Initialize
        mainLayout = layout;
        panelWidth = width;
        setDuration(300);
        setFillAfter(false);
        setInterpolator(new AccelerateDecelerateInterpolator());
        setAnimationListener(this);

        // Clear left and right margins
        LayoutParams params = (LayoutParams) mainLayout.getLayoutParams();
        params.rightMargin = 0;
        params.leftMargin = 0;
        mainLayout.setLayoutParams(params);
        mainLayout.requestLayout();
        mainLayout.startAnimation(this);

    }

    public void onAnimationEnd(Animation animation) {
        // not implemented
    }

    public void onAnimationRepeat(Animation animation) {
        // not implemented
    }

    public void onAnimationStart(Animation animation) {
        // not implemented
    }

}
