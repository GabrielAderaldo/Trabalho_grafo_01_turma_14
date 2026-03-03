# Status do Projeto - Unidade I (Foco: Ciência de Dados)

A infraestrutura do **Motor de Dados (Java)** está concluída. O foco agora é o consumo desses artefatos no Google Colab para a caracterização científica.

## 🟢 Concluído (Infraestrutura do Motor)
| Componente | Descrição | Status |
| :--- | :--- | :--- |
| **Data Engine** | Motor Java centralizado (`Main.java`) | **100% OK** |
| **Binary Exports** | CSR, Bitset e EdgeList (Alta Performance) | **100% OK** |
| **Unified Tests** | Suite de testes robusta e unificada | **100% OK** |
| **Integration** | Guia do Google Colab e Documentação | **100% OK** |

## ⏳ Pendente - Responsabilidades de Equipe

### 👤 Arthur Alves (Métricas e Lei de Potência)
| Tarefa | Objetivo |
| :--- | :--- |
| **Notebook** | Consumir `facebook_union_metrics.json` no Colab. |
| **Análise** | Realizar o ajuste de **Lei de Potência** no Python. |
| **Relatório** | Analisar o usuário de maior grau e sua relevância. |

### 👤 Lucas Magalhães (Topologia e Visualização)
| Tarefa | Objetivo |
| :--- | :--- |
| **Notebook** | Visualização Log-Log da distribuição de graus. |
| **Relatório** | Provar se o grafo é Planar, Regular ou Completo. |

## 🚀 Entrega Obrigatória: Notebook (.ipynb)
O time deve colaborar no Google Colab, dando pull no motor, gerando os binários e realizando a análise estatística. Consulte o [`COLAB_GUIDE.md`](./COLAB_GUIDE.md).
