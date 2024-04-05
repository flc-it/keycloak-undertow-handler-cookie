# keycloak-undertow-handler-cookie

## Présentation
Le projet *keycloak-undertow-handler-cookie* est le handler Undertow pour passer les Cookies à **SameSite:None**.

## Version

La branche **main** nécessite :
- [Undertow Core >= 2.1.0.Final](https://mvnrepository.com/artifact/io.undertow/undertow-core/2.1.0.Final)

La branche **feat/old-version** nécessite :
- [Undertow Core >= 2.0.0.Final](https://mvnrepository.com/artifact/io.undertow/undertow-core/2.0.0.Final)

### Version récente de WildFly (>= 19.1.0)
A partir de la version **WildFly Core >= 11.1.0** ce module n'est plus utile.
    
La configuration se fait au choix dans la conf Undertow ou WildFly :
- WEB-INF/**undertow-handlers.conf** avec samesite-cookie(mode=None) dedans (pour le deployment via overlay [voir ici](https://docs.wildfly.org/19.1/Admin_Guide.html#Deployment_Overlays))
- Ajout du filter **samesite-cookie** dans le standalone.xml (pour le deployment via WildFly CLI [voir ici](https://stackoverflow.com/questions/65017224/how-to-set-samesite-cookie-on-wildfly-20))

## Version à utiliser dans Keycloak
Pour connaître la version du module **Undertow Core** utilisé par Keycloak :  
Voir le jar sur le server à l'emplacement => modules\system\layers\base\io\undertow\core\main\undertow-core-{version}.jar
    
Pour connaître la version de **WildFly** utilisé par Keycloak :  
Voir le jar sur le server à l'emplacement => modules\system\layers\base\org\wildfly\extension\undertow\main\wildfly-undertow-{version}.jar