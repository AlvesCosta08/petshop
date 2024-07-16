package com.petshop.petshop.controller;

import com.petshop.petshop.model.Satisfacao;
import com.petshop.petshop.service.SatisfacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/satisfacao")
public class SatisfacaoController {

    @Autowired
    private SatisfacaoService service;


    @PostMapping
    public String enviarSatisfacao(Satisfacao satisfacao, Model model) {
        service.save(satisfacao);
        model.addAttribute("message", "Obrigado pela sua avaliação!");
        return "redirect:/"; // Redireciona para uma página de sucesso
    }

      /*
    // Listagem de clientes
    @GetMapping("/listar")
    public String listar(ModelMap model) {
        model.addAttribute("satisfacao", service.findAll());
        return "satisfacao/lista";
    }

  @GetMapping("/satisfacao/listar")
    public String buscarSatisfacao(@RequestParam String nome, Model model) {
        List<Satisfacao> satisfacoes = service.findByNome(nome);
        model.addAttribute("satisfacoes", satisfacoes);
        return "satisfacao/lista"; // Retorna a mesma página com a lista filtrada
    }*/
}