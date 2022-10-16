# TP1 - Tutoriel JEE
Dans ce premier TP, le but est de créer une application spring boot fonctionnelle permettant de fournir des API Rest. vous serez entièrement dirigé. Vous pouvez vous servir du support de cours si jamais vous sentez que celà est nécessaire. 

Créez un compte github et clonnez le projet présent [à cette adresse.](https://github.com/DwizN/tutorial-lp/)

Ouvez Eclipse et importez le projet via File -> Import -> Maven -> Existing Maven Project
Vous retrouvez ensuite sur la gauche dans l'explorateur de projets, le projet "Tutoriel-lp" Vérifiez que celui-ci ne comporte pas d'erreurs. (Pas de croix rouge sur le projet)

Ouvrez votre dashboard Spring boot (Si vous ne l'avez pas d'ouvert -> Window -> Show View -> Other -> Boot Dashboard) Faites un clic droit sur votre projet et cliquez sur (Re) Debug.

La console s'ouvre et vous devriez avoir votre serveur démarré sur le port 8080 avec votre application qui a démarré avec succès. Pour vérifiez, ouvrez votre navigateur et testez cette url : http://localhost:8080/ vous devriez avoir le message suivant d'affiché : 
Bravo ! Vous êtes prêts à démarrer le tutoriel :)

## 1. Création de votre 1er Web-service avec des données mockées.

Pour ceux qui ne le savent pas, un objet de type mock permet de simuler le comportement d'un autre objet concret de façon maitrisée. Dans notre cas nous allons créer un mock pour simuler des données présentes en base de données.

Bien que le projet que vous avez récupéré possède déjà certaines dépendances, nous allons rajouter la librairie Swagger. Elle permet la génération automatique de la documentation à partir de votre code de manière transparente.
Pour cela, dans votre fichier pom.xml, vous allez ajouter à la suite des autres dépendances déjà installées, le code suivant : 

```
<dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-ui</artifactId>
      <version>1.6.11</version>
</dependency>
```

Redémarrez votre serveur. Une fois fait, visitez l’URL http://localhost:8080/swagger-ui/index.html.

Pour le moment vous n’avez qu’une seule ligne car vous n’avez qu’un seul web-service de créé. 
Nous aimerions rajouter certaines informations comme une description de notre web service, ou encore formatter d’une meilleure manière le nom de notre classe. 
Swagger utilise [des annotations](https://www.programcreek.com/java-api-examples/?api=io.swagger.v3.oas.annotations.responses.ApiResponse) pour enrichir notre documentation. 

Rajoutez au dessus de votre classe l’annotation suivante : 
```
@Tag(name="Web Services - MonPremierController")
```
Rajoutez au dessus de votre méthode l’annotation suivante : 
```
@Operation(summary = "Affiche le message de succès du lancement de l'application")
```
Redémarrez le serveur, et observez les changements. Dans la suite des TP que nous aurons à faire, vous devrez toujours spécifier une description (@Operation) pour chaque webservice. 

## Architecture & Structure de notre code. 
Vous allez travailler sur une architecture N-tiers, et donc sur plusieurs couches, Il est donc necessaire d’organiser notre code et de créer différents packages. 
Depuis le package : com.iutlco.tutoriallp -> clic droit -> create new package.
Vous allez créer en minuscule les packages suivants: 
```
com.iutlco.tutoriallp.dto
com.iutlco.tutoriallp.controller
com.iutlco.tutoriallp.service
com.iutlco.tutoriallp.entity
com.iutlco.tutoriallp.mapper
com.iutlco.tutoriallp.repository
com.iutlco.tutoriallp.enum
com.iutlco.tutoriallp.exception
```



## Création de votre premier DTO 

Un **DTO (Data Transfer Object)** est la représentation sous forme objet, des informations affichées à l’écran pour l’utilisateur. 
Vous allez créer une classe dans votre package com.iutlco.tutoriallp.dto une classe nommée **UtilisateurDTO**. Par convention, le nom de vos classes devra toujours avoir comme suffixe en majuscule, le nom de votre package.
Ajoutez les 4 attributs de visibilité privé suivants : 
**Nom, prénom et sexe** de type **String**
**Age** de type **Integer**
Créer un constructeur par paramètre avec les 4 attributs.
Générer automatiquement les getters & les setters (Sous éclipse : ALT + SHIFT + S et ensuite + R)
Voilà, vous avez créé votre premier DTO, minimaliste, mais fonctionnel. 

## Création de votre premier service.
Un **service **(ou encore BO = **Business Object**) est un ensemble de **web-services réutilisables**. Il forment ce qu’on appelle **une API-REST.** 

Dans un premier temps, nous allons créer dans le package com.iutlco.tutoriallp.service une classe **UtilisateurServiceMock **qui sera un mock (=bouchon) des données que nous allons renvoyer via notre service à notre DTO.

Dans cette classe, vous allez créer une méthode statique « findUsers » qui va nous renvoyer une liste d’UtilisateurDTO. N’hésitez pas à utiliser le constructeur par paramètre que vous avez créé dans votre DTO ! Vous pouvez ajouter à votre liste 5 utilisateurs avec des informations différentes. 

Toujours dans le même package, vous allez cette fois-ci créer une classe «UtilisateurService »
Au dessus de votre classe, ajoutez l’annotation @Service, obligatoire pour tous les services que vous devrez créer. 
Vous allez créer une méthode  « findUsers » qui va renvoyer une liste d’utilisateurDTO (la liste que vous avez créé via votre mock juste avant).

## Création de votre premier controller.
Un **controller** (ou encore BD = Business object) est **un ensemble de web-service spécifique au front** (= non réutilisable par une autre appli)
Son but ? Retourner le DTO que vous avez créé sous format JSON via une URL. 

Pour se faire, dans le package com.iutlco.tutoriallp.controller, créez une classe « UtilisateurController »
Ajoutez au dessus de votre classe les annotations suivantes :
```
@RestController //-> est utilisé pour marquer les classes en tant que contrôleur Spring.
@RequestMapping(value = "/rest/bd/utilisateurs") //=> l’URL d’accès à votre controller.
```

Dans notre controller, nous allons devoir utiliser et apeller le service que l'on a créé auparavant. Pour celà activez l’injection automatique de dépendances de façon à avoir au début à l'intérieur de votre classe :

```
    @Autowired
    Private UtilisateurService service;
```

Créez maintenant une méthode « findUser » qui va renvoyer votre liste d’UtilisateurDTO appelé via votre UtilisateurService. 
N’oubliez pas d’ajouter l’annotation @GetMapping() au dessus de votre méthode. Elle permet à Spring de savoir quelle méthode HTTP (GET, POST, DELETE…) nous allons utiliser. Vous pauvez également renseigner l’attribut facultatif « value » si vous voulez modifier votre URL déjà préfixée par votre annotation @RequestMapping

Une fois cela fait, redémarrez votre serveur et rendez-vous sur l’URL : 
http://localhost:8080/api/utilisateurs
Si tout est ok, vous devriez retrouver un tableau avec vos 5 utilisateurs créés via votre mock.

Félicitations ! Vous avez créé votre premier web-service à partir de données mockées… maintenant nous allons enrichir ce web-service pour gérer la persistance de nos données, via une base de données mySQL. 

## 1. Création de votre 1er Web-service avec des données provenant de la BDD.

## Création de votre table Utilisateur : 

Dans votre interface PHPMyAdmin, créez une base de données intitulée « tutoriel-lp » 
Créez ensuite une table « utilisateur » avec les colonnes suivantes: 
-  id (PK) de type bigInt(20) 
- nom & prenom de type varchar(30)
- date_naissance de type date
- sexe de type varchar(10)
- is Actif de type booléen bit(1)

Retournez dans Eclipse où vous allez maintenant rajouter les dépendances relatives à la persistance des données dans votre pom.xml (dépendances JPA & mySQL) 
```
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-data-jpa</artifactId>
</dependency>
<dependency>
	<groupId>mysql</groupId>
	<artifactId>mysql-connector-java</artifactId>
	<scope>runtime</scope>
	</dependency>
```

Maintenant dans votre application.properties (votre fichier de configuration, normalement vide) vous allez renseigner les informations de votre base SQL.
Vérifiez vos informations username & password qui sont propres à votre configuration.


```
# DataSource settings: set here your own configurations for the database 
# connection. In this example we have "netgloo_blog" as database name and 
# "root" as username and password.
spring.datasource.url = jdbc:mysql://localhost:3306/tutoriel-lp?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
spring.datasource.username = root
spring.datasource.password = root

# Keep the connection alive if idle for a long time (needed in production)
spring.datasource.testWhileIdle = true
spring.datasource.validationQuery = SELECT 1

# Show or not log for each sql query
spring.jpa.show-sql = true

# Hibernate ddl auto (create, create-drop, update, none)
spring.jpa.hibernate.ddl-auto = none

# Naming strategy
spring.jpa.hibernate.naming-strategy = org.hibernate.cfg.ImprovedNamingStrategy

# Use spring.jpa.properties.* for Hibernate native properties (the prefix is
# stripped before adding them to the entity manager)

# The SQL dialect makes Hibernate generate better SQL for the chosen database
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQL5Dialect
management.endpoints.web.exposure.include = mappings
```

## Création de votre entité : 
Une **entité** (ou DO = Data Object) est la r**eprésentation sous forme objet d’une table de votre base de données**. 

Comme pour le DTO, vous allez créer une classe **UtilisateurEntity** dans votre package , reprenant les champs de votre base de données sous format objet. N’oubliez pas les getters et les setters.

Au dessus de votre classe vous devez mentionner 2 annotations : 
```
@Entite // => Pour que votre application Spring boot sache que cette classe est une entité
@Table(name="utilisateur") //=> le nom de la table de votre base de données associée à cet objet Entité.
```

Si le nom de votre attribut objet est différent du nom de votre colonne, pas de panique ! Vous pouvez utiliser cette annotation au dessus de votre attribut pour faire la référence vers votre colonne.
```
@Column(name="nom")
private String nom;
```

Pour votre clé primaire, vous pouvez utiliser ces 2 annotations : 
```
@Id 
@GeneratedValue(strategy = GenerationType.AUTO)
```

## Création de votre repository
Un **repository** (ou DAO = data access object) **gère la persistance des données entre vos objets entité et la base de données**. 
Un repository est une interface qui va étendre JPA repository de la forme : 
```
public interface UtilisateurRepository extends JpaRepository<UtilisateurEntity, Long>
```

Avec votre entité en premier paramètre et le type de votre clé primaire en 2nd.
Au dessus de votre interface, vous devez mentionner l’annotation @Repository.

## Création d’un mapper
Vous l’avez compris, nous allons manipuler des objets DTO pour le front et des objets Entité pour la base de données. On appelle ça une classe de mapping. 

Certaines librairies permettent de mapper nos beans (représentations objet) facilement et simplement. Mais pour votre apprentissage vous allez le faire manuellement. 

Dans votre package com.iutlco.tutoriallp.mapper, créez un « UtilisateurMapper » et créez 4 méthodes statiques permettant de convertir vos objets DTO en Entité et inversement.
(2 pour les objets, et 2 pour les listes d’objets) c’est ici que vous ferez en sorte de transformer votre date de naissance, au niveau de l’entité en âge côté DTO. (Vous pouvez passer par une classe custom « DateTools » pour mettre dedans votre méthode de transformation et l’appeler depuis votre mapper.

## Adaptation de votre UtilisateurService
Jusqu’à présent nous utilisions notre mock pour pouvoir afficher nos données en dur dans notre URL. Nous allons maintenant l’adapter pour afficher les données que nous avons en base. 
Pour cela, activez l’injection automatique de dépendances comme vous avez fait avec le controller de telle sorte à avoir au début de votre classe : 
```
@Autowired
Private UtilisateurRepository repo;
```

Appelez ensuite dans la méthode de votre service la méthode findAll() de votre repository à la place de celle du mock créé précédemment.

## Adaptation de votre UtilisateurController 

Jusqu’à présent, votre mock renvoyait directement une **liste d’UtilisateurDTO,** mais le service lui nous renvoie une **liste d’UtilisateurEntity**. Utilisez donc le mapper créé précédemment pour transformer votre entity en DTO ! 

Après ça, redémarrez votre serveur, rafraîchissez votre URL et vous devriez avoir votre liste d’utilisateurs provenant directement de votre base de données ! 

Nous avons créé notre premier web-service permettant de récupérer l’ensemble de nos utilisateurs. 

Maintenant, avec un peu de recherche, faites en sorte de récupérer un Utilisateur de par son identifiant.
L’url sera de la forme : http://localhost:8080/api/utilisateurs/1
(Astuce : Pour récupérer l’identifiant que vous passez en paramètres, vous pouvez utiliser l’annotation Spring @PathVariable)

to continue....
