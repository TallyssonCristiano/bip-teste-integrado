package com.example.ejb.service;

import com.example.ejb.entity.Beneficio;
import com.example.ejb.remote.BeneficioRemote;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class BeneficioEjbService implements BeneficioRemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void transfer(Long fromId, Long toId, BigDecimal amount) {

        if (fromId == null || toId == null) {
            throw new IllegalArgumentException("IDs devem ser informados");
        }

        if (fromId.equals(toId)) {
            throw new IllegalArgumentException("Transferência para o mesmo benefício não é permitida");
        }

        if (amount == null || amount.signum() <= 0) {
            throw new IllegalArgumentException("Valor inválido");
        }

        Beneficio origem = em.find(Beneficio.class, fromId, LockModeType.OPTIMISTIC);
        Beneficio destino = em.find(Beneficio.class, toId, LockModeType.OPTIMISTIC);

        if (origem == null || destino == null) {
            throw new IllegalArgumentException("Benefício não encontrado");
        }

        if (origem.getValor().compareTo(amount) < 0) {
            throw new IllegalStateException("Saldo insuficiente");
        }

        origem.setValor(origem.getValor().subtract(amount));
        destino.setValor(destino.getValor().add(amount));

        em.merge(origem);
        em.merge(destino);
    }
}
