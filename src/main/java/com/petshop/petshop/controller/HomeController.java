package com.petshop.petshop.controller;

import com.petshop.petshop.model.Produto;
import com.petshop.petshop.repository.HomeRepository;
import com.petshop.petshop.service.ProdutoService;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private HomeRepository service;

    @GetMapping("/")
    public String home(Model model) {
        List<Produto> produtos = service.findAll();
        model.addAttribute("produtos", produtos);
        return "home"; // ou o nome do seu template de homepage
    }
}
