package com.example.Buoi4.controller;

import com.example.Buoi4.entity.Product;
import com.example.Buoi4.service.ProductService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;

@RequestMapping("/course")
@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @GetMapping("")
    public String GetListBooks(Model model){
        model.addAttribute("list",productService.getAllBooks());
        model.addAttribute("title","Course List");
        return "course/list";
    }
    @GetMapping("/search")
    public String searchCourses(@RequestParam("input") String input, Model model) {
        List<Product> searchResults = productService.search(input);
        model.addAttribute("list", searchResults);
        model.addAttribute("title", "Course " + input);
        return "course/list";
    }

    @GetMapping("/create")
    public String addBook(Model model){
        model.addAttribute("course",new Product());
        model.addAttribute("title","Add Course");
        return "course/add";
    }
    @PostMapping("/create")
    public String addBook(@ModelAttribute("course") @Valid Product course , BindingResult result, @RequestParam MultipartFile imageProduct, Model model) {
        // Kiểm tra ràng buộc và đặt thông báo lỗi vào BindingResult
//        @Null @RequestParam MultipartFile imageProduct
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "course/add";
        } else {
            // Nếu không có lỗi, thêm sách và chuyển hướng đến trang danh sách sách
            course.setId(Init());
            if(imageProduct != null && imageProduct.getSize()>0)
            {
                try{
                    File saveFile = new ClassPathResource("static/images").getFile();
                    String newImageFile = UUID.randomUUID() + ".png";
                    java.nio.file.Path path =  Paths.get(saveFile.getAbsolutePath() + File.separator + newImageFile);
                    Files.copy(imageProduct.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                    course.setImage(newImageFile);
                }catch (Exception ex){
                    ex.printStackTrace();
                    return "course/add";
                }
            }


                productService.add(course);

            return "redirect:/course";
        }
    }

    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable("id") int id,Model model){
        Product editCourse=productService.getId(id);
        if(editCourse!=null){
            model.addAttribute("course",editCourse);
            model.addAttribute("title","Edit Course");
            return "course/edit";
        }
        return "not-found";

    }
    @PostMapping("/edit")
    public String editBook(@ModelAttribute("course") @Valid Product course , BindingResult result, @RequestParam MultipartFile imageProduct, Model model) {
        // Kiểm tra ràng buộc và đặt thông báo lỗi vào BindingResult
//        @Null @RequestParam MultipartFile imageProduct
        if (result.hasErrors()) {
            model.addAttribute("errors", result.getAllErrors());
            return "course/edit";
        } else {
            // Nếu không có lỗi, thêm sách và chuyển hướng đến trang danh sách sách
            if(imageProduct != null && imageProduct.getSize()>0)
            {
                try{
                    File saveFile = new ClassPathResource("static/images").getFile();
                    String newImageFile = UUID.randomUUID() + ".png";
                    java.nio.file.Path path =  Paths.get(saveFile.getAbsolutePath() + File.separator + newImageFile);
                    Files.copy(imageProduct.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                    course.setImage(newImageFile);
                }catch (Exception ex){
                    ex.printStackTrace();
                    return "course/edit";
                }
            }


            productService.update(course.getId(),course);

            return "redirect:/course";
        }
    }
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") int id){
        Product book=productService.getId(id);
        if(book!=null){
            productService.delete(id);
            return "redirect:/course";
        }
        return "not-found";
    }

    private int Init(){
        int maxID=0;
        for (Product b : productService.getAllBooks()) if(maxID<b.getId()) maxID=b.getId();
        return maxID+1;
    }
}
