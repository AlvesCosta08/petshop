package com.petshop.petshop.controller;


import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboardPage(HttpSession session, Model model) {
        Usuario usuario = (Usuario) session.getAttribute("usuarioLogado");
        if (usuario == null) {
            return "redirect:/login"; // Redirecionar se não estiver logado
        }
        model.addAttribute("usuario", usuario);
        return "dashboard"; // Retornar a view do dashboard
    }
}
