# unibetSimulator
Petite application web de gestion d'événements sportifs en direct

# Comment exécuter l'application :

Pour lancer l'application vous devez "Cloner le projet", et suivre les étapes suivantes :

1. Ouvrir un Terminal
1. Accéder à l'emplacement du projet :
	1. cd ..../unibetSimulator
1. Ensuite il faut télécharger les dépendances MAVEN du projet :
	1. mvn clean install -DskipTests
1. Et finalement vous pouvez lancer l'application :
    1. mvn spring-boot:run
    
1. Si vous voulez exécuter les tests unitaire :
	1. mvn test

# Comment visualiser et tester les API exposées par l'application :

Une fois l'application lancée, vous devez accéder au lien suivant : http://localhost:8080/swagger-ui/

le port est d'habitude égal à 8080 , mais assurez-vous de sa valeur au lancement de l'application.

SWAGGER documente la liste des API exposé par l'application et donne la possibilité de les tester en temps réel.

# Comment accéder à la base H2 de l'application :

Une fois l'application lancée, vous devez accéder au lien suivant : http://localhost:8080/h2-console

le port est d'habitude égal à 8080 , mais assurez-vous de sa valeur au lancement de l'application.

Le login et mot de passe sont :
    1. Login    : sa
    1. Password : password

## Prérequis

Java 8, Maven, Spring Tool Suite, Eclipse

## Instructions

Dans ce projet, nous allons simuler un événement (event) en direct (live) qui est un match de foot entre

deux équipes (Unibet IT vs Real madrid) qui va durer 5 minutes. Cet événement va avoir plusieurs marchés

(markets) par exemple "Qui va gagner le match ?" ou encore "qui va marquer plus que Messi ?" ouverts ou

non pour la prise de pari (bets). Chaque marché va avoir au moins une sélection (selection), par exemple

"Unibet IT va gagner le match" et qui sera représentée par un nom, un état (ouverte, fermée), une cote

(odd) et un résultat final (gagnante / perdante). Enfin, une cote a une valeur entre 1.0 et X.Y
