package com.example.backend;

import com.example.backend.entity.BeneficioEntity;
import com.example.backend.service.BeneficioService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/beneficios")
public class BeneficioController {

    private final BeneficioService beneficioService;

    public BeneficioController(BeneficioService beneficioService) {
        this.beneficioService = beneficioService;
    }

    @GetMapping
    public List<BeneficioEntity> list() {
        return beneficioService.findAll();
    }
}
