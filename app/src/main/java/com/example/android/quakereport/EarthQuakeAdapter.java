package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class EarthQuakeAdapter extends ArrayAdapter<EarthQuake> {

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

        //set the values of the earthquake object
        magnitudeView.setText(String.valueOf(earthQuake.getMagnitude()));
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
