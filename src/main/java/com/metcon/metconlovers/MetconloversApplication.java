package com.metcon.metconlovers;

import com.metcon.metconlovers.upload.FileStorageProperties;
import com.tinify.Tinify;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableConfigurationProperties({
    FileStorageProperties.class
})

public class MetconloversApplication {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


	public static void main(String[] args) {
		SpringApplication.run(MetconloversApplication.class, args);
        Tinify.setKey("427XmkZuv0yXDxviDWYZM9Fz9hQRVrKh");
	}
}

