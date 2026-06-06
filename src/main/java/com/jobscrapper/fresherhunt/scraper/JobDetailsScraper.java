package com.jobscrapper.fresherhunt.scraper;

import com.jobscrapper.fresherhunt.model.Job;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.time.LocalDate;

public class JobDetailsScraper {

    public Job scrape(
            String url,
            LocalDate targetDate) {

        try {

            Document doc =
                    Jsoup.connect(url)
                            .userAgent("Mozilla")
                            .timeout(10000)
                            .get();

            String title =
                    doc.select("h1").first().text();

            String applyLink = "";

            Elements links =
                    doc.select("a");

            String dateTime =
                    doc.select("time.entry-date")
                            .attr("datetime");

            LocalDate postDate =
                    LocalDate.parse(
                            dateTime.substring(0,10)
                    );
            System.out.println(
                    "Checking = " + title
            );

            System.out.println(
                    "Post Date = " + postDate
            );
            System.out.println(
                    "Target Date = " + targetDate
            );

            System.out.println(
                    "Is Before = "
                            + postDate.isBefore(targetDate)
            );

            if(postDate.isBefore(targetDate)) {
                System.out.println(
                        "Returning NULL"
                );
                return null;
            }

            for(Element link : links) {

                String text =
                        link.text().toLowerCase();

                if(text.contains("apply now")) {

                    applyLink =
                            link.absUrl("href");

                    break;
                }
            }

            return new Job(
                    title,
                    applyLink,
                    url,
                    ""
            );

        } catch (Exception e) {

            System.out.println(
                    "Failed: " + url
            );

            return null;
        }
    }
}