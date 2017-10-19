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
        String sql = "INSERT INTO path(data) VALUES (:data)";
        try (Connection con = sql2o.open()) {
            int id = (int) con.createQuery(sql)
                    .bind(path)
                    .executeUpdate()
                    .getKey();
            path.setPathId(id);
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
}
