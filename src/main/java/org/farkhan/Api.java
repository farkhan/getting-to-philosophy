package org.farkhan;

import org.sql2o.Sql2o;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.sql2o.converters.UUIDConverter;
import static spark.Spark.*;
import org.farkhan.dao.UrlDao;
import org.farkhan.dao.PathDao;
import org.farkhan.model.Path;

import org.farkhan.model.Url;
import org.farkhan.dao.DaoException;
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
        UrlDao urlDao = new UrlDao(sql2o);
        PathDao pathDao = new PathDao(sql2o);
        Gson gson = new Gson();
        port(port);

    get("/", (req, res) -> renderContent("app/index.html"));

    get("/url", "application/json", (req, res) -> urlDao.findAll(), gson::toJson);

    post("/url", "application/json", (req, res) -> {
        Integer count = 0;
        String body = req.body();
        String wikiUrl = baseUrl + "/wiki/India";
        Document doc = Jsoup.connect(wikiUrl).header("Accept-Encoding", "gzip, deflate")
            .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:23.0) Gecko/20100101 Firefox/23.0")
            .maxBodySize(0)
            .get();
        String firstHeading = doc.select("h1#firstHeading").text();
        Url url = gson.fromJson(req.body(), Url.class);
        url.setTitle(firstHeading);
        int urlId = urlDao.add(url);
        while (!firstHeading.equals("Philosophy")) {
            Elements mwContentText = doc.select("#mw-content-text");
            Elements filteredContent = filteredContent(mwContentText);
            Elements firstParagraph = filteredContent.select(".mw-parser-output > p");
            Elements links = firstParagraph.select("a");
            Elements filteredElements = filteredLinks(links, wikiUrl);
            Element firstElement = filteredElements.first();
            if (firstElement != null) {
                String data = firstElement.attr("href");
                doc = getNextLink(firstElement);
                firstHeading = doc.select("h1#firstHeading").text();
                count++;
                Path path = new Path(urlId, data, count, firstHeading);
                pathDao.add(path);
            }
        }
        List<Path> urlPaths = pathDao.findByUrlId(urlId);
        return urlPaths;
    }, gson::toJson);
    enableDebugScreen();
    }

    private static String renderContent(String htmlFile) {
        try {
            URL url = Api.class.getResource(htmlFile);
            java.nio.file.Path path = Paths.get(url.toURI());
            return new String(Files.readAllBytes(path), Charset.defaultCharset());
        } catch (IOException | URISyntaxException e) {
        }
        return null;
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
     * not wiki links or contain HELP:IPA in the URLs
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
                   || href.contains("Help:IPA")) {
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
