package com.gt.user.response;

public class ResponseMessage {
    private String success;
    private String error;

    public ResponseMessage(String success) {
        this.success = success;
    }

    public ResponseMessage(String success, String error) {
        this.success = success;
        this.error = error;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getSuccess() {
        return success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
