import csv
from collections import Counter
from pathlib import Path

import numpy as np
import matplotlib.pyplot as plt


# Caminho base do projeto (ajuste se necessário)
BASE = Path(__file__).resolve().parent
CSV_PATH = BASE / "data" / "generated" / "sheets" / "facebook_vertex_degrees.csv"


def load_degrees(path: Path) -> list[int]:
    degrees: list[int] = []
    with path.open(encoding="utf-8") as f:
        reader = csv.reader(f)
        next(reader)  # pula cabeçalho "Vertice,Grau"
        for _, deg in reader:
            degrees.append(int(deg))
    return degrees


def compute_degree_dist(degrees: list[int]):
    counts = Counter(degrees)
    ks = np.array(sorted(counts.keys()))
    pk = np.array([counts[k] for k in ks], dtype=float)
    pk /= pk.sum()
    return ks, pk


def plot_loglog(ks: np.ndarray, pk: np.ndarray):
    plt.figure(figsize=(6, 4))
    plt.loglog(ks, pk, marker="o", linestyle="none", label="dados")
    plt.xlabel("grau k")
    plt.ylabel("P(k)")
    plt.title("Distribuição de graus (log–log)")
    plt.tight_layout()
    plt.show()


def fit_power_law(ks: np.ndarray, pk: np.ndarray, k_min: int = 5):
    mask = ks >= k_min
    ks_tail = ks[mask]
    pk_tail = pk[mask]

    log_k = np.log10(ks_tail)
    log_pk = np.log10(pk_tail)

    b, a = np.polyfit(log_k, log_pk, 1)
    gamma = -b
    return a, b, gamma, ks_tail, 10 ** (a + b * log_k)


def plot_loglog_with_fit(ks: np.ndarray, pk: np.ndarray, ks_tail: np.ndarray, pk_fit: np.ndarray):
    plt.figure(figsize=(6, 4))
    plt.loglog(ks, pk, marker="o", linestyle="none", label="dados")
    plt.loglog(ks_tail, pk_fit, linestyle="-", color="red", label="ajuste power law")
    plt.xlabel("grau k")
    plt.ylabel("P(k)")
    plt.title("Distribuição de graus (log–log) + ajuste power law")
    plt.legend()
    plt.tight_layout()
    plt.show()


def main():
    if not CSV_PATH.exists():
        raise SystemExit(f"Arquivo de graus nao encontrado: {CSV_PATH}")

    degrees = load_degrees(CSV_PATH)
    ks, pk = compute_degree_dist(degrees)

    # Gráfico log–log simples
    plot_loglog(ks, pk)

    # Ajuste da power law na cauda
    k_min = 5
    a, b, gamma, ks_tail, pk_fit = fit_power_law(ks, pk, k_min=k_min)
    print(f"k_min = {k_min}")
    print(f"ajuste linear (log10 P(k) = a + b log10 k): a = {a:.4f}, b = {b:.4f}")
    print(f"expoente estimado da power law (gamma) ~= {gamma:.4f}")

    # Gráfico com reta ajustada
    plot_loglog_with_fit(ks, pk, ks_tail, pk_fit)


if __name__ == "__main__":
    main()

