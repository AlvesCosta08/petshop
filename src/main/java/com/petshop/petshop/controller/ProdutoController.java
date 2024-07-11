package com.petshop.petshop.controller;

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

    // Processar formulário de edição
    @PostMapping("/editar")
    public String editar(@ModelAttribute("cliente") Produto produto) {
        service.update(produto.getId(), produto);
        return "redirect:/produtos/listar"; // Redireciona para listagem após editar
    }

    private String salvarArquivoNoSistemaDeArquivos(MultipartFile file) throws IOException {
        String diretorioDeArmazenamento = "src/main/resources/static/images/produtos/";

        // Nome do arquivo original
        String nomeArquivo = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if (nomeArquivo.isEmpty()) {
            throw new IllegalArgumentException("Nome do arquivo não pode estar vazio!");
        }

        try {
            // Verifica se o diretório existe, senão cria
            Path diretorio = Paths.get(diretorioDeArmazenamento);
            if (!Files.exists(diretorio)) {
                Files.createDirectories(diretorio);
            }

            // Caminho completo onde o arquivo será armazenado
            Path caminhoCompleto = diretorio.resolve(nomeArquivo);

            // Salva o arquivo no sistema de arquivos
            Files.copy(file.getInputStream(), caminhoCompleto, StandardCopyOption.REPLACE_EXISTING);

            // Retorna a URL do arquivo salvo (pode ser o caminho relativo ou completo)
            return caminhoCompleto.toString();
        } catch (IOException e) {
            throw new IOException("Falha ao salvar o arquivo " + nomeArquivo, e);
        }
    }
}
