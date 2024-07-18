package com.petshop.petshop.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import com.petshop.petshop.model.Produto;
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


    @GetMapping("/cadastrar")
    public String cadastrar(Model model) {
        model.addAttribute("produto", new Produto());
        return "produto/cadastro";
    }



    @GetMapping("/listar")
    public String listar(
            ModelMap model,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "9") int size) {
        Page<Produto> produtosPage = service.findPage(PageRequest.of(page, size));
        model.addAttribute("produtosPage", produtosPage);
        return "produto/lista";
    }


    @PostMapping("/salvar")
    public String salvar(@Valid @ModelAttribute Produto produto,
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


    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
        service.delete(id);
        attr.addFlashAttribute("success", "Cliente excluído com sucesso.");
        return "redirect:/produtos/listar";
    }

    @GetMapping("/editar/{id}")
    public String preEditar(@PathVariable("id") Long id, ModelMap model) {
        Produto produto = service.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));
        model.addAttribute("produto", produto);
        return "produto/editar";
    }

    @PostMapping("/editar")
    public String editar(@Valid @ModelAttribute Produto produto,
                         BindingResult result,
                         @RequestParam("file") MultipartFile file,
                         @RequestParam("fotoOriginal") String fotoOriginal,
                         RedirectAttributes attr) throws IOException {
        if (result.hasErrors()) {
            return "produto/editar";
        }

        if (!file.isEmpty()) {
            String urlImagem = salvarArquivoNoSistemaDeArquivos(file);
            produto.setFoto(urlImagem);
        } else {
            produto.setFoto(fotoOriginal);
        }

        service.update(produto.getId(), produto);
        attr.addFlashAttribute("success", "Produto editado com sucesso.");
        return "redirect:/produtos/listar";
    }

    private String salvarArquivoNoSistemaDeArquivos(MultipartFile file) throws IOException {
        String nomeArquivo = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        if (nomeArquivo.isEmpty()) {
            throw new IllegalArgumentException("Nome do arquivo não pode estar vazio!");
        }

        try {
            Path diretorio = Paths.get(diretorioDeArmazenamento);
            if (!Files.exists(diretorio)) {
                Files.createDirectories(diretorio);
            }

            Path caminhoCompleto = diretorio.resolve(nomeArquivo);
            Files.copy(file.getInputStream(), caminhoCompleto, StandardCopyOption.REPLACE_EXISTING);

            return "/images/produtos/" + nomeArquivo;
        } catch (IOException e) {
            throw new IOException("Falha ao salvar o arquivo " + nomeArquivo, e);
        }
    }
}
