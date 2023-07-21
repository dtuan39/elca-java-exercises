# elca-java-exercises

# ELCA Java exercises for FPT University bootcamp program

**Project Description**: This is a small application exercise that implements a Project Information Management (PIM) tool using Java Spring Boot, Hibernate, H2 Database (for development), and Angular for the frontend. The project is targeted at newcomers to the .NET and Java group and focuses on two main functionalities: Project creation and updation, and Project list.

## Table of Contents

1. [Introduction](#introduction)
2. [Technologies Used](#technologies-used)
3. [Features](#features)
4. [Setup](#setup)
5. [Usage](#usage)
6. [Deployment](#deployment)
7. [Testing](#testing)
8. [Contribution](#contribution)
9. [License](#license)
10. [Acknowledgements](#acknowledgements)

## Introduction

This application exercise is designed to help newcomers in the .NET and Java group gain practical experience with Java Spring Boot, Hibernate, H2 Database, and Angular. The project implements a simple Project Information Management (PIM) tool with two main user stories: Project creation and updation, and Project list. The tool allows Project Managers to create and update projects and view a list of existing projects based on various search criteria.

## Technologies Used

The main technologies and libraries used in this project are:

- Java Spring Boot
- Hibernate
- H2 Database (for development)
- Angular (for frontend)
- Log4j
- Mapstruct
- Lombok

## Features

The application exercise implements the following features:

1. Project Creation and Updation:
   - Project Managers can create new projects with attributes such as Number, Name, Customer, Group, Members, Status, Start Date, and End Date.
   - Existing projects can be updated with new information.
   - Validations are in place for mandatory fields, unique project numbers, valid visas, and unexpected errors.

2. Project List:
   - Project Managers can view a list of projects.
   - They can search for projects based on Name, Number, Customer, and Status.
   - Pagination is not required in the basic level.
   - Projects can be deleted if their status is "New."

## Setup

To set up the development environment for this project, follow the steps below:

1. Clone the repository from GitHub.
2. Make sure you have Java, Node.js, and Angular CLI installed on your system.
3. Install the required dependencies for both the backend (Java Spring Boot) and frontend (Angular) parts of the application.
4. Set up the H2 Database for development purposes.
5. Configure the necessary properties for the application.

## Usage

After setting up the development environment, you can run the application locally. Here's how:

1. Start the backend server (Java Spring Boot).
2. Start the frontend server (Angular).
3. Access the application in your web browser at `http://localhost:<port>`.

## Deployment

To deploy the application and make it available on the internet, follow these steps:

1. Containerize the application using Docker.
2. Deploy the Docker container to your desired hosting environment (e.g., AWS, Google Cloud, Azure, etc.).
3. Set up a domain name and configure DNS settings to point to your deployed application.

## Testing

The application exercise comes with a set of predefined test cases. To run the tests, use the testing framework specific to each part of the application (JUnit for Java Spring Boot, Karma/Jasmine for Angular).

## Contribution

Contributions to this project are welcome! If you find any bugs, have suggestions for improvements, or want to add new features, feel free to open an issue or submit a pull request.

## License

This project is licensed under the [MIT License](LICENSE).

## Acknowledgements

We would like to express our gratitude to the creators of the technologies and libraries used in this project. Their contributions have been instrumental in building this small application exercise for newcomers to the .NET and Java group.
