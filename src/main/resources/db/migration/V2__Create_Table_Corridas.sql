CREATE TABLE IF NOT EXISTS corridas (
    id BIGSERIAL PRIMARY KEY,
    organizador_id BIGINT NOT NULL,
    nome VARCHAR(255) NOT NULL,
    data DATE NOT NULL,
    local VARCHAR(255) NOT NULL,
    distancia_km NUMERIC(5,2),
    regulamento TEXT,
    valor_inscricao NUMERIC(10,2) DEFAULT 0 CHECK (valor_inscricao >= 0),
    inscricoes_abertura TIMESTAMP NOT NULL,
    inscricoes_encerramento TIMESTAMP NOT NULL,
    data_criacao TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_organizador FOREIGN KEY (organizador_id)
        REFERENCES usuarios(id)
        ON DELETE CASCADE,
    CONSTRAINT chk_periodo_inscricao CHECK (inscricoes_encerramento > inscricoes_abertura)
);