package resources;

import java.util.ArrayList;
import java.util.List;

public class LocationsResponse {
    private List<Location> locations = new ArrayList<>();

    /* Getter, Setter */
    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    @Override
    public String toString() {
        return "LocationsResponse{" +
                "locations=" + locations +
                '}';
    }
}
