# 🚀 Warehouse API Backend

## 📖 Description

Ce projet est une API backend pour gérer un entrepôt, développée avec **JDK 17**, **Jakarta EE 10**, et déployée sur **WildFly 27**. La base de données **PostgreSQL 17** est exécutée dans un conteneur **Docker** avec un script d'initialisation pour configurer les tables et données initiales.

## 🏗 Technologies utilisées

-   **Langage** : Java (JDK 17)
-   **Framework** : Jakarta EE 10
-   **Serveur d'application** : WildFly 27
-   **Base de données** : PostgreSQL 17 (Docker)
-   **Gestion des dépendances** : Maven
-   **API REST** : JAX-RS

## 🚀 Installation et déploiement

### 0️⃣ Prérequis

Avant de commencer, assurez-vous d'avoir installé :

-   **JDK 17** :
    -   **macOS** : `brew install openjdk@17`
    -   **Ubuntu** : `sudo apt install openjdk-17-jdk`
-   **Docker** :
    -   **macOS** : Installez [Docker Desktop](https://www.docker.com/products/docker-desktop/).
    -   **Ubuntu** : `sudo apt install docker.io`. Ajoutez votre utilisateur au groupe Docker : `sudo usermod -aG docker $USER` et reconnectez-vous.
-   **Maven** :
    -   **macOS** : `brew install maven`
    -   **Ubuntu** : `sudo apt install maven`
-   **WildFly 27** : Téléchargez depuis [WildFly](https://github.com/wildfly/wildfly/releases/download/27.0.1.Final/wildfly-27.0.1.Final.zip.sha1).

**Vérification** :

```bash
java -version  # Doit afficher JDK 17
docker --version
mvn -version
```
### 1️⃣ Cloner le projet

```bash
git clone https://github.com/malekaidoudi/warehouse-repository.git
cd warehouse-repository
```

### 2️⃣ Lancer PostgreSQL avec Docker

**Nettoyer les conteneurs existants (si nécessaire)** :

```bash
docker-compose ps   # Liste tous les conteneurs, y compris ceux arrêtés egalement tu peux utiliser docker ps -a
docker-compose down  # Arrête et supprimer  le conteneur s'il est en cours d'exécution egalement tu peux utiliser docker stop warehouse_db pour arreter le conteneur et aprés docker rm warehouse_db pour le supprimer
```

Construisez et executez l'image Docker à partir du `docker-compose.yml` :

```bash
docker-compose up -d

```
> [!TIP]
> Il faut s'assurer que vous étes au dossier /warehouse-repository/reception-gesture


**Vérification** :

1.  Vérifiez que le conteneur est en cours d'exécution :
```bash
docker ps  # Le conteneur "warehouse_db" doit apparaître
```

2.  Vérifiez que la base de données est accessible :

```bash
docker exec -it warehouse_db psql -U postgres -d warehouse -c "SELECT 1"

```

3.  Vérifiez que le script `init.sql` a créé les tables :

```bash
docker exec -it warehouse_db psql -U postgres -d warehouse -c "\dt" # Normalement vous trouvez 8 tables
```
4. Si vous avez rencontré un problème vous pouvez consulter le log
   
```bash
docker-compose logs db  # db le nom de service
```
### 3️⃣ Configurer WildFly

#### a) Télécharger et extraire WildFly

Téléchargez WildFly 27.0.1 :

```bash
curl -LO https://github.com/wildfly/wildfly/releases/download/27.0.1.Final/wildfly-27.0.1.Final.zip
unzip wildfly-27.0.1.Final.zip
sudo mv wildfly-27.0.1.Final /opt/wildfly  # Nécessite sudo pour /opt

```

**Modifier les permissions (Ubuntu uniquement)** :

```bash
sudo chown -R $USER:$USER /opt/wildfly
sudo chmod -R u+rwX /opt/wildfly

```

**Vérification** :

```bash
ls -ld /opt/wildfly
```

#### b) Configurer le driver JDBC PostgreSQL

Téléchargez le driver JDBC (version 42.7.5) :

```bash
# macOS/Linux
curl -Lo /tmp/postgresql-42.7.5.jar https://jdbc.postgresql.org/download/postgresql-42.7.5.jar
```

Créez un dossier pour le module PostgreSQL :

```bash
mkdir -p /opt/wildfly/modules/org/postgresql/main
mv /tmp/postgresql-42.7.5.jar /opt/wildfly/modules/org/postgresql/main/
```

Créez le fichier `module.xml` :

```bash
cat > /opt/wildfly/modules/org/postgresql/main/module.xml <<
EOF
<?xml version="1.0" encoding="UTF-8"?>
<module xmlns="urn:jboss:module:1.9" name="org.postgresql">
    <resources>
        <resource-root path="postgresql-42.7.5.jar"/>
    </resources>
    <dependencies>
        <module name="javax.api"/>
        <module name="javax.transaction.api"/>
    </dependencies>
</module>
EOF
```

#### c) Ajouter la datasource

**Pré-requis** : Assurez-vous que le conteneur PostgreSQL est démarré (voir étape 2) avant de configurer la datasource.
Démarrez WildFly :
```bash
/opt/wildfly/bin/standalone.sh &
```
Ajoutez le driver JDBC et la datasource via `jboss-cli` :

```bash
/opt/wildfly/bin/jboss-cli.sh --connect <<
EOF
/subsystem=datasources/jdbc-driver=postgresql:add(driver-name="postgresql", driver-module-name="org.postgresql", driver-class-name="org.postgresql.Driver")
/subsystem=datasources/data-source=WarehouseDS:add(jndi-name="java:/WarehouseDS", enabled=true, driver-name="postgresql", connection-url="jdbc:postgresql://localhost:5432/warehouse", user-name="postgres", password="root")
/subsystem=datasources/data-source=WarehouseDS:write-attribute(name=valid-connection-checker-class-name, value="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLValidConnectionChecker")
/subsystem=datasources/data-source=WarehouseDS:write-attribute(name=background-validation, value=true)
/subsystem=datasources/data-source=WarehouseDS:write-attribute(name=exception-sorter-class-name, value="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLExceptionSorter")
EOF

```

### 4️⃣ Construire le projet

```bash
mvn -f /warehouse-repository/reception-gesture/pom.xml clean install -DskipTests
mvn -f /warehouse-repository/reception-gesture-api/pom.xml clean install
```

### 5️⃣ Déployer le projet

Copiez les artefacts vers WildFly :

```bash
cp reception-gesture-api/target/reception-gesture-api.war /opt/wildfly/standalone/deployments/
cp reception-gesture/target/reception-gesture.jar /opt/wildfly/standalone/deployments/
```
### 📌 Endpoints API REST

### 📜 Licence

Ce projet est sous licence MIT.

### 👥 Auteur

Développé par Malek Aidoudi.