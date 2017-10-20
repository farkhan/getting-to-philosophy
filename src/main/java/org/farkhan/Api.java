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
package org.farkhan;

import org.sql2o.Sql2o;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import static spark.Spark.*;
import org.farkhan.services.UrlService;
import org.farkhan.services.PathService;
import org.farkhan.model.Path;

import org.farkhan.model.Url;
import org.farkhan.ResponseError;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import static spark.debug.DebugScreen.enableDebugScreen;

/**
 *
 * @author Farhan Khan
 */
public class Api {
    private static String baseUrl = "https://en.wikipedia.org";
    //Port can be set with -Dserver.port=$PORT
    private static int port = System.getenv("PORT") == null ? 4567 : Integer.parseInt(System.getenv("PORT"));
    private static String JDBC_DATABASE_URL = System.getenv("JDBC_DATABASE_URL") == null
        ? "jdbc:mysql://localhost:3306/bento?user=root&password=password"
        : System.getenv("JDBC_DATABASE_URL");
    
    
    /**
     *
     * @param args
     */
    public static void main( String[] args) {
        
        Sql2o sql2o = new Sql2o(JDBC_DATABASE_URL, null, null);
        UrlService urlService = new UrlService(sql2o);
        PathService pathService = new PathService(sql2o);
        Gson gson = new Gson();
        port(port);
        staticFileLocation("/public");

    get("/url", "application/json", (req, res) -> urlService.findAll(), gson::toJson);

    post("/url", "application/json", (req, res) -> {
        Integer count = 0;
        Integer maxCount = 100;
        JsonElement element = gson.fromJson (req.body(), JsonElement.class);
        JsonObject jsonObj = element.getAsJsonObject();
        String wikiUrl = jsonObj.get("url").getAsString();
        if (!isWikiLink(wikiUrl)){
            res.status(400);
            return new ResponseError("Not a valid Wiki link!");
        };
        Document doc = Jsoup.connect(wikiUrl).header("Accept-Encoding", "gzip, deflate")
            .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
            .maxBodySize(0)
            .get();
        String firstHeading = doc.select("h1#firstHeading").text();
        Url url = gson.fromJson(req.body(), Url.class);
        url.setTitle(firstHeading);
        int urlId = urlService.add(url);
        while (!firstHeading.equals("Philosophy")) {
            if (count == maxCount) {
                res.status(408);
                return new ResponseError("Sorry, it looks like this URL will not get to Philosophy.");
            }
            Element firstElement = getFirstLinkElement(doc, wikiUrl);
            if (firstElement != null) {
                String data = firstElement.attr("href");
                doc = getNextLink(firstElement);
                firstHeading = doc.select("h1#firstHeading").text();
                count++;
                Path path = new Path(urlId, data, count, firstHeading);
                pathService.add(path);
            }
        }
        List<Path> urlPaths = pathService.findByUrlId(urlId);
        return urlPaths;
    }, gson::toJson);
    after((req, res) -> {
        res.type("application/json");
    });
    enableDebugScreen();
    }
    
    private static Element getFirstLinkElement(Document document, String wikiUrl) {
        Elements mwContentText = document.select("#mw-content-text");
        Elements filteredContent = filteredContent(mwContentText);
        Elements firstParagraph = filteredContent.select(".mw-parser-output > p");
        Elements links = firstParagraph.select("a");
        Elements filteredElements = filteredLinks(links, wikiUrl);
        return filteredElements.first();
    }
    /**
     * Fetches link and returns as Jsoup Document
     * @param element
     * @return
     * @throws IOException
     */
    public static Document getNextLink (Element element) throws IOException  {
        String url = baseUrl + element.attr("href");
        System.out.println("url: " + url);
        Document doc = Jsoup.connect(url).header("Accept-Encoding", "gzip, deflate")
            .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
            .maxBodySize(0)
            .get();
        return doc;
    }

    /**
     * Removes links if they are currentLink,
     * not wiki links, contain HELP:IPA or audio/video in the URLs
     * @param links
     * @param currentLink
     * @return
     */
    public static Elements filteredLinks (Elements links, String currentLink) {
        for (Iterator<Element> it = links.iterator(); it.hasNext(); ) {
           Element element = it.next();

           String href = element.attr("href");
           String linkText = element.text().trim();
           if (startsOrEndsWithParenthesis(linkText)
                   || href == currentLink
                   || !isWikiLink(href)
                   || href.contains("Help:IPA")
                   || href.endsWith(".ogg")) {
               it.remove();
           }
        }
        return links;
    }

    /**
     * Removes table elements from content
     * @param elements
     * @return
     */
    public static Elements filteredContent (Elements elements) {
        for (Iterator<Element> it = elements.iterator(); it.hasNext(); ) {
            Element element = it.next();
            if (element.tagName().equalsIgnoreCase("table")) {
                element.remove();
            }
        }
        return elements;
    }

    /**
     *
     * @param s
     * @return
     */
    public static Boolean startsOrEndsWithParenthesis(String s) {
        return s.startsWith("(") && s.endsWith(")");
    }

    /**
     *
     * @param link
     * @return
     */
    public static Boolean isWikiLink(String link) {
        return link.contains("/wiki/");
    }

}
