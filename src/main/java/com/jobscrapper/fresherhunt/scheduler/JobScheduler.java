package com.jobscrapper.fresherhunt.scheduler;

import com.jobscrapper.fresherhunt.model.Job;
import com.jobscrapper.fresherhunt.service.GoogleSheetService;
import com.jobscrapper.fresherhunt.service.JobService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JobScheduler {

    public void run() throws Exception {

        System.out.println(
                "Starting Daily Job Scraper..."
        );

        JobService service =
                new JobService();

        List<Job> jobs =
                service.scrapeJobs();

        GoogleSheetService sheetService =
                new GoogleSheetService();

        for(Job job : jobs) {

            sheetService.appendJob(
                    job.getTitle(),
                    job.getApplyLink(),
                    job.getPostUrl(),
                    job.getLastModified()
            );

            System.out.println(
                    "Saved -> "
                            + job.getTitle()
            );
        }

        System.out.println(
                "Finished. Total Jobs = "
                        + jobs.size()
        );
    }
}