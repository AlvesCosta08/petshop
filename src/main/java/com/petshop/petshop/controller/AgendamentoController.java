package com.petshop.petshop.controller;


import com.petshop.petshop.model.Agendamento;
import com.petshop.petshop.model.Cliente;
import com.petshop.petshop.model.Pet;
import com.petshop.petshop.model.Servico;
import com.petshop.petshop.service.AgendamentoService;
import com.petshop.petshop.service.ClienteService;
import com.petshop.petshop.service.PetService;
import com.petshop.petshop.service.ServicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoService service;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private PetService petService;

    @Autowired
    private ServicoService servicoService;


    @GetMapping("/cadastrar")
    public String cadastrar(Model model) {
        model.addAttribute("agendamento", new Agendamento());
        return "agendamento/cadastro";
    }



    @GetMapping("/listar")
    public String listar(ModelMap model) {
        model.addAttribute("agendamentos", service.getAll());
        return "agendamento/lista";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Agendamento agendamento, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "agendamento/cadastro";
        }
        service.create(agendamento);
        attr.addFlashAttribute("success", "Agendamento inserido com sucesso.");
        return "redirect:/agendamentos/listar";
    }


    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
        service.delete(id);
        attr.addFlashAttribute("success", "Agndamento excluído com sucesso.");
        return "redirect:/agendamentos/listar";
    }


    // Carregar dados do cliente para edição
    @GetMapping("/editar/{id}")
    public String preEditar(@PathVariable("id") Long id, ModelMap model) {
        Agendamento agendamento = service.getById(id).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + id));
        model.addAttribute("agendamento", agendamento);
        return "agendamento/editar"; // Nome da página Thymeleaf para edição
    }


    @PostMapping("/editar")
    public String editar(@ModelAttribute("agendamento") Agendamento agendamento) {
        service.update(agendamento.getId(), agendamento);
        return "redirect:/agendamentos/listar"; // Redireciona para listagem após editar
    }

    @ModelAttribute("cliente")
    public List<Cliente> getClientes(){
        return  clienteService.getAll();
    }

    @ModelAttribute("pet")
    public List<Pet> getPet(){
        return  petService.getAll();
    }

    @ModelAttribute("servico")
    public List<Servico> getServico(){
        return  servicoService.getAll();
    }
}

