package com.library.management.controller;

import com.library.management.dto.DashboardStatsDto;
import com.library.management.entity.IssuedBook;
import com.library.management.service.DashboardService;
import com.library.management.service.IssueReturnService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService dashboardService;
    private final IssueReturnService issueReturnService;

    @GetMapping({"/", "/dashboard"})
    public String dashboard(Model model) {
        DashboardStatsDto stats = dashboardService.getDashboardStats();
        List<IssuedBook> activeIssued = issueReturnService.getActiveIssuedBooks();

        model.addAttribute("stats", stats);
        model.addAttribute("activeIssued", activeIssued);
        model.addAttribute("activePage", "dashboard");
        return "dashboard";
    }
}
