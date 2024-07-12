package com.petshop.petshop.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @Value("${file.upload-dir}")
    private String diretorioDeArmazenamento;

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

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Produto produto,
                         BindingResult result,
                         @RequestParam("file") MultipartFile file,
                         RedirectAttributes attr) throws IOException {
        if (result.hasErrors()) {
            return "produto/cadastro";
        }

        if (!file.isEmpty()) {
            String urlImagem = salvarArquivoNoSistemaDeArquivos(file); // Salva o arquivo e obtém a URL
            produto.setFoto(urlImagem); // Define a URL da imagem no produto
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

    @PostMapping("/editar")
    public String editar(@ModelAttribute Produto produto,
                         BindingResult result,
                         @RequestParam("file") MultipartFile file,
                         RedirectAttributes attr) throws IOException {
        if (result.hasErrors()) {
            return "produto/editar";
        }

        if (!file.isEmpty()) {
            String urlImagem = salvarArquivoNoSistemaDeArquivos(file); // Salva o arquivo e obtém a URL
            produto.setFoto(urlImagem); // Define a URL da imagem no produto
        }

        service.update(produto.getId(), produto);

        attr.addFlashAttribute("success", "Produto atualizado com sucesso.");
        return "redirect:/produtos/listar";
    }
    private String salvarArquivoNoSistemaDeArquivos(MultipartFile file) throws IOException {
        // Obtemos o nome original do arquivo e o limpamos
        String nomeArquivo = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if (nomeArquivo.isEmpty()) {
            throw new IllegalArgumentException("Nome do arquivo não pode estar vazio!");
        }

        try {
            // Caminho relativo onde o arquivo será armazenado
            Path diretorio = Paths.get("src/main/resources/static/images/produtos");

            // Verifica se o diretório existe, senão cria
            if (!Files.exists(diretorio)) {
                Files.createDirectories(diretorio);
            }

            // Caminho completo onde o arquivo será armazenado
            Path caminhoCompleto = diretorio.resolve(nomeArquivo);

            // Salva o arquivo no sistema de arquivos
            Files.copy(file.getInputStream(), caminhoCompleto, StandardCopyOption.REPLACE_EXISTING);

            // Retorna o caminho relativo do arquivo salvo
            return "/images/produtos/" + nomeArquivo;
        } catch (IOException e) {
            throw new IOException("Falha ao salvar o arquivo " + nomeArquivo, e);
        }
    }
}
