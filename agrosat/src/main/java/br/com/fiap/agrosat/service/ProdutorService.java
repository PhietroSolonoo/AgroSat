package br.com.fiap.agrosat.service;

import br.com.fiap.agrosat.dto.ProdutorLista;
import br.com.fiap.agrosat.dto.ProdutorRequest;
import br.com.fiap.agrosat.dto.ProdutorResponse;
import br.com.fiap.agrosat.exception.ResourceNotFoundException;
import br.com.fiap.agrosat.mapper.ProdutorMapper;
import br.com.fiap.agrosat.model.Endereco;
import br.com.fiap.agrosat.model.PessoaFisica;
import br.com.fiap.agrosat.model.Produtor;
import br.com.fiap.agrosat.repository.ProdutorRepository;
import br.com.fiap.agrosat.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProdutorService {

    private final ProdutorRepository produtorRepository;
    private final UsuarioRepository usuarioRepository;
    private final ProdutorMapper produtorMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ProdutorService(ProdutorRepository produtorRepository,
                           UsuarioRepository usuarioRepository,
                           ProdutorMapper produtorMapper,
                           PasswordEncoder passwordEncoder) {
        this.produtorRepository = produtorRepository;
        this.usuarioRepository = usuarioRepository;
        this.produtorMapper = produtorMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @CacheEvict(value = "produtores", allEntries = true)
    public Produtor criar(ProdutorRequest request) {
        if (produtorRepository.existsByCpf(request.cpf())) {
            throw new IllegalArgumentException("Já existe um produtor com este CPF");
        }
        if (usuarioRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Este email já está cadastrado");
        }

        PessoaFisica usuario = new PessoaFisica(
                request.nome(),
                request.email(),
                passwordEncoder.encode(request.senha()),
                request.telefone()
        );
        usuarioRepository.save(usuario);

        Produtor produtor = new Produtor();
        produtor.setUsuario(usuario);
        produtor.setCpf(request.cpf());
        produtor.setDataNascimento(request.dataNascimento());
        produtor.setEndereco(new Endereco(
                request.logradouro(),
                request.numero(),
                request.bairro(),
                request.cidade(),
                request.estado(),
                request.cep()
        ));

        return produtorRepository.save(produtor);
    }

    @Cacheable("produtores")
    public ProdutorResponse buscarPorId(Long id) {
        Produtor produtor = produtorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produtor não encontrado para o id: " + id));
        return produtorMapper.produtorToResponse(produtor);
    }

    public Page<ProdutorLista> listar(Pageable pageable) {
        return produtorRepository.findAll(pageable).map(produtorMapper::produtorToLista);
    }

    @Transactional
    @CacheEvict(value = "produtores", allEntries = true)
    public Produtor atualizar(Long id, ProdutorRequest request) {
        Produtor produtor = produtorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produtor não encontrado para o id: " + id));

        produtor.getUsuario().setNome(request.nome());
        produtor.getUsuario().setEmail(request.email());
        produtor.getUsuario().setSenha(passwordEncoder.encode(request.senha()));
        produtor.getUsuario().setTelefone(request.telefone());

        produtor.setCpf(request.cpf());
        produtor.setDataNascimento(request.dataNascimento());
        produtor.setEndereco(new Endereco(
                request.logradouro(),
                request.numero(),
                request.bairro(),
                request.cidade(),
                request.estado(),
                request.cep()
        ));

        return produtorRepository.save(produtor);
    }

    @Transactional
    @CacheEvict(value = "produtores", allEntries = true)
    public void deletar(Long id) {
        if (!produtorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Produtor não encontrado para o id: " + id);
        }
        produtorRepository.deleteById(id);
    }
}