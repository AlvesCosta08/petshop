package com.petshop.petshop.controller;


import com.petshop.petshop.model.Cliente;
import com.petshop.petshop.model.Servico;
import com.petshop.petshop.service.ClienteService;
import com.petshop.petshop.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/servicos")
public class ServicoController {

    @Autowired
    private ServicoService service;


    @GetMapping("/cadastrar")
    public String cadastrar(Model model) {
        model.addAttribute("servico", new Servico());
        return "servico/cadastro";
    }



    @GetMapping("/listar")
    public String listar(ModelMap model) {
        model.addAttribute("servico", service.getAll());
        return "servico/lista";
    }


    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Servico servico, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "servico/cadastro";
        }
        service.create(servico);
        attr.addFlashAttribute("success", "Servico inserido com sucesso.");
        return "redirect:/servicos/listar";
    }


    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
        service.delete(id);
        attr.addFlashAttribute("success", "Servicos excluído com sucesso.");
        return "redirect:/servicos/listar";
    }



    @GetMapping("/editar/{id}")
    public String preEditar(@PathVariable("id") Long id, ModelMap model) {
       Servico servico = service.getById(id).orElseThrow(() -> new IllegalArgumentException("Servico não encontrado: " + id));
        model.addAttribute("servico", servico);
        return "servico/editar"; // Nome da página Thymeleaf para edição
    }

    // Processar formulário de edição
    @PostMapping("/editar")
    public String editar(@ModelAttribute("cliente") Servico servico) {
        service.update(servico.getId(), servico);
        return "redirect:/servicos/listar"; // Redireciona para listagem após editar
    }
}

