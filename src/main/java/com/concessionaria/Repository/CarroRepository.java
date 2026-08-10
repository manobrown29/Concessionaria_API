package com.concessionaria.Repository;

import com.concessionaria.model.Carro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarroRepository extends JpaRepository<Carro, Integer> {
}
