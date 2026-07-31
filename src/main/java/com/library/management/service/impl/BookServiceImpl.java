package com.library.management.service.impl;

import com.library.management.dto.BookDto;
import com.library.management.entity.Book;
import com.library.management.exception.ResourceNotFoundException;
import com.library.management.repository.BookRepository;
import com.library.management.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> searchBooks(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllBooks();
        }
        String cleanQuery = query.trim();
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrCategoryContainingIgnoreCaseOrIsbnContainingIgnoreCase(
                cleanQuery, cleanQuery, cleanQuery, cleanQuery
        );
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));
    }

    @Override
    @Transactional(readOnly = true)
    public Book getBookByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with ISBN: " + isbn));
    }

    @Override
    public Book createBook(BookDto bookDto) {
        if (bookRepository.existsByIsbn(bookDto.getIsbn())) {
            throw new IllegalArgumentException("Book with ISBN " + bookDto.getIsbn() + " already exists.");
        }

        Book book = Book.builder()
                .title(bookDto.getTitle().trim())
                .author(bookDto.getAuthor().trim())
                .isbn(bookDto.getIsbn().trim())
                .category(bookDto.getCategory().trim())
                .publisher(bookDto.getPublisher() != null ? bookDto.getPublisher().trim() : null)
                .quantity(bookDto.getQuantity())
                .availableQuantity(bookDto.getQuantity())
                .build();

        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Long id, BookDto bookDto) {
        Book existingBook = getBookById(id);

        if (!existingBook.getIsbn().equalsIgnoreCase(bookDto.getIsbn()) &&
            bookRepository.existsByIsbn(bookDto.getIsbn())) {
            throw new IllegalArgumentException("Book with ISBN " + bookDto.getIsbn() + " already exists.");
        }

        int issuedCount = existingBook.getQuantity() - existingBook.getAvailableQuantity();
        if (bookDto.getQuantity() < issuedCount) {
            throw new IllegalArgumentException("New total quantity cannot be less than currently issued books (" + issuedCount + ").");
        }

        existingBook.setTitle(bookDto.getTitle().trim());
        existingBook.setAuthor(bookDto.getAuthor().trim());
        existingBook.setIsbn(bookDto.getIsbn().trim());
        existingBook.setCategory(bookDto.getCategory().trim());
        existingBook.setPublisher(bookDto.getPublisher() != null ? bookDto.getPublisher().trim() : null);

        int quantityDiff = bookDto.getQuantity() - existingBook.getQuantity();
        existingBook.setQuantity(bookDto.getQuantity());
        existingBook.setAvailableQuantity(existingBook.getAvailableQuantity() + quantityDiff);

        return bookRepository.save(existingBook);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = getBookById(id);
        bookRepository.delete(book);
    }
}
