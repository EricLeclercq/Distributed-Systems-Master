# Akka Remote

Exemple de programme en Java pour créer et utiliser des Acteurs Akka répartis sur plusieurs JVM. 

Pour compiler le code, utiliser Maven avec les commandes suivantes :

```
mvn clean compile install
```

Lancer d'abord le serveur :

```
mvn -pl serveur exec:java -Dexec.mainClass="sd.akka.App"
```

Puis le client :

```
mvn -pl client exec:java -Dexec.mainClass="sd.akka.App"
```
