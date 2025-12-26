package com.example.backend.service;

import com.example.backend.entity.BeneficioEntity;
import com.example.backend.repository.BeneficioRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class BeneficioService {

    private final BeneficioRepository repository;

    public BeneficioService(BeneficioRepository repository) {
        this.repository = repository;
    }

    public List<BeneficioEntity> listar() {
        return repository.findAll();
    }

    public BeneficioEntity buscar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Benefício não encontrado"));
    }

    public BeneficioEntity criar(BeneficioEntity beneficio) {
        return repository.save(beneficio);
    }

    public BeneficioEntity atualizar(Long id, BeneficioEntity dados) {
        BeneficioEntity existente = buscar(id);
        existente.setNome(dados.getNome());
        existente.setDescricao(dados.getDescricao());
        existente.setValor(dados.getValor());
        existente.setAtivo(dados.getAtivo());
        return repository.save(existente);
    }

    public void remover(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void transferir(Long origemId, Long destinoId, BigDecimal valor) {

        if (origemId == null || destinoId == null || valor == null) {
            throw new IllegalArgumentException("Parâmetros obrigatórios não informados");
        }

        if (origemId.equals(destinoId)) {
            throw new IllegalArgumentException("Origem e destino não podem ser o mesmo benefício");
        }

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor da transferência deve ser positivo");
        }

        BeneficioEntity origem = buscar(origemId);
        BeneficioEntity destino = buscar(destinoId);

        if (origem.getValor().compareTo(valor) < 0) {
            throw new IllegalStateException("Saldo insuficiente no benefício de origem");
        }

        origem.setValor(origem.getValor().subtract(valor));
        destino.setValor(destino.getValor().add(valor));

        repository.save(origem);
        repository.save(destino);
    }

}
