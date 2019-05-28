package com.example.wk.fatparser.POJOsForConvert;

import com.example.wk.fatparser.POJOs.Food;

import java.io.Serializable;
import java.util.List;

public class COwner implements Serializable {
    private String name;
    private String url;
    private List<CFood> foods;

    public COwner() {
    }

    public COwner(String name, String url, List<CFood> foods) {
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

    public List<CFood> getFoods() {
        return foods;
    }

    public void setFoods(List<CFood> foods) {
        this.foods = foods;
    }
}
