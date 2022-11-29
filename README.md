# iwa-project

This is the backend of a web application made for people searching for seasonal jobs. 
This project was created during the 5th year of the Computer Science and management curriculum at Polytech Montpellier.

## How to run locally

### Prerequesities

You must have the following software installed on your computer to be able to run the backend locally

- Docker Compose version at least 2.12.2
- Docker version at least 20.10.21

We recommend using Docker Desktop to make the installation of both software easier.

### Running the project

1. Clone this repository using `git clone https://github.com/MarouanLaroui/iwa-project.git`
2. Go into the `iwa-project` directory that was created. 
There must be 6 sub directories `gateway-ms`, `user-ms`, `offer-ms`, `recruitement-ms`, `feedback-ms`, `kafka`.
3. Go into the `kafka` sub directory and run the command `docker-compose up`. **Wait that the container is running before continuing to step 4.**
4. Go into the 5 sub directories that ends with `-ms` and run the command `docker-compose --env-file .env.dev up --build`.
This will build the micro services containers.
