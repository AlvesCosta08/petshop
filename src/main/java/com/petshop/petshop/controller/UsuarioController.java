package com.petshop.petshop.controller;


import com.petshop.petshop.model.Cliente;
import com.petshop.petshop.model.Usuario;
import com.petshop.petshop.service.ClienteService;
import com.petshop.petshop.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    // Página de cadastro de usuarios
    @GetMapping("/cadastrar")
    public String cadastrar(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/cadastro";
    }

    // Listagem de usuarios
    @GetMapping("/listar")
    public String listar(ModelMap model) {
        model.addAttribute("usuarios", service.getAll());
        return "usuario/lista";
    }

    // Salvar usuario
    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Usuario usuario, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "usuario/cadastro";
        }
        service.create(usuario);
        attr.addFlashAttribute("success", "Usuário inserido com sucesso.");
        return "redirect:/usuarios/listar";
    }

    // Excluir usuario
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
        service.delete(id);
        attr.addFlashAttribute("success", "Usuário excluído com sucesso.");
        return "redirect:/usuarios/listar";
    }

    // Carregar dados do usuario para edição
    @GetMapping("/editar/{id}")
    public String preEditar(@PathVariable("id") Long id, ModelMap model) {
        Usuario usuario = service.getById(id).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado: " + id));
        model.addAttribute("usuario", usuario);
        return "usuario/editar"; // Nome da página Thymeleaf para edição
    }

    // Processar formulário de edição
    @PostMapping("/editar")
    public String editar(@ModelAttribute("usuario") Usuario usuario,BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "usuario/cadastro";
        }
        service.update(usuario.getId(), usuario);
        attr.addFlashAttribute("success", "Usuário atualizado com sucesso.");
        return "redirect:/usuarios/listar"; // Redireciona para listagem após editar
    }
}

