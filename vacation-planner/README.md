# Smart Vacation Planner

This is a full-stack application designed to help users plan their vacations. The backend is built with Java and Spring Boot, while the frontend is built with Angular.

## Backend

The backend is a Spring Boot application that provides a REST API for managing users, trips, and itineraries.

### Prerequisites

- Java 21
- Maven
- PostgreSQL

### Running the Application

1. **Clone the repository:**
   ```sh
   git clone https://github.com/your-username/vacation-planner.git
   cd vacation-planner/backend
   ```

2. **Configure the database:**

   Open `src/main/resources/application.properties` and update the following properties to match your PostgreSQL configuration:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/your-database
   spring.datasource.username=your-username
   spring.datasource.password=your-password
   ```

3. **Run the application:**
   ```sh
   mvn spring-boot:run
   ```

The application will start on port `8080`.

### API Endpoints

- `POST /api/auth/signup`: Register a new user.
- `POST /api/auth/login`: Authenticate a user and get a JWT token.
- `GET /api/trips`: Get all trips for the authenticated user.
- `POST /api/trips`: Create a new trip.
- `GET /api/trips/{id}`: Get a trip by ID.
- `PUT /api/trips/{id}`: Update a trip.
- `DELETE /api/trips/{id}`: Delete a trip.
- `GET /api/trips/{tripId}/items`: Get all itinerary items for a trip.
- `POST /api/trips/{tripId}/items`: Add an itinerary item to a trip.
- `PUT /api/trips/{tripId}/items/{itemId}`: Update an itinerary item.
- `DELETE /api/trips/{tripId}/items/{itemId}`: Delete an itinerary item.

## Frontend

The frontend is an Angular application that provides a user interface for interacting with the backend API.

### Prerequisites

- Node.js
- Angular CLI

### Running the Application

1. **Navigate to the frontend directory:**
   ```sh
   cd ../frontend
   ```

2. **Install dependencies:**
   ```sh
   npm install
   ```

3. **Run the application:**
   ```sh
   ng serve
   ```

The application will start on port `4200`.