package com.example.anton.yandextestproject;

/*{
        "id":1080505,
        "name":"Tove Lo",
        "genres":[
        "pop",
        "dance",
        "electronics"
        ],
        "tracks":81,
        "albums":22,
        "link":"http://www.tove-lo.com/",
        "description":"шведская певица и автор песен. Она привлекла к себе внимание в 2013 году с выпуском сингла «Habits», но настоящего успеха добилась с ремиксом хип-хоп продюсера Hippie Sabotage на эту песню, который получил название «Stay High». 4 марта 2014 года вышел её дебютный мини-альбом Truth Serum, а 24 сентября этого же года дебютный студийный альбом Queen of the Clouds. Туве Лу является автором песен таких артистов, как Icona Pop, Girls Aloud и Шер Ллойд.",
        "cover":{
        "small":"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/300x300",
        "big":"http://avatars.yandex.net/get-music-content/dfc531f5.p.1080505/1000x1000"
        }
        },*/
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
        return this.description.substring(0,30);
    }
}

