package com.app.crmapp.Models;

public class AddStaffModel {


    /**
     * message :  Created Successfully.
     * success : 200
     */

    private String message;
    private int success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }
}
