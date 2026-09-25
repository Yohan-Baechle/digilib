# Digilib

[![CI](https://github.com/Yohan-Baechle/digilib/actions/workflows/ci.yml/badge.svg)](https://github.com/Yohan-Baechle/digilib/actions/workflows/ci.yml)

Application **desktop** de gestion de bibliothèque développée en **JavaFX**.
Interface graphique moderne (icônes Ikonli, police Poppins) avec persistance
locale via **SQLite**.

## Fonctionnalités

- Gestion des **livres** (ajout, édition, suppression)
- Gestion des **utilisateurs**
- Gestion des **emprunts** (loans)
- Navigation par barre latérale entre les différents écrans de gestion

## Architecture (MVC)

```
controller/   Contrôleurs JavaFX (un par écran + Sidebar + Main)
model/        Modèle applicatif (AppModel) et entités (Book, User, Loan)
util/         Utilitaires (BookUtils)
view/         ViewFactory : chargement et navigation entre les vues FXML
```

## Stack technique

- **Java** + **JavaFX** (controls, FXML)
- **Ikonli** (icônes Material 2) pour l'interface
- **SQLite** (`sqlite-jdbc`) pour la persistance
- **JUnit 5** pour les tests
- **Maven** (wrapper inclus)

## Mise en route

Prérequis : JDK 17+.

```bash
./mvnw clean javafx:run
```

## Build

```bash
./mvnw clean package
```

## Licence

Distribué sous licence MIT. Voir [LICENSE](LICENSE).
