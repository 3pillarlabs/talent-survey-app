# talent-survey-app

This application facilitates the Employees survey done by the Talent Services. 
* In addition to survey it will automate the back end tasks i.e. reports generation, automatic reminders, talent survey launch and shut down etc.
* This application will have features to integrate with web, mobile and other interfaces through REST API.
* Administrators will be able to add/delete/change survey questions, launch/close surveys and reports analysis.

## Highlights
* SpringBoot - Talent survey application will work as Spring Boot Application.

## Technology Stack
* Java 8
* Spring Boot
* Spring REST
* Spring Data
* JPA
* Hibernate
* MySQL
* Angular 4
* Logging - slf4j
* TestNG
* Gradle
* Docker
* Docker Compose

## CI/CD
* Build Status - TravisCI
* Code Quality - SonarCloud

## Structure
The SDK is a multi-module Java application.
```
build.gradle
settings.gradle
tsa-gui/
build.gradle
  src/
    main/java
    test/java
tsa-web/
build.gradle
  src/
    main/java
    test/java
```
