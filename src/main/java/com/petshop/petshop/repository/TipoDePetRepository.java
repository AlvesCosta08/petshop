
package com.petshop.petshop.repository;

import com.petshop.petshop.model.TipoDePet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDePetRepository extends JpaRepository<TipoDePet,Long> {

}
