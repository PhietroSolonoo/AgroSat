package br.com.fiap.agrosat.repository;

import br.com.fiap.agrosat.model.Produtor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProdutorRepository extends JpaRepository<Produtor, Long> {

    Optional<Produtor> findByCpf(String cpf);

    List<Produtor> findByUsuarioNomeContainingIgnoreCase(String nome);

    // cidade agora está dentro do @Embedded Endereco, então o caminho é endereco.cidade
    Page<Produtor> findByEnderecoCidade(String cidade, Pageable pageable);

    boolean existsByCpf(String cpf);
}