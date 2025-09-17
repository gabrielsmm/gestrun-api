CREATE TABLE IF NOT EXISTS resultados (
    id BIGSERIAL PRIMARY KEY,
    inscricao_id BIGINT NOT NULL,
    tempo TIME NOT NULL, -- tempo final do corredor
    posicao_geral INT,
    posicao_categoria INT,
    CONSTRAINT fk_inscricao FOREIGN KEY (inscricao_id) REFERENCES inscricoes(id) ON DELETE CASCADE
);
