package com.example.anton.yandextestproject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    protected String data;
    protected String URL = "http://download.cdn.yandex.net/mobilization-2016/artists.json";
    protected String artistData = "";
    protected ArtistAdapter artistAdapter;
    protected ListView mainListView;
    protected String ArtistsCacheFilename;

    public static final String LOG = "YandexTest";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArtistsCacheFilename = getString(R.string.cache_file_artists);

        setTitle(getString(R.string.main_activity_title));

        artistData = loadDataFromCache();

        drawData();

        new HttpAsyncRequestArtists().execute(URL);
    }

    private void drawData() {
        artistAdapter = handleData();

        if (null == artistAdapter) {
            setContentView(R.layout.empty_main);
            return;
        }

        setContentView(R.layout.activity_main);

        mainListView = (ListView) findViewById(R.id.main_list_view);
        mainListView.setAdapter(artistAdapter);
        mainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ArtistData item = artistAdapter.getItem(position);

                Intent intent = new Intent(MainActivity.this, DetailViewActivity.class);

                intent.putExtra("ArtistData", item);

                startActivity(intent);
            }
        });
    }

    private String loadDataFromCache() {
        String result = "";

        try {
            FileInputStream fis = openFileInput(ArtistsCacheFilename);
            StringBuffer fileContent = new StringBuffer("");

            byte[] buffer = new byte[1024];
            int n;

            while ((n = fis.read(buffer)) != -1) {
                fileContent.append(new String(buffer, 0, n));
            }

            result = fileContent.toString();
        } catch (FileNotFoundException e) {
            Log.i(LOG, "Not found cache file");
            e.printStackTrace();
        } catch (IOException e) {
            Log.i(LOG, "Error reading file");
            e.printStackTrace();
        }

        return result;
    }

    private boolean saveDataToCache(String data) {
        try {
            FileOutputStream fos = openFileOutput(ArtistsCacheFilename, Context.MODE_PRIVATE);
            fos.write(data.getBytes());
            fos.close();
        } catch (Exception e) {
            Log.e(LOG, "Error saving response to cache: " + e.toString());
            return false;
        }
        return true;
    }

    @Nullable
    private ArtistAdapter handleData() {
        Gson gson = new Gson();

        Type artistDataType = new TypeToken<List<ArtistData>>() {}.getType();

        ArrayList<ArtistData> artistDataList = gson.fromJson(artistData, artistDataType);

        if (artistDataList == null) {
            return null;
        } else {
            return new ArtistAdapter(getBaseContext(), R.layout.artist_layout, artistDataList);
        }
    }

    private class HttpAsyncRequestArtists extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {

            return GET(urls[0]);
        }

        private String convertInputStreamToString(InputStream inputStream) throws IOException {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            String result = "";
            while ((line = bufferedReader.readLine()) != null)
                result += line;

            inputStream.close();
            return result;

        }

        public String GET(String url) {
            InputStream inputStream;
            String result = "";
            try {

                // create HttpClient
                HttpClient httpclient = new DefaultHttpClient();

                // make GET request to the given URL
                HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

                // receive response as inputStream
                inputStream = httpResponse.getEntity().getContent();

                // convert inputstream to string
                if (inputStream != null)
                    result = convertInputStreamToString(inputStream);
                else
                    result = "";

            } catch (Exception e) {
                Log.d(LOG, e.getLocalizedMessage());
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.isEmpty()) {
                return;
            }

            artistData = result;
            drawData();
            saveDataToCache(artistData);
        }
    }
}
