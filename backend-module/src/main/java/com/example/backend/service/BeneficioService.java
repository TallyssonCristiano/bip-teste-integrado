package com.example.backend.service;

import com.example.backend.entity.BeneficioEntity;
import com.example.backend.repository.BeneficioRepository;
import com.example.ejb.remote.BeneficioRemote;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BeneficioService {

    private final BeneficioRepository repository;
    private final BeneficioRemote beneficioRemote;

    public BeneficioService(BeneficioRepository repository,
                            BeneficioRemote beneficioRemote) {
        this.repository = repository;
        this.beneficioRemote = beneficioRemote;
    }

    public List<BeneficioEntity> findAll() {
        return repository.findAll();
    }

    public BeneficioEntity buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Benefício não encontrado"));
    }

    public BeneficioEntity criar(BeneficioEntity beneficio) {
        return repository.save(beneficio);
    }

    public BeneficioEntity atualizar(Long id, BeneficioEntity beneficio) {
        BeneficioEntity existente = buscarPorId(id);

        existente.setNome(beneficio.getNome());
        existente.setDescricao(beneficio.getDescricao());
        existente.setValor(beneficio.getValor());
        existente.setAtivo(beneficio.getAtivo());

        return repository.save(existente);
    }

    public void remover(Long id) {
        repository.deleteById(id);
    }

    public void transferir(Long fromId, Long toId, BigDecimal amount) {
        beneficioRemote.transfer(fromId, toId, amount);
    }
}
