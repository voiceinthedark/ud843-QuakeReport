package com.example.android.quakereport;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.util.List;

/**
 * A loader class that inherits {@link AsyncTaskLoader} to allow background loading of network data
 * This class is much more efficient than the simple {@link android.os.AsyncTask} class in terms
 * of memory efficiency as well as lifecycle management.
 */
public class EarthquakeLoader extends AsyncTaskLoader<List<EarthQuake>> {

    private static final String TAG = EarthquakeLoader.class.getSimpleName();

    //The url to be requested
    private String mUrl;

    /**
     * public constructor of the custom {@link AsyncTaskLoader} class
     *  @param context the application context
     * @param url     the url to be requested
     */
    EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    /**
     * Our work will happen here on the worker thread
     *
     * @return a list of {@link EarthQuake} objects
     */
    @Nullable
    @Override
    public List<EarthQuake> loadInBackground() {

        return QueryUtils.extractEarthquakes(mUrl);
    }

    /**
     * load the data when the activity starts
     */
    @Override
    protected void onStartLoading() {

        forceLoad();
    }
}
