package com.example.ritesh.jointapp.beans;

/**
 * Created by ritesh on 14/8/16.
 */
public class BrowseJointsRowItemOne {

    /*http://theopentutorials.com/post/uncategorized/android-custom-listview-with-image-and-text-using-baseadapter/*/

    private int imageId;
    private String title;
    private String desc;

    public BrowseJointsRowItemOne(int imageId, String title, String desc){
        this.imageId = imageId;
        this.title = title;
        this.desc = desc;
    }
    public int getImageId () {
        return imageId;
    }
    public void setImageId ( int imageId){
        this.imageId = imageId;
    }
    public String getDesc () {
        return desc;
    }
    public void setDesc (String desc){
        this.desc = desc;
    }
    public String getTitle () {
        return title;
    }
    public void setTitle (String title){
        this.title = title;
    }
    @Override
    public String toString () {
        return title + "\n" + desc;
    }
}
