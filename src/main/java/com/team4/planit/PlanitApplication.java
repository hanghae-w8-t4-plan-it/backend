package com.team4.planit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing
@SpringBootApplication
@EnableScheduling
//@EnableBatchProcessing
public class PlanitApplication {

   public static void main(String[] args) {
      SpringApplication.run(PlanitApplication.class, args);
   }

}
