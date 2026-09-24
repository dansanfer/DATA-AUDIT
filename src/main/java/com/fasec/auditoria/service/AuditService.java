package com.fasec.auditoria.service;

import com.fasec.auditoria.dto.AuditEventRequestDTO;
import com.fasec.auditoria.model.AuditEvent;
import com.fasec.auditoria.repository.AuditEventRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class AuditService {

    private final AuditEventRepository repository;

    public AuditService(AuditEventRepository repository) {
        this.repository = repository;
    }

    public AuditEvent registrarEvento(AuditEventRequestDTO dto) {
        AuditEvent evento = new AuditEvent(
            dto.origem(),
            dto.entidade(),
            dto.idEntidade(),
            dto.tipoOperacao(),
            dto.autor(),
            dto.dataHoraEvento(),
            dto.estadoAnterior(),
            dto.estadoAtual(),
            dto.metadados()
        );
        return repository.save(evento);
    }

    public List<AuditEvent> listarTodos() {
        return repository.findAll();
    }

    public List<AuditEvent> listarPorEntidade(String entidade, String idEntidade) {
        return repository.findByEntidadeAndIdEntidade(entidade, idEntidade);
    }
}