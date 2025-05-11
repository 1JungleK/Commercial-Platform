package com.jungle.protocol.requests;

import java.io.Serializable;

public class Request implements Serializable {
    private static final long serialVersionUID = 1L;

    private RequestType type;

    public Request() { };

    public Request(RequestType type) {
        this.type = type;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }
}
