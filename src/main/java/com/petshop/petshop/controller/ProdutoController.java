package com.petshop.petshop.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Produto", description = "Cadastro e gerenciamento de Produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @Value("${file.upload-dir}")
    private String diretorioDeArmazenamento;

    @Operation(summary = "Exibe o formulário para criar um novo produto")
    @GetMapping("/cadastrar")
    @ApiResponse(responseCode = "200", description = "Formulário exibido com sucesso")
    public String cadastrar(Model model) {
        model.addAttribute("produto", new Produto());
        return "produto/cadastro";
    }

    @Operation(summary = "Lista todos os produtos com paginação")
    @GetMapping("/listar")
    @ApiResponse(responseCode = "200", description = "Lista de produtos exibida com sucesso")
    public String listar(
            ModelMap model,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "3") int size) {
        Page<Produto> produtosPage = service.findPage(PageRequest.of(page, size));
        model.addAttribute("produtosPage", produtosPage);
        return "produto/lista";
    }

    @Operation(summary = "Salva um novo produto")
    @PostMapping("/salvar")
    @ApiResponse(responseCode = "201", description = "Produto criado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de validação de dados")
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

    @Operation(summary = "Exclui um produto pelo seu ID")
    @GetMapping("/excluir/{id}")
    @ApiResponse(responseCode = "200", description = "Produto excluído com sucesso")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
        service.delete(id);
        attr.addFlashAttribute("success", "Produto excluído com sucesso.");
        return "redirect:/produtos/listar";
    }

    @Operation(summary = "Exibe o formulário para editar um produto existente")
    @GetMapping("/editar/{id}")
    @ApiResponse(responseCode = "200", description = "Formulário de edição exibido com sucesso")
    @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    public String preEditar(@PathVariable("id") Long id, ModelMap model) {
        Produto produto = service.getById(id)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));
        model.addAttribute("produto", produto);
        return "produto/editar";
    }

    @Operation(summary = "Atualiza um produto existente")
    @PostMapping("/editar")
    @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de validação de dados")
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
        return "redirect:/produtos/listar"; // Redireciona para listagem após editar
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
