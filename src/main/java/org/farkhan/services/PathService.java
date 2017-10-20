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

import org.farkhan.services.impl.PathServiceImpl;
import org.farkhan.model.Path;
import org.farkhan.services.ServiceException;
import org.sql2o.Connection;
import org.sql2o.Sql2o;
import org.sql2o.Sql2oException;
import java.util.List;

/**
 *
 * @author Farhan Khan
 */
public class PathService implements PathServiceImpl {
    private Sql2o sql2o;
    
    /**
     *
     * @param sql2o
     */
    public PathService(Sql2o sql2o) {
        this.sql2o = sql2o;
    }
    
    /**
     *
     * @param path
     * @throws ServiceException
     */
    @Override
    public void add(Path path) throws ServiceException {
        String sql = "INSERT INTO path (url_id, data, hop, title) VALUES (:urlId, :data, :hop, :title)";
        try (Connection con = sql2o.open()) {
            con.createQuery(sql)
                    .addParameter("urlId", path.getUrlId())
                    .addParameter("data", path.getData())
                    .addParameter("hop", path.getHop())
                    .addParameter("title", path.getTitle())
                    .executeUpdate();
        } catch (Sql2oException ex) {
            throw new ServiceException(ex, "Problem adding path");
        }
    }
    
    /**
     *
     * @return
     */
    @Override
    public List<Path> findAll() {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * FROM path")
                    .throwOnMappingFailure(false)
                    .executeAndFetch(Path.class);
        }
    }
    
    /**
     *
     * @param pathId
     * @return Path
     */
    @Override
    public Path findById(int pathId) {
        try (Connection con = sql2o.open()) {
            return con.createQuery("SELECT * from path WHERE path_id = :pathId")
                    .addParameter("pathId", pathId)
                    .executeAndFetchFirst(Path.class);
        }
    }

    /**
     *
     * @param urlId
     * @return Path
     */
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
