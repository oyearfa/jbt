package com.example.task.model;

import android.net.Uri;

public class Pojo {
    String img;
    String thumb;
    String title;
    Uri uri;
    public Pojo() {
    }

    public Pojo(String str, String str2, String str3) {
        this.title = str;
        this.thumb = str2;
        this.img = str3;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getThumb() {
        return this.thumb;
    }

    public void setThumb(String str) {
        this.thumb = str;
    }

    public String getImg() {
        return this.img;
    }

    public void setImg(String str) {
        this.img = str;
    }

    public Uri getUri() {
        return this.uri;
    }

    public void setUri(Uri uri2) {
        this.uri = uri2;
    }

}
