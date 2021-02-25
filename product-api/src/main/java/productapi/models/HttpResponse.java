package productapi.models;

import java.util.Date;
import java.util.Objects;

import org.springframework.http.HttpStatus;

public class HttpResponse {

    /*
        Class which is used for the custom HTTP response
    */

    private Date timeStamp;
    private int httpStatusCode;
    private HttpStatus httpStatus;
    private String reason;
    private String message;

    public HttpResponse() {
    }

    public HttpResponse(int httpStatusCode, HttpStatus httpStatus, String reason, String message) {
        this.httpStatusCode = httpStatusCode;
        this.httpStatus = httpStatus;
        this.reason = reason;
        this.message = message;
        this.timeStamp = new Date();
    }

    public Date getTimeStamp() {
        return this.timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    public int getHttpStatusCode() {
        return this.httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "{" + " timeStamp='" + getTimeStamp() + "'" + ", httpStatusCode='" + getHttpStatusCode() + "'"
                + ", httpStatus='" + getHttpStatus() + "'" + ", reason='" + getReason() + "'" + ", message='"
                + getMessage() + "'" + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof HttpResponse)) {
            return false;
        }
        HttpResponse httpResponse = (HttpResponse) o;
        return Objects.equals(timeStamp, httpResponse.timeStamp) && httpStatusCode == httpResponse.httpStatusCode
                && Objects.equals(httpStatus, httpResponse.httpStatus) && Objects.equals(reason, httpResponse.reason)
                && Objects.equals(message, httpResponse.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(timeStamp, httpStatusCode, httpStatus, reason, message);
    }

}
