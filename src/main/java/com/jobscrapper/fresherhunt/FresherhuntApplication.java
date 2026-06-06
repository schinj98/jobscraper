package com.jobscrapper.fresherhunt;

import com.jobscrapper.fresherhunt.scheduler.JobScheduler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class FresherhuntApplication
		implements CommandLineRunner {

	@Autowired
	private JobScheduler scheduler;

	public static void main(String[] args) {
		SpringApplication.run(
				FresherhuntApplication.class,
				args
		);
	}

	@Override
	public void run(String... args)
			throws Exception {

		scheduler.run();
	}
}