# spring-boot-slingshot

slingshot project with spring boot and spring security and spring data jpa as well as elastic search using jest

# Feature

* Embedded tomcat server
* Spring Data JPA and Spring Security for Authentication
* Spring Data JPA configuration for database
* Jest for ElasticSearch
* Websocket + sockjs + stompjs
* Bootstrap + thymeleaf
* Language (cn + en)

# Configuration

To use this project create a database named spring_boot_slingshot in your mysql database (make sure it is running at localhost:3306)

```sql
CREATE DATABASE spring_boot_slingshot CHARACTER SET utf8 COLLATE utf8_unicode_ci;
```

Note that the default username and password for the mysql is configured to 

* username: root
* password: chen0469

If your mysql or mariadb does not use these configuration, please change the settings in src/resources/config/application-default.properties

# Usage

This is just a template project that provides slingshot. Just use it as the starting point for your spring boot project development.

Note that the application will generate two accounts in the database on startup if they don't exist:

ADMIN:
username: admin
password: admin

DEMO:
username: demo
password: demo



