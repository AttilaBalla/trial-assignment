package com.usermanager.entity.json;

public class ResponseJson {

    private boolean success;
    private String information;

    public ResponseJson(boolean success) {
        this.success = success;
    }

    public ResponseJson(boolean success, String information) {
        this.success = success;
        this.information = information;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }
}
