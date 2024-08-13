package web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import web.exeption.UserNotFoundException;
import web.model.User;
import web.service.UserService;
import web.service.UserServiceImpl;

@Controller
@RequestMapping("/user")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "all_users";
    }

    @GetMapping("/new")
    public String showFormAddUser(Model model) {
        model.addAttribute("user", new User());
        return "add_user";
    }

    @PostMapping()
    public String addUser(@ModelAttribute("user") User user) {
        userService.saveUser(user.getFirstName(), user.getLastName(), user.getEmail());
        return "redirect:/user";
    }

    @GetMapping("/{id}")
    public String showUserById(@PathVariable("id") int id, Model model) {
        try {
            User user = userService.showUserById(id);
            model.addAttribute("user", user);
            return "selected_user";
        } catch (UserNotFoundException e) {
            return "redirect:/error_page";
        }
    }

    @GetMapping("/{id}/edit")
    public String editUser(Model model, @PathVariable("id") int id) {
        try {
            model.addAttribute("user", userService.showUserById(id));
            return "edit_user";
        } catch (UserNotFoundException e) {
            return "redirect:/error_page";
        }
    }

    @PostMapping("/{id}/update")
    public String updateUser(@ModelAttribute("user") User user, @PathVariable("id") int id) {
        try {
            userService.updateUserById(id, user);
            return "redirect:/user";
        } catch (UserNotFoundException e) {
            return "redirect:/error_page";
        }
    }

    @PostMapping("/{id}/delete")
    public String deleteUser(@PathVariable("id") int id) {
        try {
            userService.removeUserById(id);
            return "redirect:/user";
        } catch (UserNotFoundException e) {
            return "redirect:/error_page";
        }
    }
}