package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

@Configuration
public class StudentConfig {
    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            // Define student entity instances
            Student mary = new Student(
                    "Mary",
                    "Mary@gmail.com",
                    LocalDate.of(2000, Month.JANUARY, 20)
            );

            Student alex = new Student(
                    "Alex",
                    "Alex@gmail.com",
                    LocalDate.of(2004, Month.JANUARY, 20)
            );

            repository.saveAll(
                    // Create list of students and save to our database
                    List.of(mary, alex)
            );
        };
    }
}
