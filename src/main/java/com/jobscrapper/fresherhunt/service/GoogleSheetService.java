package com.jobscrapper.fresherhunt.service;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

import java.io.FileInputStream;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GoogleSheetService {

    private static final String APPLICATION_NAME =
            "FreshersHunt Scraper";

    private static final String SPREADSHEET_ID =
            "1W2YQS2MezMaKpZuq0ti4plq5DUs5gy7-HiuRZZaYoUs";

    private static final String RANGE =
            "Sheet1!A:E";

    public Sheets getSheetsService()
            throws Exception {

        GoogleCredentials credentials =
                GoogleCredentials
                        .fromStream(
                                new FileInputStream(
                                        "credentials.json"
                                )
                        )
                        .createScoped(
                                Collections.singleton(
                                        SheetsScopes.SPREADSHEETS
                                )
                        );

        return new Sheets.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(),
                new HttpCredentialsAdapter(credentials)
        )
                .setApplicationName(
                        APPLICATION_NAME
                )
                .build();
    }

    public void appendJob(
            String title,
            String applyLink,
            String postUrl,
            String lastModified
    ) throws Exception {

        Sheets service =
                getSheetsService();

        ValueRange body =
                new ValueRange()
                        .setValues(
                                List.of(
                                        List.of(
                                                title,
                                                applyLink,
                                                postUrl,
                                                lastModified,
                                                java.time.LocalDateTime.now().toString()
                                        )
                                )
                        );

        service.spreadsheets()
                .values()
                .append(
                        SPREADSHEET_ID,
                        RANGE,
                        body
                )
                .setValueInputOption(
                        "RAW"
                )
                .execute();
    }
    public Set<String> getExistingUrls() throws Exception {

        Sheets service = getSheetsService();

        ValueRange response =
                service.spreadsheets()
                        .values()
                        .get(
                                SPREADSHEET_ID,
                                "Sheet1!C:C"
                        )
                        .execute();

        Set<String> urls =
                new HashSet<>();

        List<List<Object>> values =
                response.getValues();

        if(values == null) {
            return urls;
        }

        for(List<Object> row : values) {

            if(row.isEmpty()) {
                continue;
            }

            urls.add(
                    row.get(0).toString()
            );
        }

        return urls;
    }
}