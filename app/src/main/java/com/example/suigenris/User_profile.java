package com.example.suigenris;
public class User_profile {

    public String post;
    public String name;
    public String email;
    public String password;
    public int event_reg ;

    public User_profile(){



    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getEvent_reg() {
        return event_reg;
    }

    public void setEvent_reg(int event_reg) {
        this.event_reg = event_reg;
    }

    public User_profile(String post, String name, String email, String password, int event_reg) {
        this.post = post;
        this.name = name;
        this.email = email;
        this.password = password;
        this.event_reg = event_reg;
    }
}
