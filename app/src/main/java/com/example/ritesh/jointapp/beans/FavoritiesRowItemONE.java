package com.example.ritesh.jointapp.beans;

/**
 * Created by ritesh on 2/8/16.
 */
public class FavoritiesRowItemONE {

    private int imageId;
    private String title;
    private int desc;

    public  FavoritiesRowItemONE(int imageId, String title, int desc){
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
    public int getDesc () {
        return desc;
    }
    public void setDesc (int desc){
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
