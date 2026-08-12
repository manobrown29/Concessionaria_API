package com.concessionaria.Repository;

import com.concessionaria.model.Carro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CarroRepository extends JpaRepository<Carro, Integer> {

    boolean existsByChassi(String chassi);

    boolean existsByPlaca(String placa);

    @Query("""
            SELECT c FROM Carro c
            WHERE (:cor IS NULL OR c.cor = :cor)
              AND (:anoFabricacao IS NULL OR c.anoFabricacao = :anoFabricacao)
            """)
    List<Carro> buscarComFiltro(@Param("cor") String cor, @Param("anoFabricacao") Integer anoFabricacao);
}
