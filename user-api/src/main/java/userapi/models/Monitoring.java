package userapi.models;

import java.util.Objects;

public class Monitoring {

    /*
        Class which represents Monitoring object which will be send to monitoring-api and
        stored to the database
    */

    private String message;
    private String api;

    public Monitoring() {
    }

    public Monitoring(String message, String api) {
        this.message = message;
        this.api = api;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getApi() {
        return this.api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public Monitoring message(String message) {
        setMessage(message);
        return this;
    }

    public Monitoring api(String api) {
        setApi(api);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Monitoring)) {
            return false;
        }
        Monitoring monitoring = (Monitoring) o;
        return Objects.equals(message, monitoring.message) && Objects.equals(api, monitoring.api);
    }

    @Override
    public int hashCode() {
        return Objects.hash(message, api);
    }

    @Override
    public String toString() {
        return "{" + " message='" + getMessage() + "'" + ", api='" + getApi() + "'" + "}";
    }

}
