package com.example.backend.controller;


import com.example.backend.service.BeneficioService;
import com.example.backend.entity.BeneficioEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/beneficios")
public class BeneficioController {

    private final BeneficioService service;

    public BeneficioController(BeneficioService service) {
        this.service = service;
    }

    @GetMapping({ "", "/" })
    public List<BeneficioEntity> listar() {
        return service.listar();
    }

    @GetMapping("/{id:\\d+}")
    public BeneficioEntity buscar(@PathVariable("id") Long id) {
        return service.buscar(id);
    }

    @PostMapping
    public BeneficioEntity criar(@RequestBody BeneficioEntity beneficio) {
        return service.criar(beneficio);
    }

    @PutMapping("/{id}")
    public BeneficioEntity atualizar(
            @PathVariable Long id,
            @RequestBody BeneficioEntity beneficio) {
        return service.atualizar(id, beneficio);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.remover(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/transfer")
    public ResponseEntity<Void> transferir(
            @RequestParam("origemId") Long origemId,
            @RequestParam("destinoId") Long destinoId,
            @RequestParam("valor") BigDecimal valor) {

        service.transferir(origemId, destinoId, valor);
        return ResponseEntity.noContent().build();
    }

}
