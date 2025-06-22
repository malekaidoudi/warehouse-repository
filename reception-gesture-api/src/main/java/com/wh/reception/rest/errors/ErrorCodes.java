package com.wh.reception.rest.errors;

public final class ErrorCodes {
    private ErrorCodes() {
        // Constructeur privé pour empêcher l'instanciation
    }

 // Codes d'erreur pour les ressources non trouvées
    public static final String NOT_FOUND_GENERIC = "API_404_001"; // Générique pour toute ressource non trouvée
    public static final String CATEGORY_NOT_FOUND = "CAT_404_001"; // Spécifique pour les catégories
    public static final String PARCEL_NOT_FOUND = "PAR_404_001"; // Spécifique pour les colis
    public static final String DIMENSION_NOT_FOUND = "DIM_404_001"; // Spécifique pour les dimensions
    public static final String RECEPTION_NOT_FOUND = "REC_404_001"; // Spécifique pour les réceptions
    public static final String ITEM_NOT_FOUND = "ITM_404_001";  // Spécifique pour les items
    public static final String PALETTE_NOT_FOUND = "PAL_404_001"; // Spécifique pour les utilisateurs
    
    // Codes d'erreur pour les données invalides
    public static final String BAD_REQUEST_GENERIC = "API_400_001"; // Générique pour toute mauvaise requête
    public static final String CATEGORY_INVALID_DATA = "CAT_400_001"; // Spécifique pour les catégories
    public static final String CATEGORY_ALREADY_EXISTS = "CAT_400_002"; // Spécifique si un libellé doit être unique
    public static final String PARCEL_INVALID_DATA = "PAR_400_001"; // Spécifique pour les colis
    public static final String PARCEL_MISSING_RECEPTION_ID = "PAR_400_002"; // Spécifique si un colis doit être lié à une réception
    public static final String DIMENSION_INVALID_DATA = "DIM_400_001"; // Spécifique pour les dimensions
    public static final String PALETTE_INVALID_DATA = "PAL_400_001"; // Spécifique pour les palettes
    public static final String RECEPTION_INVALID_DATA = "REC_400_001"; // Spécifique pour les réceptions
    public static final String ITEM_INVALID_DATA = "ITM_400_001"; // Spécifique pour les items
    
    // Codes d'erreur pour les conflits

    // Codes d'erreur pour les erreurs internes du serveur
    public static final String INTERNAL_SERVER_ERROR = "API_500_001";
}