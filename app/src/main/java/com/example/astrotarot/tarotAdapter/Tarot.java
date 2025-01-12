package com.example.astrotarot.tarotAdapter;

public class Tarot {

    private int id;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Tarot(int id, String description) {
        this.id = id;
        this.description = description;
    }
}
