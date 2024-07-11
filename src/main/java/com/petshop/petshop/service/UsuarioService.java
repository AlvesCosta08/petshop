
package com.petshop.petshop.service;


import com.petshop.petshop.model.Cliente;
import com.petshop.petshop.model.Usuario;
import com.petshop.petshop.repository.ClienteRepository;
import com.petshop.petshop.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

        @Autowired
        private UsuarioRepository repository;

        public List<Usuario> getAll() {
            return repository.findAll();
        }

        public Optional<Usuario> getById(Long id) {
            return repository.findById(id);
        }

        public Usuario create(Usuario usuario) {
            return repository.save(usuario);
        }

        public Optional<Usuario> update(Long id, Usuario usuario) {
            if (!repository.existsById(id)) {
                return Optional.empty();
            }
            usuario.setId(id); // Garantindo que o ID seja preservado
            Usuario updatedUsuario = repository.save(usuario);
            return Optional.of(updatedUsuario);
        }

        public void delete(Long id) {
            repository.deleteById(id);
        }

    public Usuario findByUsername(String username) {
        return repository.findByUsername(username);
    }
}
