# TASK-002: Análise Estatística do Grafo (Unidade I)

## Descrição
Implementar a lógica de carregamento do grafo e cálculo das métricas fundamentais exigidas para o relatório da Unidade I, utilizando o dataset unificado do Facebook.

## Métricas Obrigatórias
1. **Ordem (V):** Número total de vértices.
2. **Tamanho (E):** Número total de arestas.
3. **Densidade (D):** Proporção de arestas existentes em relação ao máximo possível.
4. **Graus:**
   - Grau Médio.
   - Grau Máximo (e qual vértice o possui).
   - Grau Mínimo.
5. **Distribuição de Graus:** Frequência de cada grau no grafo.

## Requisitos Técnicos
- Utilizar `edu.princeton.cs.algs4.Graph` para representar o grafo.
- Utilizar `edu.princeton.cs.algs4.In` para ler o arquivo `data/generated/facebook_union.txt`.
- A saída deve ser clara e organizada no terminal.

## Critérios de Aceite
- O programa deve imprimir corretamente:
  - V = 4039
  - E = 88234
- O cálculo da densidade deve ser aproximadamente 0.0108.
- A distribuição de graus deve ser calculada sem erros de índice.
