package com.library.management.service.impl;

import com.library.management.dto.StudentDto;
import com.library.management.entity.Student;
import com.library.management.exception.ResourceNotFoundException;
import com.library.management.repository.StudentRepository;
import com.library.management.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> searchStudents(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllStudents();
        }
        String cleanQuery = query.trim();
        return studentRepository.findByNameContainingIgnoreCaseOrRollNumberContainingIgnoreCaseOrDepartmentContainingIgnoreCase(
                cleanQuery, cleanQuery, cleanQuery
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Student getStudentByRollNumber(String rollNumber) {
        return studentRepository.findByRollNumber(rollNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with Roll Number: " + rollNumber));
    }

    @Override
    public Student createStudent(StudentDto studentDto) {
        if (studentRepository.existsByRollNumber(studentDto.getRollNumber())) {
            throw new IllegalArgumentException("Student with Roll Number " + studentDto.getRollNumber() + " already exists.");
        }
        if (studentRepository.existsByEmail(studentDto.getEmail())) {
            throw new IllegalArgumentException("Student with Email " + studentDto.getEmail() + " already exists.");
        }

        Student student = Student.builder()
                .name(studentDto.getName().trim())
                .rollNumber(studentDto.getRollNumber().trim())
                .department(studentDto.getDepartment().trim())
                .year(studentDto.getYear().trim())
                .email(studentDto.getEmail().trim())
                .phone(studentDto.getPhone().trim())
                .build();

        return studentRepository.save(student);
    }

    @Override
    public Student updateStudent(Long id, StudentDto studentDto) {
        Student existingStudent = getStudentById(id);

        if (!existingStudent.getRollNumber().equalsIgnoreCase(studentDto.getRollNumber()) &&
            studentRepository.existsByRollNumber(studentDto.getRollNumber())) {
            throw new IllegalArgumentException("Student with Roll Number " + studentDto.getRollNumber() + " already exists.");
        }

        if (!existingStudent.getEmail().equalsIgnoreCase(studentDto.getEmail()) &&
            studentRepository.existsByEmail(studentDto.getEmail())) {
            throw new IllegalArgumentException("Student with Email " + studentDto.getEmail() + " already exists.");
        }

        existingStudent.setName(studentDto.getName().trim());
        existingStudent.setRollNumber(studentDto.getRollNumber().trim());
        existingStudent.setDepartment(studentDto.getDepartment().trim());
        existingStudent.setYear(studentDto.getYear().trim());
        existingStudent.setEmail(studentDto.getEmail().trim());
        existingStudent.setPhone(studentDto.getPhone().trim());

        return studentRepository.save(existingStudent);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = getStudentById(id);
        studentRepository.delete(student);
    }
}
