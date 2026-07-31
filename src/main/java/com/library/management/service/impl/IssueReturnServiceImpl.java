package com.library.management.service.impl;

import com.library.management.dto.IssueBookRequest;
import com.library.management.entity.Book;
import com.library.management.entity.IssuedBook;
import com.library.management.entity.Student;
import com.library.management.exception.BookNotAvailableException;
import com.library.management.exception.ResourceNotFoundException;
import com.library.management.repository.BookRepository;
import com.library.management.repository.IssuedBookRepository;
import com.library.management.repository.StudentRepository;
import com.library.management.service.IssueReturnService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class IssueReturnServiceImpl implements IssueReturnService {

    private final IssuedBookRepository issuedBookRepository;
    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;

    @Override
    public IssuedBook issueBook(IssueBookRequest request) {
        Student student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with ID: " + request.getStudentId()));

        Book book = bookRepository.findById(request.getBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + request.getBookId()));

        if (book.getAvailableQuantity() <= 0) {
            throw new BookNotAvailableException("Book '" + book.getTitle() + "' is currently out of stock.");
        }

        // Decrease available quantity
        book.setAvailableQuantity(book.getAvailableQuantity() - 1);
        book.updateStatus();
        bookRepository.save(book);

        LocalDate issueDate = LocalDate.now();
        LocalDate dueDate = request.getDueDate() != null ? request.getDueDate() : issueDate.plusDays(14);

        IssuedBook issuedBook = IssuedBook.builder()
                .student(student)
                .book(book)
                .issueDate(issueDate)
                .dueDate(dueDate)
                .status(IssuedBook.IssueStatus.ISSUED)
                .notes(request.getNotes())
                .build();

        return issuedBookRepository.save(issuedBook);
    }

    @Override
    public IssuedBook returnBook(Long issueId) {
        IssuedBook issuedBook = getIssuedBookById(issueId);

        if (issuedBook.getStatus() == IssuedBook.IssueStatus.RETURNED) {
            throw new IllegalStateException("Book issue transaction # " + issueId + " has already been returned.");
        }

        Book book = issuedBook.getBook();
        book.setAvailableQuantity(book.getAvailableQuantity() + 1);
        book.updateStatus();
        bookRepository.save(book);

        issuedBook.setReturnDate(LocalDate.now());
        issuedBook.setStatus(IssuedBook.IssueStatus.RETURNED);

        return issuedBookRepository.save(issuedBook);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IssuedBook> getAllIssuedBooks() {
        List<IssuedBook> list = issuedBookRepository.findAllWithStudentAndBook();
        updateOverdueStatuses(list);
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<IssuedBook> getActiveIssuedBooks() {
        List<IssuedBook> list = issuedBookRepository.findByStatus(IssuedBook.IssueStatus.ISSUED);
        updateOverdueStatuses(list);
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public List<IssuedBook> searchIssuedBooks(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllIssuedBooks();
        }
        List<IssuedBook> list = issuedBookRepository.searchIssuedBooks(query.trim());
        updateOverdueStatuses(list);
        return list;
    }

    @Override
    @Transactional(readOnly = true)
    public IssuedBook getIssuedBookById(Long id) {
        return issuedBookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Issued book record not found with ID: " + id));
    }

    private void updateOverdueStatuses(List<IssuedBook> list) {
        LocalDate today = LocalDate.now();
        for (IssuedBook ib : list) {
            if (ib.getStatus() == IssuedBook.IssueStatus.ISSUED && ib.getDueDate().isBefore(today)) {
                ib.setStatus(IssuedBook.IssueStatus.OVERDUE);
            }
        }
    }
}
