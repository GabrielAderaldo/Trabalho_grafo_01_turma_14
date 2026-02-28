# JIRA-016: Coeficiente de Clustering Médio

## Descrição
Implementar o cálculo do coeficiente de clustering para o grafo. Esta métrica indica quão "agrupados" os nós estão (se os amigos de um usuário também são amigos entre si).

## Requisitos
- Para cada vértice `v`, calcular: $C_v = \frac{2 	imes 	ext{triângulos conectando v}}{	ext{grau}(v) 	imes (	ext{grau}(v) - 1)}$.
- Calcular a média de todos os $C_v$.

## Definição de Pronto (DoD)
- Método `double averageClusteringCoefficient()` implementado no Wrapper.
- Passar no teste `tests.ClusteringTest`.
