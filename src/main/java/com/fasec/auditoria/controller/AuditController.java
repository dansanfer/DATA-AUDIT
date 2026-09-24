package com.fasec.auditoria.controller;

import com.fasec.auditoria.dto.AuditEventRequestDTO;
import com.fasec.auditoria.model.AuditEvent;
import com.fasec.auditoria.service.AuditService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<AuditEvent>> listarTodos() {
        return ResponseEntity.ok(auditService.listarTodos());
    }

    @GetMapping("/{entidade}/{idEntidade}")
    public ResponseEntity<List<AuditEvent>> listarPorEntidade(
            @PathVariable String entidade, 
            @PathVariable String idEntidade) {
        return ResponseEntity.ok(auditService.listarPorEntidade(entidade, idEntidade));
    }
}