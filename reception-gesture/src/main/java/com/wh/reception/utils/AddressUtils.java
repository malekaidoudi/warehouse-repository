package com.wh.reception.utils;

public class AddressUtils {

    /**
     * Extrait le code postal de l'adresse et le retourne comme un Long.
     * Suppose que le code postal est la dernière partie de l'adresse, séparée par une virgule.
     *
     * @param address Adresse au format "Rue, Ville, CodePostal" (ex: "123 Shipping St, City, 75001")
     * @return Le code postal comme Long, ou 0 si non trouvé ou non numérique
     */
    public static Long extractPostalCode(String address) {
        if (address == null || address.trim().isEmpty()) {
            return 0L;
        }
        String[] parts = address.split(",");
        if (parts.length > 0) {
            String lastPart = parts[parts.length - 1].trim();
            // Vérifier si la dernière partie est un nombre (code postal)
            try {
                return Long.parseLong(lastPart);
            } catch (NumberFormatException e) {
                return 0L;
            }
        }
        return 0L;
    }
}