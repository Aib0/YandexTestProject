package com.example.anton.yandextestproject;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ArtistAdapter extends ArrayAdapter<ArtistData> {
    final public static String TAG = "YandexTestArtistAdapter";

    int resource;

    /**
     * Контекст вызова, для получения ресурсов
     */
    Context context;

    /**
     * Адаптер для вывода на экран списка артистов
     *
     * @param context Context
     * @param resource int
     * @param objects ArrayList<ArtistData> список объектов
     */
    public ArtistAdapter(Context context, int resource, ArrayList<ArtistData> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
    }

    /**
     * Получение готового для вывода view-a
     *
     * @param position int
     * @param convertView View
     * @param parent ViewGroup
     * @return View
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout artistView;

        ArtistData artistElement = getItem(position);

        /**
         * Делаем то, что написано в базовом методе
         */
        if (convertView == null) {
            artistView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater layoutInflater;
            layoutInflater = (LayoutInflater) getContext().getSystemService(inflater);
            layoutInflater.inflate(resource, artistView, true);
        } else {
            artistView = (LinearLayout) convertView;
        }

        /**
         * Устанавливаем различные свойства layout-a
         */
        TextView artistTitle = (TextView) artistView.findViewById(R.id.artist_title);
        TextView artistTagList = (TextView) artistView.findViewById(R.id.artist_tag_list);
        TextView artistSongCount = (TextView) artistView.findViewById(R.id.artist_song_count);
        ImageView artistPhoto = (ImageView) artistView.findViewById(R.id.artist_small_img);

        artistTitle.setText(artistElement.name);
        artistTagList.setText(artistElement.getGenresString());
        artistSongCount.setText(String.format(context.getResources().getString(R.string.song_count_template), artistElement.albums, artistElement.tracks));

        /**
         * И подгружаем из интернета/кэша большую картинку с артистом,
         * подставляя на время загрузки placeholder
         * и соответствующую картинку, ели загрузка неудачна
         */
        try {
            Picasso
                    .with(context)
                    .load(artistElement.cover.small)
                    .placeholder(R.drawable.loading)
                    .error(R.drawable.no_image)
                    .into(artistPhoto);
        } catch (Exception e) {
            Log.e(TAG, "error downloading file: " + e.toString());
        }

        return artistView;
    }
}
