package com.setec.controllers;

import com.setec.models.Product;
import com.setec.services.CategoryService;
import com.setec.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
@RequestMapping("product")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }
    @GetMapping("/")
    public String index(Model model){
        return findPaginated(model,1);
    }

    @GetMapping("page/{pageNo}")
    public String findPaginated(Model model,@PathVariable int pageNo){
        int pageSize=2;
        var products=this.productService.paginated(pageNo,pageSize);
        model.addAttribute("products",products.getContent());
        model.addAttribute("currentPage",pageNo);
        model.addAttribute("totalPages",products.getTotalPages());
        model.addAttribute("totalItems",products.getTotalElements());
        return "layout/products/index";
    }
    @GetMapping("show")
    public String showPage(Model model){
        Product product=new Product();
        var categories=this.categoryService.getCategoryAll();
        model.addAttribute("product",product);
        model.addAttribute("categories",categories);
        return "layout/products/create.html";
    }
    @PostMapping("create")
    public String createItem(@ModelAttribute Product product, Model model, @RequestParam(value = "file",required = false) MultipartFile file, RedirectAttributes redirectAttributes) throws IOException {
        model.addAttribute("product",product);
        this.productService.createItem(product,file);
        redirectAttributes.addFlashAttribute("success","Product created successfully");
        return "redirect:/product/show";
    }
    @GetMapping("delete/{id}")
    public  String deleteById(@PathVariable int id,RedirectAttributes redirectAttributes) throws IOException {
        this.productService.deleteById(id);
        redirectAttributes.addFlashAttribute("success","Product deleted successfully");
        return "redirect:/product/";
    }
    @GetMapping("edit/{id}")
    public  String editById(@PathVariable int id,Model model) throws IOException {
        var categories=this.categoryService.getCategoryAll();
        var product=this.productService.editById(id);
        model.addAttribute("categories",categories);
        model.addAttribute("product",product);
        return "layout/products/edit.html";
    }
    @PostMapping("update/{id}")
    public  String editById(@PathVariable int id,@ModelAttribute Product product,@RequestParam(value = "file",required = false)MultipartFile file,Model model,RedirectAttributes redirectAttributes) throws IOException {
        var categories=this.categoryService.getCategoryAll();
        var productExisting=this.productService.updateById(id,product,file);
        model.addAttribute("product",productExisting);
        model.addAttribute("categories",categories);
        redirectAttributes.addFlashAttribute("success","Product updated successfully");
        return "redirect:/product/edit/"+id;
    }
    @PostMapping("search")
    public  String search(@RequestParam(value = "search",required = false)String search,Model model){
        var products=this.productService.searchByName(search);
        model.addAttribute("products",products);
        return "layout/products/index.html";
    }
}
