package com.example.android.quakereport;

import java.util.Date;
import java.util.GregorianCalendar;

public class EarthQuake {

    private double mMagnitude;
    private String mLocation;
    private Date mDate;

    public double getMagnitude() {
        return mMagnitude;
    }

    public String getLocation() {
        return mLocation;
    }

    public Date getDate() {
        return mDate;
    }

    private EarthQuake(Builder builder){
        this.mLocation = builder.location;
        this.mMagnitude = builder.mmagnitude;
        this.mDate = builder.mdate;
    }

    public static class Builder{ /*start of inner class Builder*/
        //required fields
        private final String location;

        //optional fields
        private double mmagnitude = 0.0;
        private Date mdate = GregorianCalendar.getInstance().getTime();

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

        public EarthQuake build() {
            return new EarthQuake(this);
        }

    }/*end of inner class builder*/



}
