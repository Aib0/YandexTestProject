package com.example.anton.yandextestproject;

public class ArtistData {
    public static final String ID = "id";
    public static final String NAME = "name";
    public static final String TRACKS = "tracks";
    public static final String DESCRIPTION = "description";

    public int id;
    public String name;
    public String[] genres;
    public int tracks;
    public int albums;
    public String link;
    public String description;
    public Cover cover;

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
            return result.substring(0, result.length() - glue.length());
        } else {
            return result;
        }
    }
}

