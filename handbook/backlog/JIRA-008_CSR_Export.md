# JIRA-008: Exportação em Formato CSR (Alta Performance)

## Descrição
Implementar a exportação do grafo para o formato binário Compressed Sparse Row (CSR). Este formato é otimizado para representação compacta e acesso sequencial rápido.

## Especificação Técnica
O arquivo `facebook_union.csr` deve conter:
1.  **Metadata (8 bytes):** V (int) e E (int).
2.  **Offsets Array:** V+1 inteiros (4 bytes cada).
    - `offsets[i]` armazena a posição inicial dos vizinhos do vértice `i` no array `edges`.
3.  **Edges Array:** 2 * E inteiros (4 bytes cada).
    - Todos os vizinhos concatenados.

## Requisitos de Implementação
- Criar o método `void exportCSR(String path)` na classe `FacebookGraph`.
- Utilizar `java.nio.ByteBuffer` ou `DataOutputStream` para garantir a ordem dos bytes.

## Definição de Pronto (DoD)
- O código deve passar no teste `src/tests/CSRTest.java`.
- O carregamento do arquivo gerado deve reconstruir um grafo idêntico ao original.
