# JIRA-010: Exportação de Matriz de Incidência (Esparsa)

## Descrição
Implementar a exportação da matriz de incidência de forma compacta (binário esparso).

## Especificação Técnica
- Arquivo: `facebook_union.inc.bin`
- Formato:
    - [4 bytes]: V (int)
    - [4 bytes]: E (int)
    - [Repetição E vezes]:
        - [4 bytes]: ID do vértice de origem (v)
        - [4 bytes]: ID do vértice de destino (w)

## Por que usar?
Economiza 350MB de memória e reduz o arquivo para apenas ~700KB, mantendo a fidelidade à relação Vértice-Aresta.

## Definição de Pronto (DoD)
- O código deve passar no teste `src/tests/IncidenceBinaryTest.java`.
