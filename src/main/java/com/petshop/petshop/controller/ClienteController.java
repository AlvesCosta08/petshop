package com.petshop.petshop.controller;


import com.petshop.petshop.model.Cliente;
import com.petshop.petshop.service.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
        List<Cliente> clientes = service.getAll();
        model.addAttribute("cliente", clientes);
        return "cliente/lista";
    }


    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Cliente cliente, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "cliente/cadastro";
        }
        service.create(cliente);
        attr.addFlashAttribute("success", "Cliente inserido com sucesso.");
        return "redirect:/clientes/listar";
    }


    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
        try {
            service.delete(id);
            attr.addFlashAttribute("success", "Cliente excluído com sucesso.");
            return "redirect:/clientes/listar";
        }catch (DataIntegrityViolationException e){
            return "redirect:/clientes/listar?error=violacao-chave-estrangeira";
        }
    }


    @GetMapping("/editar/{id}")
    public String preEditar(@PathVariable("id") Long id, ModelMap model) {
        Cliente cliente = service.getById(id).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + id));
        model.addAttribute("cliente", cliente);
        return "cliente/editar"; // Nome da página Thymeleaf para edição
    }


    @PostMapping("/editar")
    public String editar(@Valid @ModelAttribute("cliente") Cliente cliente,BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "cliente/cadastro";
        }
        attr.addFlashAttribute("success", "Cliente atualizado com sucesso.");
        service.update(cliente.getId(), cliente);
        return "redirect:/clientes/listar"; // Redireciona para listagem após editar
    }

    @GetMapping
    public String getAllClientes(Model model, @RequestParam(value = "nome", required = false) String nome) {
        List<Cliente> clientes;
        if (nome != null && !nome.isEmpty()) {
            clientes = service.searchByNome(nome);
        } else {
            clientes = service.getAll();
        }
        model.addAttribute("clientes", clientes);
        model.addAttribute("nome", nome);
        return "cliente/lista_nome";
    }

}

