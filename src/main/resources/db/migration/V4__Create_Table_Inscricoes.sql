DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'sexo_inscricao_enum') THEN
        CREATE TYPE sexo_inscricao_enum AS ENUM ('M', 'F');
    END IF;
END$$;

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'inscricao_status_enum') THEN
        CREATE TYPE inscricao_status_enum AS ENUM ('PENDENTE','CONFIRMADA','CANCELADA');
    END IF;
END$$;

CREATE TABLE IF NOT EXISTS inscricoes (
    id BIGSERIAL PRIMARY KEY,
    corrida_id BIGINT NOT NULL,
    nome_corredor VARCHAR(255) NOT NULL,
    documento VARCHAR(50),
    data_nascimento DATE NOT NULL,
    sexo sexo_inscricao_enum NOT NULL,
    email VARCHAR(255),
    telefone VARCHAR(50),
    status inscricao_status_enum NOT NULL DEFAULT 'PENDENTE',
    numero_peito INT, -- só definido na entrega do kit
    data_criacao TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_corrida FOREIGN KEY (corrida_id) REFERENCES corridas(id) ON DELETE CASCADE
);
