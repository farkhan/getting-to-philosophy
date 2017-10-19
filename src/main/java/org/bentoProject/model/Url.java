/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bentoProject.model;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Farhan Khan
 */
public class Url {
    private int urlId;
    @SerializedName("url")
    private String data;
    private String title;

    public Url(String data) {
        this.data = data;
    }

    public int getUrlId() {
        return urlId;
    }

    public void setUrlId(int urlId) {
        this.urlId = urlId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Url url = (Url) o;

        if (urlId != url.urlId) return false;
        return data.equals(url.data);
    }

    @Override
    public int hashCode() {
        int result = urlId;
        result = 31 * result + data.hashCode();
        return result;
    }
}
