# JIRA-009: Exportação de Matriz de Adjacência (Bitset)

## Descrição
Implementar a exportação da matriz de adjacência usando compressão de bits (1 bit por conexão).

## Especificação Técnica
- Arquivo: `facebook_union.mat.bin`
- Formato: 
    - [4 bytes]: V (int)
    - [Resto]: Array de bytes gerado pelo `java.util.BitSet`.

## Por que usar?
Reduz o espaço em disco de ~16MB (se fosse texto) para ~2MB (binário bit-a-bit).

## Definição de Pronto (DoD)
- O código deve passar no teste `src/tests/AdjMatrixBinaryTest.java`.
