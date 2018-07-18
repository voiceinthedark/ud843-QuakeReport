/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<EarthQuake>> {

    private static final String SAVED_ADAPTER = "earthquakeadapter";
    private static final String SAVED_EARTHQUAKE_LIST = "earthquakelist";
    private static final String LOG_TAG = EarthquakeActivity.class.getName();
    //url string that will return the last 10 earthquakes with 5.0 magnitude or more
    private static final String URL_QUERY =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=5&limit=10";

    private EarthQuakeAdapter mEarthQuakeAdapter;
    private List<EarthQuake> mEarthQuakeList;

    /**
     * setup a click listener
     */
    private AdapterView.OnItemClickListener mItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //get the url of the object at position clicked
            String url = ((EarthQuake) parent.getAdapter().getItem(position)).getUrl();
            //setup the intent to view in the browser
            Intent openUrlIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            //start the activity
            startActivity(openUrlIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);


        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        mEarthQuakeAdapter =
                new EarthQuakeAdapter(this, R.layout.list_item_activity,
                        new ArrayList<EarthQuake>());

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mEarthQuakeAdapter);
        /**
         * Set click listener to open the website for the event
         */
        earthquakeListView.setOnItemClickListener(mItemClickListener);

        /**
         * Get the support load manager and initialize the loader.
         * the initLoader takes three arguments an id; a {@link Bundle} argument
         * and a {@link android.support.v4.app.LoaderManager.LoaderCallbacks}
         */
        getSupportLoaderManager().initLoader(0, null, this);

    }

    /**
     * call the custom {@link android.support.v4.content.AsyncTaskLoader} on start and init the
     * Url query
     * @param id
     * @param args
     * @return our custom Loader<List<EarthQuake>>
     */
    @NonNull
    @Override
    public Loader<List<EarthQuake>> onCreateLoader(int id, @Nullable Bundle args) {
        return new EarthquakeLoader(EarthquakeActivity.this, URL_QUERY);
    }

    /**
     * Once loading is finished update the UI, by clearing than filling the custom {@link android.widget.ArrayAdapter}
     * @param loader
     * @param data
     */
    @Override
    public void onLoadFinished(@NonNull Loader<List<EarthQuake>> loader, List<EarthQuake> data) {
        //save the list
        mEarthQuakeList = data;
        //clear the Earthquake adapter of all its elements
        mEarthQuakeAdapter.clear();

        //if async task fetched a valid list add them to the earthquake adapter
        if (data != null && !data.isEmpty()) {
            mEarthQuakeAdapter.addAll(data);
        }

    }

    /**
     * if loading is reset clear the {@link android.widget.ArrayAdapter}
     * @param loader the custom {@link android.support.v4.content.AsyncTaskLoader}
     */
    @Override
    public void onLoaderReset(@NonNull Loader<List<EarthQuake>> loader) {
        mEarthQuakeAdapter.clear();
    }


}
