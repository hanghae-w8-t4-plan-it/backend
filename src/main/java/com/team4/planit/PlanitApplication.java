package com.team4.planit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
//@EnableBatchProcessing
public class PlanitApplication {

   public static void main(String[] args) {
      SpringApplication.run(PlanitApplication.class, args);
   }

}
