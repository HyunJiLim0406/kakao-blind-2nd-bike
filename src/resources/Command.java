package resources;

import java.util.ArrayList;
import java.util.List;

public class Command {
    private int truck_id;
    private List<Integer> command = new ArrayList<>();

    /* Getter, Setter */
    public int getTruck_id() {
        return truck_id;
    }

    public void setTruck_id(int truck_id) {
        this.truck_id = truck_id;
    }

    public List<Integer> getCommand() {
        return command;
    }

    public void setCommand(List<Integer> command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "Command{" +
                "truck_id=" + truck_id +
                ", command=" + command +
                '}';
    }
}
