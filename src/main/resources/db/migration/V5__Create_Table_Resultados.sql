CREATE TABLE IF NOT EXISTS resultados (
    id BIGSERIAL PRIMARY KEY,
    inscricao_id BIGINT NOT NULL UNIQUE, -- 1:1 com inscrição
    tempo TIME NOT NULL,
    posicao_geral INT,
    data_criacao TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_inscricao FOREIGN KEY (inscricao_id) REFERENCES inscricoes(id) ON DELETE CASCADE
);
