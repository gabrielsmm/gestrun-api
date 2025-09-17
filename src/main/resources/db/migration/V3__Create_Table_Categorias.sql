DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'sexo_categoria_enum') THEN
        CREATE TYPE sexo_categoria_enum AS ENUM ('M', 'F', 'A');
    END IF;
END$$;

CREATE TABLE IF NOT EXISTS categorias (
    id BIGSERIAL PRIMARY KEY,
    corrida_id BIGINT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    idade_min INT,
    idade_max INT,
    sexo sexo_categoria_enum NOT NULL DEFAULT 'A', -- M=masc, F=fem, A=ambos
    CONSTRAINT fk_corrida FOREIGN KEY (corrida_id) REFERENCES corridas(id) ON DELETE CASCADE
);
