# Utiliser l'image officielle de PostgreSQL
FROM postgres:17

# Définir un mot de passe pour l'utilisateur postgres
ENV POSTGRES_PASSWORD=root
ENV POSTGRES_DB=warehouse

# Copier un script SQL pour initialiser la base de données
COPY init.sql /docker-entrypoint-initdb.d/


