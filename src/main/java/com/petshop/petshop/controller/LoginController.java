package com.petshop.petshop.controller;

import com.petshop.petshop.model.Usuario;
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
        return "login";
    }

    @PostMapping("/auth")
    public String authenticate(@RequestParam("username") String username,
                               @RequestParam("password") String password,
                               Model model, HttpSession session) {
        Usuario usuario = service.findByUsername(username);

        if (usuario != null && usuario.getSenha().equals(password)) {
            // Autenticação bem-sucedida
            // Adiciona o usuário à sessão
            session.setAttribute("usuarioLogado", usuario);

            return "redirect:/dashboard"; // Redireciona para a página de dashboard
        } else {
            // Autenticação falhou
            model.addAttribute("error", "Usuário ou senha inválidos.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Invalida a sessão (logout)
        session.invalidate();
        return "redirect:/login";
    }
}

