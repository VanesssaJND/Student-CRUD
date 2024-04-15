package com.example.demo.student.service;

import com.example.demo.student.Student;
import com.example.demo.student.StudentDTO;
import com.example.demo.student.StudentRepository;
import com.example.demo.student.exception.StudentNotFoundException;
import com.example.demo.student.exception.badRequestException;
import com.example.demo.student.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public void addStudent(StudentDTO studentDTO) {
        Boolean existsEmail = studentRepository
                .selectExistsEmail(studentDTO.getEmail());
        if (existsEmail) {
            throw new badRequestException(
                    "Email " + studentDTO.getEmail() + " taken");
        }

        Student student = new Student();
        student.setName(studentDTO.getName());
        student.setEmail(studentDTO.getEmail());
        student.setGender(studentDTO.getGender());
        student.setDob(studentDTO.getDob());

        studentRepository.save(student);
    }

    public void deleteStudent(Long studentId) {
        if(!studentRepository.existsById(studentId)) {
            throw new StudentNotFoundException(
                    "Student with id " + studentId + " does not exists");
        }
        studentRepository.deleteById(studentId);
    }

    public void updateStudent(Long studentId, String name, String email) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(()->new IllegalStateException(
                        "student with id " + studentId + " does not exist"
                ));
        if(name!=null &&
                !name.isEmpty() &&
                !Objects.equals(student.getName(), name)){
            student.setName(name);
        }

        if(email !=null &&
                !email.isEmpty() &&
                !Objects.equals(student.getEmail(), email)){
            Optional<Student> studentOptional=studentRepository.findStudentByEmail(email);
            if(studentOptional.isPresent()){
                throw new IllegalStateException("email taken");
            }
            student.setEmail(email);
        }
    }
}
