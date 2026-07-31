package com.library.management.service;

import com.library.management.dto.BookDto;
import com.library.management.entity.Book;

import java.util.List;

public interface BookService {

    List<Book> getAllBooks();

    List<Book> searchBooks(String query);

    Book getBookById(Long id);

    Book getBookByIsbn(String isbn);

    Book createBook(BookDto bookDto);

    Book updateBook(Long id, BookDto bookDto);

    void deleteBook(Long id);
}
