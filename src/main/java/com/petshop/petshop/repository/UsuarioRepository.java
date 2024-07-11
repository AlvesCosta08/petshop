
package com.petshop.petshop.repository;

import com.petshop.petshop.model.Cliente;
import com.petshop.petshop.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

    Usuario findByUsername(String username);
}
