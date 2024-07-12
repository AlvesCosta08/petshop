
package com.petshop.petshop.service;

import com.petshop.petshop.model.Agendamento;
import com.petshop.petshop.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgendamentoService {
    @Autowired
    private AgendamentoRepository repository;



    public List<Agendamento> findAllWithDetails() {
            return repository.findAllWithDetails();
    }


    public Optional<Agendamento> getById(Long id) {
        return repository.findById(id);
    }

    public Agendamento create(Agendamento agendamento) {
        return repository.save(agendamento);
    }

    public Optional<Agendamento> update(Long id, Agendamento agendamento) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        agendamento.setId(id); // Garantindo que o ID seja preservado
        Agendamento updatedAgndamento = repository.save(agendamento);
        return Optional.of(updatedAgndamento);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
