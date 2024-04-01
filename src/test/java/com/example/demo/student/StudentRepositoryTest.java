package com.example.demo.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
@DataJpaTest
class StudentRepositoryTest {

    @Autowired
    private StudentRepository underTest;

    @AfterEach
    void tearDown(){
        underTest.deleteAll();
    }

    @Test
    void ItShouldCheckIfStudentEmailExists() {
        //given
        String mail = "vane@gmail.com";
        Student student = new Student(
                "Vanessa",
                "vane@gmail.com",
                Gender.FEMALE,
                LocalDate.of(2000, Month.APRIL, 17)
        );

        underTest.save(student);
        //when
        boolean expected = underTest.selectExistsEmail(mail);
        //then
        assertThat(expected).isTrue();
    }
    @Test
    void ItShouldCheckIfStudentEmailDoesNotExists() {
        //given
        String mail = "vane@gmail.com";
        //when
        boolean expected = underTest.selectExistsEmail(mail);
        //then
        assertThat(expected).isFalse();
    }

    @Test
    void ItShouldFindStundentByEmail(){
        //given
        String mail = "vane@gmail.com";

        //when

        //then

    }
}