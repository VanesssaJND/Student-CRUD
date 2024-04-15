package com.example.demo.student;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;
import static org.assertj.core.api.Assertions.assertThat;

class StudentMapperTest {

    private StudentMapper studentMapper;

    @Test
    void ShouldTestStudentToDTO() {
        Student student =new Student("Vanessa",
                "vane@gmail.com",
                Gender.FEMALE,
                LocalDate.of(2000, Month.APRIL, 17));

        StudentDTO studentDTO = studentMapper.INSTANCE.StudentToDTO(student);

        assertThat(studentDTO).isNotNull();
        assertThat(studentDTO.getName()).isEqualTo(student.getName());
        assertThat(studentDTO.getEmail()).isEqualTo(student.getEmail());
        assertThat(studentDTO.getGender()).isEqualTo(student.getGender());
        assertThat(studentDTO.getDob()).isEqualTo(student.getDob());
    }

    @Test
    void ShouldTestStudentDTOtoDStudent() {
        StudentDTO studentDTO =new StudentDTO("Vanessa",
                "vane@gmail.com",
                Gender.FEMALE,
                LocalDate.of(2000, Month.APRIL, 17));

        Student student = studentMapper.INSTANCE.StudentDTOtoDStudent(studentDTO);

        assertThat(studentDTO).isNotNull();
        assertThat(studentDTO.getName()).isEqualTo(student.getName());
        assertThat(studentDTO.getEmail()).isEqualTo(student.getEmail());
        assertThat(studentDTO.getGender()).isEqualTo(student.getGender());
        assertThat(studentDTO.getDob()).isEqualTo(student.getDob());
    }
}