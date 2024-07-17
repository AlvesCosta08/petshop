package com.petshop.petshop.controller;

import com.petshop.petshop.model.Cliente;
import com.petshop.petshop.model.Pet;
import com.petshop.petshop.service.ClienteService;
import com.petshop.petshop.service.PetService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService service;

    @Autowired
    private ClienteService clienteService;

    // Página de cadastro de pet
    @GetMapping("/cadastrar")
    public String cadastrar(Model model) {
        model.addAttribute("pet", new Pet());
        return "pet/cadastro";
    }

    // Listagem de pet
    @GetMapping("/listar")
    public String listar(ModelMap model) {
        model.addAttribute("pet", service.getAll());
        return "pet/lista";
    }

    // Salvar pet
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Pet pet, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "pet/cadastro";
        }
        service.create(pet);
        attr.addFlashAttribute("success", "Pet inserido com sucesso.");
        return "redirect:/pets/listar";
    }

    // Excluir pet
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
        service.delete(id);
        attr.addFlashAttribute("success", "Pet excluído com sucesso.");
        return "redirect:/pets/listar";
    }


    @GetMapping("/editar/{id}")
    public String preEditar(@PathVariable("id") Long id, ModelMap model) {
        Pet pet = service.getById(id).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + id));
        model.addAttribute("pet", pet);
        return "pet/editar"; // Nome da página Thymeleaf para edição
    }


    @PostMapping("/editar")
    public String editar(@Valid @ModelAttribute("pets") Pet pet,BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "pet/cadastro";
        }
        service.update(pet.getId(), pet);
        attr.addFlashAttribute("success", "Pet atualizado com sucesso.");
        return "redirect:/pets/listar"; // Redireciona para listagem após editar
    }

    @ModelAttribute("cliente")
    public List<Cliente> getClientes(){
        return  clienteService.getAll();
    }
}
