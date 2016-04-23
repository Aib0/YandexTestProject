package com.example.anton.yandextestproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class DetailViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        ArtistData element = intent.getParcelableExtra("ArtistData");



        setContentView(R.layout.artist_detail);
    }
}
