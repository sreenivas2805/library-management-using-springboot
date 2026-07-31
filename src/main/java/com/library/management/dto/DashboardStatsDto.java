package com.library.management.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardStatsDto {

    private long totalBooks;
    private long availableBooks;
    private long issuedBooks;
    private long totalStudents;
    private long overdueBooks;
}
