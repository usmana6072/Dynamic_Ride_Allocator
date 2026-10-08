# Dynamic Ride Allocator

Dynamic Ride Allocator is a JavaFX desktop application designed to model urban transportation networks and dynamically assign ride requests to available drivers based on graph traversal and minimum distance algorithms. Built using Java 21/25 and OpenJFX, the application provides role-specific interfaces for riders, drivers, and system administrators, enabling local ride dispatching without external third-party mapping APIs.

## Overview

In urban ride-hailing environments, efficient driver allocation requires calculating optimal routes and proximity across interconnected road networks. Dynamic Ride Allocator models cities as graph structures—where locations act as nodes and roads act as weighted edges—to compute vehicle proximity and allocate drivers dynamically.

The application serves three primary user groups:
- **Riders**: Book rides, view estimated fares based on distance and vehicle type, track ongoing rides, and review trip histories.
- **Drivers**: Manage online availability, accept or decline ride requests, track earnings, and execute trip status updates.
- **Administrators**: Manage city road graph structures (locations and roads), approve new driver registrations, and block or unblock user accounts.

## Features

- **Role-Based Authentication**: Dedicated registration and login flows for Riders, Drivers, and Administrators with account status validation (approval checks for drivers and block list enforcement).
- **Graph-Based City Network**: Representation of urban areas using adjacency lists with configurable bidirectional road distances.
- **Dynamic Driver Allocation**: Nearest-driver lookup using Breadth-First Search (BFS) up to a defined hop limit, combined with a custom Min-Heap data structure to extract the closest available driver.
- **Ride Request and Booking System**: Option to select pickup/drop-off locations and vehicle types (Bike, Car Economy, Car Comfort) with fare calculation proportional to distance.
- **Driver Request Portal**: Real-time incoming ride request queue for drivers to accept or decline bookings.
- **Trip Lifecycle Tracking**: State management for trips (`Ongoing`, `Completed`, `Cancelled`) accessible by both rider and driver dashboards.
- **Administrator Management Screen**: Interface to approve pending driver accounts, monitor user rosters, and modify graph nodes and edges.
- **Local Persistent Data Storage**: Persistence layer using Java Object Serialization and file I/O for storing user profiles, trip records, and road configurations locally (`C:\DRA`).

## Tech Stack

| Technology | Purpose |
|------------|---------|
| Java | Core application logic and algorithmic processing |
| JavaFX 21 (OpenJFX) | Desktop GUI component rendering |
| JavaFX FXML | Declarative layout design for application screens |
| Maven | Build lifecycle management and dependency handling |
| Custom Min-Heap & BFS Graph | Proximity calculation and driver allocation algorithms |
| Java Object Serialization | Binary and text file persistence for local data management |

## Architecture

The project follows a modular Model-View-Controller (MVC) architectural pattern:

- **View Layer**: Defined in `.fxml` files residing under `src/main/resources/Layouts/`, providing structured UI components for each screen.
- **Controller Layer**: Located in `com.example.dynamic_ride_allocator.Controllers` (partitioned into `AdminControllers`, `DriverControllers`, and `RiderControllers`). Controllers manage event handling, user interactions, and view navigation.
- **Model Layer**: Data classes in `com.example.dynamic_ride_allocator.Models` (`Rider`, `Driver`, `Admin`, `Trip`) that store state and implement `Serializable` for persistent storage.
- **Data & Storage Layer**:
  - `UsersData`: Maintains in-memory maps (`HashMap`) of active users and history records while providing binary stream read/write methods to local files.
  - `FileHandler`: Handles plain text export and import of graph locations and road connections.
- **Graph & Algorithm Engine (`graphs` package)**:
  - `CityGraph`: Holds the graph topology using an adjacency list (`Map<String, List<Road>>`).
  - `DriverFinder`: Executes Breadth-First Search (BFS) over the graph to find reachable drivers within a specified number of hops.
  - `DriverMinHeap`: Implements a custom binary min-heap (`heapifyUp`, `heapifyDown`, `extractMin`) to select the driver with the shortest distance to the pickup location.
  - `RideAllocator`: Acts as a façade to coordinate location management, graph initialization, driver lookup, and trip assignment.

## Project Structure

```text
Dynamic_Ride_Allocator/
├── .mvn/
│   └── wrapper/
├── src/
│   └── main/
│       ├── java/
│       │   ├── com/example/dynamic_ride_allocator/
│       │   │   ├── Controllers/
│       │   │   │   ├── AdminControllers/
│       │   │   │   │   ├── AdminDashboardController.java
│       │   │   │   │   ├── AdminProfileController.java
│       │   │   │   │   ├── ApproveDriverScreenController.java
│       │   │   │   │   └── ManageUsersScreenController.java
│       │   │   │   ├── DriverControllers/
│       │   │   │   │   ├── AvailabilityScreenController.java
│       │   │   │   │   ├── CurrentRideScreenController.java
│       │   │   │   │   ├── DriverDashboardController.java
│       │   │   │   │   ├── DriverProfileScreenController.java
│       │   │   │   │   └── RideRequestsScreenController.java
│       │   │   │   ├── RiderControllers/
│       │   │   │   │   ├── BookRideController.java
│       │   │   │   │   ├── CurrentRideController.java
│       │   │   │   │   ├── RideConfirmedController.java
│       │   │   │   │   ├── RideHistoryController.java
│       │   │   │   │   ├── RiderDashboardController.java
│       │   │   │   │   └── RiderProfileController.java
│       │   │   │   ├── LoginController.java
│       │   │   │   ├── RegisterController.java
│       │   │   │   └── WelcomeController.java
│       │   │   ├── DataLayer/
│       │   │   │   └── UsersData.java
│       │   │   ├── Helpers/
│       │   │   │   └── AppendableObjectOutputStream.java
│       │   │   ├── Models/
│       │   │   │   ├── Admin.java
│       │   │   │   ├── Driver.java
│       │   │   │   ├── Rider.java
│       │   │   │   └── Trip.java
│       │   │   ├── graphs/
│       │   │   │   ├── CityGraph.java
│       │   │   │   ├── DriverFinder.java
│       │   │   │   ├── DriverMinHeap.java
│       │   │   │   ├── FileHandler.java
│       │   │   │   ├── RideAllocator.java
│       │   │   │   └── Road.java
│       │   │   └── Main.java
│       │   └── module-info.java
│       └── resources/
│           └── Layouts/
│               ├── admin_dashboard.fxml
│               ├── admin_profile_screen.fxml
│               ├── approve_drivers_screen.fxml
│               ├── book_ride_screen.fxml
│               ├── current_ride_controller.fxml
│               ├── driver_abailability_screen.fxml
│               ├── driver_current_ride_screen.fxml
│               ├── driver_dashboard.fxml
│               ├── driver_profile_screen.fxml
│               ├── driver_ride_requests_screen.fxml
│               ├── login_screen.fxml
│               ├── manage_users_screen.fxml
│               ├── register_screen.fxml
│               ├── ride_confirmed_screen.fxml
│               ├── ride_history_screen.fxml
│               ├── rider_dashboard.fxml
│               ├── rider_profile_screen.fxml
│               └── welcome.fxml
├── mvnw
├── mvnw.cmd
├── pom.xml
└── README.md
```

## Authors

This application was developed by:
- **M. Usman Ali** ([usmana6072](https://github.com/usmana6072))
- **Laraib Khalid** ([laraibkhalid-24](https://github.com/laraibkhalid-24))
