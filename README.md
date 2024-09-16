# Car Dealership Management System

## Overview

The **Car Dealership Management System** is a web application designed for car dealerships and their administrators to streamline customer registration, consultation, and automobile selection processes.

## Features

- **Customer Registration**: Administrators can register new customers, saving their preferences and contact details.
- **Car Search and Filtering**: Allows administrators to find available cars using various filters and criteria.
- **Modification of Customer Preferences**: Administrators can modify customer preferences to help them choose the best configuration.
- **Supplier Collaboration**: Enables collaboration with car suppliers, updating the inventory and establishing partnerships with other companies.
- **New Car Registration**: Allows administrators to add new cars to the system.

## Key Advantages

- Efficient management of customer needs and preferences.
- A convenient system for searching and filtering cars.
- Support for consultations and assistance in customer decision-making.
- Collaboration with suppliers to expand the range of automobiles.

## System Setup

### Prerequisites

1. **MySQL Database**: The project uses MySQL as the database.
2. **Database Name**: The database should be named `carsalon`.
3. **Database Credentials**: Modify the `application.yml` file to include your MySQL database username and password.

### Database Configuration

The necessary database tables are created automatically if they do not exist. To use ready-made data, the following text files are provided:

- `cars.txt`
- `clients.txt`
- `suppliers.txt`
- `supplier_car.txt`
- `client_car_supplier.txt`

These files contain pre-filled data that can be imported into the database.

### Image Upload

Ensure that the system allows image uploads, as it includes functionality for adding car images.

## Database Structure

### Tables

#### Cars

Stores data about cars. No field can be null.

- `Id` (auto increment) – Primary key
- `Brand` (varchar) – Car brand
- `Model` (varchar) – Car model
- `Car_type` (varchar) – Car type
- `Transmission_type` (varchar) – Transmission type
- `Fuel_type` (varchar) – Fuel type
- `Fuel_consumption` (decimal) – Fuel consumption
- `Price` (decimal) – Car price
- `Used` (Boolean) – 1 for used cars, 0 for new
- `Photo_path` (varchar) – Path to the car's image
- `Year` (int) – Year of manufacture

#### Clients

Stores customer data and preferences. Preferences can be null.

- `Id` (auto increment) – Primary key
- `Client_name` (varchar) – Customer's full name
- `Email` (varchar) – Customer's email
- `Car_type` (varchar) – Preferred car type
- `Transmission_type` (varchar) – Preferred transmission type
- `Fuel_type` (varchar) – Preferred fuel type
- `Max_fuel_consumption` (decimal) – Maximum fuel consumption
- `Max_price` (decimal) – Maximum price
- `Used` (Boolean) – 1 for used cars, 0 for new
- `Photo_path` (varchar) – Path to the customer's preferred car image
- `Year` (int) – Preferred car year

#### Suppliers

Stores data about suppliers.

- `Id` (auto increment) – Primary key
- `Name` (varchar) – Supplier's name
- `Email` (varchar) – Supplier's email
- `Price` (decimal) – Delivery price
- `Deliver_days` (int) – Delivery time

#### Supplier_car

Represents a many-to-many relationship between cars and suppliers.

- `Id` (auto increment) – Primary key
- `Supplier_id` (foreign key) – Reference to the supplier
- `Car_id` (foreign key) – Reference to the car

#### Client_car_supplier

Records customer orders.

- `Id` (auto increment) – Primary key
- `Supplier_id` (foreign key) – Reference to the supplier
- `Client_id` (foreign key) – Reference to the client
- `Car_id` (foreign key) – Reference to the car
- `Comments` (varchar) – Order comments
- `Created_at` (timestamp) – Order creation time

## Project Structure

### Packages

- **Controllers**: Manages pages displaying and interacting with cars, clients, and orders.
  - `CarController`
  - `ClientController`
  - `OrderController`
  
- **Data**: Contains entity classes representing database tables:
  - `Car`
  - `Client`
  - `Supplier`
  - `SupplierCar`
  - `ClientCarSupplier`
  
- **Repositories**: Spring Data JPA repositories for accessing database entities.
  - `CarRepository`
  - `ClientRepository`
  - `SupplierRepository`
  - `ClientCarSupplierRepository`
  - `SupplierCarRepository`
  
- **Services**: Business logic and service layer for entities.
  - `CarService`
  - `ClientService`
  - `SupplierService`
  - `ClientCarSupplierService`
  - `SupplierCarService`

### Directories

- **Static/images/cars**: Directory to store car images.
  
- **Templates**: Contains HTML templates for web pages.
  - **/car**:
    - `add_car` – Form for adding a new car.
    - `car_filter` – Displays cars based on customer preferences.
    - `car_info` – Displays detailed information about a selected car.
    - `cars` – Displays all cars.
    - `no_car` – Message for when no cars match the search criteria.
  
  - **/client**:
    - `add_client` – Form for adding a new client.
    - `client_info` – Displays detailed information about a selected client.
    - `clients` – Displays all clients without orders.
  
  - **/order**:
    - `order_info` – Displays detailed order information.
    - `orders` – Displays all orders.
  
  - **/fragments**:
    - `header` – Top menu with links to various pages.
    - `modify_client` – Form for modifying client preferences.
    - `order_modul` – Form for placing an order.

## Deployment

To deploy the project:

1. Clone the repository.
2. Set up the MySQL database with the name `carsalon`.
3. Adjust the database credentials in `application.yml`.
4. Import the provided `.txt` files into the database for pre-filled data.
5. Run the project.

