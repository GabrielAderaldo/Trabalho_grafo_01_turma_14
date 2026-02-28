# Status do Projeto - Unidade I

Este documento mapeia o progresso do trabalho e as responsabilidades de cada membro.

## ✅ Concluído
| ID | Tarefa | Responsável | Status |
| :--- | :--- | :--- | :--- |
| TASK-001 | Conversor de Dataset (EdgeListConverter) | Grupo | Finalizado |
| JIRA-004 | Mapeamento de Vizinhança | Grupo | Finalizado |
| JIRA-005 | Matriz de Adjacência | Grupo | Finalizado |
| JIRA-006 | Matriz de Incidência | Grupo | Finalizado |
| JIRA-007 | Visualização Textual (toString) | Grupo | Finalizado |
| JIRA-008 | Exportação CSR (Binário) | Grupo | Finalizado |
| JIRA-009 | Exportação Matriz Adjacência (Bitset) | Grupo | Finalizado |
| JIRA-010 | Exportação Matriz Incidência (Esparsa) | Grupo | Finalizado |
| JIRA-011 | Lista de Arestas (Edge List) | Grupo | Finalizado |
| JIRA-012 | Exportação Edge List (Binário) | Grupo | Finalizado |

## ⏳ Pendente (Aguardando Implementação)
| ID | Tarefa | Responsável | Arquivo | Teste de Validação |
| :--- | :--- | :--- | :--- | :--- |
| JIRA-001 | Estatísticas de Graus e Densidade | **Artur** | FacebookGraph.java | `tests.DegreeStatsTest` |
| JIRA-002 | Conectividade (DFS Manual) | **Gabriel** | FacebookGraph.java | `tests.ConnectivityTest` |
| JIRA-003 | Bipartição (Coloração) | **Gabriel** | FacebookGraph.java | `tests.BipartiteTest` |

## 🚀 Como Validar o Trabalho
Sempre que implementar uma função, rode o comando:
```bash
make test-all
```
O objetivo é que todos os testes apareçam como **[OK]**.
