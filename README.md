# order-management-API-platform

## Data Storing

In memory Cache is used to store data. If it is migrated to a database,
below DDL scripts can be used to create tables.
```
CREATE TABLE User (
    email VARCHAR(255) NOT NULL PRIMARY KEY,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL
);

CREATE TABLE `Order` (
    reference_number VARCHAR(50) NOT NULL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    item_name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    shipping_address TEXT NOT NULL,
    status ENUM('NEW', 'DISPATCHED', 'CANCELLED') DEFAULT 'NEW',
    FOREIGN KEY (email) REFERENCES User(email)
);
```
## Dockerize the Application
```
docker build -t order-management-api .
docker run -p 8000:8000 order-management-api
```