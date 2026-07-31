package com.library.management.service;

import com.library.management.dto.IssueBookRequest;
import com.library.management.entity.IssuedBook;

import java.util.List;

public interface IssueReturnService {

    IssuedBook issueBook(IssueBookRequest request);

    IssuedBook returnBook(Long issueId);

    List<IssuedBook> getAllIssuedBooks();

    List<IssuedBook> getActiveIssuedBooks();

    List<IssuedBook> searchIssuedBooks(String query);

    IssuedBook getIssuedBookById(Long id);
}
