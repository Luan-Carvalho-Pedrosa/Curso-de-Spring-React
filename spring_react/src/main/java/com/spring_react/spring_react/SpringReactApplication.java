package com.spring_react.spring_react;

import java.io.IOException;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
@EnableWebMvc
public class SpringReactApplication implements WebMvcConfigurer {


	
		
		@Override
		public void addCorsMappings(CorsRegistry registry) {
			// TODO Auto-generated method stub
			registry.addMapping("/**").
			allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS");
		}
		
	
		public static void main(String[] args) throws IOException {
			SpringApplication.run(SpringReactApplication.class, args);
			System.setProperty("java.awt.headless", "false");
			ThisApplication.openWindow();
		
			
		}


	

		
				
	
	
	
    
	

	
	

}
