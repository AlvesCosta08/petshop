package com.petshop.petshop.controller;

import com.petshop.petshop.model.Servico;
import com.petshop.petshop.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/servicos") // Adiciona o prefixo para todas as rotas
public class ServicoController {

    @Autowired
    private ServicoService service;

    // Método para exibir o formulário de cadastro
    @GetMapping("/cadastrar")
    public String cadastrar(Model model) {
        model.addAttribute("servico", new Servico());
        return "servico/cadastro";
    }

    // Método para listar todos os serviços
    @GetMapping("/listar")
    public String listar(ModelMap model) {
        model.addAttribute("servico", service.getAll());
        return "servico/lista";
    }

    // Método para salvar um novo serviço
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Servico servico, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "servico/cadastro";
        }
        try {
            service.create(servico);
            attr.addFlashAttribute("success", "Serviço inserido com sucesso.");
        } catch (DataIntegrityViolationException e) {
            attr.addFlashAttribute("fail", "Erro ao inserir o serviço: " + e.getMessage());
        }
        return "redirect:/servicos/listar";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
        service.delete(id);
        attr.addFlashAttribute("success", "Usuário excluído com sucesso.");
        return "redirect:/servicos/listar";
    }

    // Método para exibir o formulário de edição com os dados do serviço
    @GetMapping("/editar/{id}")
    public String preEditar(@PathVariable("id") Long id, ModelMap model) {
        Servico servico = service.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado: " + id));
        model.addAttribute("servico", servico);
        return "servico/editar"; // Nome da página Thymeleaf para edição
    }

    // Método para salvar as alterações do serviço editado
    @PostMapping("/editar")
    public String editar(@ModelAttribute("servico") Servico servico, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "servico/editar";
        }
        try {
            service.update(servico.getId(), servico);
            attr.addFlashAttribute("success", "Serviço atualizado com sucesso.");
        } catch (DataIntegrityViolationException e) {
            attr.addFlashAttribute("fail", "Erro ao atualizar o serviço: " + e.getMessage());
        }
        return "redirect:/servicos/listar"; // Redireciona para listagem após editar
    }
}


