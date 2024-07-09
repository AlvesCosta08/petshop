package com.petshop.petshop.controller;


import com.petshop.petshop.model.Cliente;
import com.petshop.petshop.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ClienteController {
    @Autowired
    private ClienteService service;

    @GetMapping("/cadastrar")
    public String cadastrar(Cliente cliente) {
        return "/cliente/cadastro";
    }

    @GetMapping("/listar")
    public String listar(ModelMap model) {
        model.addAttribute("clientes",service.getAll());
        return "/cliente/lista";
    }

    @PostMapping("/salvar")
    public String salvar(Cliente cliente){
        service.create(cliente);
        return "redirect:/cliente/cadastrar";
    }
}