package com.jungle.protocol;

/**
 *  ForgotPwdRequest class represents a request to reset the password.
 *  It extends the Request class and contains
 *  the username, password, and email fields.
 * 
 *  @author Zhixin Li
 * 
 *  @version May 13, 2025
 */

public class ForgotPwdRequest extends Request {
    private static final long serialVersionUID = 1L;

    private String username;
    private String password;
    private String email;

    public ForgotPwdRequest() {
        super(RequestType.FORGOT_PWD);
    }

    public ForgotPwdRequest(String username, String password, String email) {
        super(RequestType.FORGOT_PWD);
        this.username = username;
        this.password= password;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    


}
