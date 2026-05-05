# Compte Rendu — Tutoring Manager

**Membres du groupe :** AbdelAziz Mseddi, Mohamed Amine Barkati, Adem Ayari, Houssem Eddine Graja

---

## 1. Description du sujet

**Tutoring Manager** est un serveur REST de gestion de cours particuliers. Il permet à chaque tuteur de gérer ses élèves, cours, inscriptions, séances et paiements via une API JSON sécurisée par JWT, consommée par deux interfaces graphiques (web et mobile).

---

## 2. Architecture en couches

| Couche | Rôle |
|---|---|
| **Security** | `JwtAuthFilter` valide le token Bearer et injecte le `userId` dans le contexte de sécurité |
| **Controller** | Reçoit la requête HTTP, lit le `userId` authentifié, délègue au service |
| **Service** | Logique métier — vérifie la propriété de la ressource (`403` si l'utilisateur ne correspond pas) |
| **Repository** | Accès aux données via Spring Data JPA |
| **Entity / H2** | Mapping objet-relationnel, base de données in-memory |
| **DTO** | Objets de transfert JSON (`Request` en entrée, `Response` en sortie) |
| **ExceptionHandler** | Traduit toute exception en réponse HTTP normalisée |

**Stack :** Java 21 · Spring Boot 3.3.5 · Spring Security 6 · JWT · Spring Data JPA · H2 · Maven · Swagger

**Modèle de données :**

```
user ──┬── student ──── enrollment ──── tutoring_session
       ├── tutoring_class ──┘                │
       └── payment ◄────────────────────────┘
```

---

## 3. Endpoints de l'API

Seuls `/auth/register` et `/auth/login` sont publics. Toutes les autres routes exigent `Authorization: Bearer <token>`. Le `userId` est toujours extrait du token côté serveur, jamais transmis par le client. La pagination est disponible via `?page=&size=`.

| Route | Méthodes disponibles | Note |
|---|---|---|
| `/auth/register` · `/auth/login` | POST | Retourne `{ token, userId }` |
| `/students` | GET · POST | Liste paginée |
| `/students/{id}` | GET · PUT · DELETE | |
| `/tutoring-classes` | GET · POST | Liste paginée |
| `/tutoring-classes/{id}` | GET · PUT · DELETE | |
| `/enrollments` | GET · POST | Liste paginée |
| `/enrollments/{id}` | GET · PUT · DELETE | |
| `/enrollments/{id}/status` | PATCH `?status=` | `active` · `paused` · `completed` |
| `/tutoring-sessions` | GET · POST | Liste paginée |
| `/tutoring-sessions/{id}` | GET · PUT · DELETE | |
| `/payments` | GET · POST | Liste paginée |
| `/payments/{id}` | GET · PUT · DELETE | |

**Codes HTTP :** `200/201` succès · `400` champ invalide · `401` token absent/expiré · `403` ressource d'un autre utilisateur · `404` introuvable · `409` conflit
