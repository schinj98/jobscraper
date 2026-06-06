package com.jobscrapper.fresherhunt.scraper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class SitemapScraper {

    public List<String> getUrls() throws Exception {

        Set<String> uniqueUrls =
                new LinkedHashSet<>();

        for(int page = 1; page <= 20; page++) {

            String pageUrl;

            if(page == 1) {

                pageUrl =
                        "https://freshershunt.in/jobs-by-batch-year/2026-batch/";

            } else {

                pageUrl =
                        "https://freshershunt.in/jobs-by-batch-year/2026-batch/page/"
                                + page + "/";
            }

            System.out.println(
                    "Reading Page = " + page
            );

            Document doc =
                    Jsoup.connect(pageUrl)
                            .userAgent("Mozilla/5.0")
                            .get();

            Elements links =
                    doc.select("article a");

            if(links.isEmpty()) {

                System.out.println(
                        "No more pages."
                );

                break;
            }

            for(Element link : links) {

                String url =
                        link.absUrl("href");

                if(url.contains("/author/")) {
                    continue;
                }

                if(url.contains("#more")) {
                    continue;
                }

                if(url.contains("2026")) {
                    uniqueUrls.add(url);
                }
            }
        }

        return new ArrayList<>(uniqueUrls);
    }
}