CREATE SCHEMA IF NOT EXISTS audit_schema;

CREATE TABLE IF NOT EXISTS audit_schema.tb_sistema_info (
    id SERIAL PRIMARY KEY,
    nome_sistema VARCHAR(100) NOT NULL,
    versao VARCHAR(20) NOT NULL,
    data_instalacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela imutável de eventos de auditoria recebidos
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