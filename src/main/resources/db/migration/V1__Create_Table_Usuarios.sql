DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'perfil_usuario_enum') THEN
        CREATE TYPE perfil_usuario_enum AS ENUM ('ADMIN', 'ORGANIZADOR', 'CORREDOR');
    END IF;
END$$;

CREATE TABLE IF NOT EXISTS usuarios (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    senha VARCHAR(255) NOT NULL,
    perfil perfil_usuario_enum NOT NULL DEFAULT 'ORGANIZADOR',
    data_criacao TIMESTAMP DEFAULT NOW()
);