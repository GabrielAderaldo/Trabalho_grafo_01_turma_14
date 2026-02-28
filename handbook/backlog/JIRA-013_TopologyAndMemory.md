# JIRA-013: Classificação Topológica e Análise de Memória

## Descrição
Realizar a classificação qualitativa do grafo do Facebook e comparar o custo de memória das representações implementadas.

## Requisitos (Análise Teórica)
1.  **Classificação:** O grafo é Regular? É Completo? É Planar? (Justificar).
2.  **Identificação de Ciclos:** O grafo possui ciclos? (Baseado no teste de bipartição, se não for bipartido, tem ciclo ímpar).
3.  **Custo de Memória:** Calcular (em bytes) quanto cada estrutura ocupa para V=4039 e E=88234:
    - Lista de Adjacência: $8V + 16E$ bytes.
    - Matriz de Adjacência: $V^2$ bytes.
    - Matriz de Incidência: $V 	imes E$ bytes.

## Definição de Pronto (DoD)
- Documento de texto ou seção no relatório final com essas respostas.
