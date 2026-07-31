package com.library.management.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookDto {

    private Long id;

    @NotBlank(message = "Book title is required")
    @Size(min = 2, max = 255, message = "Title must be between 2 and 255 characters")
    private String title;

    @NotBlank(message = "Author name is required")
    @Size(min = 2, max = 255, message = "Author must be between 2 and 255 characters")
    private String author;

    @NotBlank(message = "ISBN is required")
    @Size(min = 5, max = 20, message = "ISBN must be between 5 and 20 characters")
    private String isbn;

    @NotBlank(message = "Category is required")
    private String category;

    private String publisher;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    private Integer availableQuantity;

    private String status;
}
