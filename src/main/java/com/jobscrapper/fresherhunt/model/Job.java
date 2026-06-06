package com.jobscrapper.fresherhunt.model;

public class Job {

    private String title;
    private String applyLink;
    private String postUrl;
    private String lastModified;

    public Job(String title,
               String applyLink,
               String postUrl,
               String lastModified) {

        this.title = title;
        this.applyLink = applyLink;
        this.postUrl = postUrl;
        this.lastModified = lastModified;
    }

    public String getTitle() {
        return title;
    }

    public String getApplyLink() {
        return applyLink;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public String getLastModified() {
        return lastModified;
    }

    @Override
    public String toString() {
        return title + "," +
                applyLink + "," +
                postUrl + "," +
                lastModified;
    }
}