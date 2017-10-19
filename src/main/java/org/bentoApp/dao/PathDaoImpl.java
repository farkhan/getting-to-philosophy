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

    /**
     *
     * @param path
     * @throws DaoException
     */
    void add(Path path) throws DaoException ;

    /**
     *
     * @return
     */
    List<Path> findAll();

    /**
     *
     * @param path_id
     * @return
     */
    Path findById(int path_id);

    /**
     *
     * @param url_id
     * @return
     */
    List<Path> findByUrlId(int url_id);
}
