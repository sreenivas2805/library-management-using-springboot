package com.library.management.service;

import com.library.management.dto.StudentDto;
import com.library.management.entity.Student;

import java.util.List;

public interface StudentService {

    List<Student> getAllStudents();

    List<Student> searchStudents(String query);

    Student getStudentById(Long id);

    Student getStudentByRollNumber(String rollNumber);

    Student createStudent(StudentDto studentDto);

    Student updateStudent(Long id, StudentDto studentDto);

    void deleteStudent(Long id);
}
