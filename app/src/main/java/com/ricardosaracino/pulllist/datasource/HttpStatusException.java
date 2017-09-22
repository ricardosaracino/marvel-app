package com.ricardosaracino.pulllist.datasource;

import org.apache.http.HttpException;

public class HttpStatusException extends HttpException {
    private int statusCode;

    public HttpStatusException(String message, int statusCode) {
        super(message);

        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }
}
