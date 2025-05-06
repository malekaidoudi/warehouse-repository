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

### 1️⃣ Prérequis  
Avant de commencer, assurez-vous d'avoir installé :  
- **JDK 17**  
- **Docker** (pour PostgreSQL)  
- **WildFly 27**  

### 2️⃣ Cloner le projet  
```
bash
git clone https://github.com/malekaidoudi/warehouse-repository.git
cd warehouse-repository
```
###  3️⃣ Lancer PostgreSQL avec Docker:
```
docker run --name warehouse-db -e POSTGRES_USER=user-name -e POSTGRES_PASSWORD=secret -e POSTGRES_DB=warehouse -p 5432:5432 -d postgres:17
```
### 4️⃣ Configurer la base de données
Mettre à jour persistence.xml avec la configuration correcte pour PostgreSQL.

### 5️⃣ Déployer sur WildFly
Construire le projet :
 ```
mvn clean install
 ```
Déployer l'artefact (war) sur WildFly :

 ```
bash
cp target/warehouse-api.war /wildfly/standalone/deployments/
```
📌 Endpoints API REST

........
.......
......

📜 Licence
Ce projet est sous licence MIT.

👥 Auteur
Développé par Malek Aidoudi.
