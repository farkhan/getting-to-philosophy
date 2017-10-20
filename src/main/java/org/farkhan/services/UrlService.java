/* 
 * Copyright (C) 2017 Farhan Khan
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.farkhan.services;

import org.farkhan.services.impl.UrlServiceImpl;
import org.farkhan.model.Url;
import org.farkhan.services.ServiceException;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;


import java.util.List;
/**
 *
 * @author Farhan Khan
 */
public class UrlService implements UrlServiceImpl {
    private Sql2o sql2o;
    
    /**
     *
     * @param sql2o
     */
    public UrlService(Sql2o sql2o) {
        this.sql2o = sql2o;
    }
    
    /**
     *
     * @param url
     * @return int
     * @throws ServiceException
     */
    @Override
    public int add(Url url) throws ServiceException {
        String sql = "INSERT INTO url (data, title) VALUES (:data, :title)";
        try (Connection con = sql2o.open()) {
            Long id = (Long) con.createQuery(sql, true)
                    .addParameter("data", url.getData())
                    .addParameter("title", url.getTitle())
                    .executeUpdate()
                    .getKey();
            return id.intValue();
        } catch (Sql2oException ex) {
            throw new ServiceException(ex, "Problem adding url");
        }
    }

    /**
     *
     * @return Url
     */
    @Override
    public List<Url> findAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM url")
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Url.class);
        }
    }

    /**
     *
     * @param urlId
     * @return Url
     */
    @Override
    public Url findById(int urlId) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * from url WHERE url_id = :urlId")
                    .addParameter("urlId", urlId)
                    .executeAndFetchFirst(Url.class);
        }
    }
}
