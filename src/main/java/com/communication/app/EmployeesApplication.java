package com.communication.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication

public class EmployeesApplication {
	private static String UPLOAD_DIR = System.getProperty("user.home") + "/uploads";
	public static void main(String[] args) {
		File uploadDir = new File(UPLOAD_DIR);
		if(!uploadDir.exists()) {
			uploadDir.mkdirs();
		}
		SpringApplication.run(EmployeesApplication.class, args);
	}
}
