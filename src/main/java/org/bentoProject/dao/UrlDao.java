/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bentoProject.dao;

import org.bentoProject.model.Url;

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
    public void add(Url url) throws DaoException {
        String sql = "INSERT INTO url(data) VALUES (:data)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .bind(url)
                    .executeUpdate()
                    .getKey();
            url.setUrlId(id);
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
