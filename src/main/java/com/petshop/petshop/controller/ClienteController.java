package com.petshop.petshop.controller;


import com.petshop.petshop.model.Cliente;
import com.petshop.petshop.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService service;

    @GetMapping("/cadastrar")
    public String cadastrar(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "cliente/cadastro";
    }

    @GetMapping("/listar")
    public String listar(ModelMap model) {
        model.addAttribute("clientes", service.getAll());
        return "cliente/lista";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Cliente cliente, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "cliente/cadastro";
        }
        service.create(cliente);
        attr.addFlashAttribute("success", "Cliente inserido com sucesso.");
        return "redirect:/clientes/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
        service.delete(id);
        attr.addFlashAttribute("success", "Cliente excluído com sucesso.");
        return "redirect:/clientes/listar";
    }


    // Método para carregar os dados do cliente para edição
    @GetMapping("/editar/{id}")
    public String preEditar(@PathVariable("id") Long id, ModelMap model) {
        model.addAttribute("cliente", service.getById(id));
        return "cliente/cadastro"; // Nome da página Thymeleaf para edição
    }

    // Método para processar o formulário de edição
    @PostMapping("/editar")
    public String editar(@ModelAttribute("cliente") Cliente cliente, RedirectAttributes att) {
        service.update(cliente.getId(), cliente); // Atualiza o cliente usando o método update do serviço
        att.addFlashAttribute("mensagem", "Cliente atualizado com sucesso."); // Adiciona uma mensagem para exibir na próxima página
        return "redirect:/clientes/cadastrar"; // Redireciona para a página de cadastro após editar
    }
}

