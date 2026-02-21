FROM eclipse-temurin:17-jdk

WORKDIR /app

RUN apt-get update \
    && apt-get install -y --no-install-recommends make \
    && rm -rf /var/lib/apt/lists/*

COPY . .

RUN test -d lib/algs4/src/main/java || \
    (echo "ERRO: submodule algs4 ausente. Rode 'git submodule update --init --recursive'." && exit 1)

CMD ["make", "help"]
