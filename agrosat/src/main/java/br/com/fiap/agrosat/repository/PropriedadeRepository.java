package br.com.fiap.agrosat.repository;

import br.com.fiap.agrosat.model.Propriedade;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PropriedadeRepository extends JpaRepository<Propriedade, Long> {

    List<Propriedade> findByProdutorId(Long idProdutor);

    Page<Propriedade> findByCulturaEqualsIgnoreCase(String cultura, Pageable pageable);

    List<Propriedade> findByStatus(String status);

    List<Propriedade> findTop5ByOrderByAreaHaDesc();
}
