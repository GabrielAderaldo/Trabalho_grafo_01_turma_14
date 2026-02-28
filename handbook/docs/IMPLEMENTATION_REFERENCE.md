# Lógica do Conversor de Dataset

Para o `EdgeListConverter`, a lógica deve garantir que o arquivo gerado tenha o cabeçalho V e E exigido pela biblioteca.

## Pseudo-código
```text
Ler todas as linhas do arquivo (v w)
Armazenar arestas em uma lista temporária
Identificar o maior ID encontrado (max_id)
Imprimir (max_id + 1)  <-- V
Imprimir (tamanho da lista) <-- E
Imprimir todas as arestas da lista
```
