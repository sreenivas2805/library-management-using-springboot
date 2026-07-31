package com.library.management.config;

import com.library.management.entity.Book;
import com.library.management.entity.IssuedBook;
import com.library.management.entity.Student;
import com.library.management.entity.User;
import com.library.management.repository.BookRepository;
import com.library.management.repository.IssuedBookRepository;
import com.library.management.repository.StudentRepository;
import com.library.management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;
    private final IssuedBookRepository issuedBookRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void run(String... args) {
        try {
            if (bookRepository.count() == 0) {
                log.info("Initializing sample data for Library Management System...");


            // Seed Users
            if (userRepository.count() == 0) {
                userRepository.save(User.builder()
                        .username("admin")
                        .password("admin123")
                        .email("admin@library.org")
                        .fullName("Head Librarian")
                        .role(User.UserRole.ADMIN)
                        .build());
            }

            // Seed Books
            Book b1 = Book.builder()
                    .title("Clean Code: A Handbook of Agile Software Craftsmanship")
                    .author("Robert C. Martin")
                    .isbn("978-0132350884")
                    .category("Computer Science")
                    .publisher("Prentice Hall")
                    .quantity(5)
                    .availableQuantity(4)
                    .build();

            Book b2 = Book.builder()
                    .title("Effective Java (3rd Edition)")
                    .author("Joshua Bloch")
                    .isbn("978-0134685991")
                    .category("Computer Science")
                    .publisher("Addison-Wesley")
                    .quantity(4)
                    .availableQuantity(3)
                    .build();

            Book b3 = Book.builder()
                    .title("Introduction to Algorithms (4th Edition)")
                    .author("Thomas H. Cormen")
                    .isbn("978-0262046305")
                    .category("Computer Science")
                    .publisher("MIT Press")
                    .quantity(3)
                    .availableQuantity(3)
                    .build();

            Book b4 = Book.builder()
                    .title("Design Patterns: Elements of Reusable Object-Oriented Software")
                    .author("Erich Gamma, Richard Helm")
                    .isbn("978-0201633610")
                    .category("Software Engineering")
                    .publisher("Addison-Wesley")
                    .quantity(2)
                    .availableQuantity(2)
                    .build();

            Book b5 = Book.builder()
                    .title("Spring Boot in Action")
                    .author("Craig Walls")
                    .isbn("978-1617292545")
                    .category("Web Development")
                    .publisher("Manning Publications")
                    .quantity(6)
                    .availableQuantity(6)
                    .build();

            List<Book> savedBooks = bookRepository.saveAll(List.of(b1, b2, b3, b4, b5));

            // Seed Students
            Student s1 = Student.builder()
                    .name("Alex Johnson")
                    .rollNumber("CS-2024-001")
                    .department("Computer Science")
                    .year("3rd Year")
                    .email("alex.johnson@university.edu")
                    .phone("+1 555-0192")
                    .build();

            Student s2 = Student.builder()
                    .name("Sophia Martinez")
                    .rollNumber("CS-2024-042")
                    .department("Computer Science")
                    .year("2nd Year")
                    .email("sophia.m@university.edu")
                    .phone("+1 555-0148")
                    .build();

            Student s3 = Student.builder()
                    .name("David Chen")
                    .rollNumber("EE-2024-015")
                    .department("Electrical Engineering")
                    .year("4th Year")
                    .email("david.chen@university.edu")
                    .phone("+1 555-0173")
                    .build();

            Student s4 = Student.builder()
                    .name("Emily Watson")
                    .rollNumber("ME-2024-008")
                    .department("Mechanical Engineering")
                    .year("1st Year")
                    .email("emily.watson@university.edu")
                    .phone("+1 555-0129")
                    .build();

            List<Student> savedStudents = studentRepository.saveAll(List.of(s1, s2, s3, s4));

            // Seed Issued Books
            IssuedBook ib1 = IssuedBook.builder()
                    .student(savedStudents.get(0))
                    .book(savedBooks.get(0))
                    .issueDate(LocalDate.now().minusDays(5))
                    .dueDate(LocalDate.now().plusDays(9))
                    .status(IssuedBook.IssueStatus.ISSUED)
                    .notes("Required for term project")
                    .build();

            IssuedBook ib2 = IssuedBook.builder()
                    .student(savedStudents.get(1))
                    .book(savedBooks.get(1))
                    .issueDate(LocalDate.now().minusDays(10))
                    .dueDate(LocalDate.now().plusDays(4))
                    .status(IssuedBook.IssueStatus.ISSUED)
                    .notes("Course reference book")
                    .build();

            issuedBookRepository.saveAll(List.of(ib1, ib2));

            log.info("Sample library data initialized successfully!");
        }
        } catch (Exception e) {
            log.error("Failed to seed initial data: {}", e.getMessage(), e);
        }
    }
}

