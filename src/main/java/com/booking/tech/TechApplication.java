package com.booking.tech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;



@SpringBootApplication
public class TechApplication {
    public static void main(String[] args) {
        SpringApplication application = null;
        if (args.length > 0){
            application = new SpringApplicationBuilder()
                    .sources(TechApplication.class)
                    .web(WebApplicationType.NONE)
                    .build();
        }
        else {
            application = new SpringApplicationBuilder()
                    .sources(TechApplication.class)
                    .web(WebApplicationType.SERVLET)
                    .build();
        }
        application.run(args);



    }

}








