package resources;

import java.util.ArrayList;
import java.util.List;

public class TrucksResponse {
    private List<Truck> trucks = new ArrayList<>();

    /* Getter, Setter */
    public List<Truck> getTrucks() {
        return trucks;
    }

    public void setTrucks(List<Truck> trucks) {
        this.trucks = trucks;
    }
}
