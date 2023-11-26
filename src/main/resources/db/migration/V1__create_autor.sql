CREATE TABLE autor (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR NOT NULL,
    email VARCHAR NOT NULL,
    descricao TEXT NOT NULL,
    instante DATE NOT NULL
);