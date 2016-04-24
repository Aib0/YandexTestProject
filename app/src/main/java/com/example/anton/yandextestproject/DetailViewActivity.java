package com.example.anton.yandextestproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailViewActivity extends AppCompatActivity {

    private static final String TAG = "DetailArtistView";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        ArtistData element = intent.getParcelableExtra("ArtistData");

        setContentView(R.layout.artist_detail);

        ImageView artistBigimage = (ImageView)findViewById(R.id.artist_big_img);

        TextView artistBiography = (TextView) findViewById(R.id.artist_biography);
        TextView artistTagList = (TextView) findViewById(R.id.artist_tag_list);
        TextView artistSongCount = (TextView) findViewById(R.id.artist_song_count);
//        ActionBar actionBar = getActionBar();
        setTitle(element.name);
        artistBiography.setText(element.description);
        artistTagList.setText(element.getTagList());
        artistSongCount.setText(String.format(getResources().getString(R.string.song_count_template), element.albums, element.tracks));

        try {
            Picasso
                    .with(this)
                    .load(element.cover.big)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.no_image)
                    .into(artistBigimage);
        } catch (Exception e) {
            Log.e(TAG, "error downloading file: " + e.toString());
        }
    }
}
