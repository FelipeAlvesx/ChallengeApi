-- Atualiza todos os registros onde xp é NULL para 0
UPDATE users SET xp = 0 WHERE xp IS NULL;

-- Garante que a coluna não aceita NULL no futuro (opcional, mas recomendado)
ALTER TABLE users MODIFY COLUMN xp INT NOT NULL DEFAULT 0;

