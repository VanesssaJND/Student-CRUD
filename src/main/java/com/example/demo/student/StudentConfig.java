package com.example.demo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;
import java.util.List;


import static java.util.Calendar.APRIL;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner (StudentRepository repository){
        return args -> {
            Student marian = new Student(
                    "Marian", "marian.jamal@gmail.com",
                    LocalDate.of(2000, APRIL, 5)
            );

            Student alex = new Student(
                    "Alex", "alex.tut@gmail.com",
                    LocalDate.of(1986, APRIL, 5)
            );

            repository.saveAll(List.of(marian,alex));
        };
    }
}
