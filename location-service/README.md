# Location service

Service that stores locations, points of interest. Provides a REST API to CRUD the locations.

## Running the service

Start the database 

    docker run --name location-postgres -p 5432:5432 -e POSTGRES_USER=locationservice -e POSTGRES_PASSWORD=locationservice -d postgres

Run the app

    Application
    
Insert a customer

```bash
curl -X POST -d '{"firstName": "christophe", "lastName": "hesters"}' -H 'Content-Type: application/json' localhost:8080/customers
{"id":4,"firstName":"christophe","lastName":"hesters"}

curl localhost:8080/customers/4
{"id":4,"firstName":"christophe","lastName":"hesters"}
```

Insert a location

```bash
curl -X POST -d '{"name": "CGI Rotterdam", "lat": 51.953326, "lon": 4.5586302}' -H 'Content-Type: application/json' localhost:8080/locations


```
