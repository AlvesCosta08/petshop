package com.petshop.petshop.service;

import com.petshop.petshop.model.Satisfacao;
import com.petshop.petshop.repository.SatisfacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SatisfacaoService {

    @Autowired
    private SatisfacaoRepository satisfacaoRepository;

    public void save(Satisfacao satisfacao) {
        satisfacaoRepository.save(satisfacao);
    }

    public List<Satisfacao> findAll() {
        return satisfacaoRepository.findAll();
    }
    public List<Satisfacao> findByNome(String nome) {
        return satisfacaoRepository.findByNomeContaining(nome);
    }
}