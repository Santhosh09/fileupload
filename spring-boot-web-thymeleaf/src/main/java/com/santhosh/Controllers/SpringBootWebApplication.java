package com.santhosh.Controllers;

import javax.annotation.Resource;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.santhosh.Service.SaveFileService;

@SpringBootApplication(scanBasePackages={"com.santhosh.Controllers", "com.santhosh.Service"})
public class SpringBootWebApplication implements CommandLineRunner{

	@Resource
	SaveFileService saveFileService;
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(SpringBootWebApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		saveFileService.deleteAll();
		saveFileService.init();
	}
	
}