package resources;

import java.util.ArrayList;
import java.util.List;

public class SimulateRequest {
    private List<Command> commands = new ArrayList<>();

    /* Getter, Setter */
    public List<Command> getCommands() {
        return commands;
    }

    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }
}
