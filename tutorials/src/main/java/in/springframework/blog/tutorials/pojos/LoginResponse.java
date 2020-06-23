package in.springframework.blog.tutorials.pojos;

import java.util.Date;

public class LoginResponse {
    public LoginResponse(String authToken, Date expiry) {
        this.authToken = authToken;
        this.expiry = expiry;
    }

    public LoginResponse() {
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    private String authToken;
    private Date expiry;
}
