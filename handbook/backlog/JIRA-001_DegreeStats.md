# JIRA-001: Estatísticas de Graus (Manual)

## Descrição
Implementar manualmente o cálculo de graus (mínimo, máximo e médio) percorrendo a estrutura de adjacência do grafo, sem utilizar métodos auxiliares de bibliotecas externas para a contagem.

## Requisitos
- Implementar os métodos na classe `app.FacebookGraph`:
  - `maxDegree()`, `minDegree()`, `avgDegree()`.
- **🚨 RESTRIÇÃO CRÍTICA:** É proibido o uso de `G.degree(v)`. O grau deve ser calculado iterando manualmente sobre `G.adj(v)` para satisfazer o requisito pedagógico de "implementação do zero".

## Definição de Pronto (DoD)
- Passar no teste `tests.DegreeStatsTest`.
