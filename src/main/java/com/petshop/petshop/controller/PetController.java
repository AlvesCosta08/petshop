package com.petshop.petshop.controller;

import com.petshop.petshop.model.Pet;
import com.petshop.petshop.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pets")
public class PetController {

    @Autowired
    private PetService petService;

    // Endpoint para listar todos os pets
    @GetMapping("/")
    public String getAllPets(Model model) {
        List<Pet> pets = petService.getAll();
        model.addAttribute("pets", pets);
        return "pets-list"; // Nome do arquivo HTML Thymeleaf (pets-list.html)
    }

    // Endpoint para exibir detalhes de um pet específico
    @GetMapping("/{id}")
    public String getPetById(@PathVariable("id") Long id, Model model) {
        Optional<Pet> pet = petService.getById(id);
        pet.ifPresent(p -> model.addAttribute("pet", p));
        return "pet-details"; // Nome do arquivo HTML Thymeleaf (pet-details.html)
    }

    // Endpoint para exibir o formulário de cadastro de pet
    @GetMapping("/new")
    public String showPetForm(Model model) {
        model.addAttribute("pet", new Pet());
        return "pet-form"; // Nome do arquivo HTML Thymeleaf (pet-form.html)
    }

    // Endpoint para processar o formulário de cadastro de pet
    @PostMapping("/save")
    public String savePet(@ModelAttribute("pet") Pet pet) {
        petService.create(pet);
        return "redirect:/pets/"; // Redireciona para a lista de pets após salvar
    }

    // Endpoint para exibir o formulário de edição de pet
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Pet> pet = petService.getById(id);
        pet.ifPresent(p -> model.addAttribute("pet", p));
        return "pet-form"; // Nome do arquivo HTML Thymeleaf (pet-form.html) para edição
    }

    // Endpoint para processar o formulário de edição de pet
    @PostMapping("/update/{id}")
    public String updatePet(@PathVariable("id") Long id, @ModelAttribute("pet") Pet petDetails) {
        Optional<Pet> updatedPet = petService.update(id, petDetails);
        // Lógica para tratamento de erro se pet não for encontrado
        return "redirect:/pets/"; // Redireciona para a lista de pets após atualizar
    }

    // Endpoint para deletar um pet
    @GetMapping("/delete/{id}")
    public String deletePet(@PathVariable("id") Long id) {
        petService.delete(id);
        return "redirect:/pets/"; // Redireciona para a lista de pets após deletar
    }
}
