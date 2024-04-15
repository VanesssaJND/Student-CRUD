package com.example.demo.student;

import com.example.demo.student.exception.StudentNotFoundException;
import com.example.demo.student.exception.badRequestException;
import com.example.demo.student.service.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTestImpl {

    @Mock
    private StudentRepository studentRepository;
    @Mock
    private StudentMapper studentMapper;
    private StudentServiceImpl underTest;

    @BeforeEach
    void setUp(){
        underTest = new StudentServiceImpl(studentRepository);
    }

    @DisplayName("Test the Get all students method")
    @Test
    void canGetAllStudents() {
        //when
        underTest.getAllStudents();
        //then
        verify(studentRepository).findAll();
    }
    @DisplayName("Test if a new student can be add")
    @Test
    void canAddStudent() {
        //given
        StudentDTO studentDTO = new StudentDTO(
                "Vanessa",
                "vane@gmail.com",
                Gender.FEMALE,
                LocalDate.of(2000, Month.APRIL, 17));
        //when
        underTest.addStudent(studentDTO);

        //then
        ArgumentCaptor<Student> studentArgumentCaptor =
                ArgumentCaptor.forClass(Student.class);

        verify(studentRepository
        ).save(studentArgumentCaptor.capture());

        Student capturedStudent = studentArgumentCaptor.getValue();
        assertThat(studentDTO.getName()).isEqualTo(capturedStudent.getName());
        assertThat(studentDTO.getEmail()).isEqualTo(capturedStudent.getEmail());
        assertThat(studentDTO.getGender()).isEqualTo(capturedStudent.getGender());
        assertThat(studentDTO.getDob()).isEqualTo(capturedStudent.getDob());
    }
    @DisplayName("Test exception handling")
    @Test
    void willThrowWhenEmailIsTaken() {
        //given
        StudentDTO studentDTO = new StudentDTO(
                "Vanessa",
                "vane@gmail.com",
                Gender.FEMALE,
                LocalDate.of(2000, Month.APRIL, 17));

        given(studentRepository.selectExistsEmail(studentDTO.getEmail()))
                .willReturn(true);
        //when
        //then
        assertThatThrownBy(() ->underTest.addStudent(studentDTO))
                .isInstanceOf(badRequestException.class)
                .hasMessageContaining("Email " + studentDTO.getEmail()+ " taken");

        verify(studentRepository, never()).save(any());
    }
    @DisplayName("Test delete student method")
    @Test
    void canDeleteStudent() {
        //given
        Long studentId = 1L;
        given(studentRepository.existsById(studentId))
                .willReturn(true);
        //When
        underTest.deleteStudent(studentId);

        //Then
        verify(studentRepository).existsById(studentId);
        verify(studentRepository).deleteById(studentId);
    }

    @Test
    void willThrowWhenNotFoundStudent() {
        //given
        Long studentId = 1L;
        given(studentRepository.existsById(studentId))
                .willReturn(false);

        //when
        //then
        assertThatThrownBy(() -> underTest.deleteStudent(studentId))
                .isInstanceOf(StudentNotFoundException.class)
                .hasMessageContaining("Student with id " + studentId + " does not exist");
        verify(studentRepository, never()).deleteById(studentId);
    }

    @DisplayName("JUnit test for updating student operation")
    @Test
    void canUpdateStudent() {
        // Given
        Long studentId = 1L;
        String newName = "Elisa";
        String newEmail = "lulu@gmail.com";
        Student existingStudent = new Student(
                "Vanessa",
                "vane@gmail.com",
                Gender.FEMALE,
                LocalDate.of(2000, Month.APRIL, 17));

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.findStudentByEmail(newEmail)).thenReturn(Optional.empty());

        // When
        underTest.updateStudent(studentId, newName, newEmail);

        // Then
        verify(studentRepository).findById(studentId);
        verify(studentRepository).findStudentByEmail(newEmail);
        assertThat(existingStudent.getName()).isEqualTo(newName);
        assertThat(existingStudent.getEmail()).isEqualTo(newEmail);
    }
    @DisplayName("Throw exception if the email that is being try to update is taken")
    @Test
    void willThrowWhenEmailIsTakenWhileUpdating() {
        //given
        Long studentId = 1L;
        String newName = "Elisa";
        String newEmail = "lulu@gmail.com";

        Student existingStudent = new Student(
                "Vanessa",
                "vane@gmail.com",
                Gender.FEMALE,
                LocalDate.of(2000, Month.APRIL, 17));

        //when
        given(studentRepository.findById(studentId))
                .willReturn(Optional.of(existingStudent));
        given(studentRepository.findStudentByEmail(newEmail))
                .willReturn(Optional.of(new Student()));

        //then
        assertThatThrownBy(() ->underTest.updateStudent(studentId,newName,newEmail))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("email taken");
    }
}