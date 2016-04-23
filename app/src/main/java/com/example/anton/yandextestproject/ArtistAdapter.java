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
    final public static String TAG = "ArtistAdapter";

    int resource;
    Context context;
    String songCountTemplate;

    public ArtistAdapter(Context context, int resource, ArrayList<ArtistData> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        this.songCountTemplate = context.getResources().getString(R.string.song_count_template);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout artistView;

        ArtistData artistElement = getItem(position);

        if (convertView == null) {
            artistView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater layoutInflater;
            layoutInflater = (LayoutInflater) getContext().getSystemService(inflater);
            layoutInflater.inflate(resource, artistView, true);

        } else {
            artistView = (LinearLayout) convertView;
        }

        TextView artistTitle = (TextView) artistView.findViewById(R.id.artist_title);
        TextView artistTagList = (TextView) artistView.findViewById(R.id.artist_tag_list);
        TextView artistSongCount = (TextView) artistView.findViewById(R.id.artist_song_count);
        ImageView artistPhoto = (ImageView) artistView.findViewById(R.id.artist_small_img);

        artistTitle.setText(artistElement.name);
        artistTagList.setText(artistElement.getTagList());
        artistSongCount.setText(String.format(songCountTemplate, artistElement.albums, artistElement.tracks));

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
