package com.fasec.auditoria.repository;

import com.fasec.auditoria.model.AuditEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AuditEventRepository extends JpaRepository<AuditEvent, Long> {
    List<AuditEvent> findByEntidadeAndIdEntidade(String entidade, String idEntidade);
    List<AuditEvent> findByAutor(String autor);
}