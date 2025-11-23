package com.setec.controllers;

import com.setec.models.MyUser;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("user/")
public class UserController {
    @Value("${file.upload-dir}")
    private String uploadFolder;

    @GetMapping("")
    public String index(Model model) {
        List<MyUser> userLists = new ArrayList<>();
        for (var item : MyUser.userLists) {
            MyUser myUser = new MyUser();
            myUser.setId(item.getId());
            myUser.setUserName(item.getUserName());
            myUser.setGender(item.getGender());
            myUser.setPassword(item.getPassword());
            myUser.setAddress(item.getAddress());
            myUser.setDateOfBirth(item.getDateOfBirth());
            myUser.setPhoto(item.getPhoto());
            myUser.setRoleName(item.getRoleName());
            userLists.add(myUser);
        }
        model.addAttribute("users", userLists);
        return "layout/users/index";
    }

    @GetMapping("show")
    public String showPage() {
        return "layout/users/create";
    }

    public static int id = 1;

    @PostMapping("create")
    public String createUser(HttpServletRequest request, @RequestParam(value = "file", required = false) MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        MyUser user = new MyUser();
        user.setId(id);
        user.setUserName(request.getParameter("userName"));
        user.setPassword(request.getParameter("password"));
        user.setGender(request.getParameter("gender"));
        user.setDateOfBirth(LocalDate.parse(request.getParameter("dateOfBirth")));
        user.setAddress(request.getParameter("address"));
        Path uploadPath = Paths.get(uploadFolder);
        if (Files.notExists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        if (file.isEmpty()) {
            user.setPhoto("");
        } else {
            String fileName = file.getOriginalFilename();
            Path targetPath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
            user.setPhoto(fileName);
        }
        user.setRoleName(request.getParameter("roleName"));
        MyUser.userLists.add(user);
        redirectAttributes.addFlashAttribute("success","User created successfully");
        id++;
        return "redirect:/user/show";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        MyUser user = MyUser.userLists.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);
        model.addAttribute("user", user);
        return "layout/users/edit";
    }

    @PostMapping("/update/{id}")
    public String update(@PathVariable int id, Model model, @RequestParam(value = "file", required = false) MultipartFile file, RedirectAttributes redirectAttributes,HttpServletRequest request) throws IOException {
        MyUser user = MyUser.userLists.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(null);

        user.setUserName(request.getParameter("userName"));
        user.setPassword(request.getParameter("password"));
        user.setGender(request.getParameter("gender"));
        user.setDateOfBirth(LocalDate.parse(request.getParameter("dateOfBirth")));
        user.setAddress(request.getParameter("address"));
        Path uploadPath = Paths.get(uploadFolder);

        if (Files.notExists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        if(user.getPhoto()!=null && !user.getPhoto().isEmpty()) {
            if(file!=null && !file.isEmpty()) {
                Path uploadPath1 = Paths.get(uploadFolder,user.getPhoto());
                Files.deleteIfExists(uploadPath1);
                String fileName = file.getOriginalFilename();
                assert fileName != null;
                Path targetPath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                user.setPhoto(fileName);
            }
        }else {
            if(file!=null && !file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                Path targetPath = uploadPath.resolve(fileName);
                Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
                user.setPhoto(fileName);
            }
        }

        user.setRoleName(request.getParameter("roleName"));
        redirectAttributes.addFlashAttribute("success","User updated successfully");
        model.addAttribute("user", user);
        return "layout/users/edit";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") int id) throws IOException {
        MyUser user=MyUser.userLists.stream().filter(u->u.getId() == id).findFirst().orElse(null);
        if(user!=null) {
            if(user.getPhoto()!=null && !user.getPhoto().isEmpty()) {
                Path uploadPath = Paths.get(uploadFolder,user.getPhoto());
                Files.deleteIfExists(uploadPath);
            }
            MyUser.userLists.remove(user);
        }
        return "redirect:/user/";
    }
}
