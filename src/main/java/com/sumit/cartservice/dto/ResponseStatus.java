package com.sumit.cartservice.dto;

public class ResponseStatus {
    private String status;
    private String statusMessage;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    @Override
    public String toString() {
        return "ResponseStatus{" +
                "statusMessage='" + statusMessage + '\'' +
                '}';
    }
}
