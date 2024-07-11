package com.petshop.petshop.controller;


import com.petshop.petshop.model.Cliente;
import com.petshop.petshop.model.Produto;
import com.petshop.petshop.service.ClienteService;
import com.petshop.petshop.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    // Página de cadastro de produto
    @GetMapping("/cadastrar")
    public String cadastrar(Model model) {
        model.addAttribute("produto", new Produto());
        return "produto/cadastro";
    }


    // Listagem de produto
    @GetMapping("/listar")
    public String listar(ModelMap model) {
        model.addAttribute("produto", service.getAll());
        return "produto/lista";
    }

    // Salvar produto
    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Produto produto, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            return "produto/cadastro";
        }
        service.create(produto);
        attr.addFlashAttribute("success", "Produto inserido com sucesso.");
        return "redirect:/produtos/listar";
    }

    // Excluir produto
    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
        service.delete(id);
        attr.addFlashAttribute("success", "Cliente excluído com sucesso.");
        return "redirect:/produtos/listar";
    }


    // Carregar dados do cliente para edição
    @GetMapping("/editar/{id}")
    public String preEditar(@PathVariable("id") Long id, ModelMap model) {
       Produto produto = service.getById(id).orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado: " + id));
        model.addAttribute("produto", produto);
        return "produto/editar"; // Nome da página Thymeleaf para edição
    }

    // Processar formulário de edição
    @PostMapping("/editar")
    public String editar(@ModelAttribute("cliente") Produto produto) {
        service.update(produto.getId(), produto);
        return "redirect:/produtos/listar"; // Redireciona para listagem após editar
    }
}

