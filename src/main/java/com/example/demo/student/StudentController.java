package com.example.demo.student;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService studentService;


    @GetMapping
    public List<Student> getStudent(){
        return studentService.getAllStudents();
    }
    @PostMapping
    public void registerNewStudent(@Valid @RequestBody Student student){
        studentService.addStudent(student);
    }
    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId){
        studentService.deleteStudent(studentId);
    }

    @PutMapping(path = "{studentId}")
    @Transactional
    public void updateStudent(
            @PathVariable("studentId") Long studentId,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email){
        studentService.updateStudent(studentId, name, email);
    }
}
