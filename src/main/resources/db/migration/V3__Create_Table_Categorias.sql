CREATE TYPE sexo_categoria_enum AS ENUM ('M', 'F', 'A');

CREATE TABLE categorias (
    id BIGSERIAL PRIMARY KEY,
    corrida_id BIGINT NOT NULL,
    nome VARCHAR(100) NOT NULL,
    idade_min INT,
    idade_max INT,
    sexo sexo_categoria_enum NOT NULL DEFAULT 'A',
    CONSTRAINT fk_corrida FOREIGN KEY (corrida_id) REFERENCES corridas(id) ON DELETE CASCADE
);
