package com.setec.controllers;

//import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.setec.models.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("")
public class HomeController {
//    @GetMapping("")
//    public String home(Model model, Authentication auth) {
//        User user = new User();
//        user.setName("Chanthou");
//        model.addAttribute("user", user);
//        model.addAttribute("userName", auth.getName());
//        return "layout/contentHome";
//    }
    @GetMapping("/{id}")
    @ResponseBody
    public String findUserById(@PathVariable  int id) {
        System.out.println("Id:"+id);
        return "Id:"+id;
    }
    @GetMapping("/search")
    @ResponseBody
    public  String search(@RequestParam String keyword , @RequestParam int page){
        return "keyword ="+keyword+" page ="+page;
    }
    @GetMapping("/search1")
    @ResponseBody
    public String search1(@RequestParam(defaultValue = "1") int page) {
        return "Page = " + page;
    }

    @GetMapping("/test")
    public String test(){
        return "index1";
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
