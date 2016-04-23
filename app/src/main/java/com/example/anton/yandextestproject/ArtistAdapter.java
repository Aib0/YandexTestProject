package com.example.anton.yandextestproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ArtistAdapter extends ArrayAdapter<ArtistData> {
    int resource;

    public ArtistAdapter(Context context, int resource, ArrayList<ArtistData> objects) {
        super(context, resource, objects);

        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout artistView;

        ArtistData artistElement = getItem(position);

        if (convertView == null) {
            artistView = new LinearLayout(getContext());
            String inflater = Context.LAYOUT_INFLATER_SERVICE;
            LayoutInflater layoutInflater;
            layoutInflater = (LayoutInflater)getContext().getSystemService(inflater);
            layoutInflater.inflate(resource, artistView, true);

        } else {
            artistView = (LinearLayout) convertView;
        }

        TextView artistTitle = (TextView)artistView.findViewById(R.id.artist_title);
        TextView artistShortDesc = (TextView)artistView.findViewById(R.id.artist_short_desc);

        artistTitle.setText(artistElement.name);
        artistShortDesc.setText(artistElement.getShortDesc());

        return artistView;
    }
}
