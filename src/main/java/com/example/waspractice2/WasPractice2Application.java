package com.example.waspractice2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

// Get /calculate?operand1=&operator=*&operand2=55
//@SpringBootApplication
public class WasPractice2Application {

    public static void main(String[] args) throws IOException {
//        SpringApplication.run(WasPractice2Application.class, args);

        new CustomWebApplicationServer(8081).start();
    }

}
