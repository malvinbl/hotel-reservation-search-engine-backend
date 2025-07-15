# Hotel Reservation Search Engine Backend

## Overview
The repo contains the implementation of a Rest API that provides a solution to the Hotel Reservation Search Engine Challenge.

"Hotel Reservation Search Engine Challenge"

Objective:

- Application development with Spring Boot.

- The application must provide two REST calls:

  - POST call to the /search path with the following structure:
    ```
    {
        "hotelId": "1234aBc",
        "checkIn": "29/12/2023",
        "checkOut": "31/12/2023",
        "ages": [30, 29, 1, 3]
    }
    ```

    - It will return an identifier attributed to this search:
      ```
      {
          "searchId": "xxxxx"
      }
      ```

  - GET call to the /count path with a single parameter called
  searchId. This parameter will contain the value returned by the previous /search POST request and will return the number of similar searches:
    ```
    {
        "searchId": "xxxxx",
        "search": {
          "hotelId": "1234aBc",
          "checkIn": "29/12/2023",
          "checkOut": "31/12/2023",
          "ages": [3, 29, 30, 1]
        }
        "count": 100
    }
    ```
```

```
The call to /search must perform the following actions:

- Validate the submitted payload

- The payload must be received in an immutable object

- That payload must be sent to the Kafka topic 'hotel_availability_searches'

A Kafka topic consumer must be added, 'hotel_availability_searches', where messages are collected and persisted in a database (Mongo or PostgreSQL).
```

```
Important Notes:
- Lombok cannot be used

- The application must include tests


## Guidelines

1. Docker installed on the PC where the application will be executed is required

2. Go to the project directory

3. Run
    ```sh
   docker compose up -d app
    ```

### Swagger
http://localhost:8080/api/v1/swagger-ui/index.html

#### Data to consult:

POST example request

```
{
    "hotelId": "1234aBc", 
    "checkIn": "25/06/2025", 
    "checkOut": "25/06/2025", 
    "ages": [30, 29, 1, 3]
}
```

### Kafka Client GUI
http://localhost

### Test coverage report

- Run
    ```sh
   ./mvnw clean verify
    ```

target/site/jacoco/index.html
[See coverage report](target/site/jacoco/index.html)

```
Or have IntelliJ IDEA installed

right button → Run with Coverage
```

## More Info
It is used:

Maven

Java 21

Spring Boot v3.5.3

MongoDB Data Base

Junit + Mockito

Kafka

Docker
