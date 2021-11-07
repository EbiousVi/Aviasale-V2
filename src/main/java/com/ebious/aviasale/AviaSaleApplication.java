package com.ebious.aviasale;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;


@SpringBootApplication
public class AviaSaleApplication {
    public static void main(String[] args) {
        SpringApplication.run(AviaSaleApplication.class, args);
    }
}
