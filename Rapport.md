# Rapport de Projet - ASTA - Bryan Bohec, Armando Lopes De Sousa, Benjamin Thibout (ING2 APP LSI2)

## Application de Suivi de Tutorat d'Apprentis (ASTA)

## Initialisation de l'application

### Prérequis

- **Java 17**
- **Maven**

### Via Internet

Vous pouvez tester notre application directement sur Internet depuis n'importe quel appareil connecté à Internet ! Pour cela, il suffit d'utiliser le lien suivant :<https://asta-production.up.railway.app/>.

### Via projet local

Si vous souhaitez installer notre projet pour le tester localement, il vous suffit d'utiliser le .zip de notre projet remis sur Moodle et d'utiliser un IDE tel qu'IntelliJ pour pouvoir le lancer après l'avoir ouvert.

Pour lancer le projet en local, il n'y a normalement pas de configuration particulière à effectuer. Toutefois, si vous souhaitez utiliser une base de données locale, il faudra modifier le fichier application.properties.

### Informations complémentaires

Pour tester l'application, veuillez utiliser les identifiants suivants (qui correspondent aux identifiants d'un tuteur utilisant l'application) :

- **Email** : `tuteur1@asta.fr`
- **Mot de passe** : `root`

## Outils

### IDE Utilisé

Selon les membres de l'équipe, Intellij ou VSCode (avec extensions Java)

### Système de Gestion de Base de Données

- **SGBD** : PostgreSQL 17.5
- **Hébergement** : Neon (Service de bases de données PostgreSQL en ligne)

## Réponses aux Questions

### Sur quel aspect de votre travail souhaitez-vous attirer l'attention du correcteur ?

Nous aimerions mettre en avant les points suivants :

- **Validation des données (inputs) :**

  - Gérée avec `Bean Validation`, dans les DTO, qui nous permettent de fournir des messages d'erreur explicites directement dans les champs grâce à `BindingResult`.

- **Gestion d'erreurs :**

  - Handler global d'exceptions (`@ControllerAdvice`) qui nous sert à afficher une page d'erreur spécifique lorsque l'on essaie d'accéder à une ressource qui n'existe pas via URL. Cela nous permet de les gérer à un endroit unique et non dans les contrôleurs.
  - Exceptions métier personnalisées (`ApprentiDejaExistantException`, `RessourceIntrouvableException`, etc.) gérées dans les services et contrôleurs directement avec l'utilisation de `FlashAttribute` pour les messages d'erreur/succès.

- **Sécurité :**

  - Authentification avec `Spring Security`.
  - Hashage des mots de passe avec `BCrypt`.
  - Protection des routes nécessitant une authentification, et redirection si nécessaire.

- **Gestion avancée des relations JPA :**
  - Relations bidirectionnelles (`OneToMany`, `ManyToOne`, `OneToOne`).
  - Gestion du cycle de vie des entités avec `cascade` et `orphanRemoval`.

### Quelle est la plus grande difficulté rencontrée ? Comment l'avez-vous gérée/solutionnée/contournée ?

**Difficulté principale :**

Une des parties qui nous a donné du mal a tout d'abord été le choix entre des contrôleurs REST ou des contrôleurs Thymeleaf. Nous avons trouvé le sujet assez incohérent, car il requiert Thymeleaf (donc un moteur de template géré côté serveur), mais à la fois une documentation Swagger, ce qui nous a paru comme une incitation implicite de créer des endpoints REST (donc retournant du JSON). Nous pensons que ce choix de consignes était probablement voulu, pour nous pousser à réfléchir au sujet plus en profondeur.

Nous n'avons pas exploré la possibilité de créer des endpoints REST, puis de les appeler dans des contrôleurs Thymeleaf car cela ne nous a pas paru comme une bonne pratique : Pourquoi créer une couche en plus et appeler des contrôleurs REST alors que nous pouvons gérer les données sans faire d'appels HTTP ? Cette méthode aurait pu être utile si nous avions un frontend différent de Thymeleaf (ou si nous avions voulu faire le bonus avec un framework frontend).

Des endpoints REST nous auraient aussi permis d'utiliser la documentation automatique de Swagger. Dans notre cas, elle documente manuellement les endpoints pour Thymeleaf (nous nous posons des questions quant à la pertinence de celle-ci).

Avec ces endpoints Thymeleaf, nous avons géré les appels directement dans les formulaires, avec la méthode vue en cours pour gérer les PATCH/DELETE sur des formulaires HTML qui ne les supportent pas par défaut. Pour les messages d'erreurs, nous n'utilisons donc pas des status code HTTP, mais gérons les erreurs dans les modèles à l'aide de `FlashAttribute`.

### Quelle a été la contribution de chaque membre de l'équipe ?

- Contribution faite à 3 :
  - Création de la structure de données (les modèles)
  - Écriture du rapport
- Contributions de Bryan :
  - Participation au front (nav, page de liste, page de création, page de modification)
  - Participation à la création des services (Contrôleurs/Services/Repository) + les routeurs Thymeleaf
  - Gestion des exceptions avec ControllerAdvice/GlobalExceptionHandler et création d'exceptions custom
  - Gestion de l'année académique automatique
- Contributions d'Armando :
  - Participation au front (page de liste, modification, historique, login)
  - Participation à la création des services (Contrôleurs/Services/Repository) + les routeurs Thymeleaf
  - Création de la fonctionnalité de recherche
  - Création de la doc Swagger
- Contributions de Benjamin :
  - Création de la page login
  - Gestion globale de l'authentification
  - Refactoring intégral du projet
  - Cleaning du code

### Si vous deviez retenir trois points de ce cours en général et de ce projet en particulier, quels seraient ces trois points ?

#### Point 1 : **L'importance de l'architecture en couches et de la séparation des responsabilités**

Le découpage Controller → Service → Repository n'est pas qu'une convention arbitraire, ça devient un ensemble de règles qui permettent de :

- Faciliter la maintenance et l'évolution du code
- Réutiliser la logique métier
- Rendre le code plus lisible et compréhensible

#### Point 2 : **La gestion des relations JPA**

Les relations bidirectionnelles offrent une grande flexibilité, mais nécessitent :

- Une attention particulière aux configurations (`cascade`, `orphanRemoval`, `fetch`)
- Une gestion des deux côtés de la relation lors des modifications/suppressions

#### Point 3 : **La gestion des exceptions**

Nous avons trouvé qu'en Spring Boot, la gestion des exceptions (customs et globale) est particulièrement efficace et simple d'utilisation en termes de code. Le document fourni (Clean Code - Gestion des exceptions) est très clair et a permi de bien nous aiguiller sur les bonnes pratiques.

### Les fonctionnalités que vous n'avez pas eues le temps de mettre en œuvre et pourquoi

Nous estimons que nous avons rempli le cahier des charges minimum (MVP) du projet. Nous pensons qu'il n'y a pas vraiment de fonctionnalités que nous avont manqués. Cependant, il reste pour nous un point qui n'a pas été évoqué dans le rapport : la gestion de plusieurs utilisateurs simultanés. Nous n'avons pas lié le tuteur à des apprentis spécifiques. C'est-à-dire que nous avons un tuteur qui a accès à tous les apprentis du système. Il faudrait sécuriser les endpoints et services pour s'assurer de récupérer uniquement les apprentis du tuteur qui est actuellement connecté.

De plus, même si non indiqué explicitement dans les consignes du projet, nous n'avons pas implémenté le fait de pouvoir modifier directement les entreprises et maîtres d'apprentissage créés. De même, nous ne pouvons créer de nouveaux tuteurs (utilisateurs) directement depuis l'application.

Pour finir, nous aurions bien aimé factoriser un peu plus le code, notamment au niveau des services. En effet, certaines méthodes sont très similaires entre les services (particulièrement pour les entités Rapport, Visite et Soutenance). Nous aurions pu créer une classe abstraite ou une interface commune pour ces services afin de réduire la duplication de code. De même pour le css que nous avons laissé pour la plupart dans les fichiers html.

### À quel niveau, dans votre projet, avez-vous réussi à respecter entièrement ou partiellement les principes SOLID ?

#### **S - Single Responsibility Principle (Responsabilité Unique)**

On a essayé de faire en sorte que chaque classe ait une seule responsabilité bien définie. Par exemple :

- `ApprentiService` s'occupe uniquement de la logique métier des apprentis
- `VisiteService` gère tout ce qui concerne les visites (création, modification, suppression, validations)
- `ApprentiControleur` se charge de traiter les requêtes HTTP liées aux apprentis et de renvoyer les bonnes vues
- `DetailsUtilisateurService` ne fait que charger les utilisateurs pour l'authentification

Globalement, on a bien respecté ce principe. Chaque classe a un rôle précis et ne fait pas trop de choses différentes.

#### **O - Open/Closed Principle (Ouvert/Fermé)**

Ce principe dit qu'on devrait pouvoir étendre le code sans avoir à modifier l'existant. On l'a partiellement appliqué :

- L'utilisation des DTOs (`CreerApprentiDto`, `ModifierApprentiDto`, etc.) nous permet d'ajouter de nouveaux champs de formulaire sans toucher aux entités
- Le `GlobalExceptionHandler` nous permet d'ajouter de nouveaux types d'exceptions sans modifier les contrôleurs

Par contre, si on voulait ajouter un nouveau type d'entité similaire à Visite, Rapport ou Soutenance, on serait obligés de créer beaucoup de code similaire. On aurait pu faire mieux avec une classe abstraite commune, mais on n'a pas eu le temps.

#### **L - Liskov Substitution Principle (Substitution de Liskov)**

Ce principe concerne l'héritage et les classes dérivées. Dans notre projet, on n'a quasiment pas utilisé d'héritage (à part pour les exceptions qui héritent de `RuntimeException`), donc on n'a pas vraiment eu l'occasion d'appliquer ce principe.

#### **I - Interface Segregation Principle (Ségrégation des Interfaces)**

C'est le principe de ne pas forcer une classe à implémenter des méthodes dont elle n'a pas besoin. On a plutôt bien respecté ça :

- Les repositories JPA (via `JpaRepository`) héritent seulement des méthodes qu'on utilise vraiment
- On a créé des DTOs spécifiques pour chaque cas d'usage plutôt qu'un seul gros DTO :
  - `CreerApprentiDto` pour la création d'un apprenti
  - `ModifierApprentiDto` pour la modification (avec moins de champs obligatoires)
  - `CreerRapportDto`, `CreerVisiteDto`, `CreerSoutenanceDto` pour chaque type d'entité

Comme ça, on évite d'avoir des champs inutiles ou des validations qui ne s'appliquent pas selon le contexte.

#### **D - Dependency Inversion Principle (Inversion des Dépendances)**

Ce principe dit qu'on devrait dépendre d'abstractions (interfaces) plutôt que d'implémentations concrètes.

Dans notre projet, on a partiellement respecté ce principe. Spring et JPA l'intègrent déjà naturellement : nos services dépendent des interfaces `Repository` fournies par Spring Data JPA, et on utilise l'injection de dépendances partout.

Par contre, pour vraiment appliquer ce principe à fond, on aurait dû créer des interfaces pour tous nos services (comme `IApprentiService`, `IVisiteService`, etc.) et faire en sorte que les contrôleurs dépendent de ces interfaces plutôt que des classes concrètes. Là, nos contrôleurs dépendent directement des classes `ApprentiService`, `VisiteService`, etc. Ce n'est pas extrêmement grave pour un projet de cette taille, mais sur un gros projet ça permettrait de mieux découpler le code et de faire des tests unitaires plus facilement.

## Persistance - Analyse critique

### Choix entre SQL natif et JPQL

Dans notre projet, on a fait le choix d'utiliser principalement JPQL plutôt que du SQL natif. On a quand même une requête SQL native dans `ApprentiRepository.searchApprentisByAnneeAcademique()`, mais c'était surtout pour respecter la consigne du sujet qui demandait d'avoir au moins une requête SQL.

Pourquoi on préfère JPQL ?

- C'est plus simple à écrire et à maintenir : on manipule directement nos entités Java
- Ça marche avec n'importe quelle base de données sans avoir à réécrire les requêtes (on pourrait passer de PostgreSQL à MySQL facilement).
- Spring Data JPA optimise automatiquement nos requêtes
- Les méthodes générées comme `findById()` ou `existsByEmail()` nous évitent d'écrire du SQL répétitif

Le SQL natif a ses avantages (performances brutes, fonctions SQL avancées), mais pour notre projet, on n'en a pas vraiment eu besoin. Les requêtes JPQL et les méthodes dérivées de Spring Data suffisent largement pour nos besoins.

### Utilisation de @Transactional

On a mis `@Transactional` sur nos méthodes de service qui modifient la base de données (création, modification, suppression). L'idée, c'est de garantir que si une opération plante en cours de route, tout est annulé automatiquement pour éviter d'avoir des données incohérentes.

Par exemple, dans la méthode `incrementerAnneeAcademiqueCourante()`, on modifie l'année académique courante en base. Si ça plante au milieu de l'opération, le `@Transactional` va annuler la modification pour éviter d'avoir une année académique dans un état incohérent.

Autre exemple important : dans le service des apprentis, les méthodes comme `creerApprenti()` ou `modifierApprenti()` sont transactionnelles. Si on crée un apprenti avec ses dossiers annuels et que ça plante en cours de route, tout est annulé (atomicité).

Pour les méthodes qui font juste de la lecture (comme `getApprentis()` ou `getApprentisActifs()`), on ne met pas `@Transactional` car ce n'est pas nécessaire.

**Auteurs** : Bryan Bohec, Armando Lopes De Sousa, Benjamin Thibout (ING2 APP LSI2)
