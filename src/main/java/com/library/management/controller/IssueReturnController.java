package com.library.management.controller;

import com.library.management.dto.IssueBookRequest;
import com.library.management.entity.Book;
import com.library.management.entity.IssuedBook;
import com.library.management.entity.Student;
import com.library.management.service.BookService;
import com.library.management.service.IssueReturnService;
import com.library.management.service.StudentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class IssueReturnController {

    private final IssueReturnService issueReturnService;
    private final BookService bookService;
    private final StudentService studentService;

    @GetMapping("/issue")
    public String showIssueBookForm(@RequestParam(name = "bookId", required = false) Long preSelectedBookId,
                                    Model model) {
        IssueBookRequest request = new IssueBookRequest();
        request.setDueDate(LocalDate.now().plusDays(14));
        if (preSelectedBookId != null) {
            request.setBookId(preSelectedBookId);
        }

        List<Student> students = studentService.getAllStudents();
        List<Book> books = bookService.getAllBooks();

        model.addAttribute("issueRequest", request);
        model.addAttribute("students", students);
        model.addAttribute("books", books);
        model.addAttribute("activePage", "issue");
        return "issue-return/issue";
    }

    @PostMapping("/issue")
    public String issueBook(@Valid @ModelAttribute("issueRequest") IssueBookRequest request,
                            BindingResult bindingResult,
                            Model model,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("students", studentService.getAllStudents());
            model.addAttribute("books", bookService.getAllBooks());
            model.addAttribute("activePage", "issue");
            return "issue-return/issue";
        }
        try {
            IssuedBook issuedBook = issueReturnService.issueBook(request);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Book '" + issuedBook.getBook().getTitle() + "' successfully issued to " + issuedBook.getStudent().getName() + "!");
            return "redirect:/issued-books";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("students", studentService.getAllStudents());
            model.addAttribute("books", bookService.getAllBooks());
            model.addAttribute("activePage", "issue");
            return "issue-return/issue";
        }
    }

    @GetMapping("/return")
    public String showReturnBookPage(Model model) {
        List<IssuedBook> activeIssuedBooks = issueReturnService.getActiveIssuedBooks();
        model.addAttribute("activeIssuedBooks", activeIssuedBooks);
        model.addAttribute("activePage", "return");
        return "issue-return/return";
    }

    @PostMapping("/return/{id}")
    public String returnBook(@PathVariable("id") Long issueId, RedirectAttributes redirectAttributes) {
        try {
            IssuedBook returnedBook = issueReturnService.returnBook(issueId);
            redirectAttributes.addFlashAttribute("successMessage",
                    "Book '" + returnedBook.getBook().getTitle() + "' successfully returned by " + returnedBook.getStudent().getName() + "!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/issued-books";
    }

    @GetMapping("/issued-books")
    public String listIssuedBooks(@RequestParam(name = "query", required = false) String query, Model model) {
        List<IssuedBook> issuedBooks = issueReturnService.searchIssuedBooks(query);
        model.addAttribute("issuedBooks", issuedBooks);
        model.addAttribute("query", query != null ? query : "");
        model.addAttribute("activePage", "issued-books");
        return "issue-return/issued-list";
    }
}
