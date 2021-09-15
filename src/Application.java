import algorithm.Algorithm;
import api.ApiCaller;
import com.fasterxml.jackson.core.JsonProcessingException;
import resources.*;

import java.util.ArrayList;
import java.util.List;

public class Application {
    public static void main(String[] args) throws InterruptedException, JsonProcessingException {
        final String BASE_URL = "https://kox947ka1a.execute-api.ap-northeast-2.amazonaws.com/prod/users";
        final String X_AUTH_TOKEN = "2a7b4b2aa3edda905076d6412d23607d";
        final int PROBLEM = 1;

        ApiCaller apiCaller = new ApiCaller(BASE_URL);
        StartResponse startResponse = apiCaller.start(X_AUTH_TOKEN, PROBLEM);
        String token = startResponse.getAuth_key();
        System.out.println("token = " + token);

//        Command command = new Command();
//        command.setTruck_id(0);
//        List<Integer> list = new ArrayList<>();
//        list.add(1);
//        command.setCommand(list);
//
//        List<Command> commands = new ArrayList<>();
//        commands.add(command);
//
//        SimulateRequest simulateRequest = new SimulateRequest();
//        simulateRequest.setCommands(commands);

        int time = startResponse.getTime();
        Algorithm algorithm = new Algorithm(PROBLEM);
        while (time <= 720) {
            Thread.sleep(10);
            //System.out.println("time = " + time);

            LocationsResponse locationsResponse = apiCaller.locations(token);
            TrucksResponse trucksResponse = apiCaller.trucks(token);
            SimulateRequest request = algorithm.getRequest(locationsResponse, trucksResponse);
            List<Command> commands = request.getCommands();
//            commands.forEach(c->{
//                System.out.println(c.toString());
//            });

            SimulateResponse simulateResponse = apiCaller.simulate(token, request);

            System.out.println(simulateResponse.toString());
            time = simulateResponse.getTime();
        }
        ScoreResponse scoreResponse = apiCaller.score(token);
        System.out.println("Score = " + scoreResponse.getScore());
    }
}
