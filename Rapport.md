# Rapport de Projet - ASTA - Bryan Bohec, Armando Lopes de Sousa, Benjamin Thibout (ING2 APP LSI2)

## Application de Suivi de Tutorat d'Apprentis

---

## 3.1. Identifiants de Test

Pour tester l'application, utilisez les identifiants suivants :

- **Email** : `tuteur1@asta.fr`
- **Mot de passe** : `root`

---

## 3.2. Informations sur l'Outillage

### 3.2.1. IDE Utilisé

- **IDE** : Selon les membres de l'équipe, Intellij ou VSCode (avec extensions Java)

### 3.2.2. Système de Gestion de Base de Données

- **SGBD** : PostgreSQL 17.5
- **Hébergement** : Neon (Service de bases de données PostgreSQL en ligne)

---

## 3.3. Instructions pour Lancer et Tester l'Application

### Prérequis

1. **Java 17**
2. **Maven**

### Lancement de l'application

- Accessible depuis l'url déployée : https://asta-production.up.railway.app/

- Lancer le projet en local, il y a normalement pas de configuration à effectuer (remplacer le application.properties si vous voulez utiliser une BD locale)

---

## 3.4. Réponses aux Questions

### a) Sur quel aspect de votre travail souhaitez-vous attirer l'attention du correcteur ?

Nous aimerions mettre en avant les points suivants :

1. **- Validation des données (inputs)** :

   - Géré avec Bean Validation dans les DTO qui nous permet de fournir des messages d'erreur explicite directement dans les champs grâce à BindingResult

2. **Gestion d'erreurs** :

   - Handler global d'exceptions (`@ControllerAdvice`) pour afficher une page d'erreur spécifique lorsque l'on essaie d'accéder à une ressource qui n'éxiste pas via l'URL. Cela nous permets de les gérer a un endroit unique et non dans les controleurs.
   - Exceptions métier personnalisées (ApprentiDejaExistantException, RessourceIntrouvableException, etc.) gérées dans les services et controleurs directement avec l'utilisation de FlashAttribute pour les messages d'erreurs/succès.

3. **Sécurité** :

   - Authentification avec Spring Security
   - Hashage des mots de passe avec BCrypt
   - Protection des routes nécessitant une authentification, et redirection si nécessaire

4. **Gestion avancée des relations JPA** :
   - Relations bidirectionnelles (OneToMany, ManyToOne, OneToOne)
   - Gestion du cycle de vie des entités avec cascade et orphanRemoval

### b) Quelle est la plus grande difficulté rencontrée ? Comment l'avez-vous gérée/solutionnée/contournée ?

**Difficulté principale** :

La partie qui nous a donné du mal est le choix entre des controleurs REST ou des controleurs thymeleaf. Nous avons trouvé le sujet assez incohérent, car il requiert thymeleaf (donc un moteur de template géré côté serveur), mais à la fois une documentation Swagger, ce qui nous a paru comme une incitation implicite de créer des endpoints REST (donc retournant du JSON). Nous pensons que ce choix de consignes était probablement voulu pour nous pousser à réfléchir ce sujet.

Nous n'avons pas exploré la possibilité de créer des endpoints REST, puis de les appeler dans des controleurs thymeleaf car cela ne nous a pas paru comme une bonne pratique : Pourquoi créer une couche en plus et appeler des controlleurs REST alors que nous pouvons gérer les données sans faire d'appels HTTP ? Cette méthode aurait pu être utile si nous avions un frontend different de thymeleaf (ou avons voulu faire le bonus avec un framework frontend).

Des endpoints REST nous auraient aussi permis d'utiliser la documentation automatique de Swagger. Dans notre cas, elle documente manuellement les endpoints pour thymeleaf (nous nous posons des questions quant à la pertinence de celle-ci).

Avec ces endpoints thymeleaf, nous avons géré les appels directement dans les formulaires, avec la méthode vu en cours pour gérer les PATCH/DELETE sur des forms HTML qui ne les supporte pas par défaut. Pour les messages d'erreurs, nous n'utilisons donc pas des status code HTTP, mais gérons les erreurs dans les models à l'aide de FlashAttribute.

### c) Quelle a été la contribution de chaque membre de l'équipe ?

- Contribution faite à 3 :
  - Création de la structure de données (les modèles)
- Contributions de Bryan :
  - Participation au front (nav, page de liste, page de création, page de modification)
  - Participation à la création des services (Controlleurs/Services/Repository) + les routeurs thymeleaf
  - Gestion des exceptions avec ControllerAdvice/GlobalExceptionHandler et création d'exceptions custom
  - Gestion de l'année académique automatique
- Contributions d'Armando :
  - Participation au front (page de liste, modification, historique, login)
  - Participation à la création des services (Controlleurs/Services/Repository) + les routeurs thymeleaf
  - Création de la fonctionnalité de recherche
  - Création de la doc Swagger
- Contributions de Benjamin :
    - Création de la page login
    - Gestion globale de l'authentification
    - Refactoring intégral du projet
    - cleaning du code

### d) Si vous deviez retenir trois points de ce cours en général et de ce projet en particulier, quels seraient ces trois points ?

#### Point 1 : **L'importance de l'architecture en couches et de la séparation des responsabilités**

Le découpage Controller → Service → Repository n'est pas qu'une convention arbitraire, ça devient un ensemble de règles qui permettent de :

- Faciliter la maintenance et l'évolution du code
- Réutiliser la logique métier
- Rendre le code plus lisible et compréhensible

#### Point 2 : **La gestion des relations JPA**

Les relations bidirectionnelles offrent une grande flexibilité, mais nécessitent :

- Une attention particulière aux configurations (cascade, orphanRemoval, fetch)
- Une gestion des deux côtés de la relation lors des modifications/suppressions

#### Point 3 : **La gestion des exceptions**

Nous avons trouvé qu'en Spring Boot, la gestion des exceptions (customs et globale) est particulièrement efficace et simple d'utilisation en termes de code. Le document fourni (Clean Code - Gestion des exceptions) est très clair et a permi de bien nous aiguiller sur les bonnes pratiques.

### e) Les fonctionnalités que vous n'avez pas eu le temps de mettre en œuvre et pourquoi

Nous estimons que nous avons rempli le cahier des charges minimum du projet. Nous pensons qu'il n'y a pas vraiment de fonctionnalités que nous avont manqués. Cependant, il reste pour nous un point qui n'a pas été évoqué dans le rapport : la gestion de plusieurs utilisateurs simultanés. Nous n'avons pas lié le tuteur à des apprentis spécifiques. C'est-à-dire que nous avons un tuteur qui a accès à tous les apprentis du système. Il faudrait sécuriser les endpoints et services pour s'assurer de récupérer uniquement les apprentis du tuteur qui est actuellement connecté.

### f) À quel niveau, dans votre projet, avez-vous réussi à respecter entièrement ou partiellement les principes SOLID ?

#### **S - Single Responsibility Principle (Responsabilité Unique)**

Chaque classe a une responsabilité unique et bien définie :

- `ApprentiService` : Gestion de la logique métier des apprentis uniquement
- `VisiteService` : Gestion des visites (CRUD + validations spécifiques)
- `ApprentiControleur` : Gestion des requêtes HTTP liées aux apprentis
- `CustomUserDetailsService` : Chargement des utilisateurs pour l'authentification uniquement

---

#### **O - Open/Closed Principle (Ouvert/Fermé)**

Le code est ouvert à l'extension mais pourrait être amélioré.


- Utilisation de DTOs permet d'ajouter de nouveaux champs sans modifier les entités
- `GlobalExceptionHandler` permet d'ajouter de nouveaux types d'exceptions sans toucher aux contrôleurs

---

#### **L - Liskov Substitution Principle (Substitution de Liskov)**

Pas d'héritage dans le projet, donc pas vraiment d'occasions d'appliquer ce principe.

---

#### **I - Interface Segregation Principle (Ségrégation des Interfaces)** ✅ **Respecté**

Aucune classe n'est forcée d'implémenter des méthodes inutiles.

- Les repositories JPA n'exposent que les méthodes nécessaires
- Les DTOs sont spécifiques à chaque cas d'usage :
  - `CreerApprentiDto` : Pour la création
  - `ModifierApprentiDto` : Pour la modification (moins de champs)
  - `CreerRapportDto`, `CreerVisiteDto`, `CreerSoutenanceDto` : Spécifiques à chaque entité

---

#### **D - Dependency Inversion Principle (Inversion des Dépendances)**

Les classes devraient dépendre d'abstractions (interfaces) plutôt que d'implémentations concrètes.
En soit, Spring et JPA intègrent déjà ce principe (services qui dépendent des interfaces Repository, injection de dépendances...).

Cpendantour vraiment mettre en pratique ce principe, on aurait du utiliser des interfaces pour chaque service, et utiliser ces interfaces en tant que dépendances des controleurs. Car actuellement, tous les controleurs dépendant directement des classes de services.

---

## 6. Persistance - Analyse critique

### b) Utilisation d'une requête SQL native

Nous avons implémenté une requête SQL native dans `ApprentiRepository.findByEmailNative()` :

**Avantages de SQL natif :**

- Performance brute maximale
- Accès aux fonctions SQL spécifiques (fenêtrage, CTE, etc.)

**Inconvénients (pourquoi nous préférons JPQL) :**

- Perte de portabilité : la requête est liée à PostgreSQL/MySQL/etc.
- Pas de validation à la compilation
- Difficile à maintenir et à tester

**Notre choix architectural :** Nous privilégions JPQL pour 99% des requêtes
car notre application doit pouvoir changer de base de données facilement
et bénéficier des requêtes prédefinies et des optimisations.

### c) Justification de @Transactional

Nous appliquons @Transactional selon le principe ACID :

- Sur toutes les méthodes d'écriture (create, update, delete)
- Sur les opérations impliquant plusieurs entités liées
- Pas sur les lectures simples

Exemple critique : `commencerNouvelleAnnee()`
→ DOIT être transactionnel pour garantir que TOUS les apprentis passent
en L2 ou AUCUN (atomicité).



**Auteurs** : Bryan Bohec, Armando Lopes De Sousa, Benjamin Thibout (ING2 APP LSI2)
