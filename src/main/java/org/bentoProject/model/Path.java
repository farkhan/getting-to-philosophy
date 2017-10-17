/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bentoProject.model;

/**
 *
 * @author Farhan Khan
 */
public class Path {
    private int id;
    private int urlId;
    private String data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUrlId() {
        return urlId;
    }

    public void setUrlId(int UrlId) {
        this.urlId = UrlId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Path(int UrlId, String data) {
        this.urlId = UrlId;
        this.data = data;
    }

     @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Path path = (Path) o;

        if (id != path.id) return false;
        if (urlId != path.urlId) return false;
        return data.equals(path.data);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + urlId;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }
}
