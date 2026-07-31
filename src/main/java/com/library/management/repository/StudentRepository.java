package com.library.management.repository;

import com.library.management.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    Optional<Student> findByRollNumber(String rollNumber);

    boolean existsByRollNumber(String rollNumber);

    boolean existsByEmail(String email);

    List<Student> findByNameContainingIgnoreCaseOrRollNumberContainingIgnoreCaseOrDepartmentContainingIgnoreCase(
            String name, String rollNumber, String department
    );
}
