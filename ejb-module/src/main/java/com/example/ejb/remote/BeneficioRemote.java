package com.example.ejb.remote;

import java.math.BigDecimal;

public interface BeneficioRemote {
    void transfer(Long fromId, Long toId, BigDecimal amount);
}
