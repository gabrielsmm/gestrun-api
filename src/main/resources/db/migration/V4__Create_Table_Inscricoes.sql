CREATE TYPE sexo_inscricao_enum AS ENUM ('M', 'F');
CREATE TYPE inscricao_status_enum AS ENUM ('PENDENTE', 'CONFIRMADA', 'CANCELADA');

CREATE TABLE inscricoes (
    id BIGSERIAL PRIMARY KEY,
    corrida_id BIGINT NOT NULL,
    nome_corredor VARCHAR(255) NOT NULL,
    documento VARCHAR(50),
    data_nascimento DATE NOT NULL,
    sexo sexo_inscricao_enum NOT NULL,
    email VARCHAR(255),
    telefone VARCHAR(50),
    status inscricao_status_enum NOT NULL DEFAULT 'PENDENTE',
    numero_peito INT,
    data_criacao TIMESTAMP NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_corrida FOREIGN KEY (corrida_id) REFERENCES corridas(id) ON DELETE CASCADE
);
