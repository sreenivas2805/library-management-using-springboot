package com.library.management.controller;

import com.library.management.dto.BookDto;
import com.library.management.entity.Book;
import com.library.management.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public String listBooks(@RequestParam(name = "query", required = false) String query, Model model) {
        List<Book> books = bookService.searchBooks(query);
        model.addAttribute("books", books);
        model.addAttribute("query", query != null ? query : "");
        model.addAttribute("activePage", "books");
        return "books/list";
    }

    @GetMapping("/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("bookDto", new BookDto());
        model.addAttribute("activePage", "books");
        return "books/add";
    }

    @PostMapping("/add")
    public String addBook(@Valid @ModelAttribute("bookDto") BookDto bookDto,
                          BindingResult bindingResult,
                          Model model,
                          RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("activePage", "books");
            return "books/add";
        }
        try {
            bookService.createBook(bookDto);
            redirectAttributes.addFlashAttribute("successMessage", "Book '" + bookDto.getTitle() + "' added successfully!");
            return "redirect:/books";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("activePage", "books");
            return "books/add";
        }
    }

    @GetMapping("/edit/{id}")
    public String showEditBookForm(@PathVariable("id") Long id, Model model) {
        Book book = bookService.getBookById(id);
        BookDto bookDto = BookDto.builder()
                .id(book.getId())
                .title(book.getTitle())
                .author(book.getAuthor())
                .isbn(book.getIsbn())
                .category(book.getCategory())
                .publisher(book.getPublisher())
                .quantity(book.getQuantity())
                .availableQuantity(book.getAvailableQuantity())
                .status(book.getStatus().name())
                .build();

        model.addAttribute("bookDto", bookDto);
        model.addAttribute("activePage", "books");
        return "books/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable("id") Long id,
                             @Valid @ModelAttribute("bookDto") BookDto bookDto,
                             BindingResult bindingResult,
                             Model model,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("activePage", "books");
            return "books/edit";
        }
        try {
            bookService.updateBook(id, bookDto);
            redirectAttributes.addFlashAttribute("successMessage", "Book updated successfully!");
            return "redirect:/books";
        } catch (IllegalArgumentException e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("activePage", "books");
            return "books/edit";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        try {
            bookService.deleteBook(id);
            redirectAttributes.addFlashAttribute("successMessage", "Book deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete book. It may have associated records.");
        }
        return "redirect:/books";
    }
}
