package com.example.ejb.remote;

import jakarta.ejb.Remote;
import java.math.BigDecimal;

@Remote
public interface BeneficioRemote {

    void transfer(Long fromId, Long toId, BigDecimal amount);
}
