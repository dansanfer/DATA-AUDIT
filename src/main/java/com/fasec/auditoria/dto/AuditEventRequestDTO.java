package com.fasec.auditoria.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;

public record AuditEventRequestDTO(
    @NotBlank(message = "Origem é obrigatória")
    String origem,

    @NotBlank(message = "Entidade é obrigatória")
    String entidade,

    @NotBlank(message = "ID da Entidade é obrigatório")
    String idEntidade,

    @NotBlank(message = "Tipo de Operação é obrigatório")
    String tipoOperacao,

    @NotBlank(message = "Autor é obrigatório")
    String autor,

    @NotNull(message = "Data e Hora do Evento são obrigatórias")
    LocalDateTime dataHoraEvento,

    String estadoAnterior,
    String estadoAtual,
    String metadados
) {}