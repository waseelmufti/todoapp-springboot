package com.todoapp.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.todoapp.payloads.UserDTO;


@Controller
public class AuthController {
    @RequestMapping(value="/login", method=RequestMethod.GET)
    public String loginForm(Model model, Principal principal) {
        String viewOrUrl = principal == null ? "auth/login" : "redirect:/todo";
        model.addAttribute("user", new UserDTO());
        return viewOrUrl;
    }

}
