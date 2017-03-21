package com.example.ritesh.jointapp.beans;

/**
 * Created by ritesh on 3/8/16.
 */
public class DashboardRowItemONE {

    private int imageId;  // user image
    private int sentinbox; // inbox or sent
    private int desc;  // arrow
    private String title;  // user name
    private String money;  // money
    private String date;  // date

    public  DashboardRowItemONE(int imageId, int desc, int sentinbox, String title, String money, String date ){
        this.imageId = imageId;
        this.desc = desc;
        this.sentinbox = sentinbox;
        this.title = title;
        this.money = money;
        this.date = date;

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

    public int getSentinbox () {
        return sentinbox;
    }
    public void setSentinbox (int sentinbox){
        this.sentinbox = sentinbox;
    }

    public String getTitle () {
        return title;
    }
    public void setTitle (String title){
        this.title = title;
    }

    public String getMoney () {
        return money;
    }
    public void setMoney (String money){
        this.money = money;
    }

    public String getDate () {
        return date;
    }
    public void setDate (String date){
        this.date = date;
    }


    @Override
    public String toString () {
        return title + "\n" + desc;
    }
}
