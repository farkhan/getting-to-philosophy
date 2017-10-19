/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bentoapp.dao;

import org.bentoapp.model.Path;

import java.util.List;
/**
 *
 * @author Farhan Khan
 */
public interface PathDaoImpl {
    void add(Path path) throws DaoException ;
    List<Path> findAll();
    Path findById(int path_id);
    List<Path> findByUrlId(int url_id);
}
