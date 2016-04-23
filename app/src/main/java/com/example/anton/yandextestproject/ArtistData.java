package com.example.anton.yandextestproject;

import android.os.Parcel;
import android.os.Parcelable;

public class ArtistData implements Parcelable {
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String TRACKS = "tracks";
    public static final String DESCRIPTION = "description";
    public static final int MAX_TAGS_LENGTH = 50;

    public int id;
    public String name;
    public String[] genres;
    public int tracks;
    public int albums;
    public String link;
    public String description;
    public Cover cover;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeInt(genres.length);
        dest.writeStringArray(genres);
        dest.writeInt(tracks);
        dest.writeInt(albums);
        dest.writeString(link);
        dest.writeString(description);
        dest.writeString(cover.big);
        dest.writeString(cover.small);
    }
    
    public static final Parcelable.Creator<ArtistData> CREATOR = new Parcelable.Creator<ArtistData>() {
        public ArtistData createFromParcel(Parcel in) {
            return new ArtistData(in);
        }

        public ArtistData[] newArray(int size) {
            return new ArtistData[size];
        }
    };

    private ArtistData(Parcel in) {
        id = in.readInt();
        name = in.readString();

        int genresLength = in.readInt();
        genres = new String[genresLength];
        in.readStringArray(genres);
        tracks = in.readInt();
        albums = in.readInt();
        link = in.readString();
        description = in.readString();

        cover = new Cover();
        cover.big = in.readString();
        cover.small = in.readString();
    }

    public class Cover {
        public String small;
        public String big;
    }

    public String getShortDesc() {
        if (this.description.length() > 30) {
            return this.description.substring(0, 30).concat("...");
        } else {
            return this.description;
        }
    }

    public String getTagList() {
        String result = "";
        String glue = ", ";

        for (String tag : this.genres) {
            result += tag + glue;
        }

        if (!result.isEmpty()) {
            result =  result.substring(0, result.length() - glue.length());
        }

        if (result.length() > MAX_TAGS_LENGTH) {
            result = result.substring(0, MAX_TAGS_LENGTH) + "<...>";
        }

        return result;
    }
}

