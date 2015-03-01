package com.internetitem.config.server.services.dataModel;

public abstract class AbstractResponse {
    
    protected String message;
    protected boolean success;

    public AbstractResponse() {
        this.success = true;
    }

    public AbstractResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public String getMessage() {
        
        return message;
    }

    public boolean isSuccess() {
        return success;
    }
}
