/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bentoProject.dao;

import org.bentoProject.model.Url;

import java.util.List;
/**
 *
 * @author Farhan Khan
 */
public interface UrlDaoImpl {
    void add(Url url) throws DaoException ;
    List<Url> findAll();
    Url findById(int id);
}
