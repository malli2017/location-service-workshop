package nl.toefel.location.service.entity;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class LocationTest {

    @Test
    public void testLocation() {
        Location location = new Location("a", 1.0, 2.0);
        assertThat(location.getId()).isNull();
        assertThat(location.getName()).isEqualTo("a");
        assertThat(location.getLat()).isEqualTo(1.0);
        assertThat(location.getLon()).isEqualTo(2.0);
    }
}