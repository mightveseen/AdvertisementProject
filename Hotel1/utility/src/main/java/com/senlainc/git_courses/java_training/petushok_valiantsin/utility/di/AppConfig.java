package com.senlainc.git_courses.java_training.petushok_valiantsin.utility.di;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@ComponentScan(basePackages = {
        "com.senlainc.git_courses.java_training.petushok_valiantsin.repository",
        "com.senlainc.git_courses.java_training.petushok_valiantsin.service",
        "com.senlainc.git_courses.java_training.petushok_valiantsin.controller"})
public class AppConfig {

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
