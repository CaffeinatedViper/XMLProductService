# Product Management API Project

This Spring Boot project is designed to demonstrate the importing and management of product data from an XML file. It provides capabilities for viewing product details, including searching by name. The project is built using Gradle and configured to handle XML files and offer a REST API.

## Project Setup
1. Build the project using the Gradle Wrapper:
``` r
./gradlew build
```
2. Run the application:
``` r
./gradlew boot
```
## Endpoints
- `POST /api/products/upload`: Loads the XML file and returns the number of records.
- `GET /api/products`: Returns a list of all products.
- `GET /api/products/search?name={product_name}`: Returns information about a product with the specified name.
## Documentation

For more detailed information about the project and endpoint descriptions, refer to the Swagger documentation available after starting the application at `http://localhost:8080/swagger-ui.html`.