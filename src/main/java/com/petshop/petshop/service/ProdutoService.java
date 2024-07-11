
package com.petshop.petshop.service;

import com.petshop.petshop.model.Cliente;
import com.petshop.petshop.model.Produto;
import com.petshop.petshop.repository.ClienteRepository;
import com.petshop.petshop.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {
    @Autowired
    private ProdutoRepository repository;

    public List<Produto> getAll() {
        return repository.findAll();
    }

    public Optional<Produto> getById(Long id) {
        return repository.findById(id);
    }

    public Produto create(Produto produto) {
        return repository.save(produto);
    }

    public Optional<Produto> update(Long id, Produto produto) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        produto.setId(id); // Garantindo que o ID seja preservado
        Produto updatedProduto = repository.save(produto);
        return Optional.of(updatedProduto );
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
