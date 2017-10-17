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
public class Url {
    private int id;
    private String data;

    public Url(String data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Url url = (Url) o;

        if (id != url.id) return false;
        return data.equals(url.data);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + data.hashCode();
        return result;
    }
}
