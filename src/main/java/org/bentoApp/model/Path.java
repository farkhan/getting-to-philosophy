/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bentoapp.model;

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

    /**
     *
     * @param UrlId
     * @param data
     * @param hop
     * @param title
     */
    public Path(int UrlId, String data, int hop, String title) {
        this.urlId = UrlId;
        this.data = data;
        this.hop = hop;
        this.title = title;
    }

    /**
     *
     * @return
     */
    public int getPathId() {
        return pathId;
    }

    /**
     *
     * @param pathId
     */
    public void setPathId(int pathId) {
        this.pathId = pathId;
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
     * @param UrlId
     */
    public void setUrlId(int UrlId) {
        this.urlId = UrlId;
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
    public int getHop() {
        return hop;
    }

    /**
     *
     * @param hop
     */
    public void setHop(int hop) {
        this.hop = hop;
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
