package com.example.annualleave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AnnualLeaveApplication {

    public static void main(String[] args) {
        SpringApplication.run(AnnualLeaveApplication.class, args);
    }

}
