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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        // Create a fake list of earthquake locations.

        //extract earthquakes object from the QueryUtils
        List<EarthQuake> earthQuakes = QueryUtils.extractEarthquakes();
        /*earthQuakes.add(new EarthQuake.Builder("San Francisco")
        .magnitude(7.2)
                .date(new GregorianCalendar(2016,2,2).getTime())
        .build());
        earthQuakes.add(new EarthQuake.Builder("London")
                .magnitude(6.1)
                .date(new GregorianCalendar(2015,6,20).getTime())
                .build());
        earthQuakes.add(new EarthQuake.Builder("Tokyo")
                .magnitude(3.9)
                .date(new GregorianCalendar(2014,10,12).getTime())
                .build());
        earthQuakes.add(new EarthQuake.Builder("Mexico City")
                .magnitude(5.4)
                .date(new GregorianCalendar(2014,4,3).getTime())
                .build());
        earthQuakes.add(new EarthQuake.Builder("Moscow")
                .magnitude(2.8)
                .date(new GregorianCalendar(2013,1,31).getTime())
                .build());
        earthQuakes.add(new EarthQuake.Builder("Rio de Janeiro")
                .magnitude(4.9)
                .date(new GregorianCalendar(2012,8,19).getTime())
                .build());
        earthQuakes.add(new EarthQuake.Builder("Paris")
                .magnitude(1.6)
                .date(new GregorianCalendar(2011,9,30).getTime())
                .build());*/

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        EarthQuakeAdapter earthQuakeAdapter =
                new EarthQuakeAdapter(this, R.layout.list_item_activity, earthQuakes);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(earthQuakeAdapter);
    }
}
