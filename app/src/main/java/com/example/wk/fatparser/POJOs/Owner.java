package com.example.wk.fatparser.POJOs;

import java.io.Serializable;
import java.util.List;

public class Owner implements Serializable {
    private String name;
    private String url;
    private List<Food> foods;

    public Owner() {
    }

    public Owner(String name, String url, List<Food> foods) {
        this.name = name;
        this.url = url;
        this.foods = foods;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Food> getFoods() {
        return foods;
    }

    public void setFoods(List<Food> foods) {
        this.foods = foods;
    }
}
