-- 1. Criar um usuário organizador
INSERT INTO usuarios (nome, email, senha, perfil)
VALUES ('Gabriel Mendes', 'gabriel@maratonas.com', '$2a$10$0Y7oV.wzcvyFa1y98KnvAO4caywHSUzJUgsBWod3E4Ghc8Uqx1aoe', 'ADMIN') -- senha: 12345
ON CONFLICT DO NOTHING;
-- Obs: senha deve ser hash real com BCrypt no backend

-- 2. Criar uma corrida vinculada ao organizador
INSERT INTO corridas (organizador_id, nome, data, local, distancia_km, regulamento)
VALUES (1, 'Maratona da Cidade', '2025-10-20', 'Parque Central', 10.0, 'Lorem ipsum dolor sit amet, consectetur adipiscing elit.')
ON CONFLICT DO NOTHING;

-- 3. Criar categorias para a corrida
INSERT INTO categorias (corrida_id, nome, idade_min, idade_max, sexo)
VALUES
(1, 'Masculino 18-29', 18, 29, 'M'),
(1, 'Feminino 18-29', 18, 29, 'F')
ON CONFLICT DO NOTHING;

-- 4. Criar inscrições (sem número de peito ainda)
INSERT INTO inscricoes (corrida_id, nome_corredor, documento, data_nascimento, sexo, email, telefone, status)
VALUES
(1, 'Carlos Silva', '123456789', '1995-06-15', 'M', 'carlos@email.com', '11999998888', 'PENDENTE'),
(1, 'Maria Souza', '987654321', '1997-03-20', 'F', 'maria@email.com', '11988887777', 'PENDENTE')
ON CONFLICT DO NOTHING;
