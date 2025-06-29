# 🚀 Warehouse API Backend

## 📖 Description

Ce projet est une API backend permettant de gérer un entrepôt, développée avec **JDK 17**, **Jakarta EE 10** et déployée sur **WildFly 27**.  
La base de données **PostgreSQL 17** est exécutée dans un **conteneur Docker** pour une gestion simplifiée.

## 🏗 Technologies utilisées

- **Langage** : Java (JDK 17)
- **Framework** : Jakarta EE 10
- **Serveur d'application** : WildFly 27
- **Base de données** : PostgreSQL 17 (Docker)
- **Gestion des dépendances** : Maven
- **API REST** : JAX-RS

## 🚀 Installation et Déploiement

### 0️⃣ Prérequis

Avant de commencer, assurez-vous d'avoir installé :

- **JDK 17**
- **Docker** (pour PostgreSQL)
- **WildFly 27**

### 1️⃣ Cloner le projet

```
bash
git clone https://github.com/malekaidoudi/warehouse-repository.git
cd warehouse-repository
```

### 2️⃣ Lancer PostgreSQL avec Docker:

```
docker run --name warehouse-db -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=root -e POSTGRES_DB=warehouse -p 5432:5432 -d postgres:17
```

️⃣

### 3️⃣ Déployer sur WildFly

<details>
<summary><strong>Telecharger WildFly27.0.1</strong></summary>

```
bash
wget https://github.com/wildfly/wildfly/releases/download/27.0.1.Final/wildfly-27.0.1.Final.zip
```

</details>
<details>
<summary><strong>Extraire l’archive</strong></summary>

```bash
unzip wildfly-27.0.1.Final.zip
```

</details>
<details>
  <summary><strong>Déplacez WildFly dans un répertoire comme /opt/wildfly</strong></summary>
  
```
bash
sudo mv wildfly-27.0.1.Final /opt/wildfly
```
</details>
<details>
  <summary><strong>Télécharger le driver JDBC PostgreSQL (par exemple, version 42.7.5)</strong></summary>
  
```
bash
wget -P /tmp https://jdbc.postgresql.org/download/postgresql-42.7.5.jar
```
</details>
<details>
  <summary><strong>Créez un dossier pour le module PostgreSQL</strong></summary>
  
```
bash
mkdir -p /opt/wildfly/modules/org/postgresql/main
```
</details>
<details>
  <summary><strong>Deplacer le driver dans le dossier qu'on a crée</strong></summary>

```
bash
mv /tmp/postgresql-42.7.5.jar /opt/wildfly/modules/org/postgresql/main/
```

</details>
<details>
  <summary><strong>Créez un fichier module.xml dans le dossier qu'on a crée</strong></summary>
  
```
bash
cat > /opt/wildfly/modules/org/postgresql/main/module.xml <<EOF
<?xml version="1.0" encoding="UTF-8"?>
<module xmlns="urn:jboss:module:1.9" name="org.postgresql">
    <resources>
        <resource-root path="postgresql-42.7.3.jar"/>
    </resources>
    <dependencies>
        <module name="javax.api"/>
        <module name="javax.transaction.api"/>
    </dependencies>
</module>
EOF
```
</details>
<details>
  <summary><strong>Ajouter la datasource</strong></summary>

```
bash
/opt/wildfly/bin/jboss-cli.sh --connect <<EOF
/subsystem=datasources/data-source=WarehouseDS:add(jndi-name="java:/WarehouseDS", enabled=true, driver-name="postgresql", connection-url="jdbc:postgresql://localhost:5432/warehouse", user-name="postgres", password="root")
/subsystem=datasources/data-source=WarehouseDS:write-attribute(name=valid-connection-checker-class-name, value="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLValidConnectionChecker")
/subsystem=datasources/data-source=WarehouseDS:write-attribute(name=background-validation, value=true)
/subsystem=datasources/data-source=WarehouseDS:write-attribute(name=exception-sorter-class-name, value="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLExceptionSorter")
/subsystem=datasources/jdbc-driver=postgresql:add(driver-name="postgresql", driver-module-name="org.postgresql", driver-class-name="org.postgresql.Driver")
EOF
```

</details>

### 4️⃣ Construire le projet

```
bash
mvn clean install
```

> [!TIP]
> Rajouter l'option <i>-DskipTests</i> pour faire sauter les tests

### 5️⃣ Deployer le project

Déployer l'artefact (war) et l'artefact (jar) sur WildFly :

```
cp reception-gesture-api/target/reception-gesture-api.war /opt/wildfly/standalone/deployments/
cp reception-gesture/target/reception-gesture.jar /opt/wildfly/standalone/deployments/
```

📌 Endpoints API REST

........
.......
......

📜 Licence
Ce projet est sous licence MIT.

👥 Auteur
Développé par Malek Aidoudi.
