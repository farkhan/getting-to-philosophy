package org.bentoProject;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import org.sql2o.Sql2o;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Iterator;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.sql2o.converters.UUIDConverter;
import static spark.Spark.*;
import org.bentoProject.dao.UrlDao;
import org.bentoProject.model.Path;
import org.bentoProject.model.Url;
import org.bentoProject.dao.DaoException;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static spark.debug.DebugScreen.enableDebugScreen;

/**
 *
 * @author Farhan Khan
 */
public class Api {
    
    public static void main( String[] args) {
    
    }
}
