package com.petshop.petshop.repository;

import com.petshop.petshop.model.Satisfacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SatisfacaoRepository extends JpaRepository<Satisfacao, Long> {
    List<Satisfacao> findByNomeContaining(String nome);
}
