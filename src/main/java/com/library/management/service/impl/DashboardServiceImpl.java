package com.library.management.service.impl;

import com.library.management.dto.DashboardStatsDto;
import com.library.management.entity.IssuedBook;
import com.library.management.repository.BookRepository;
import com.library.management.repository.IssuedBookRepository;
import com.library.management.repository.StudentRepository;
import com.library.management.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    private final BookRepository bookRepository;
    private final StudentRepository studentRepository;
    private final IssuedBookRepository issuedBookRepository;

    @Override
    public DashboardStatsDto getDashboardStats() {
        long totalBooks = bookRepository.count();
        long totalStudents = studentRepository.count();
        long issuedCount = issuedBookRepository.countByStatus(IssuedBook.IssueStatus.ISSUED);

        Long sumAvailable = bookRepository.sumAvailableQuantity();
        long availableBooks = sumAvailable != null ? sumAvailable : 0;

        List<IssuedBook> activeIssues = issuedBookRepository.findByStatus(IssuedBook.IssueStatus.ISSUED);
        LocalDate today = LocalDate.now();
        long overdueCount = activeIssues.stream()
                .filter(ib -> ib.getDueDate() != null && ib.getDueDate().isBefore(today))
                .count();

        return DashboardStatsDto.builder()
                .totalBooks(totalBooks)
                .availableBooks(availableBooks)
                .issuedBooks(issuedCount)
                .totalStudents(totalStudents)
                .overdueBooks(overdueCount)
                .build();
    }
}
