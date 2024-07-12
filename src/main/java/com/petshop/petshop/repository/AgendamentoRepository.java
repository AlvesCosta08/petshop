
package com.petshop.petshop.repository;

import com.petshop.petshop.model.Agendamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento,Long> {
    @Query("SELECT a FROM Agendamento a JOIN FETCH a.cliente JOIN FETCH a.servico JOIN FETCH a.pet")
    List<Agendamento> findAllWithDetails();
}
