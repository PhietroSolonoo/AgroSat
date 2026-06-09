package br.com.fiap.agrosat.repository;

import br.com.fiap.agrosat.model.Alerta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertaRepository extends JpaRepository<Alerta, Long> {

    Page<Alerta> findByPropriedadeId(Long idPropriedade, Pageable pageable);

    List<Alerta> findByPropriedadeIdAndResolvido(Long idPropriedade, Integer resolvido);

    List<Alerta> findByTipoAndPropriedadeId(String tipo, Long idPropriedade);

    long countByResolvidoAndPropriedadeId(Integer resolvido, Long idPropriedade);
}
