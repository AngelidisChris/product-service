package service.errorHandler;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.Date;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class ErrorDetails {
    private Date timestamp;
    private String message;
    private int status;

    public ErrorDetails(Date timestamp, String message, int status) {
        this.timestamp = timestamp;
        this.message = message;
        this.status = status;
    }

    @Override
    public String toString() {
        return
                "[{\"timestamp\":\"" + timestamp +
                "\", \"message\":\"" + message +
                "\", \"status\":" + status + " }]";
    }
}