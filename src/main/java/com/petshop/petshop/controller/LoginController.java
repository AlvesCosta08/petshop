package com.petshop.petshop.controller;

import com.petshop.petshop.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UsuarioService service;

    @GetMapping("")
    public String loginPage() {
        return "login"; // Retorna a página de login
    }

    @PostMapping("/auth")
    public String authenticate(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               Model model, HttpSession session) {
        try {
            // O Spring Security vai gerenciar a autenticação, portanto, não é necessário fazer a verificação manual aqui
            // Apenas a configuração de segurança é necessária
            return "redirect:/dashboard"; // Redireciona para a página de dashboard após login bem-sucedido
        } catch (Exception e) {
            model.addAttribute("error", "Usuário ou senha inválidos.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalida a sessão
        return "redirect:/login";
    }
}
