package api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import resources.*;

import java.util.HashMap;
import java.util.Map;

public class ApiCaller {
    private String baseUrl;

    public ApiCaller(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public StartResponse start(String token, int problem) {
        Map<String, String> params = new HashMap<>();
        params.put("problem", String.valueOf(problem));

        Map<String, String> header = new HashMap<>();
        header.put("X-Auth-Token", token);
        header.put("Content-Type", "application/json");

        HttpHelper http = new HttpHelper();
        String response = http.send(SendType.POST, baseUrl + "/start?" + paramToString(params), header, null);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(response, StartResponse.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public LocationsResponse locations(String token) {
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", token);
        header.put("Content-Type", "application/json");

        HttpHelper http = new HttpHelper();
        String response = http.send(SendType.GET, baseUrl + "/locations", header, null);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(response, LocationsResponse.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public TrucksResponse trucks(String token) {
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", token);
        header.put("Content-Type", "application/json");

        HttpHelper http = new HttpHelper();
        String response = http.send(SendType.GET, baseUrl + "/trucks", header, null);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(response, TrucksResponse.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public SimulateResponse simulate(String token, SimulateRequest request) throws JsonProcessingException {
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", token);
        header.put("Content-Type", "application/json");

        HttpHelper http = new HttpHelper();
        String response = http.send(SendType.PUT, baseUrl + "/simulate", header, new ObjectMapper().writeValueAsString(request));
        ObjectMapper mapper = new ObjectMapper();
        //System.out.println("response = " + response);
        try {
            return mapper.readValue(response, SimulateResponse.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public ScoreResponse score(String token){
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", token);
        header.put("Content-Type", "application/json");

        HttpHelper http = new HttpHelper();
        String response = http.send(SendType.GET, baseUrl + "/score", header, null);
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(response, ScoreResponse.class);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    public String paramToString(Map<String, String> params) {
        StringBuilder result = new StringBuilder();
        params.forEach((key, value) -> {
            result.append(key);
            result.append("=");
            result.append(value);
            result.append("&");
        });

        String str = result.toString();
        return str.length() > 0 ? str.substring(0, str.length() - 1) : str;
    }
}
