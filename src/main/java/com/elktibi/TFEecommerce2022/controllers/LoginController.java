package com.elktibi.TFEecommerce2022.controllers;

import com.elktibi.TFEecommerce2022.models.users.User;
import com.elktibi.TFEecommerce2022.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/login-form")
    public String login() {
        return "registration/login-form";
    }

    @GetMapping("/registration-form")
    public String getRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "registration/registration-form";
    }

    @PostMapping("/register")
    public String registerNewUser(@Valid User user, BindingResult bindingResult)  {
        if(!bindingResult.hasErrors()){
            userService.saveNewUser(user);
        }
        return "index";
    }
}
