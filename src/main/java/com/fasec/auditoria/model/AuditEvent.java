package com.fasec.auditoria.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "tb_audit_event", schema = "audit_schema")
public class AuditEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String origem;

    @Column(nullable = false, length = 50)
    private String entidade;

    @Column(name = "id_entidade", nullable = false, length = 100)
    private String idEntidade;

    @Column(name = "tipo_operacao", nullable = false, length = 50)
    private String tipoOperacao;

    @Column(nullable = false, length = 150)
    private String autor;

    @Column(name = "data_hora_evento", nullable = false)
    private LocalDateTime dataHoraEvento;

    @Column(name = "estado_anterior", columnDefinition = "TEXT")
    private String estadoAnterior;

    @Column(name = "estado_atual", columnDefinition = "TEXT")
    private String estadoAtual;

    @Column(columnDefinition = "TEXT")
    private String metadados;

    @Column(name = "data_criacao", nullable = false, updatable = false)
    private LocalDateTime dataCriacao = LocalDateTime.now();

    public AuditEvent() {}

    public AuditEvent(String origem, String entidade, String idEntidade, String tipoOperacao, 
                      String autor, LocalDateTime dataHoraEvento, String estadoAnterior, 
                      String estadoAtual, String metadados) {
        this.origem = origem;
        this.entidade = entidade;
        this.idEntidade = idEntidade;
        this.tipoOperacao = tipoOperacao;
        this.autor = autor;
        this.dataHoraEvento = dataHoraEvento;
        this.estadoAnterior = estadoAnterior;
        this.estadoAtual = estadoAtual;
        this.metadados = metadados;
        this.dataCriacao = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getOrigem() { return origem; }
    public String getEntidade() { return entidade; }
    public String getIdEntidade() { return idEntidade; }
    public String getTipoOperacao() { return tipoOperacao; }
    public String getAutor() { return autor; }
    public LocalDateTime getDataHoraEvento() { return dataHoraEvento; }
    public String getEstadoAnterior() { return estadoAnterior; }
    public String getEstadoAtual() { return estadoAtual; }
    public String getMetadados() { return metadados; }
    public LocalDateTime getDataCriacao() { return dataCriacao; }
}