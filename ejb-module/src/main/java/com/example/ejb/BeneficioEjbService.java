package com.example.ejb;

import com.example.ejb.entity.Beneficio;
import com.example.ejb.remote.BeneficioRemote;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;

@Stateless
public class BeneficioEjbService implements BeneficioRemote {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public void transfer(Long fromId, Long toId, BigDecimal amount) {
        if (fromId == null || toId == null) {
            throw new IllegalArgumentException("IDs de origem e destino devem ser informados");
        }

        if (fromId.equals(toId)) {
            throw new IllegalArgumentException("Transferência para o mesmo benefício não é permitida");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor da transferência deve ser maior que zero");
        }

        Beneficio from = em.find(Beneficio.class, fromId, LockModeType.OPTIMISTIC);
        Beneficio to   = em.find(Beneficio.class, toId, LockModeType.OPTIMISTIC);

        if (from == null || to == null) {
            throw new IllegalArgumentException("Benefício de origem ou destino não encontrado");
        }

        if (from.getValor().compareTo(amount) < 0) {
            throw new IllegalStateException("Saldo insuficiente para transferência");
        }

        from.setValor(from.getValor().subtract(amount));
        to.setValor(to.getValor().add(amount));

        em.merge(from);
        em.merge(to);
    }
}
