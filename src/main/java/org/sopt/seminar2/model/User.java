package org.sopt.seminar2.model;

public class User {
    private String name;
    private String part;

    public User(){
    }

    public User(final String name, final String part) {
        this.name = name;
        this.part = part;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPart(final String part) {
        this.part = part;
    }

    public String getName() {
        return name;
    }

    public String getPart() {
        return part;
    }
}
