package com.fasec.auditoria.repository;

import com.fasec.auditoria.model.DataViolation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DataViolationRepository extends JpaRepository<DataViolation, Long> {
    List<DataViolation> findBySeveridade(String severidade);
}