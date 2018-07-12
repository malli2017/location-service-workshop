# Location service

Service that stores locations, points of interest. Provides a REST API to CRUD the locations.

## Running the service

docker run --name location-postgres -p 5432:5432 -e POSTGRES_USER=locationservice -e POSTGRES_PASSWORD=locationservice -d postgres