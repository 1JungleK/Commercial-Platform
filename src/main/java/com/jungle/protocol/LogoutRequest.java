package com.jungle.protocol;

public class LogoutRequest extends Request {
    private static final long serialVersionUID = 1L;

    public LogoutRequest() {
        super(RequestType.LOGOUT);
    }
    
}
