package com.todoapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.todoapp.payloads.AlertMessage;
import com.todoapp.payloads.TodoItemDTO;
import com.todoapp.services.TodoItemService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/todo/{todoId}/items")
public class TodoItemController {
    @Autowired
    TodoItemService todoItemService;
    
    @RequestMapping("/create")
    public String create(@PathVariable("todoId") Integer todoId, Model model){
        model.addAttribute("todoId", todoId);
        model.addAttribute("todoItem", new TodoItemDTO());
        return "todoItems/create";
    }
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String save(@Valid @ModelAttribute("todoItem") TodoItemDTO todoItemDTO, @PathVariable("todoId") Integer todoId,
        BindingResult bindingResult, RedirectAttributes redirectAttributes){
        
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("alertMessage", new AlertMessage("danger", "There are some errors in your form"));
            redirectAttributes.addFlashAttribute("todoItem", todoItemDTO);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.todoItem", bindingResult);
            return "redirect:/todo/"+todoId+"/items/create";
        }
        try{
            this.todoItemService.saveTodoItem(todoId, todoItemDTO);
            redirectAttributes.addFlashAttribute("alertMessage", new AlertMessage("success", "Todo item saved successfully"));
        }catch(Exception ex){
            redirectAttributes.addFlashAttribute("alertMessage", new AlertMessage("danger", ex.getMessage()));
        }
        return "redirect:/todo/"+todoId;
    }
}
