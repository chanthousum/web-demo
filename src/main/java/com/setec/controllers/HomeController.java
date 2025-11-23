package com.setec.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.setec.models.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
    @GetMapping("")
    public String home(Model model) {
        User user = new User();
        user.setName("Chanthou");
        model.addAttribute("user", user);
        model.addAttribute("userName", "Sum Chanthou");
        return "layout/contentHome";
    }

    @PostMapping("/add")
    public String add(HttpServletRequest request, Model model) {
        double num1 = Double.parseDouble(request.getParameter("num1"));
        double num2 = Double.parseDouble(request.getParameter("num2"));
        double total = num1 + num2;
        HttpSession session = request.getSession();
        session.setAttribute("total", total);
        model.addAttribute("total", total);
        return "result";
    }
    @GetMapping("/contentPage")
    public String contentPage() {
        return "layout/content";
    }
}
