package com.fasec.auditoria.service;

import com.fasec.auditoria.dto.AuditEventRequestDTO;
import com.fasec.auditoria.model.AuditEvent;
import com.fasec.auditoria.repository.AuditEventRepository;
import com.fasec.auditoria.util.HashUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditService {

    private final AuditEventRepository repository;

    public AuditService(AuditEventRepository repository) {
        this.repository = repository;
    }

    public AuditEvent registrarEvento(AuditEventRequestDTO dto) {
        String hash = HashUtil.gerarHashSha256(
            dto.origem(),
            dto.entidade(),
            dto.idEntidade(),
            dto.tipoOperacao(),
            dto.autor(),
            dto.dataHoraEvento().toString(),
            dto.estadoAtual()
        );

        AuditEvent evento = new AuditEvent(
            dto.origem(),
            dto.entidade(),
            dto.idEntidade(),
            dto.tipoOperacao(),
            dto.autor(),
            dto.dataHoraEvento(),
            dto.estadoAnterior(),
            dto.estadoAtual(),
            dto.metadados(),
            hash
        );
        return repository.save(evento);
    }

    // Consulta com paginação estruturada
    public Page<AuditEvent> listarPaginado(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Page<AuditEvent> listarPorEntidadePaginado(String entidade, String idEntidade, Pageable pageable) {
        return repository.findByEntidadeAndIdEntidade(entidade, idEntidade, pageable);
    }

    public List<AuditEvent> listarTodos() {
        return repository.findAll();
    }

    public List<AuditEvent> listarPorEntidade(String entidade, String idEntidade) {
        return repository.findByEntidadeAndIdEntidade(entidade, idEntidade);
    }
}