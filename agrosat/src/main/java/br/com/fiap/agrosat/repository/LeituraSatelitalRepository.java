package br.com.fiap.agrosat.repository;

import br.com.fiap.agrosat.model.LeituraSatelital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeituraSatelitalRepository extends JpaRepository<LeituraSatelital, Long> {

    List<LeituraSatelital> findByPropriedadeIdOrderByDataLeituraDesc(Long idPropriedade);

    Optional<LeituraSatelital> findTopByPropriedadeIdOrderByDataLeituraDesc(Long idPropriedade);

    List<LeituraSatelital> findByFonte(String fonte);
}
