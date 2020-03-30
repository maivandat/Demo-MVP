package com.example.demomvp.data.model;

import java.io.Serializable;

public class Song implements Serializable {
    private String mTitile;
    private String mUrlImage;
    private String mUrlSong;

    public Song(SongBuilder movieBuilder) {
        mTitile = movieBuilder.mTitle;
        mUrlImage = movieBuilder.mUrlImage;
        mUrlSong = movieBuilder.mUrlSong;
    }

    public Song() {
    }


    public String getTitile() {
        return mTitile;
    }

    public void setTitile(String titile) {
        mTitile = titile;
    }

    public String getUrlImage() {
        return mUrlImage;
    }

    public void setUrlImage(String urlImage) {
        mUrlImage = urlImage;
    }

    public String getUrlSong() {
        return mUrlSong;
    }

    public void setUrlSong(String urlSong) {
        mUrlSong = urlSong;
    }

    public static class SongBuilder {
        private String mTitle;
        private String mUrlImage;
        private String mUrlSong;

        public SongBuilder(String title, String urlImage, String urlSong) {
            mTitle = title;
            mUrlImage = urlImage;
            mUrlSong = urlSong;
        }

        public SongBuilder() {
        }


        public SongBuilder title(String title) {
            mTitle = title;
            return this;
        }

        public SongBuilder urlImage(String urlImage) {
            mUrlImage = urlImage;
            return this;
        }

        public SongBuilder urlSong(String urlSong) {
            mUrlSong = urlSong;
            return this;
        }

        public Song build() {
            return new Song(this);
        }
    }

    public final class SongEntry {
        public static final String SONG = "results";
        public static final String TITLE = "songName";
        public static final String URL_IMAGE = "imageURL";
        public static final String URL_SONG = "songURL";
    }
}
