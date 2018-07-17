package com.example.android.quakereport;

import java.util.Date;
import java.util.GregorianCalendar;

public class EarthQuake {

    private double mMagnitude;
    private String mLocation;
    private Date mDate;
    //string to hold url of earthquake
    private String mUrl;

    public double getMagnitude() {
        return mMagnitude;
    }

    public String getLocation() {
        return mLocation;
    }

    public Date getDate() {
        return mDate;
    }

    public String getUrl() {
        return mUrl;
    }

    private EarthQuake(Builder builder){
        this.mLocation = builder.location;
        this.mMagnitude = builder.mmagnitude;
        this.mDate = builder.mdate;
        this.mUrl = builder.murl;
    }

    public static class Builder{ /*start of inner class Builder*/
        //required fields
        private final String location;

        //optional fields
        private double mmagnitude = 0.0;
        private Date mdate = GregorianCalendar.getInstance().getTime();
        private String murl = "";

        public Builder(String location){
            this.location = location;
        }

        public Builder magnitude(double the_magnitude){
            mmagnitude = the_magnitude;
            return this;
        }
        public Builder date(Date the_date){
            this.mdate = the_date;
            return this;
        }

        public Builder url(String url){
            this.murl = url;
            return this;
        }

        public EarthQuake build() {
            return new EarthQuake(this);
        }

    }/*end of inner class builder*/



}
