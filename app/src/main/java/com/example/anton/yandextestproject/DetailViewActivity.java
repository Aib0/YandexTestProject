package com.example.anton.yandextestproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailViewActivity extends AppCompatActivity {

    private static final String TAG = "DetailArtistView";

    /**
     * Основной класс детальной ифнормации по артисту
     *
     * @param savedInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Получаем объект, пришедший из родительской activity в виде Parcel
         */
        ArtistData element = getIntent().getParcelableExtra(getString(R.string.artist_data_intent_extra));

        setContentView(R.layout.artist_detail);

        /**
         * Устанавливаем различные свойства layout-a
         */
        ImageView artistBigImage = (ImageView) findViewById(R.id.artist_big_img);
        TextView artistBiography = (TextView) findViewById(R.id.artist_biography);
        TextView artistTagList = (TextView) findViewById(R.id.artist_tag_list);
        TextView artistSongCount = (TextView) findViewById(R.id.artist_song_count);

        setTitle(element.name);
        artistBiography.setText(element.description);
        artistTagList.setText(element.getGenresString());
        artistSongCount.setText(String.format(getResources().getString(R.string.song_count_template), element.albums, element.tracks));

        /**
         * И подгружаем из интернета/кэша большую картинку с артистом,
         * подставляя на время загрузки placeholder
         * и соответствующую картинку, ели загрузка неудачна
         */
        try {
            Picasso
                    .with(this)
                    .load(element.cover.big)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.no_image)
                    .into(artistBigImage);
        } catch (Exception e) {
            Log.e(TAG, "error downloading file: " + e.toString());
        }
    }
}
