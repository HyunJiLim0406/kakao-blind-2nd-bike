package algorithm;

import resources.*;

import java.util.*;

public class Algorithm {
    public enum Commands {
        NONE, UP, DOWN, RIGHT, LEFT, ON, OFF;
    }

    Map<Integer, Position> idToPos = new HashMap<>();
    Map<Position, Integer> PosToId = new HashMap<>();
    int[][] board;
    int size, bike;

    public Algorithm(int problem) {
        size = (problem == 1) ? 5 : 60;
        bike = (problem == 1) ? 4 : 3;
        board = new int[size][size];

        int idx = 0, row = size - 1, col = 0;
        while (idx < size * size) {
            idToPos.put(idx, new Position(row, col));
            PosToId.put(new Position(row, col), idx);
            row--;

            if (row < 0) {
                row = size - 1;
                col++;
            }
            idx++;
        }
    }

    public SimulateRequest getRequest(LocationsResponse locationsResponse, TrucksResponse trucksResponse) {
        List<Truck> trucks = trucksResponse.getTrucks();
        List<Integer> refill = findEmpty(locationsResponse);
        List<Command> commands = new ArrayList<>();
        for (int i = 0; i < refill.size(); i++) {
            if (i == trucks.size())
                break;
            Command command = new Command();
            command.setTruck_id(trucks.get(i).getId());
            Stack<Position> route = route(refill.get(i), trucks.get(i).getLocation_id());
            command.setCommand(commandList(route, trucks.get(i)));
            commands.add(command);
        }
        SimulateRequest simulateRequest = new SimulateRequest();
        simulateRequest.setCommands(commands);
        return simulateRequest;
    }

    private List<Integer> commandList(Stack<Position> route, Truck truck) {
        List<Integer> command = new ArrayList<>();
        int bikeCnt = truck.getLoaded_bikes_count();

        //System.out.println("route.size() = " + route.size());
        if (route.empty()) {
            command.add(0);
            return command;
        }
        Position pos = route.pop();
        //System.out.println("route.size() = " + route.size());
        while (!route.empty()) {
            if (board[pos.row][pos.col] >= bike) {
                int loaded_bike = Math.min(board[pos.row][pos.col] / 2, 20 - bikeCnt);
                board[pos.row][pos.col] -= loaded_bike;
                bikeCnt += loaded_bike;
                while (loaded_bike != 0) {
                    command.add(Commands.ON.ordinal());
                    loaded_bike--;
                }
            } else if (board[pos.row][pos.col] == 0 && bikeCnt != 0) {
                board[pos.row][pos.col]++;
                bikeCnt--;
                command.add(Commands.OFF.ordinal());
            }
            Position nextPos = route.pop();
            command.add(findDir(pos, nextPos).ordinal());
//            System.out.println("pos = " + pos);
//            System.out.println("nextPos = " + nextPos);
            pos = nextPos;
        }
        while (bikeCnt > 0 && command.size() < 10)
            command.add(Commands.OFF.ordinal());
        //System.out.println("command = " + command.toString());
        if (command.size() >= 10)
            command = command.subList(0, 10);
        return command;
    }

    private Commands findDir(Position source, Position dest) {
        if (source.row == dest.row) {
            if (source.col < dest.col)
                return Commands.RIGHT;
            return Commands.LEFT;
        }
        if (source.row > dest.row)
            return Commands.UP;
        return Commands.DOWN;
    }

    private Stack<Position> route(Integer source, Integer dest) {
        int[] x = {-1, 1, 0, 0};
        int[] y = {0, 0, -1, 1};

        Stack<Position> route = new Stack<>();
        Queue<Position> queue = new LinkedList<>();

        Position sourcePos = idToPos.get(source);
        Position destPos = idToPos.get(dest);

        int[][] visited = new int[size][size];
        visited[sourcePos.row][sourcePos.col] = 1;
        queue.offer(sourcePos);

        while (!queue.isEmpty()) {
            Position pos = queue.poll();
            if (pos == destPos)
                break;
            for (int i = 0; i < 4; i++) {
                int nr = pos.row + x[i];
                int nc = pos.col + y[i];
                if (nr < 0 || nr >= size || nc < 0 || nc >= size || visited[nr][nc] != 0)
                    continue;
                visited[nr][nc] = visited[pos.row][pos.col] + 1;
                queue.offer(new Position(nr, nc));
            }
        }

        int row = destPos.row, col = destPos.col;
        int idx = visited[row][col];
        route.push(new Position(row, col));
        while (idx > 1) {
            int start = (int) (Math.random() * 4);
            for (int i = 0; i < 4; i++) {
                int nr = row + x[(i+start)%4];
                int nc = col + y[(i+start)%4];
                if (nr < 0 || nr >= size || nc < 0 || nc >= size || visited[nr][nc] != idx - 1)
                    continue;
                route.push(new Position(nr, nc));
                break;
            }
            idx--;
        }
        return route;
    }

    private List<Integer> findEmpty(LocationsResponse locationsResponse) {
        List<Integer> refill = new ArrayList<>();
        for (Location l : locationsResponse.getLocations()) {
            if (l.getLocated_bikes_count() == 0)
                refill.add(l.getId());
            Position pos = idToPos.get(l.getId());
            board[pos.row][pos.col] = l.getLocated_bikes_count();
            System.out.print(l.getLocated_bikes_count());
        }
        return refill;
    }

    static class Position {
        private final int row;
        private final int col;

        public Position(int row, int col) {
            this.row = row;
            this.col = col;
        }
    }
}
