# AudioBook Project

## Prerequisites

Ensure you have the following installed:

- **Java 17** or later

- **Maven** (latest version recommended)

- **MySQL Server** (or another compatible database)

- **Postman** (optional, for API testing)

## Project Setup

### 1. Clone the Repository

```
git clone <repository-url> 
cd AudioBook
```

### 2. Configure the Database

1. Create a new MySQL database:

   ```
    CREATE DATABASE audiobook_db;
   ```

3. Import the schema from src/main/resources/db/migration/schema/Dump20250324.sql:
   
   ```
    mysql -u <username> -p audio_book < src/main/resources/db/migration/schema/Dump20250324.sql
   ```
   
3. Update database credentials in src/main/resources/application.properties:

   ```
    spring.datasource.url=jdbc:mysql://localhost:3306/audio_book
    spring.datasource.username=your-username
    spring.datasource.password=your-password
   ```

### 3. Build and Run the Project

Using Maven
  
  ```
  mvn clean install
  mvn spring-boot:run
  ```

Using Executable JAR

  ```
  mvn clean package
  java -jar target/audiobook-0.0.1-SNAPSHOT.jar
  ```

### 4. Access the Application

- API will be available at: http://localhost:8080

- Swagger UI (if enabled): http://localhost:8080/swagger-ui.html
