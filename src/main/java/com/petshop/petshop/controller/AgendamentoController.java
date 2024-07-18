package com.petshop.petshop.controller;


import com.petshop.petshop.model.Agendamento;
import com.petshop.petshop.model.Cliente;
import com.petshop.petshop.model.Pet;
import com.petshop.petshop.model.Servico;
import com.petshop.petshop.service.AgendamentoService;
import com.petshop.petshop.service.ClienteService;
import com.petshop.petshop.service.PetService;
import com.petshop.petshop.service.ServicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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

    @GetMapping("/listarPorNome")
    public String listarAgendamentos(@RequestParam(required = false) String clienteNome, Model model) {
        List<Agendamento> agendamentosPorNome;
        agendamentosPorNome = service.findAllWithDetails(clienteNome);
        model.addAttribute("agendamentos", agendamentosPorNome);
        model.addAttribute("clienteNome", clienteNome); // Adiciona o nome do cliente ao modelo
        return "agendamento/lista_nome";
    }

    @GetMapping("/listar")
    public String listar(ModelMap model) {
        List<Agendamento> agendamentos = service.getAll();
        model.addAttribute("agendamentos", agendamentos);
        return "agendamento/lista";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute("agendamento") Agendamento agendamento, BindingResult result, RedirectAttributes attr,Model model) {
        if (result.hasErrors()) {
            model.addAttribute("cliente", clienteService.getAll());
            model.addAttribute("pet", petService.getAll());
            model.addAttribute("servico", servicoService.getAll());
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

    @GetMapping("/editar/{id}")
    public String preEditar(@PathVariable("id") Long id, ModelMap model) {
        Agendamento agendamento = service.getById(id).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + id));

        model.addAttribute("agendamento", agendamento);
        return "agendamento/editar"; // Nome da página Thymeleaf para edição
    }


    @PostMapping("/editar")
    public String editar(@Valid @ModelAttribute("agendamento") Agendamento agendamento, BindingResult result, RedirectAttributes attr, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("cliente", clienteService.getAll());
            model.addAttribute("pet", petService.getAll());
            model.addAttribute("servico", servicoService.getAll());
            return "agendamento/cadastro";
        }
        service.update(agendamento.getId(), agendamento);
        attr.addFlashAttribute("success", "Agendamento atualizado com sucesso.");
        return "redirect:/agendamentos/listar";
    }
    @GetMapping("/horariosMaisAgendados")
    public String listarHorariosMaisAgendados(Model model) {
        Map<LocalDate, List<String>> contagemHorarios = service.contarAgendamentosPorDia();
        model.addAttribute("agendamentosPorDia", contagemHorarios); // Alterado para "agendamentosPorDia"
        return "agendamento/horarios"; // Nome da página Thymeleaf onde os dados serão exibidos
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

