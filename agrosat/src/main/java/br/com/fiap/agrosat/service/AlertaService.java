package br.com.fiap.agrosat.service;

import br.com.fiap.agrosat.dto.AlertaLista;
import br.com.fiap.agrosat.dto.AlertaRequest;
import br.com.fiap.agrosat.dto.AlertaResponse;
import br.com.fiap.agrosat.exception.ResourceNotFoundException;
import br.com.fiap.agrosat.mapper.AlertaMapper;
import br.com.fiap.agrosat.model.Alerta;
import br.com.fiap.agrosat.model.LeituraSatelital;
import br.com.fiap.agrosat.model.Propriedade;
import br.com.fiap.agrosat.repository.AlertaRepository;
import br.com.fiap.agrosat.repository.LeituraSatelitalRepository;
import br.com.fiap.agrosat.repository.PropriedadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AlertaService {

    private final AlertaRepository alertaRepository;
    private final PropriedadeRepository propriedadeRepository;
    private final LeituraSatelitalRepository leituraRepository;
    private final AlertaMapper alertaMapper;

    @Autowired
    public AlertaService(AlertaRepository alertaRepository,
                         PropriedadeRepository propriedadeRepository,
                         LeituraSatelitalRepository leituraRepository,
                         AlertaMapper alertaMapper) {
        this.alertaRepository = alertaRepository;
        this.propriedadeRepository = propriedadeRepository;
        this.leituraRepository = leituraRepository;
        this.alertaMapper = alertaMapper;
    }

    public Alerta criar(AlertaRequest request) {
        Propriedade propriedade = propriedadeRepository.findById(request.idPropriedade())
                .orElseThrow(() -> new ResourceNotFoundException("Propriedade não encontrada para o id: " + request.idPropriedade()));

        Alerta alerta = new Alerta();
        alerta.setPropriedade(propriedade);
        alerta.setTipo(request.tipo());
        alerta.setNivel(request.nivel());
        alerta.setDescricao(request.descricao());
        alerta.setDataAlerta(LocalDate.now());
        alerta.setResolvido(0);

        if (request.idLeitura() != null) {
            LeituraSatelital leitura = leituraRepository.findById(request.idLeitura())
                    .orElseThrow(() -> new ResourceNotFoundException("Leitura não encontrada para o id: " + request.idLeitura()));
            alerta.setLeitura(leitura);
        }

        return alertaRepository.save(alerta);
    }

    public AlertaResponse buscarPorId(Long id) {
        Alerta alerta = alertaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta não encontrado para o id: " + id));
        return alertaMapper.alertaToResponse(alerta);
    }

    public Page<AlertaLista> listarPorPropriedade(Long idPropriedade, Pageable pageable) {
        if (!propriedadeRepository.existsById(idPropriedade)) {
            throw new ResourceNotFoundException("Propriedade não encontrada para o id: " + idPropriedade);
        }
        return alertaRepository.findByPropriedadeId(idPropriedade, pageable)
                .map(alertaMapper::alertaToLista);
    }

    public Alerta resolverAlerta(Long id) {
        Alerta alerta = alertaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta não encontrado para o id: " + id));
        alerta.setResolvido(1);
        alerta.setDataResolucao(LocalDate.now());
        return alertaRepository.save(alerta);
    }

    public void deletar(Long id) {
        if (!alertaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Alerta não encontrado para o id: " + id);
        }
        alertaRepository.deleteById(id);
    }
}
