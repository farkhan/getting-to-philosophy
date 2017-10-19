/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bentoProject.dao;

import org.bentoProject.model.Path;

import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;

import java.util.List;
/**
 *
 * @author Farhan Khan
 */
public class PathDao implements PathDaoImpl {
    private Sql2o sql2o;
    
    public PathDao(Sql2o sql2o) {
        this.sql2o = sql2o;
    }
    
    @Override
    public void add(Path path) throws DaoException {
        String sql = "INSERT INTO path (url_id, data, hop, title) VALUES (:urlId, :data, :hop, :title)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("urlId", path.getUrlId())
                    .addParameter("data", path.getData())
                    .addParameter("hop", path.getHop())
                    .addParameter("title", path.getTitle())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            throw new DaoException(ex, "Problem adding path");
        }
    }
    
    @Override
    public List<Path> findAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM path")
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Path.class);
        }
    }
    
    @Override
    public Path findById(int pathId) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * from path WHERE path_id = :pathId")
                    .addParameter("pathId", pathId)
                    .executeAndFetchFirst(Path.class);
        }
    }

    @Override
    public List<Path> findByUrlId(int urlId) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT title, data, hop FROM path WHERE url_id = :urlId")
                    .addParameter("urlId", urlId)
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Path.class);
        }
    }
}
