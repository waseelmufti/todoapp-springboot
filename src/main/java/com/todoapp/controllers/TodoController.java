package com.todoapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.todoapp.payloads.AlertMessage;
import com.todoapp.payloads.TodoItemDTO;
import com.todoapp.payloads.TodoListDTO;
import com.todoapp.services.TodoItemService;
import com.todoapp.services.TodoService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.security.Principal;


@Controller
@RequestMapping("/todo")
public class TodoController {
    @Autowired
    private TodoService todoService;
    @Autowired
    private TodoItemService todoItemService;

    @Value("${app.url}")
    private String APP_URL;

    @RequestMapping("")
    public String index(Model model) {
        model.addAttribute("todos", this.todoService.getAllTodos());
        return "todo/index";
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create(Model model) {
        if (!model.containsAttribute("todo")) {
            // Add the named model attribute here
            model.addAttribute("todo", new TodoListDTO());
        }
        model.addAttribute("action", "create");
        return "todo/create";
    }
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView save(@Valid @ModelAttribute("todo") TodoListDTO todo, BindingResult bindingResult, 
        RedirectAttributes redirectAttributes, Principal principal) {
        /*
        * check validation errors
        * set error bindings to show errors
        * set model to populate fields
        */ 
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("alertMessage", new AlertMessage("danger", "There are some errors in your form"));
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.todo", bindingResult);
            redirectAttributes.addFlashAttribute("todo", todo);
            return new ModelAndView("redirect:/todo/create");
        }
        // save todo list
        this.todoService.addTodo(todo);
        redirectAttributes.addFlashAttribute("alertMessage", new AlertMessage("info", "Todo list created successfully"));
        return new ModelAndView("redirect:/todo");
    }

    @RequestMapping("{id}/edit")
    public String edit(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        try {
            if(!model.containsAttribute("todo")){
                model.addAttribute("todo", this.todoService.getTodo(id));
            }
            model.addAttribute("action", "edit");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("alertMessage", new AlertMessage("danger", e.getMessage()));
            return "redirect:/todo";
        }
        return "todo/create";
    }

    @RequestMapping(value = "{id}/update", method = RequestMethod.POST)
    public String update(@PathVariable("id") Integer id, @Valid @ModelAttribute("todo") TodoListDTO todo, 
                            BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        try {
            if (bindingResult.hasErrors()) {
                redirectAttributes.addFlashAttribute("alertMessage", new AlertMessage("danger","There are some errors in you form"));
                redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.todo", bindingResult);
                redirectAttributes.addFlashAttribute("todo", todo);
                return "redirect:/todo/"+id+"/edit";
            }
            // update todo list
            this.todoService.updateTodo(todo, id);
            redirectAttributes.addFlashAttribute("alertMessage", new AlertMessage("success", "Todo list updated successfully"));
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("alertMessage", new AlertMessage("danger", e.getMessage()));
        }
        return "redirect:/todo";
        
    }
    
    
    @RequestMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model, 
        RedirectAttributes redirectAttributes, HttpServletRequest request,
        @RequestParam(value = "status", required = false) String status) {
        try {
            model.addAttribute("todo", this.todoService.getTodo(id));
            List<TodoItemDTO> todoItemsDTO = null;
            if(status != null && status.equals("completed")){
                todoItemsDTO = this.todoItemService.getAllCompletedTodoItem(id);
            }else{
                status = "all";
                todoItemsDTO = this.todoItemService.getAllTodoItem(id);
            }
            model.addAttribute("APP_URL", APP_URL);
            model.addAttribute("todoItems", todoItemsDTO);
            model.addAttribute("status", status);
            model.addAttribute("screentype", "private");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("alertMessage", new AlertMessage("danger", e.getMessage()));
            return "redirect:/todo";
        }
        return "todo/show";
    }
    @RequestMapping("/{id}/share/{passcode}")
    public String shareView(@PathVariable("id") Integer id, @PathVariable("passcode") String passcode, 
        Model model, 
        RedirectAttributes redirectAttributes, HttpServletRequest request,
        @RequestParam(value = "status", required = false, defaultValue = "all") String status) {
        try {
            model.addAttribute("todo", this.todoService.getTodoByIdAndPasscode(id, passcode));
            List<TodoItemDTO> todoItemsDTO = null;
            if(status != null && status.equals("completed")){
                todoItemsDTO = this.todoItemService.getAllCompletedTodoItem(id, passcode);
            }else{
                status = "all";
                todoItemsDTO = this.todoItemService.getAllTodoItem(id, passcode);
            }
            model.addAttribute("APP_URL", APP_URL);
            model.addAttribute("todoItems", todoItemsDTO);
            model.addAttribute("status", status);
            model.addAttribute("screentype", "public");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("alertMessage", new AlertMessage("danger", e.getMessage()));
            return "redirect:/error";
        }
        return "todo/show";
    }

    @RequestMapping(value = "/{id}/delete", method = {RequestMethod.DELETE,  RequestMethod.POST})
    public String delete(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        try{
            this.todoService.deleteTodo(id);
            redirectAttributes.addFlashAttribute("alertMessage", new AlertMessage("danger", "Todo delete successfully"));
        }catch(Exception e){
            redirectAttributes.addFlashAttribute("alertMessage", new AlertMessage("danger", e.getMessage()));
        }
        return "redirect:/todo";
    }

}
