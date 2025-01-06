Recipe Service Backend
This project contains the backend service for managing recipes. It is built using Spring Boot and uses an H2 database to store and retrieve recipes. The backend exposes various REST API endpoints to allow interaction with the recipe data.

Table of Contents
1. Backend Setup and Run Instructions
2. API Endpoints
3. Technologies Used
4. Database Configuration
5. Logging Configuration

Backend Setup and Run Instructions
1. Clone the Repository
Clone the repository to your local machine:
git clone https://github.com/akashpratap0703/recipe-service.git
2. Install Dependencies
Make sure you have Java 11 or higher installed. You also need Maven to manage dependencies and build the application.
To install the dependencies, run:
cd recipe-service
mvn install
3. Configure Database
The backend service uses an H2 database for storing recipes. It is configured as an embedded database. You can configure the database connection in the application.properties file located in src/main/resources.

Here is the database configuration:

properties
spring.application.name=recipeService

spring.h2.console.enabled=true
spring.h2.console.path=/console/
spring.datasource.url=jdbc:h2:file:C:/project/recipeService/recipeService/src/main/resources/data/foodpanda
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=password
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
H2 Database: In-memory database for development.
Database URL: The path to the H2 database file.
Console: You can access the H2 console at http://localhost:8080/console/ (Make sure spring.h2.console.enabled=true).
4. Run the Backend
To start the backend service, use the following Maven command:

mvn spring-boot:run
This will run the application on http://localhost:8080 by default. You can now make requests to the exposed API endpoints.

5. Access the H2 Database Console
If you want to interact with the database, you can access the H2 console at the following URL:

http://localhost:8080/console/
Use the following credentials to log in:

JDBC URL: jdbc:h2:file:C:/project/recipeService/recipeService/src/main/resources/data/foodpanda
Username: sa
Password: password
create receipe table with below query
CREATE TABLE recipes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    ingredients MEDIUMTEXT,
    Instructions MEDIUMTEXT,
    tags MEDIUMTEXT,
   meal_types MEDIUMTEXT,
    prep_time_minutes INT,
    cook_time_minutes INT,
    servings INT,
    difficulty VARCHAR(50),
    cuisine VARCHAR(50),
    calories_per_serving INT,
    image VARCHAR(255),
    rating DECIMAL(3, 2),
    review_count INT,
    user_id INT
);

API Endpoints
The backend exposes the following API endpoints:

1. POST /recipes/list
Description: Save a list of recipes.
Request Body: A list of Recipe objects (name, cuisine, image, etc.).
Response: Returns the saved recipes.
Response Code: 200 OK on success.
2. GET /recipes/list
Description: Fetch all recipes from the database.
Response: A list of Recipe objects.
Response Code: 200 OK on success.
3. GET /recipes/list/{id}
Description: Fetch a single recipe by its ID.
Path Variable: id (Recipe ID).
Response: A Recipe object.
Response Code:
200 OK if the recipe exists.
404 Not Found if the recipe does not exist.
4. GET /recipes/search
Description: Search for recipes based on a search parameter.
Request Param: searchParam (A string to search recipes by name, cuisine, etc.).
Response: A list of Recipe objects matching the search query.
Response Code:
200 OK if the search is successful.
400 Bad Request if the search parameter is invalid.

Technologies Used
1. Spring Boot: Java framework used to create RESTful APIs.
2. H2 Database: Embedded database used to store recipes.
3. Swagger/OpenAPI: For API documentation.
4. SLF4J: Used for logging.
5. Database Configuration
The application uses H2 as the default database. The database is configured to be embedded and stored in the following directory:

properties
spring.datasource.url=jdbc:h2:file:C:/project/recipeService/recipeService/src/main/resources/data/foodpanda
If you want to change the database settings, modify the spring.datasource.url, spring.datasource.username, and spring.datasource.password properties in the application.properties file.

Logging Configuration
The backend service uses SLF4J for logging. All API interactions are logged with relevant information (e.g., number of recipes fetched, recipe ID).

To change the logging level, modify the application.properties file:

properties
logging.level.root=DEBUG
This will enable DEBUG level logging, which is helpful for troubleshooting and development.

