/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.farkhan.model;

import com.google.gson.annotations.SerializedName;

/**
 *
 * @author Farhan Khan
 */
public class Url {
    private transient int urlId;
    @SerializedName("url")
    private String data;
    private String title;

    /**
     *
     * @param data
     */
    public Url(String data) {
        this.data = data;
    }

    /**
     *
     * @return
     */
    public int getUrlId() {
        return urlId;
    }

    /**
     *
     * @param urlId
     */
    public void setUrlId(int urlId) {
        this.urlId = urlId;
    }

    /**
     *
     * @return
     */
    public String getData() {
        return data;
    }

    /**
     *
     * @param data
     */
    public void setData(String data) {
        this.data = data;
    }

    /**
     *
     * @return
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     */
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
