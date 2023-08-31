package com.brsinventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class BrsInventoryServiceApplication {

    public static void main(String[] args) {
//        SpringApplication.run(BrsInventoryServiceApplication.class, args);

        new SpringApplicationBuilder()
                .profiles("prod")
                .sources(BrsInventoryServiceApplication.class)
                .run(args);
    }

}
