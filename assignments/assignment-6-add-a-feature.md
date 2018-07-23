# Assignment 5 Adding a feature

The goal of this assignment is to add a feature to the location-service and see
how CI/CD automatically deploys it to staging (accept).

Assignment: add an optional description column to Location.

 1. Create a branch feature-description in your personal fork created in [assignment-4](assignment-5-create-a-pipeline.md)
 
        git checkout -b feature-description
 
 1. Goto *location-service/src/main/resources/db/migration* and add a new file:
 
    V3__add_description_column.sql
    ```sql
    ALTER TABLE location ADD COLUMN description VARCHAR(255);
    ```
    
    This script will be automatically executed once on application startup.  
    
 2. Open *location-service/src/main/java/nl/toefel/location/service/entity/Location.java*
    and add:
    ```java
    @JsonInclude(Include.NON_EMPTY)
    @Column(name = "description")
    private String description;

    @JsonInclude(Include.NON_EMPTY)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    ```
 3. Commit & push this branch
 
        git commit -am "feature description implemented"
        git push
 
 4. Open a pull-request in github and wait for the PullRequest-pipeline to complete.
 5. Then merge this pull-request to master, and see that it get's deployed to accept. 
 5. Once this is deployed, you can create a new location with a description:
 
     ```bash
     curl -X POST -d '{"name": "mount-everst", "description":"high mountain", "lat": 51.953326, "lon": 4.5586302}' -H 'Content-Type: application/json' [ip-address]:8080/v1/locations
     ```

 5. And retrieve it using:
  
     ```bash
     curl [ip-address]:8080/v1/locations/mount-everst
     ```
