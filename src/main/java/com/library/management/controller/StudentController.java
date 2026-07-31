package com.library.management.controller;

import com.library.management.dto.StudentDto;
import com.library.management.entity.Student;
import com.library.management.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;

    @GetMapping
    public String listStudents(@RequestParam(name = "query", required = false) String query, Model model) {
        List<Student> students = studentService.searchStudents(query);
        model.addAttribute("students", students);
        model.addAttribute("query", query != null ? query : "");
        model.addAttribute("activePage", "students");
        return "students/list";
    }

    @GetMapping("/add")
    public String showAddStudentForm(Model model) {
        model.addAttribute("studentDto", new StudentDto());
        model.addAttribute("activePage", "students");
        return "students/add";
    }

    @PostMapping("/add")
    public String addStudent(@Valid @ModelAttribute("studentDto") StudentDto studentDto,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("activePage", "students");
            return "students/add";
        }
        try {
            studentService.createStudent(studentDto);
            redirectAttributes.addFlashAttribute("successMessage", "Student '" + studentDto.getName() + "' registered successfully!");
            return "redirect:/students";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("activePage", "students");
            return "students/add";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditStudentForm(@PathVariable("id") Long id, Model model) {
        Student student = studentService.getStudentById(id);
        StudentDto studentDto = StudentDto.builder()
                .id(student.getId())
                .name(student.getName())
                .rollNumber(student.getRollNumber())
                .department(student.getDepartment())
                .year(student.getYear())
                .email(student.getEmail())
                .phone(student.getPhone())
                .build();

        model.addAttribute("studentDto", studentDto);
        model.addAttribute("activePage", "students");
        return "students/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable("id") Long id,
                               @Valid @ModelAttribute("studentDto") StudentDto studentDto,
                               BindingResult bindingResult,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("activePage", "students");
            return "students/edit";
        }
        try {
            studentService.updateStudent(id, studentDto);
            redirectAttributes.addFlashAttribute("successMessage", "Student details updated successfully!");
            return "redirect:/students";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("activePage", "students");
            return "students/edit";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            studentService.deleteStudent(id);
            redirectAttributes.addFlashAttribute("successMessage", "Student record deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete student record. Active issued books may be linked.");
        }
        return "redirect:/students";
    }
}
