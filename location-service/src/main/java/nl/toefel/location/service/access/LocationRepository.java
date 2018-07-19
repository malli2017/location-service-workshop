package nl.toefel.location.service.access;

import nl.toefel.location.service.entity.Location;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LocationRepository extends CrudRepository<Location, Long> {
    Optional<Location> findByName(String name);
}
