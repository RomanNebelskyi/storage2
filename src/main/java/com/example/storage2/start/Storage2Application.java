package com.example.storage2.start;

import com.example.storage2.controller.OrderController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackages = {
    "com.example.storage2.*"}, basePackageClasses = OrderController.class)
@EnableScheduling
public class Storage2Application {

  public static void main(String[] args) {
    SpringApplication.run(Storage2Application.class, args);
  }

}
