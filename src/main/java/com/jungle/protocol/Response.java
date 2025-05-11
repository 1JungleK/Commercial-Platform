package com.jungle.protocol;

import java.io.Serializable;

/**
 *  Response class represents a response to a request.
 *  It contains a status, message, and data.
 * 
 *  @author Zhixin Li
 * 
 *  @version May 11, 2025
 */

 
public class Response implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private ResponseStatus status;
    private String message;
    private Object data;

    public Response() {
        this.status = ResponseStatus.UNKNOWN;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
