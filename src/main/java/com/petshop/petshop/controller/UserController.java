package com.petshop.petshop.controller;

import com.petshop.petshop.model.User;
import com.petshop.petshop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuario")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public String getAllUsrs(Model model) {
        List<User> usuarios = service.getAll();
        model.addAttribute("usuarios", usuarios);
        return "usuario/listar";
    }

    @GetMapping("/{id}")
    public String getUserById(@PathVariable Long id, Model model) {
        Optional<User> usuario = service.getById(id);
        if (usuario.isPresent()) {
            model.addAttribute("usuario", usuario.get());
            return "usuario/view";
        } else {
            return "redirect:/usuario";
        }
    }

    @GetMapping("/cadastrar")
    public String showCreateForm(Model model) {
        model.addAttribute("usuario", new User());
        return "usuario/cadastrar";
    }

    @PostMapping
    public String createUser(@ModelAttribute User user) {
        service.create(user);
        return "redirect:/usuario";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<User> user = service.getById(id);
        if (user.isPresent()) {
            model.addAttribute("usuario", user.get());
            return "usuario/editar";
        } else {
            return "redirect:/listar";
        }
    }

    @PostMapping("/editar/{id}")
    public String updateUsuario(@PathVariable Long id, @ModelAttribute User user) {
        service.update(id, user);
        return "redirect:/usuario";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/usuario";
    }
}