package com.kosh.authservice.exception;


public class APIException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String errorDescription;
    private int statusCode;

    private APIException(String errorDescription,String message,int statusCode) {
        super(message);
        this.errorDescription=errorDescription;
        this.statusCode=statusCode;
    }

    private APIException(String errorDescription,String message,int statusCode,Throwable cause) {
        super(message,cause);
        this.errorDescription=errorDescription;
        this.statusCode=statusCode;
    }

    public static APIException forServerError() {
        return new APIException("Failed Processing Request","Please Contact Server Admin",500);
    }

    public static APIException forClientError(String errorDescription) {
        return new APIException(errorDescription,"Bad Request",400);
    }
    public static APIException forNotFoundError(String errorDescription) {
        return new APIException(errorDescription,"NOT FOUND",404);
    }
    public static APIException forEmptyResponse(String errorDescription) {
        return new APIException(errorDescription,"OK",200);
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}