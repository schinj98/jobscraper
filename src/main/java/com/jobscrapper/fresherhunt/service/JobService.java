package com.jobscrapper.fresherhunt.service;

import com.jobscrapper.fresherhunt.model.Job;
import com.jobscrapper.fresherhunt.scraper.JobDetailsScraper;
import com.jobscrapper.fresherhunt.scraper.SitemapScraper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class JobService {

    public List<Job> scrapeJobs()
            throws Exception {

        SitemapScraper sitemapScraper =
                new SitemapScraper();

        JobDetailsScraper jobScraper =
                new JobDetailsScraper();

        GoogleSheetService sheetService =
                new GoogleSheetService();

        Set<String> existingUrls =
                sheetService.getExistingUrls();

        System.out.println(
                "Already Stored = "
                        + existingUrls.size()
        );

        List<String> urls =
                sitemapScraper.getUrls();

        List<Job> jobs =
                new ArrayList<>();

        LocalDate targetDate =
                LocalDate.now(ZoneId.of("Asia/Kolkata"));

        for(String url : urls) {
            if(existingUrls.contains(url)) {

                System.out.println(
                        "Skipping = " + url
                );

                continue;
            }
            Job job =
                    jobScraper.scrape(
                            url,
                            targetDate
                    );

            if(job == null) {

                System.out.println(
                        "Older date reached. Breaking."
                );

                break;
            }

            jobs.add(job);
        }

        return jobs;
    }
}