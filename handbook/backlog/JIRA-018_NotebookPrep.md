# JIRA-018: Integração com Notebook (Python Prep)

## Descrição
Implementar exportadores otimizados para garantir que o Notebook Jupyter consiga carregar os dados do grafo sem processamento adicional.

## Requisitos
1.  **CSV de Frequências:** Criar `exportDegreeFrequencies(String path)` gerando:
    `Grau,Quantidade`
2.  **Métricas JSON:** Criar `exportMetricsJSON(String path)` com os resultados de:
    - V, E, Densidade, Grau Médio, Clustering Médio.

## Diferencial Pedagógico
Demonstra que a equipe entende o fluxo de trabalho de uma pipeline de Ciência de Dados, onde o Java faz o processamento pesado e o Python faz a visualização estatística.

## Definição de Pronto (DoD)
- Arquivos `.csv` e `.json` gerados em `data/generated/` e legíveis pelo Pandas.
