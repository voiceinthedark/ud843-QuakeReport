package com.example.android.quakereport;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    private static final String TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link EarthQuake} objects that has been built up from
     * parsing a JSON response.
     */
    public static ArrayList<EarthQuake> extractEarthquakes(String urlToQuery) {

        if(urlToQuery.equals("")){
            return null;
        }

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<EarthQuake> earthquakes = new ArrayList<>();

        //Create a URL object
        URL urlObject = createURL(urlToQuery);
        String response = "";

        if (urlObject != null) {
            //Make Http Connection on the url
            try {
                response = makeHttpRequest(urlObject);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return null;
        }

        if (!response.equals("")) {
            //parse JSON response and return eartquakes list
            earthquakes = (ArrayList<EarthQuake>) parseJSONResponse(response);
        }


        // Return the list of earthquakes
        return earthquakes;
    }

    private static List<EarthQuake> parseJSONResponse(String response) {
        List<EarthQuake> earthquakes = new ArrayList<>();
        // Try to parse the response. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            //Setup the Json object to accept the sample JSON response
            JSONObject root = new JSONObject(response);

            //Get the "features" array
            JSONArray featuresJSONArray = root.optJSONArray("features");


            for (int i = 0; i < featuresJSONArray.length(); i++) {
                //Get the properties JSONObject from the JSONArray
                JSONObject propertiesJSONObject = featuresJSONArray
                        .getJSONObject(i)
                        .getJSONObject("properties");
                //get the magnitude from the properties object
                double magnitude = propertiesJSONObject.getDouble("mag");
                //get the location string
                String location = propertiesJSONObject.getString("place");
                //get the time long value
                long time = propertiesJSONObject.getLong("time");

                //get the url of the earthquake
                String url = propertiesJSONObject.getString("url");

                //add the data to the earthquakes List
                earthquakes.add(new EarthQuake
                        .Builder(location)
                        .magnitude(magnitude)
                        .date(new Date(time))
                        .url(url)
                        .build());
            }


        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        return earthquakes;
    }

    /**
     * Transform a url string into a valid URL object
     *
     * @param queryString the query string to be converted
     * @return a well formed URL object
     */
    private static URL createURL(String queryString) {
        URL url = null;
        //if query string is empty return null
        if (queryString.equals("")) {
            return null;
        }
        try {
            url = new URL(queryString);

        } catch (MalformedURLException e) {
            Log.e(TAG, "createURL: Malformed URL", e);
        }

        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String response = "";

        if(url == null){
            return response;
        }

        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            //if the response code we receive is ok proceed to open the stream
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                response = getResponseStream(inputStream);
            }


        } catch (IOException e) {
            Log.e(TAG, "makeHttpRequest: ", e);
        }
        finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }

            if(inputStream != null){
                inputStream.close();
            }
        }
        return response;
    }

    /**
     * Helper method to extract the response from the input stream
     *
     * @param inputStream the input stream received from the http connection
     * @return a string response of JSON format
     */
    private static String getResponseStream(InputStream inputStream) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStreamReader streamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(streamReader);


            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = reader.readLine();
            }

            streamReader.close();
            reader.close();


        } catch (IOException e) {
            Log.e(TAG, "getResponseStream: ", e);
        }

        return stringBuilder.toString();
    }

}
