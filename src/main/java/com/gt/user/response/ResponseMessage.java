package com.gt.user.response;

public class ResponseMessage {
    private int success;
    private String error;

    public ResponseMessage(int success) {
        this.success = success;
    }

    public ResponseMessage(int success, String error) {
        this.success = success;
        this.error = error;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
