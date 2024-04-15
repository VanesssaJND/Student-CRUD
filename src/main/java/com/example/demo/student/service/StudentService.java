package com.example.demo.student.service;

import com.example.demo.student.Student;
import com.example.demo.student.StudentDTO;

import java.util.List;


public interface StudentService {
    public List<Student> getAllStudents();
    public void addStudent(StudentDTO studentDTO);
    public void deleteStudent(Long studentId);
    public void updateStudent(Long studentId, String name, String email);
}
