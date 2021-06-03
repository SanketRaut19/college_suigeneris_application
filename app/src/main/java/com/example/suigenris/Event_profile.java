package com.example.suigenris;

public class Event_profile {

    public String event;
    public String name;
    public String email;
    public String mob_no;
    public String amount;

    public Event_profile(){
    }



    public Event_profile(String event, String name, String email, String mob_no, String amount) {
        this.event = event;
        this.name = name;
        this.email = email;
        this.mob_no = mob_no;
        this.amount = amount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMob_no() {
        return mob_no;
    }

    public void setMob_no(String mob_no) {
        this.mob_no = mob_no;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}