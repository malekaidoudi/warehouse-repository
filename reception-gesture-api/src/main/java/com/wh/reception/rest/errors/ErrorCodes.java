package com.wh.reception.rest.errors;

public final class ErrorCodes {
    private ErrorCodes() {
        // Constructeur privé pour empêcher l'instanciation
    }

 // Error codes for resources not found
    public static final String NOT_FOUND_GENERIC = "API_404_001"; // Generic for any resource not found
    public static final String CATEGORY_NOT_FOUND = "CAT_404_001"; // Specific to categories ID not found
    public static final String DIMENSION_NOT_FOUND = "DIM_404_001"; // Specific to dimensions ID not found
    public static final String RECEPTION_NOT_FOUND = "REC_404_001"; // Specific to receptions ID not found
    public static final String PARCEL_NOT_FOUND = "PAR_404_001"; // Specific to parcels ID not found
    //public static final String ITEM_NOT_FOUND = "ITM_404_001";  // Specific to items ID not found
    //public static final String PALETTE_NOT_FOUND = "PAL_404_001"; // Specific to pallets ID not found

    // Error codes for invalid data
    public static final String BAD_REQUEST_GENERIC = "API_400_001"; // Generic for any bad request
    public static final String DATE_DEL_EXP_MUST_BE_LINKED = "DATE_400_001"; // Specific if a parcel or palette has missing delivery or expiration dates
    public static final String DATE_DEL_EXP_MUST_BE_VALID = "DATE_400_002"; // Specific if a parcel or palette has an invalid delivery or expiration date
    public static final String DATE_DEL_EXP_MUST_BE_CONST = "DATE_400_003"; // Specific if a parcel or palette has a delivery date and an expiration date that are not consistent
    public static final String CATEGORY_ALREADY_EXISTS = "CAT_400_001"; // Specific if a label must be unique
    public static final String CATEGORY_INVALID_LABEL = "CAT_400_002"; // Specific if a label must be between 3 and 50
    public static final String DIMENSION_INVALID_LABEL = "DIM_400_001"; // Specific if a dimension label must be between 3 and 50
    public static final String DIMENSION_ALREADY_EXISTS = "DIM_400_002"; // Specific if a dimension must be unique
    public static final String DIMENSION_INVALID_DIMENSION = "DIM_400_003"; // Specific if a dimension must be positive and of a certain length
    public static final String PARCEL_MISSING_RECEPTION_ID = "PAR_400_001"; // Specific if a parcel must be linked to a reception
  
    //public static final String ITEM_INVALID_DATA = "ITM_400_001"; // Specific to items
    //public static final String PARCEL_INVALID_DATA = "PAR_400_001"; // Specific to parcels
    //public static final String PARCEL_MISSING_RECEPTION_ID = "PAR_400_002"; // Specific if a parcel must be linked to a reception
    //public static final String RECEPTION_INVALID_DATA = "REC_400_001"; // Specific to receptions
    //public static final String PALETTE_INVALID_DATA = "PAL_400_001"; // Specific to pallets
    
    // Codes d'erreur pour les conflits

    // Codes d'erreur pour les erreurs internes du serveur
    public static final String INTERNAL_SERVER_ERROR = "API_500_001";
}