package com.internetitem.config.server.services.dataModel;

public class ErrorResponse extends AbstractResponse {
    
    public ErrorResponse(String message) {
        super(message, false);
    }
}
