package monitoringapi.models;

import java.util.Date;
import java.util.Objects;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Monitoring {
    
    /* 
        Monitoring object used for checking the status of the whole system
    */

    @Id
    private String id;
    private String message;
    private String api;
    private Date createdDate;

    public Monitoring() {
    }

    public Monitoring(String message, String api) {
        this.message = message;
        this.api = api;
        this.createdDate = new Date();
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
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

    public Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Monitoring)) {
            return false;
        }
        Monitoring monitoring = (Monitoring) o;
        return Objects.equals(id, monitoring.id) && Objects.equals(message, monitoring.message) && Objects.equals(api, monitoring.api) && Objects.equals(createdDate, monitoring.createdDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, message, api, createdDate);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", message='" + getMessage() + "'" +
            ", api='" + getApi() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
    
}
