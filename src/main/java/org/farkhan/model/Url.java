/* 
 * Copyright (C) 2017 Farhan Khan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
