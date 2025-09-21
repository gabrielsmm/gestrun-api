CREATE TABLE IF NOT EXISTS corridas (
    id BIGSERIAL PRIMARY KEY,
    organizador_id BIGINT NOT NULL,
    nome VARCHAR(255) NOT NULL,
    data DATE NOT NULL,
    local VARCHAR(255) NOT NULL,
    distancia_km NUMERIC(5,2),
    regulamento TEXT,
    data_criacao TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_organizador FOREIGN KEY (organizador_id) REFERENCES usuarios(id) ON DELETE CASCADE
);