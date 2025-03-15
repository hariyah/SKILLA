package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @GetMapping("/")
    public String rootEndpoint(){
        String message = "Hello everybody";
        return message;
    }

    @GetMapping("/hello")
    public String helloEndpoint(){
        String message = "hi everybody i'm in hello page";
        return message;
    }

    /** The endpoint should be accessible via /greet*/
    @GetMapping("/greet")
    public String greetEndpoint(){
        String message = "Welcome to Spring Boot!";
        return message;
    }

    /** The endpoint should be accessible via /{name}.*/
    @GetMapping("/{name}")
    public String greetUser(@PathVariable String name) {
        return "Hello " + name + "! Welcome to Spring Boot!!!!!!!!!!!!!!!";
    }

/**(Hello John! Welcome to Spring Boot!) */
    @GetMapping("/greet/{name}")
    public String greetUser(@PathVariable String name,
            @RequestParam(required = false) String message) {
        if (message != null && !message.isEmpty()) {
            return "Hello " + name + "! " + message;
        }
        return "Hello " + name + "! Welcome to Spring Boot!";
    }

}
