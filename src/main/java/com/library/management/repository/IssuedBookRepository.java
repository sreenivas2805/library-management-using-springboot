package com.library.management.repository;

import com.library.management.entity.IssuedBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IssuedBookRepository extends JpaRepository<IssuedBook, Long> {

    List<IssuedBook> findByStatus(IssuedBook.IssueStatus status);

    List<IssuedBook> findByStudentId(Long studentId);

    List<IssuedBook> findByBookId(Long bookId);

    long countByStatus(IssuedBook.IssueStatus status);

    @Query("SELECT ib FROM IssuedBook ib JOIN FETCH ib.student JOIN FETCH ib.book ORDER BY ib.issueDate DESC")
    List<IssuedBook> findAllWithStudentAndBook();

    @Query("SELECT ib FROM IssuedBook ib JOIN FETCH ib.student s JOIN FETCH ib.book b " +
           "WHERE LOWER(s.name) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(s.rollNumber) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(b.title) LIKE LOWER(CONCAT('%', :query, '%')) " +
           "OR LOWER(b.isbn) LIKE LOWER(CONCAT('%', :query, '%')) ORDER BY ib.issueDate DESC")
    List<IssuedBook> searchIssuedBooks(@Param("query") String query);
}
