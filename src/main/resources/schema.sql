-- Create database
CREATE DATABASE IF NOT EXISTS airways_db;
USE airways_db;

-- Countries table
CREATE TABLE IF NOT EXISTS countries (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    code VARCHAR(2) NOT NULL UNIQUE
);

-- Cities table
CREATE TABLE IF NOT EXISTS cities (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    country_id BIGINT NOT NULL,
    FOREIGN KEY (country_id) REFERENCES countries(id)
);

-- Airports table
CREATE TABLE IF NOT EXISTS airports (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    code VARCHAR(3) NOT NULL UNIQUE,
    city_id BIGINT NOT NULL,
    terminal VARCHAR(50),
    latitude DOUBLE,
    longitude DOUBLE,
    FOREIGN KEY (city_id) REFERENCES cities(id)
);

-- Aircraft table
CREATE TABLE IF NOT EXISTS aircraft (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    registration_number VARCHAR(50) NOT NULL UNIQUE,
    model VARCHAR(100) NOT NULL,
    manufacturer VARCHAR(100) NOT NULL,
    total_seats INT NOT NULL,
    economy_seats INT NOT NULL,
    business_seats INT NOT NULL,
    first_class_seats INT NOT NULL,
    status VARCHAR(20) NOT NULL
);

-- Flights table
CREATE TABLE IF NOT EXISTS flights (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    flight_number VARCHAR(20) NOT NULL UNIQUE,
    departure_airport_id BIGINT NOT NULL,
    arrival_airport_id BIGINT NOT NULL,
    departure_time DATETIME NOT NULL,
    arrival_time DATETIME NOT NULL,
    aircraft_id BIGINT NOT NULL,
    economy_price DECIMAL(10, 2) NOT NULL,
    business_price DECIMAL(10, 2) NOT NULL,
    first_class_price DECIMAL(10, 2) NOT NULL,
    available_seats INT NOT NULL,
    status VARCHAR(20) NOT NULL,
    gate VARCHAR(10),
    FOREIGN KEY (departure_airport_id) REFERENCES airports(id),
    FOREIGN KEY (arrival_airport_id) REFERENCES airports(id),
    FOREIGN KEY (aircraft_id) REFERENCES aircraft(id)
);

-- Passengers table
CREATE TABLE IF NOT EXISTS passengers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    phone VARCHAR(20) NOT NULL,
    passport_number VARCHAR(50) NOT NULL UNIQUE,
    date_of_birth DATE NOT NULL,
    gender VARCHAR(10) NOT NULL,
    nationality VARCHAR(100)
);

-- Users table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at DATETIME NOT NULL,
    last_login DATETIME
);

-- Bookings table
CREATE TABLE IF NOT EXISTS bookings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_reference VARCHAR(50) NOT NULL UNIQUE,
    flight_id BIGINT NOT NULL,
    passenger_id BIGINT NOT NULL,
    user_id BIGINT,
    seat_class VARCHAR(20) NOT NULL,
    seat_number VARCHAR(10) NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    status VARCHAR(20) NOT NULL,
    booking_date DATETIME NOT NULL,
    FOREIGN KEY (flight_id) REFERENCES flights(id),
    FOREIGN KEY (passenger_id) REFERENCES passengers(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Payments table
CREATE TABLE IF NOT EXISTS payments (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    booking_id BIGINT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payment_method VARCHAR(20) NOT NULL,
    status VARCHAR(20) NOT NULL,
    payment_date DATETIME NOT NULL,
    transaction_id VARCHAR(100),
    FOREIGN KEY (booking_id) REFERENCES bookings(id)
);

-- Insert sample data
INSERT INTO countries (name, code) VALUES 
('United States', 'US'),
('United Kingdom', 'GB'),
('India', 'IN'),
('United Arab Emirates', 'AE'),
('Singapore', 'SG');

INSERT INTO cities (name, country_id) VALUES 
('New York', 1),
('Los Angeles', 1),
('London', 2),
('Mumbai', 3),
('Delhi', 3),
('Dubai', 4),
('Singapore', 5);

INSERT INTO airports (name, code, city_id, terminal, latitude, longitude) VALUES 
('John F. Kennedy International Airport', 'JFK', 1, 'Terminal 4', 40.6413, -73.7781),
('Los Angeles International Airport', 'LAX', 2, 'Terminal 2', 33.9416, -118.4085),
('London Heathrow Airport', 'LHR', 3, 'Terminal 5', 51.4700, -0.4543),
('Chhatrapati Shivaji International Airport', 'BOM', 4, 'Terminal 2', 19.0896, 72.8656),
('Indira Gandhi International Airport', 'DEL', 5, 'Terminal 3', 28.5562, 77.1000),
('Dubai International Airport', 'DXB', 6, 'Terminal 3', 25.2532, 55.3657),
('Singapore Changi Airport', 'SIN', 7, 'Terminal 3', 1.3644, 103.9915);

INSERT INTO aircraft (registration_number, model, manufacturer, total_seats, economy_seats, business_seats, first_class_seats, status) VALUES 
('N12345', 'Boeing 777-300ER', 'Boeing', 350, 280, 50, 20, 'ACTIVE'),
('N67890', 'Airbus A380-800', 'Airbus', 525, 425, 80, 20, 'ACTIVE'),
('N54321', 'Boeing 787-9 Dreamliner', 'Boeing', 290, 234, 48, 8, 'ACTIVE'),
('N98765', 'Airbus A350-900', 'Airbus', 325, 265, 48, 12, 'ACTIVE');

INSERT INTO users (username, email, password, first_name, last_name, role, created_at) VALUES 
('admin', 'admin@airways.com', '$2a$10$JKPvT5OiRVcGVz7RJHhzZeI/8Mq9l5VqXYcMK7KVmX7S3FH.7qJoK', 'Admin', 'User', 'ADMIN', NOW()),
('user1', 'user1@example.com', '$2a$10$JKPvT5OiRVcGVz7RJHhzZeI/8Mq9l5VqXYcMK7KVmX7S3FH.7qJoK', 'John', 'Doe', 'USER', NOW());

INSERT INTO flights (flight_number, departure_airport_id, arrival_airport_id, departure_time, arrival_time, aircraft_id, economy_price, business_price, first_class_price, available_seats, status, gate) VALUES 
('AW101', 1, 3, '2025-11-01 08:00:00', '2025-11-01 20:00:00', 1, 450.00, 1200.00, 2500.00, 350, 'SCHEDULED', 'A12'),
('AW102', 3, 1, '2025-11-02 10:00:00', '2025-11-02 14:00:00', 2, 480.00, 1300.00, 2600.00, 525, 'SCHEDULED', 'B8'),
('AW103', 4, 6, '2025-11-03 14:00:00', '2025-11-03 18:00:00', 3, 350.00, 950.00, 1800.00, 290, 'SCHEDULED', 'C5'),
('AW104', 6, 7, '2025-11-04 22:00:00', '2025-11-05 06:00:00', 4, 280.00, 800.00, 1500.00, 325, 'SCHEDULED', 'D3'),
('AW105', 2, 5, '2025-11-05 06:00:00', '2025-11-06 02:00:00', 1, 550.00, 1500.00, 3000.00, 350, 'SCHEDULED', 'E7');
