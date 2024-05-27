package com.todoapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.todoapp.payloads.AlertMessage;
import com.todoapp.payloads.TodoListDTO;

@Controller
@RequestMapping("/todo")
public class TodoController {
    @RequestMapping("")
    public String index() {
        return "todo/index";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        model.addAttribute("todo", new TodoListDTO());
        return "todo/create";
    }
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView save(@ModelAttribute("todo") TodoListDTO todo, RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("alertMesage", new AlertMessage("info", "Todo list created successfully"));
        return new ModelAndView("redirect:/todo/create");
    }

    @RequestMapping("/edit")
    public String edit() {
        return "todo/edit";
    }
    
    @RequestMapping("/show")
    public String show() {
        return "todo/show";
    }

    @RequestMapping("/delete")
    public String delete() {
        return "todo/delete";
    }

}
