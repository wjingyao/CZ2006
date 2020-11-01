package com.CMI;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CMIApplication {

public static void main(String[] args) {
SpringApplication.run(CMIApplication.class, args);
}

}