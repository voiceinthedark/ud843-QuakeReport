package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class EarthQuakeAdapter extends ArrayAdapter<EarthQuake> implements Serializable
        /*in order to save state of object*/ {

    private static final String DATE_PATTERN = "MMM, dd yyyy";
    private static final String TIME_PATTERN = "hh:mm a";

    private List<EarthQuake> mEarthQuakeList;
    private Context mContext;
    private int mResourceId;

    public EarthQuakeAdapter(@NonNull Context context, int resource, @NonNull List<EarthQuake> objects) {
        super(context, resource, objects);
        mEarthQuakeList = objects;
        mContext = context;
        mResourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //recycle views if not in use
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(mContext).inflate(mResourceId, parent, false);
        }

        //attach views
        TextView magnitudeView = view.findViewById(R.id.item_magnitude);
        TextView offsetView = view.findViewById(R.id.item_offset);
        TextView locationView = view.findViewById(R.id.item_location);
        TextView dateView = view.findViewById(R.id.item_date);
        TextView timeView = view.findViewById(R.id.item_time);

        //get the EarthQuake object
        EarthQuake earthQuake = mEarthQuakeList.get(position);

        //format the magnitude to be 1 decimal place
        DecimalFormat magnitudeFormatter = new DecimalFormat("0.0");
        String magnitude = magnitudeFormatter.format(earthQuake.getMagnitude());

        //set the values of the earthquake object
        magnitudeView.setText(magnitude);

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(earthQuake.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);


        //Get the location
        String location = earthQuake.getLocation();
        //split location into offset and place
        String[] formattedLocation = splitLocationString(location);
        String offset = formattedLocation[0];
        String place = formattedLocation[1];

        //set offset
        offsetView.setText(offset);
        //set location
        locationView.setText(place);
        /**
         * Format the date object to become more readable
         */
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN, Locale.US);
        String date = dateFormat.format(earthQuake.getDate());
        dateView.setText(date);

        /**
         * Formate the time object
         */
        SimpleDateFormat timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.US);
        String time = timeFormat.format(earthQuake.getDate());
        timeView.setText(time);


        return view;
    }

    /**
     * Helper method to extract a color for magnitude force
     * @param magnitude the magnitue of the earthquake object
     * @return the color to be displayed on the circular shape
     */
    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

    /**
     * Helper method to split location string into two tokens: an offset and a place
     * @param location the location string extracted from the JSON response
     * @return a String array containing the offset and the place
     */
    private String[] splitLocationString(String location){
        int indexOfSplit = location.indexOf("of");
        String offset;
        String place;

        if (indexOfSplit != -1) {
            //move three characters ahead
            indexOfSplit += 3;
            offset = location.substring(0, indexOfSplit);
            place = location.substring(indexOfSplit, location.length());

        } else {
            offset = "Near the";
            place = location;
        }

        offset = offset.trim();
        return new String[]{offset, place};
    }
}
