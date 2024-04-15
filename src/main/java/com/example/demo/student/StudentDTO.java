package com.example.demo.student;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class StudentDTO {
    private String name;
    private String email;
    private Gender gender;
    private LocalDate dob;

}
