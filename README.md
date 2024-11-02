# Sngular / Inditex Java Developer Test
This is a test for Sngular / Inditex as applicant for the Java Developer position performed by Jesus Gregorio Perez.

# Description
This project is a Spring Boot application that implement 1 endpoint:
1. GET /api/v1/prices 
- It accepts as input parameters: application date, product identifier and brand identifier.
- It returns as output data: product identifier, brand identifier, price list identifier, start date, end date and price.

# Requirements
For building and running the application you need:
- Maven 3
- JDK 21

# Running the application locally
Compilation and installation:
```
mvn clean install
```

Running tests:
```
mvn test
```

Running the application using Spring Boot Maven plugin:
```
mvn spring-boot:run
```

Test endpoint:
```
curl -XGET 'http://localhost:8080/api/v1/prices?productId=35455&brandId=1&date=2020-06-14T16:00:00'
```

# Technologies
- This project employs Java 21, Spring Boot 3.2.2, and Maven 3.
- While OpenJDK 21.0.2 has been used for development, other distributions might be considered for production.
- Lombok is utilized for automatic code generation via annotations, enhancing code readability.
- Spring Web is included for the presentation layer.
- Spring Data JPA is used for the persistence layer.
- H2 is used as a development database. Consider another database engine for integration and production.
- MapStruct is integrated for object mapping.
- Junit, Mockito and RestTemplate is employed for testing.

# Architecture
This project follows a Layered Architecture. It includes the following layers: 
- Presentation layer containing controller class for handling HTTP requests.
- Business layer containing service class implementing business logic.
- Persistence layer containing repository interfaces for abstracting database access.
- Database layer containing entity classes representing the database schema.

# Design
I will design a REST API based on Java, using the Spring Boot framework to expose the requested endpoints.

Key aspects of the implementation will not be considered for this project:
- Security measures using Spring Security, although crucial for real-world internet-exposed endpoints, are omitted as this is a test scenario.
- OpenAPI documentation which is recommended for proper API documentation.
- Develop Dockerfile to deploy isolated development environment and make easier reproducibility.
- Logging for Debugging and Troubleshooting.
- Centralized exception handling using ControllerAdvice.
- Data specifications have not been provided, therefore, it is necessary to review the sizes of the database columns.
- Different Spring profiles configuration for multiple environments. 

## Directory structure
Directory structure follows a Maven standard directory layout including src/main/ java and resources for source code and src/test/ java and resources for Testing.

Package name follow Java convention for layered architecture:
- controller: Presentation layer containing controller class for handling HTTP requests.
- service: Business layer containing service class implementing business logic.
- repository: Persistence layer containing repository interfaces for abstracting database access.
- model: Database layer containing entity classes representing the database schema.

Additionally, the following packages have been included:
- config: Spring context configurations.
- dto: Data transfer objects.
- mapper: MapStruct configuration for mapping objects.

## Presentation layer
[PriceController.java](src%2Fmain%2Fjava%2Fcom%2Fsngular%2Ftest%2Finditex%2Fcontroller%2FPriceController.java)

The first segment of the route is a prefix /api because:
- It identifies that the endpoints belong to an API.
- It is a convention widely adopted.
- It facilitates grouping of endpoints for common configurations like security policies.

The second segment of the route is /v1 because:
- It facilitates versioning.
- It clearly identifies the version for linking documentation.

For the endpoint, we will use the HTTP GET method and the route /api/v1/prices because:
- The GET method denotes that we are getting a resource.
- /prices indicates that a resource of type price will be returned.
- Accepts product identifier, brand identifier, and application date request parameters for filtering prices.
- It returns the highest priority price that aligns with the specified filters, as prices are ordered in descending order of priority. 
- The result includes: product identifier, brand indentifier, price list, start date, end date, price and currency.
- If there is no match it returns error 404 not found.

## Business layer
[PriceServiceImpl.java](src%2Fmain%2Fjava%2Fcom%2Fsngular%2Ftest%2Finditex%2Fservice%2FPriceServiceImpl.java)
The business layer serves as an intermediary between the presentation and persistence layers.

It contains business logic, however, in this sample project, it is reduced to execute the find method from the persistence layer while specifying filters, sorting and limiting rows options. Additionally, it maps the result to DTO. 

## Persistence layer
[PriceRepository.java](src%2Fmain%2Fjava%2Fcom%2Fsngular%2Ftest%2Finditex%2Frepository%2FPriceRepository.java)
The persistence layer is between the business layer and the database layer.

PriceRepository extends from JPARepository, providing access to query methods.

## Database layer
[PriceEntity.java](src%2Fmain%2Fjava%2Fcom%2Fsngular%2Ftest%2Finditex%2Fdomain%2FPriceEntity.java)
The database layer reflects the database schema in the entity: [PriceEntity.java](src/main/java/com/sngular/test/inditex/domain/PriceEntity.java)

PriceEntity field price is defined as BigDecimal to 
- Provide exact decimal arithmetic, avoiding floating-point precision issues
- Allows specifying precision = 4 and scale = 2. This field must be fixed following database field requirements. 

## Database initialization
Script-base and Hibernate initialization have been disabled on application.yml configuration file. 

The following scripts are provided for database initialization during testing stage.
[delete_prices_data.sql](src%2Fmain%2Fresources%2Fdelete_prices_data.sql)
[insert_prices_data.sql](src%2Fmain%2Fresources%2Finsert_prices_data.sql)
[prices_schema.sql](src%2Fmain%2Fresources%2Fprices_schema.sql)

Price is defined as a DECIMAL to:
- Provide fixed precision = 4 and scale = 2. Total digits are 4. Decimal places are 2.
- The maximum allowable value is 99,99. This value is established based on the test requirements. However, this limitation may be subject to review for production purposes. 

## Cache
Caching endpoint /api/v1/prices to improve performance when request by the same parameters.

## Sorting and limiting
Sorting is utilized when querying prices from the database:
- Sorting by priority descending is implemented when searching for prices to database.
- Result set is limited to 1 result. 
- As a result of sorting, it returns the highest priority price that aligns with the specified filters.

# Patterns
- Builder Pattern: Used for the creation of DTO objects during testing stage.
- Dependency injection Pattern with Constructor Injection: Used to promote loose coupling and improving code reusability.

# Tests
- Integration testing is conducted to verify that required tests 1, 2, 3, 4, and 5 ([TestJava2023 (2).txt](TestJava2023%20%282%29.txt)) pass successfully: [PriceIntegrationTest.java](src%2Ftest%2Fjava%2Fcom%2Fsngular%2Ftest%2Finditex%2Fintegration%2FPriceIntegrationTest.java).
- Presentation layer is tested on [PriceControllerTest.java](src%2Ftest%2Fjava%2Fcom%2Fsngular%2Ftest%2Finditex%2Fcontroller%2FPriceControllerTest.java).
- Persistence layer is tested in [PriceRepositoryTest.java](src%2Ftest%2Fjava%2Fcom%2Fsngular%2Ftest%2Finditex%2Frepository%2FPriceRepositoryTest.java).
