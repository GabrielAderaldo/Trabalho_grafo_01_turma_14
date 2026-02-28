# JIRA-014: Exportação de Dados Crus para Tratamento Externo

## Descrição
Implementar a exportação dos dados brutos de grau de cada vértice em formato CSV. Esta tarefa visa fornecer a base de dados para que a equipe realize o tratamento estatístico (frequências e gráficos) em ferramentas externas (Excel, Python, R).

## Requisitos
- Implementar o método `void exportRawVertexDegrees(String path)` no Wrapper.
- O arquivo CSV deve conter exatamente duas colunas: `Vertice` e `Grau`.
- Deve conter uma linha para cada um dos 4.039 vértices.

## Definição de Pronto (DoD)
- Arquivo `data/generated/raw_vertex_degrees.csv` gerado.
- O código deve passar no teste `src/tests/RawDegreeExportTest.java`.
- **Desafio da Equipe:** Usar este CSV para calcular a distribuição de frequências manualmente.
