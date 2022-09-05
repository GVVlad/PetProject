package com.petproject.controller;

import com.petproject.model.User;
import com.petproject.service.RoleService;
import com.petproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("user", new User());

        return "create-user";
    }

    @PostMapping("/create")
    public String create(@Validated @ModelAttribute("user") User user, BindingResult result) {
        if (result.hasErrors()) {
            return "create-user";
        }
        user.setRole(roleService.readById(2));
        var newUser = userService.create(user);

        //return "redirect:/users/all";
        return "redirect:/todos/all/users/" + newUser.getId();
    }

    @GetMapping("/{id}/read")
    public String read(@PathVariable long id,Model model){
        var user = userService.readById(id);
        model.addAttribute("user",user);

        return "user-info";
    }

    @GetMapping("/{id}/update")
    public String update(@PathVariable long id, Model model){
        var user = userService.readById(id);
        model.addAttribute("user",user);
        model.addAttribute("roles",roleService.getAll());

        return "update-user";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable long id,
                         Model model,
                         @Validated @ModelAttribute("user") User user,
                         @RequestParam("roleId") long roleId,
                         BindingResult result){
        var oldUser = userService.readById(id);

        if(result.hasErrors()){
            user.setRole(oldUser.getRole());
            model.addAttribute("roles", roleService.getAll());
            return "update-user";
        }

        if(oldUser.getRole().getName().equals("USER")){
            user.setRole(oldUser.getRole());
        }else {
            user.setRole(roleService.readById(roleId));
        }

        userService.update(user);

        return String.format("redirect:/users/%d/read",id);
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable long id){
        userService.delete(id);
        return "redirect:/users/all";
    }

    @GetMapping("/all")
    public String getAll(Model model){
        model.addAttribute("users",userService.getAll());
        return "users-list";
    }

}
