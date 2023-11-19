# Credit Points Calculation Application

## Overview

This application provides a credit points calculation system for users based on the sustainblity actvites they do. Users can submit their activity along with other details, and the application calculates and saves their credit points.

## Table of Contents

- [Installation](#installation)
- [Running the app](#runningtheapp)
- [Endpoints](#endpoints)
- [Data Structure](#data-structure)
- [Frontend](#frontend)

## Installation

1. **Clone the repository:**

   ```bash
   git clone <repository-url>

2. **Navigate to the project directory**

   ```bash
   cd credit-points-application

3. **Install dependancies**

   ```bash
   ./mvnw clean dependency:resolve
   ```

    If you are on a Windows machine, that will be:

    ```bash
   mvnw clean dependency:resolve
   ```

## Running the app

1. **To start the API run the following command in the termianl:**

   ```sh
   ./mvnw spring-boot:run
   ```

   Or on Windows:

   ```cmd
   mvnw spring-boot:run
   ```
2. **Access the application at http://localhost:8080**

## Endpoints

- **GET /api/creditpoints/users:** Get a list of all users.
- **POST /api/creditpoints/calculate:** Adds a user to JSON file and calculates credit points for a user based on submitted activity.
- **GET /api/creditpoints/user/{name}:** Get total points for a specific user.
- **GET /api/creditpoints/office/{office}:** Get total points for a specific office.
- **GET /api/creditpoints/users/sorted:** Get all users sorted by credit points.
- **DELETE /api/creditpoints/user/{name}:** Delete a user by name.
- **PUT /api/creditpoints/user/{name}:** Update a user by name.
- **GET /api/creditpoints/users/sorted:** Get all users sorted by credit points.

This API requires the uses of API testing tools such as postman to handle the post, update and delete requests.

## Data Structure

1. **Example data structure for a post or update request:**

```json
{
  "name": "John Doe",
  "office": "Office A",
  "activity": "Cycling",
  "creditPoints": 50
}

```
2. **The output for a post request will look like this**

Credit points: 10

3. **The output for a post request will look like this**

4. **The output for a post request will look like this**

5. **The output for a post request will look like this**

## Frontend

You can accecss the project frontend by opeing the index file in flie explorer. 

### Note:

Not all the crud opperations are avaible in the front end