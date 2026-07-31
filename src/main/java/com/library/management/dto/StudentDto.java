package com.library.management.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDto {

    private Long id;

    @NotBlank(message = "Student name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Roll number is required")
    @Size(min = 3, max = 50, message = "Roll number must be between 3 and 50 characters")
    private String rollNumber;

    @NotBlank(message = "Department is required")
    private String department;

    @NotBlank(message = "Year/Semester is required")
    private String year;

    @NotBlank(message = "Email is required")
    @Email(message = "Please enter a valid email address")
    private String email;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9+--\\s]{7,15}$", message = "Please enter a valid phone number")
    private String phone;
}
