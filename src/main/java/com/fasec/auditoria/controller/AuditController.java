package com.fasec.auditoria.controller;

import com.fasec.auditoria.dto.AuditEventRequestDTO;
import com.fasec.auditoria.model.AuditEvent;
import com.fasec.auditoria.service.AuditService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/audit/events")
public class AuditController {

    private final AuditService auditService;

    public AuditController(AuditService auditService) {
        this.auditService = auditService;
    }

    @PostMapping
    public ResponseEntity<AuditEvent> registrar(@RequestBody @Valid AuditEventRequestDTO dto) {
        AuditEvent salvo = auditService.registrarEvento(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    // Endpoint paginado padrão: devolve 10 registos por página, ordenados pelos mais recentes
    @GetMapping
    public ResponseEntity<Page<AuditEvent>> listarPaginado(
            @PageableDefault(page = 0, size = 10, sort = "dataCriacao", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(auditService.listarPaginado(pageable));
    }

    // Endpoint paginado por entidade auditada
    @GetMapping("/{entidade}/{idEntidade}")
    public ResponseEntity<Page<AuditEvent>> listarPorEntidadePaginado(
            @PathVariable String entidade,
            @PathVariable String idEntidade,
            @PageableDefault(page = 0, size = 10, sort = "dataCriacao", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(auditService.listarPorEntidadePaginado(entidade, idEntidade, pageable));
    }
}