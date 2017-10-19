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
    private transient int pathId;
    private transient int urlId;
    private String data;
    private int hop;
    private String title;

    public Path(int UrlId, String data, int hop, String title) {
        this.urlId = UrlId;
        this.data = data;
        this.hop = hop;
        this.title = title;
    }

    public int getPathId() {
        return pathId;
    }

    public void setPathId(int pathId) {
        this.pathId = pathId;
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

    public int getHop() {
        return hop;
    }

    public void setHop(int hop) {
        this.hop = hop;
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

        Path path = (Path) o;

        if (pathId != path.pathId) return false;
        if (urlId != path.urlId) return false;
        return data.equals(path.data);
    }

    @Override
    public int hashCode() {
        int result = pathId;
        result = 31 * result + urlId;
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }
}
