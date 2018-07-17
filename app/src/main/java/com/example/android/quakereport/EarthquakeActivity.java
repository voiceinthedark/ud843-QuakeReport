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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    //url string that will return the last 10 earthquakes with 6.0 magnitude or more
    public static final String URL_QUERY =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";


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

        /**
         * Create our async task and execute it on the URL Query
         */
        EarthquakeAsyncTask task = new EarthquakeAsyncTask();
        task.execute(URL_QUERY);
    }

    /**
     * Update the UI with the received list of earth quakes
     *
     * @param earthQuakes list of earthquake objects fetched from the USGS server
     */
    private void updateUI(ArrayList<EarthQuake> earthQuakes) {
        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        EarthQuakeAdapter earthQuakeAdapter =
                new EarthQuakeAdapter(this, R.layout.list_item_activity, earthQuakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(earthQuakeAdapter);
        /**
         * Set click listener to open the website for the event
         */
        earthquakeListView.setOnItemClickListener(mItemClickListener);
    }


    private class EarthquakeAsyncTask extends AsyncTask<String, Void, ArrayList<EarthQuake>> {

        @Override
        protected ArrayList<EarthQuake> doInBackground(String... strings) {
            //extract earthquakes data
            return QueryUtils.extractEarthquakes(strings[0]);
        }

        @Override
        protected void onPostExecute(ArrayList<EarthQuake> earthQuakes) {
            updateUI(earthQuakes);
        }
    }
}
