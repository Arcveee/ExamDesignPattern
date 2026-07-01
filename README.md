# ExamDesignPattern

Projet d'examen universitaire — Design Patterns appliqués à une architecture microservices Spring Boot.

## Modules

| Module | Port | Rôle |
|---|---|---|
| `badwallet-api` | 8080 | Gestion de portefeuilles électroniques (wallets, dépôts, retraits, transferts, paiement de factures, historique) |
| `payment-service` | 8081 | Service externe simulant les factures (ISM, WOYAFAL) consultées par badwallet-api |
| `shared-dto` | — | Types partagés (enums, DTOs communs) entre les deux services |

## Stack technique

- Java 17 — Spring Boot 3.2.x — Spring Data JPA — H2 (dev)
- Maven multi-modules — Bean Validation — MapStruct

## Design Patterns implémentés

- **Builder** — construction des entités `Wallet` et `Transaction`
- **Strategy + Factory** — moyens de dépôt (`CREDIT_CARD`, `WALLET_TARGET`)
- **Strategy** — calcul des frais de retrait
- **Template Method** — pipeline commun de traitement des transactions
- **Observer** — historisation automatique via `ApplicationEventPublisher`
- **Adapter / Proxy** — consommation de l'API `payment-service`

## Architecture

```
Controller → Service → Repository
             ↑
          DTO (entrée/sortie) — jamais d'entité JPA exposée directement
```

## Git Workflow

- `main` → code production stable, merge uniquement depuis `develop`, taggé à chaque livraison
- `develop` → branche d'intégration, reçoit les merges des branches `feature/*`
- `feature/*` → créées depuis `develop`, fusionnées dans `develop`, supprimées après fusion

## Lancer le projet

```bash
# Compiler tous les modules
mvn clean install

# Lancer badwallet-api
cd badwallet-api && mvn spring-boot:run

# Lancer payment-service
cd payment-service && mvn spring-boot:run
```
