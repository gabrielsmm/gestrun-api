CREATE TYPE perfil_usuario_enum AS ENUM ('ADMIN', 'ORGANIZADOR');

CREATE TABLE usuarios (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    perfil perfil_usuario_enum NOT NULL DEFAULT 'ORGANIZADOR',
    data_criacao TIMESTAMP NOT NULL DEFAULT NOW()
);
