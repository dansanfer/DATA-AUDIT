-- Criação do schema isolado para o subsistema de auditoria
CREATE SCHEMA IF NOT EXISTS audit_schema;

-- Registo inicial de controlo de arranque
CREATE TABLE IF NOT EXISTS audit_schema.tb_sistema_info (
    id SERIAL PRIMARY KEY,
    nome_sistema VARCHAR(100) NOT NULL,
    versao VARCHAR(20) NOT NULL,
    data_instalacao TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO audit_schema.tb_sistema_info (nome_sistema, versao)
VALUES ('Subsistema de Auditoria de Dados', '1.0.0')
ON CONFLICT DO NOTHING;