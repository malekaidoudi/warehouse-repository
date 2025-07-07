# Reception Gesture Common

Ce module contient les DTOs (Data Transfer Objects) et les utilitaires partagés pour le projet reception-gesture.

## Structure du projet

```
reception-gesture-common/
├── src/main/java/com/wh/reception/
│   ├── dto/                          # DTOs pour les entités
│   │   ├── CategoryDTO.java
│   │   ├── DimensionDTO.java
│   │   ├── ParcelDTO.java
│   │   └── update/                   # DTOs pour les mises à jour
│   │       ├── CategoryUpdateDTO.java
│   │       ├── DimensionUpdateDTO.java
│   │       └── ParcelUpdateDTO.java
│   ├── mapper/                       # Interfaces des mappers
│   │   ├── CategoryMapper.java
│   │   ├── DimensionMapper.java
│   │   └── ParcelMapper.java
│   └── utils/                        # Utilitaires
│       └── ValidationUtils.java
└── src/test/java/                    # Tests unitaires
```

## Fonctionnalités

### DTOs (Data Transfer Objects)
- **CategoryDTO** : Représentation des catégories
- **DimensionDTO** : Représentation des dimensions
- **ParcelDTO** : Représentation des colis
- **UpdateDTOs** : DTOs spécialisés pour les mises à jour partielles

### Mappers
Interfaces définissant les contrats de mapping entre entités et DTOs. Les implémentations sont fournies dans le module EJB.

### Utilitaires
- **ValidationUtils** : Utilitaires pour la validation des données
  - Validation Bean Validation
  - Validation des dates de livraison/expiration
  - Validation des chaînes de caractères

## Utilisation

### Validation d'un DTO
```java
CategoryDTO category = new CategoryDTO("Electronics", "Electronic devices");
if (ValidationUtils.isValid(category)) {
    // DTO valide
} else {
    String errors = ValidationUtils.getValidationErrors(category);
    // Traiter les erreurs
}
```

### Validation des dates de cargo
```java
LocalDate deliveryDate = LocalDate.now().plusDays(1);
LocalDate expirationDate = LocalDate.now().plusDays(7);
ValidationUtils.validateCargoDate(deliveryDate, expirationDate);
```

## Dépendances

Ce module utilise :
- Jakarta EE 10 API
- Jakarta Bean Validation
- Jackson pour la sérialisation JSON
- JUnit 5 pour les tests

## Tests

Exécuter les tests :
```bash
mvn test
```

Les tests couvrent :
- Validation des DTOs
- Fonctionnalités des utilitaires
- Égalité et hashCode des DTOs