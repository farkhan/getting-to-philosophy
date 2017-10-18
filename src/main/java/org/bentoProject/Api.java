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
    private static String baseUrl = "https://en.wikipedia.org";
    
    public static void main( String[] args) {
        Sql2o sql2o = new Sql2o("jdbc:sqlite:bento.db", null, null);
        UrlDao urlDao = new UrlDao(sql2o);
        Gson gson = new Gson();
    
    post("/url", "application/json", (req, res) -> {
        Url url = gson.fromJson(req.body(), Url.class);
        urlDao.add(url);
        res.status(201);
        return url;
    }, gson::toJson);

    get("/url", "application/json", (req, res) -> urlDao.findAll(), gson::toJson);

    get("/philosophy", "application/json", (req, res) -> {
        Integer count = 0;
        String url = baseUrl + "/wiki/New_York_City";

        Document doc = Jsoup.connect(url).header("Accept-Encoding", "gzip, deflate")
            .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
            .maxBodySize(0)
            .get();
        String firstHeading = doc.select("h1#firstHeading").text();
        while (!firstHeading.equals("Philosophy")) {
            Elements mwContentText = doc.select("#mw-content-text");
            Elements filteredContent = filteredContent(mwContentText);
            Elements firstParagraph = filteredContent.select(".mw-parser-output > p");
                Elements links = firstParagraph.select("a");
                Elements filteredElements = filteredLinks(links, url);
                Element firstElement = filteredElements.first();
                if (firstElement != null) {
                    doc = getNextLink(firstElement);
                    firstHeading = doc.select("h1#firstHeading").text();
                }
        }
        return url;
    }, gson::toJson);
    enableDebugScreen();
    }

    public static Document getNextLink (Element element) throws IOException  {
        String url = baseUrl + element.attr("href");
        System.out.println("url: " + url);
        Document doc = Jsoup.connect(url).header("Accept-Encoding", "gzip, deflate")
            .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
            .maxBodySize(0)
            .get();
        return doc;
    }

    public static Elements filteredLinks (Elements links, String currentLink) {
        for (Iterator<Element> it = links.iterator(); it.hasNext(); ) {
           Element element = it.next();

           String href = element.attr("href");
           String linkText = element.text().trim();
           if (startsOrEndsWithParenthesis(linkText)
                   || href == currentLink
                   || !isWikiLink(href)
                   || href.contains("Help:IPA")) {
               it.remove();
           }
        }
        return links;
    }

    public static Elements filteredContent (Elements elements) {
        for (Iterator<Element> it = elements.iterator(); it.hasNext(); ) {
            Element element = it.next();
            if (element.tagName().equalsIgnoreCase("table")) {
                System.out.println("tagName: " + element.tagName());
                element.remove();
            }
        }
        return elements;
    }

    public static Boolean startsOrEndsWithParenthesis(String s) {
        return s.startsWith("(") && s.endsWith(")");
    }

    public static Boolean isWikiLink(String link) {
        return link.contains("/wiki/");
    }
}
