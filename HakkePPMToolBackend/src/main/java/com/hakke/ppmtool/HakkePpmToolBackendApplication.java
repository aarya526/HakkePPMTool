package com.hakke.ppmtool;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@SpringBootApplication
public class HakkePpmToolBackendApplication {

	@GetMapping("/hello")
	@ResponseBody
	public String helloWorld() {

		return "Hello World!";
	}

	public static void main(String[] args) {
		SpringApplication.run(HakkePpmToolBackendApplication.class, args);
	}

}
