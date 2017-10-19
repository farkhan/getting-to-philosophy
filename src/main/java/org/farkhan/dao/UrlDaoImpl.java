/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.farkhan.dao;

import org.farkhan.model.Url;

import java.util.List;
/**
 *
 * @author Farhan Khan
 */
public interface UrlDaoImpl {

    /**
     *
     * @param url
     * @return
     * @throws DaoException
     */
    int add(Url url) throws DaoException ;

    /**
     *
     * @return
     */
    List<Url> findAll();

    /**
     *
     * @param id
     * @return
     */
    Url findById(int id);
}
