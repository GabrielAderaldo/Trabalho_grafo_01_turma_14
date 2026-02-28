# JIRA-001: Estatísticas de Graus (Manual)

## Descrição
Implementar manualmente o cálculo de graus (mínimo, máximo e médio) percorrendo a estrutura de adjacência do grafo, sem utilizar métodos auxiliares de bibliotecas externas para a contagem.

## Requisitos
- Implementar os métodos na classe `app.FacebookGraph`:
  - `maxDegree()`: Retorna o maior grau.
  - `minDegree()`: Retorna o menor grau.
  - `avgDegree()`: Retorna a média (sum / V).
  - `density()`: Retorna `2.0 * E / (V * (V-1))`.

## Definição de Pronto (DoD)
- O código deve passar no teste `src/tests/DegreeStatsTest.java`.
