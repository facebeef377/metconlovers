package com.metcon.metconlovers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.metcon.metconlovers.upload.FileStorageProperties;

@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})

public class MetconloversApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetconloversApplication.class, args);
	}
}

