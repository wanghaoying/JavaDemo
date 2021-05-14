package com.wanghaoying.springsecuritydemo1.pojo;

public class Users {
    private String userName;
    private String password;
    private String email;

    public Users() {
    }

    public Users(String username, String password, String email) {
        this.userName = username;
        this.password = password;
        this.email = email;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
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

    @Override
    public String toString() {
        return "Users{" +
                "username='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
