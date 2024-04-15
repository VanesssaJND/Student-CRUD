package com.example.demo.student;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StudentMapper {

    StudentMapper INSTANCE = Mappers.getMapper(StudentMapper.class);
    StudentDTO StudentToDTO(Student student);
    Student StudentDTOtoDStudent(StudentDTO studentDTO);
}