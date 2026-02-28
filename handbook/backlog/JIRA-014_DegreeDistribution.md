# JIRA-014: Exportação para Gráficos (Distribuição de Graus)

## Descrição
Gerar um arquivo CSV com a frequência de cada grau para que a equipe possa produzir o gráfico de distribuição de graus no Excel/Python.

## Requisitos
- Implementar o método `void exportDegreeDistribution(String path)` no Wrapper.
- O arquivo deve ter duas colunas: `Grau` e `Frequencia`.

## Definição de Pronto (DoD)
- Arquivo `data/generated/degree_distribution.csv` gerado com sucesso.
- O código deve passar no teste `src/tests/DegreeDistributionTest.java`.
