package com.example.swplanetapi.Exceptions;

import java.util.Date;

public class ExceptionsResponse {
  
    private Date timestamp;
    private String message;
    private String details;

    public ExceptionsResponse(Date timestamp, String message, String details) {

        this.timestamp = timestamp;
        this.message = message;
        this.details = details;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

}
