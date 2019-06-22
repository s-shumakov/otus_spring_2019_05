package ru.otus.spring01;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.otus.spring01.service.TestingService;

@SpringBootApplication
public class Hw01StudentTestingApplication implements CommandLineRunner {
	@Autowired
	private TestingService testingService;

	public static void main(String[] args) {
		SpringApplication.run(Hw01StudentTestingApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		testingService.runTest();
	}

}
