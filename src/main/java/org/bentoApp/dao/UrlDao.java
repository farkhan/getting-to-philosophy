/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bentoapp.dao;

import org.bentoapp.model.Url;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;
/**
 *
 * @author Farhan Khan
 */
public class UrlDao implements UrlDaoImpl {
    private Sql2o sql2o;
    
    public UrlDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }
    
    @Override
    public int add(Url url) throws DaoException {
        String sql = "INSERT INTO url (data, title) VALUES (:data, :title)";
        try (Connection con = sql2o.open()) {
            Long id = (Long) con.createQuery(sql, true)
                    .addParameter("data", url.getData())
                    .addParameter("title", url.getTitle())
                    .executeUpdate()
                    .getKey();
            return id.intValue();
        } catch (Sql2oException ex) {
            throw new DaoException(ex, "Problem adding url");
        }
    }

    @Override
    public List<Url> findAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM url")
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Url.class);
        }
    }

    @Override
    public Url findById(int urlId) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * from url WHERE url_id = :urlId")
                    .addParameter("urlId", urlId)
                    .executeAndFetchFirst(Url.class);
        }
    }
}
