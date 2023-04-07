
# Favourite Recipe Service

A Java standalone app that lets users manage their favorite recipes. It ought to support fetching, updating, adding, and removing recipes. Moreover, users ought to be able to narrow down the selection of recipes using one or more of the following standards:

1. Whether or not the dish is vegetarian
2. The number of servings
3. Specific ingredients (either include or exclude)
4. Text search within the instructions.

For example, the API should be able to handle the following search requests:

1. All vegetarian recipes

2. Recipes that can serve 4 persons and have “potatoes” as an ingredient

3. Recipes without “salmon” as an ingredient that has “oven” in the instructions.



## API Reference
Postman Collection provided in the project.

Database diagram also provided in the project

## How To Run Application

Please run provided docker-compose.yml file before starting the application.
With the terminal, go to project directory and run 'docker-compose up'. Then docker will create postgreSQL database on 5432 port with provided username/psw and database name.



#### Documentation 

```http
  localhost:8080/documentation 
```



## Tech Stack

**Technologies:** Java 17, Spring Boot , Docker, PostgreSQL,  Junit, Mockito, Integration Test, Test Container.

