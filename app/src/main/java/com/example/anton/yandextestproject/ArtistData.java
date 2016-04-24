package com.example.anton.yandextestproject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Класс хранения данных об артисте
 */
public class ArtistData implements Parcelable {
    /**
     * Ограничение на максимальную длинну строки списка тэгов
     */
    public static final int MAX_TAGS_LENGTH = 50;

    /**
     * Названия соответствуют полям в json-e
     */
    public int id;
    public String name;
    public String[] genres;
    public int tracks;
    public int albums;
    public String link;
    public String description;
    public Cover cover;

    /**
     * Класс для хранения двух ссылок на изображения артиста
     */
    public class Cover {
        public String small;
        public String big;
    }

    /**
     * Функция для "склеивания" жанров исполнителя
     *
     * @return String
     */
    public String getGenresString() {
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

    /**
     * Методы, необходимые для реализации Parcelable
     *
     * А он нам нужен чтобы без проблем передавать объект данных артиста между activity
     */

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
}

