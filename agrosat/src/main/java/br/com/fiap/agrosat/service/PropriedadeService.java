package br.com.fiap.agrosat.service;

import br.com.fiap.agrosat.dto.PropriedadeLista;
import br.com.fiap.agrosat.dto.PropriedadeRequest;
import br.com.fiap.agrosat.dto.PropriedadeResponse;
import br.com.fiap.agrosat.exception.ResourceNotFoundException;
import br.com.fiap.agrosat.mapper.PropriedadeMapper;
import br.com.fiap.agrosat.model.Cooperativa;
import br.com.fiap.agrosat.model.Produtor;
import br.com.fiap.agrosat.model.Propriedade;
import br.com.fiap.agrosat.repository.CooperativaRepository;
import br.com.fiap.agrosat.repository.ProdutorRepository;
import br.com.fiap.agrosat.repository.PropriedadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PropriedadeService {

    private final PropriedadeRepository propriedadeRepository;
    private final ProdutorRepository produtorRepository;
    private final CooperativaRepository cooperativaRepository;
    private final PropriedadeMapper propriedadeMapper;

    @Autowired
    public PropriedadeService(PropriedadeRepository propriedadeRepository,
                              ProdutorRepository produtorRepository,
                              CooperativaRepository cooperativaRepository,
                              PropriedadeMapper propriedadeMapper) {
        this.propriedadeRepository = propriedadeRepository;
        this.produtorRepository = produtorRepository;
        this.cooperativaRepository = cooperativaRepository;
        this.propriedadeMapper = propriedadeMapper;
    }

    @CacheEvict(value = "propriedades", allEntries = true)
    public Propriedade criar(PropriedadeRequest request) {
        Produtor produtor = produtorRepository.findById(request.idProdutor())
                .orElseThrow(() -> new ResourceNotFoundException("Produtor não encontrado para o id: " + request.idProdutor()));

        Propriedade propriedade = new Propriedade();
        propriedade.setNome(request.nome());
        propriedade.setAreaHa(request.areaHa());
        propriedade.setCultura(request.cultura());
        propriedade.setLatitude(request.latitude());
        propriedade.setLongitude(request.longitude());
        propriedade.setStatus(request.status() != null ? request.status() : "ATIVO");
        propriedade.setDataCadastro(LocalDate.now());
        propriedade.setProdutor(produtor);

        if (request.idCooperativa() != null) {
            Cooperativa cooperativa = cooperativaRepository.findById(request.idCooperativa())
                    .orElseThrow(() -> new ResourceNotFoundException("Cooperativa não encontrada para o id: " + request.idCooperativa()));
            propriedade.setCooperativa(cooperativa);
        }

        return propriedadeRepository.save(propriedade);
    }

    @Cacheable("propriedades")
    public PropriedadeResponse buscarPorId(Long id) {
        Propriedade propriedade = propriedadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Propriedade não encontrada para o id: " + id));
        return propriedadeMapper.propriedadeToResponse(propriedade);
    }

    public Page<PropriedadeLista> listar(Pageable pageable) {
        return propriedadeRepository.findAll(pageable).map(propriedadeMapper::propriedadeToLista);
    }

    @CacheEvict(value = "propriedades", allEntries = true)
    public Propriedade atualizar(Long id, PropriedadeRequest request) {
        Propriedade propriedade = propriedadeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Propriedade não encontrada para o id: " + id));

        Produtor produtor = produtorRepository.findById(request.idProdutor())
                .orElseThrow(() -> new ResourceNotFoundException("Produtor não encontrado para o id: " + request.idProdutor()));

        propriedade.setNome(request.nome());
        propriedade.setAreaHa(request.areaHa());
        propriedade.setCultura(request.cultura());
        propriedade.setLatitude(request.latitude());
        propriedade.setLongitude(request.longitude());
        propriedade.setStatus(request.status());
        propriedade.setProdutor(produtor);

        if (request.idCooperativa() != null) {
            Cooperativa cooperativa = cooperativaRepository.findById(request.idCooperativa())
                    .orElseThrow(() -> new ResourceNotFoundException("Cooperativa não encontrada para o id: " + request.idCooperativa()));
            propriedade.setCooperativa(cooperativa);
        }

        return propriedadeRepository.save(propriedade);
    }

    @CacheEvict(value = "propriedades", allEntries = true)
    public void deletar(Long id) {
        if (!propriedadeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Propriedade não encontrada para o id: " + id);
        }
        propriedadeRepository.deleteById(id);
    }
}
