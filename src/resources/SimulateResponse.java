package resources;

public class SimulateResponse {
    private String status;
    private int time;
    private String failed_requests_count;
    private String distance;

    /* Getter, Setter */
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getFailed_requests_count() {
        return failed_requests_count;
    }

    public void setFailed_requests_count(String failed_requests_count) {
        this.failed_requests_count = failed_requests_count;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "SimulateResponse{" +
                "status='" + status + '\'' +
                ", time=" + time +
                ", failed_requests_count=" + failed_requests_count +
                ", distance=" + distance +
                '}';
    }
}
