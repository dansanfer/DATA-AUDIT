# Subsistema de Auditoria de Dados (Data Audit Service)
## Documento de Requisitos e Especificação Técnica (ATV-001)

Este documento estabelece as diretrizes arquiteturais, funcionais e os requisitos técnicos do subsistema de auditoria de dados, desenvolvido no âmbito do Estágio Supervisionado Obrigatório (Modalidade: Projeto + Produto Computacional - FASEC).

---

### 1. Visão Geral e Problema Atendido
Sistemas operacionais transacionais, como o STI (Sistema de Tickets Interno), processam fluxos críticos de chamados, suporte e demandas departamentais. Contudo, bases de dados relacionais sem uma governança ativa sofrem com:
- Ausência de histórico auditável e imutável de alterações de status e permissões;
- Registros inconsistentes (campos obrigatórios nulos, violação de integridade referencial);
- Dificuldade em apurar violações de prazos de atendimento (SLA) ou reatribuições indevidas de tarefas;
- Dependência de auditorias manuais e lentas por scripts SQL pontuais.

O **Subsistema de Auditoria de Dados** atua como uma camada desacoplada responsável por monitorar, registrar e relatar desvios de integridade e inconformidades de forma automatizada.

---

### 2. Requisitos Funcionais (RF)

| Identificador | Nome | Descrição |
| :--- | :--- | :--- |
| **RF-01** | Ingestão de Eventos via API | O sistema deve disponibilizar um endpoint REST (`POST /api/v1/audit/events`) para receber e persistir eventos de mutação oriundos do sistema principal (STI). |
| **RF-02** | Trilha Imutável (Append-Only) | Os registros de eventos de auditoria devem ser estritamente de inserção e leitura; operações de `UPDATE` e `DELETE` são terminantemente bloqueadas na base de logs. |
| **RF-03** | Motor de Varredura Periódica | O sistema deve conter rotinas agendadas (schedulers) para realizar checagens estruturais e de consistência no banco auditado em intervalos definidos. |
| **RF-04** | Regras de Detecção de Anomalias | O motor deve acusar e categorizar as seguintes violações:<br>- **R1:** Chamado com status 'Concluído'/'Fechado' sem técnico responsável associado;<br>- **R2:** Chamado fechado sem parecer de resolução;<br>- **R3:** Data de conclusão cronologicamente anterior à data de abertura;<br>- **R4:** Registros órfãos ou campos nulos críticos em tabelas operacionais. |
| **RF-05** | Painel Analítico e Consultas | O subsistema deve expor endpoints de consulta com filtros por período, severidade (`INFO`, `WARN`, `CRITICAL`), autor da ação e identificador da entidade auditada. |

---

### 3. Requisitos Não Funcionais (RNF)

| Identificador | Categoria | Descrição Técnica |
| :--- | :--- | :--- |
| **RNF-01** | Linguagem e Framework | Back-end estruturado em **Java 21** utilizando **Spring Boot 3.x**. |
| **RNF-02** | Persistência Relacional | Armazenamento de dados transacionais e logs estruturados em banco **PostgreSQL 16**. |
| **RNF-03** | Conteinerização | Aplicação e dependências orquestradas integralmente via **Docker** e **Docker Compose**. |
| **RNF-04** | Desacoplamento Arquitetural | O subsistema opera de forma independente, consumindo eventos via JSON e não interferindo na disponibilidade do STI caso fique offline. |
| **RNF-05** | Desempenho | Tempo de resposta para ingestão de eventos síncronos na API inferior a 200 milissegundos. |

---

### 4. Contrato de Comunicação (Payload de Ingestão do STI)

Quando o STI realiza uma operação de mutação crítica em um chamado, envia o seguinte payload JSON para a auditoria:

```json
{
  "origem": "STI-TICKETS",
  "entidade": "TICKET",
  "idEntidade": "1042",
  "tipoOperacao": "UPDATE_STATUS",
  "autor": "tecnico.suporte@empresa.com",
  "dataHora": "2026-09-23T09:30:00Z",
  "estadoAnterior": {
    "status": "EM_ATENDIMENTO",
    "tecnico": "tecnico.suporte",
    "setor": "N1"
  },
  "estadoAtual": {
    "status": "CONCLUIDO",
    "tecnico": "tecnico.suporte",
    "setor": "N1"
  },
  "metadados": {
    "ipOrigem": "192.168.10.55",
    "motivoAlteracao": "Chamado resolvido pelo suporte local"
  }
}