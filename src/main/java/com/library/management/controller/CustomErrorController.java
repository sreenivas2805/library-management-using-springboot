package com.library.management.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("errorCode", "404 - Page Not Found");
                model.addAttribute("errorMessage", "The page or resource you are looking for does not exist or has been moved.");
                return "error/404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("errorCode", "500 - Internal Server Error");
                model.addAttribute("errorMessage", "An unexpected server error occurred. Please try again later.");
                return "error/404";
            }
        }

        model.addAttribute("errorCode", "Error");
        model.addAttribute("errorMessage", "An error occurred while processing your request.");
        return "error/404";
    }
}
