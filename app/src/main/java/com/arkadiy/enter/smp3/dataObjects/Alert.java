package com.arkadiy.enter.smp3.dataObjects;


import java.util.List;

class Alert {
    private int id;
    private String name;
    private String description;
    private String sendAt; //TimeDate
    private int userFrom;
    private int userto;
    private List<Integer> toDepartments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSendAt() {
        return sendAt;
    }

    public void setSendAt(String sendAt) {
        this.sendAt = sendAt;
    }

    public int getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(int userFrom) {
        this.userFrom = userFrom;
    }

    public int getUserto() {
        return userto;
    }

    public void setUserto(int userto) {
        this.userto = userto;
    }

    public List<Integer> getToDepartments() {
        return toDepartments;
    }

    public void setToDepartments(List<Integer> toDepartments) {
        this.toDepartments = toDepartments;
    }
}
