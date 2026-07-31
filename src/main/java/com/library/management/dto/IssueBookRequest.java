package com.library.management.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IssueBookRequest {

    @NotNull(message = "Please select a student")
    private Long studentId;

    @NotNull(message = "Please select a book")
    private Long bookId;

    @NotNull(message = "Due date is required")
    private LocalDate dueDate;

    private String notes;
}
