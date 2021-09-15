package resources;

public class Truck {
    private int id;
    private int location_id;
    private int loaded_bikes_count;

    /* Getter, Setter */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLocation_id() {
        return location_id;
    }

    public void setLocation_id(int location_id) {
        this.location_id = location_id;
    }

    public int getLoaded_bikes_count() {
        return loaded_bikes_count;
    }

    public void setLoaded_bikes_count(int loaded_bikes_count) {
        this.loaded_bikes_count = loaded_bikes_count;
    }
}
