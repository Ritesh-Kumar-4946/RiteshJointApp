package com.example.ritesh.jointapp.beans;

/**
 * Created by ritesh on 2/8/16.
 */
public class InboxRowItemONE {

    /*http://theopentutorials.com/post/uncategorized/android-custom-listview-with-image-and-text-using-baseadapter/*/

    private int imageId;   // userimage
    private int rightarrow;  // timerightarrow
    private String title;   // username
    private String desc;    // userstatus
    private String usercard;    // usercard
    private String time;    // time

    public InboxRowItemONE(int imageId, int rightarrow, String title, String desc, String usercard, String time) {
        this.imageId = imageId;
        this.rightarrow = rightarrow;
        this.title = title;
        this.desc = desc;
        this.usercard = usercard;
        this.time = time;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getRightarrow() {
        return rightarrow;
    }

    public void setRightarrow(int rightarrow) {
        this.rightarrow = rightarrow;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsercard() {
        return usercard;
    }

    public void setUsercard(String usercard) {
        this.usercard = usercard;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return title + "\n" + desc;
    }


}
