package com.saichethan.SpringBootProject.controller;

import com.saichethan.SpringBootProject.entity.*;
import com.saichethan.SpringBootProject.helper.MakeAdmin;
import com.saichethan.SpringBootProject.helper.Registration;
import com.saichethan.SpringBootProject.helper.UpdatePassword;
import com.saichethan.SpringBootProject.service.StudentService;
import com.saichethan.SpringBootProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private UserService userService;

    @GetMapping("/home")
    public String showHome()
    {
        return "home";
    }

    @GetMapping("/login")
    public String showLogin()
    {
        return "login";
    }

    @GetMapping("/welcome")
    public String showWelcome()
    {
        return "welcomePage";
    }

    @PostMapping("/logout")
    public String afterLogout()
    {
        return "redirect:/home";
    }

    @GetMapping("/register")
    public String showRegistrationForm()
    {
        return "register";
    }

    @PostMapping("/addNewStudent")
    public String addNewStudent(@ModelAttribute("register") Registration registration, Model model) {

        System.out.println(registration);

        Student student = new Student(registration.getFirstName(), registration.getLastName(), registration.getEmail());

        Authorities authority1 = new Authorities("ROLE_STUDENT");
//        Authorities authority2 = new Authorities("ROLE_ADMIN");

        List<Authorities> authoritiesList = new ArrayList<>();
        authoritiesList.add(authority1);
//        authoritiesList.add(authority2);

        User user = new User(student, registration.getPassword() , authoritiesList);

//        System.out.println("-----");
        student.setUser(user);
//        System.out.println("----");
//
//        System.out.println(user);

//        System.out.println(authoritiesList);

//        System.out.println(student);

        studentService.save(student);
        userService.save(user);

        model.addAttribute(student);

//        System.out.println("REQUIRED IMPORTANT THING ------------>>>>> "  + student.getUser().getId());

        return "sample";
    }

    @GetMapping("/makeadminform")
    public String showForm()
    {
        return "/admin/make-admin-form";
    }


    @PostMapping("/makeadmin")
    public String makeAdmin(@ModelAttribute("makeadmin") MakeAdmin makeAdmin, Model model)
    {
        User user = userService.findById(makeAdmin.getId());
        user.getAuthorities();
        Authorities authority = new Authorities("ROLE_ADMIN");
        System.out.println(authority);
        user.add(authority);
        System.out.println(user);
        userService.save(user);
        return "/admin/adminSuccess";
    }


    @GetMapping("/updatepasswordform")
    public String showFormForUpdatePassword()
    {
        return "/updatepassword/updatepasswordform";
    }


    @PostMapping("/updatepassword")
    public String makeAdmin(@ModelAttribute("updatepassword") UpdatePassword updatePassword, Model model)
    {
        User user = userService.findById(updatePassword.getId());

        user.setPassword(updatePassword.getPassword());

        userService.save(user);

        return "/updatepassword/updatepasswordsuccess";
    }

}
