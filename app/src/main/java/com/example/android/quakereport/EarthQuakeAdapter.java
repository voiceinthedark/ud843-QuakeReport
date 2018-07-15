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
        TextView locationView = view.findViewById(R.id.item_location);
        TextView dateView = view.findViewById(R.id.item_date);

        //get the EarthQuake object
        EarthQuake earthQuake = mEarthQuakeList.get(position);

        //set the values of the earthquake object
        magnitudeView.setText(String.valueOf(earthQuake.getMagnitude()));
        locationView.setText(earthQuake.getLocation());
        /**
         * Format the date object to become more readable
         */
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN, Locale.US);
        String date = dateFormat.format(earthQuake.getDate());
        dateView.setText(date);


        return view;
    }
}
