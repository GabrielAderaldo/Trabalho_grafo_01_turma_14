# JIRA-012: Exportação de Lista de Arestas (Binário de Largura Fixa)

## Descrição
Implementar a exportação da lista de arestas para um formato binário de largura fixa para permitir acesso aleatório ultra-rápido.

## Especificação Técnica
- Arquivo: `facebook_union.edges.bin`
- Estrutura:
    - [4 bytes]: V (int)
    - [4 bytes]: E (int)
    - [E x 8 bytes]: Pares de inteiros (v, w) representando cada aresta.

## Diferencial Técnico
Diferente do arquivo texto, este formato permite que o sistema encontre qualquer aresta `i` instantaneamente calculando o deslocamento (offset) em bytes: `8 + (i * 8)`.

## Definição de Pronto (DoD)
- O código deve passar no teste `src/tests/EdgeListBinaryTest.java`.
