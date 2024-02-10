CREATE TABLE livro (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR NOT NULL,
    resumo TEXT NOT NULL,
    sumario TEXT,
    preco DECIMAL(10, 2) NOT NULL,
    numero_paginas INT NOT NULL,
    isbn VARCHAR(20) NOT NULL,
    data_publicacao DATE NOT NULL,
    autor_id BIGINT NOT NULL,
    categoria_id BIGINT NOT NULL,
    FOREIGN KEY (autor_id) REFERENCES autor(id),
    FOREIGN KEY (categoria_id) REFERENCES categoria(id)
);