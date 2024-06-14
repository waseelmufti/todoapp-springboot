package com.todoapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.todoapp.payloads.UserDTO;


@Controller
public class AuthController {
    @RequestMapping(value="/login", method=RequestMethod.GET)
    public String loginForm(Model model) {
        model.addAttribute("user", new UserDTO());
        return "auth/login";
    }

}
