package com.library.management.controller;

import com.library.management.dto.BookDto;
import com.library.management.dto.IssueBookRequest;
import com.library.management.dto.StudentDto;
import com.library.management.entity.Book;
import com.library.management.entity.IssuedBook;
import com.library.management.entity.Student;
import com.library.management.service.BookService;
import com.library.management.service.IssueReturnService;
import com.library.management.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiRestController {

    private final BookService bookService;
    private final StudentService studentService;
    private final IssueReturnService issueReturnService;

    // Books REST API
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks(@RequestParam(required = false) String query) {
        return ResponseEntity.ok(bookService.searchBooks(query));
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@Valid @RequestBody BookDto bookDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.createBook(bookDto));
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @Valid @RequestBody BookDto bookDto) {
        return ResponseEntity.ok(bookService.updateBook(id, bookDto));
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }

    // Students REST API
    @GetMapping("/students")
    public ResponseEntity<List<Student>> getAllStudents(@RequestParam(required = false) String query) {
        return ResponseEntity.ok(studentService.searchStudents(query));
    }

    @GetMapping("/students/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok(studentService.getStudentById(id));
    }

    @PostMapping("/students")
    public ResponseEntity<Student> createStudent(@Valid @RequestBody StudentDto studentDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(studentService.createStudent(studentDto));
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @Valid @RequestBody StudentDto studentDto) {
        return ResponseEntity.ok(studentService.updateStudent(id, studentDto));
    }

    @DeleteMapping("/students/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }

    // Issue & Return REST API
    @PostMapping("/issue")
    public ResponseEntity<IssuedBook> issueBook(@Valid @RequestBody IssueBookRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(issueReturnService.issueBook(request));
    }

    @PostMapping("/return/{id}")
    public ResponseEntity<IssuedBook> returnBook(@PathVariable Long id) {
        return ResponseEntity.ok(issueReturnService.returnBook(id));
    }

    @GetMapping("/issued")
    public ResponseEntity<List<IssuedBook>> getIssuedBooks(@RequestParam(required = false) String query) {
        return ResponseEntity.ok(issueReturnService.searchIssuedBooks(query));
    }
}
