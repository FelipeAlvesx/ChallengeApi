# 💪 API de Desafios Diários

Uma API REST desenvolvida em **Java com Spring Boot** que gera **desafios diários personalizados** para os usuários, incentivando hábitos saudáveis, produtividade e bem-estar.

Cada dia, o usuário recebe um novo desafio — pode completá-lo, ganhar pontos de XP e acompanhar seu progresso ao longo do tempo.

---

## 🚀 Tecnologias utilizadas

- **Java 17+**
- **Spring Boot 3**
- **Spring Web**
- **Spring Data JPA (MySQL)**
- **Spring Security + JWT**
- **Lombok**
- **Swagger/OpenAPI** (documentação)
- **Maven**

---

## 🎯 Funcionalidades principais

- 📅 Geração automática de desafio diário  
- 🎲 Sorteio de desafios aleatórios
- 🧩 Categorias de desafios (Saúde, Estudo, Criatividade, etc.)  
- 🔐 Autenticação e cadastro de usuários
- 🏆 Sistema de XP e streak (dias consecutivos)  
- 📖 Histórico de desafios concluídos  


---

## 🗂️ Estrutura básica do projeto
- Autenticação:
    - Usuário faz login em `POST /auth/login` (ou cadastra em `POST /auth/register`).
    - Servidor retorna um token JWT que deve ser enviado no header `Authorization: Bearer <token>` em requests autenticados.

- Endpoint principal de desafios:
    - `GET /challenge` \- requer o token JWT no header `Authorization: Bearer <token>`.
    - Ao chamar, o usuário recebe o *desafio diário* associado à sua conta.
    - Cada desafio gerado fica ativo por 24 horas a partir do momento de sua geração (campo `expiresAt` na resposta).
    - Se o usuário solicitar `GET /challenge` dentro do período de 24 horas, o mesmo desafio ativo é retornado.
    - Somente após o término dessas 24 horas um novo desafio será gerado e retornado na próxima solicitação.

    - `POST /challenge/complete` \- endpoint usado para marcar como concluído o desafio diário atual.
        - Requer token JWT.
        - Marca o desafio ativo como concluído, concede o XP de conclusão ao usuário e atualiza o estado (streak/total XP).
        - Respostas esperadas:
            - `200 OK` \- conclusão bem-sucedida; retorna JSON com detalhes do XP concedido e novo total.
            - `400 Bad Request` \- nenhum desafio ativo, já concluído ou solicitação inválida.
            - `401 Unauthorized` \- token ausente/inválido.

- Comportamento esperado:
    - Proteção por Spring Security + JWT para todos endpoints que alteram ou consultam dados do usuário.
    - Lógica de verificação no serviço de desafios:
        1. Verificar se existe um desafio ativo (não expirado) para o usuário.
        2. Se existir, retornar esse desafio.
        3. Se não existir, gerar um novo desafio aleatório/por categoria, salvar com `expiresAt = now + 24h` e retornar.

- Exemplo de resposta (JSON):
```json
{
  "challengeId": 123,
  "title": "30 minutos de caminhada",
  "description": "Faça uma caminhada leve por 30 minutos.",
  "createdAt": "2026-01-20T09:00:00Z",
  "xp": 50,
  "category": "Saúde"
}
```

```
# login -> obter token
curl -X POST https://api.example.com/auth/login -H "Content-Type: application/json" -d '{"email":"user@example.com","password":"senha"}'

# obter desafio diário
curl -H "Authorization: Bearer <token>" https://api.example.com/challenge

# marcar desafio como concluído (concede XP)
curl -X POST -H "Authorization: Bearer <token>" https://api.example.com/challenge/complete
```