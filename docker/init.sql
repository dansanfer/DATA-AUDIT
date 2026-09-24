-- Criação do schema isolado
CREATE SCHEMA IF NOT EXISTS audit_schema;

-- Informações de versão e sistema
CREATE TABLE IF NOT EXISTS audit_schema.tb_sistema_info (
    id SERIAL PRIMARY KEY,
    nome_sistema VARCHAR(100) NOT NULL,
    versao VARCHAR(20) NOT NULL,
    data_instalacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO audit_schema.tb_sistema_info (nome_sistema, versao)
VALUES ('Subsistema de Auditoria de Dados', '1.0.0')
ON CONFLICT DO NOTHING;

-- Tabela de eventos de auditoria
CREATE TABLE IF NOT EXISTS audit_schema.tb_audit_event (
    id BIGSERIAL PRIMARY KEY,
    origem VARCHAR(50) NOT NULL,
    entidade VARCHAR(50) NOT NULL,
    id_entidade VARCHAR(100) NOT NULL,
    tipo_operacao VARCHAR(50) NOT NULL,
    autor VARCHAR(150) NOT NULL,
    data_hora_evento TIMESTAMP NOT NULL,
    estado_anterior TEXT,
    estado_atual TEXT,
    metadados TEXT,
    hash_integridade VARCHAR(64) NOT NULL,
    data_criacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);

-- Tabela de anomalias/inconformidades encontradas
CREATE TABLE IF NOT EXISTS audit_schema.tb_data_violation (
    id BIGSERIAL PRIMARY KEY,
    codigo_regra VARCHAR(50) NOT NULL,
    severidade VARCHAR(20) NOT NULL,
    descricao TEXT NOT NULL,
    entidade_afetada VARCHAR(50) NOT NULL,
    id_entidade_afetada VARCHAR(100),
    data_identificacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    status_resolucao VARCHAR(30) DEFAULT 'PENDENTE' NOT NULL
);

-- ====================================================================
-- REGRA DE SEGURANÇA E COMPLIANCE: IMUTABILIDADE (APPEND-ONLY)
-- ====================================================================

-- 1. Função PL/pgSQL que bloqueia operações de alteração ou exclusão
CREATE OR REPLACE FUNCTION audit_schema.fn_bloquear_mutacao_auditoria()
RETURNS TRIGGER AS $$
BEGIN
    RAISE EXCEPTION 'VIOLAÇÃO DE AUDITORIA: Registros de logs são estritamente imutáveis (Append-Only). Operações de UPDATE ou DELETE são proibidas.';
END;
$$ LANGUAGE plpgsql;

-- 2. Gatilho (Trigger) disparado antes de qualquer tentativa de UPDATE ou DELETE
DROP TRIGGER IF EXISTS trg_audit_event_imutavel ON audit_schema.tb_audit_event;
CREATE TRIGGER trg_audit_event_imutavel
BEFORE UPDATE OR DELETE ON audit_schema.tb_audit_event
FOR EACH ROW EXECUTE FUNCTION audit_schema.fn_bloquear_mutacao_auditoria();