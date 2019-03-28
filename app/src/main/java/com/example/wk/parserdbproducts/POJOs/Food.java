package com.example.wk.parserdbproducts.POJOs;

import java.io.Serializable;

public class Food implements Serializable {
    private String name;
    private String brend;
    private String url;
    private String portion;

    private String kilojoules;
    private String calories;
    private String proteins;
    private String carbohydrates;
    private String sugar;
    private String fats;
    private String saturatedFats;
    private String monoUnSaturatedFats;
    private String polyUnSaturatedFats;
    private String cholesterol;
    private String cellulose;
    private String sodium;
    private String pottassium;


    private String percentCarbohydrates;
    private String percentFats;
    private String percentProteins;

    public Food() {
    }

    public Food(String name, String brend, String url, String portion,
                String kilojoules, String calories, String proteins, String carbohydrates,
                String sugar, String fats, String saturatedFats, String monoUnSaturatedFats, String polyUnSaturatedFats,
                String cholesterol, String cellulose, String sodium, String pottassium, String percentCarbohydrates,
                String percentFats, String percentProteins) {
        this.name = name;
        this.brend = brend;
        this.url = url;
        this.portion = portion;
        this.kilojoules = kilojoules;
        this.calories = calories;
        this.proteins = proteins;
        this.carbohydrates = carbohydrates;
        this.sugar = sugar;
        this.fats = fats;
        this.saturatedFats = saturatedFats;
        this.monoUnSaturatedFats = monoUnSaturatedFats;
        this.polyUnSaturatedFats = polyUnSaturatedFats;
        this.cholesterol = cholesterol;
        this.cellulose = cellulose;
        this.sodium = sodium;
        this.pottassium = pottassium;
        this.percentCarbohydrates = percentCarbohydrates;
        this.percentFats = percentFats;
        this.percentProteins = percentProteins;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrend() {
        return brend;
    }

    public void setBrend(String brend) {
        this.brend = brend;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPortion() {
        return portion;
    }

    public void setPortion(String portion) {
        this.portion = portion;
    }

    public String getKilojoules() {
        return kilojoules;
    }

    public void setKilojoules(String kilojoules) {
        this.kilojoules = kilojoules;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getProteins() {
        return proteins;
    }

    public void setProteins(String proteins) {
        this.proteins = proteins;
    }

    public String getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(String carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public String getSugar() {
        return sugar;
    }

    public void setSugar(String sugar) {
        this.sugar = sugar;
    }

    public String getFats() {
        return fats;
    }

    public void setFats(String fats) {
        this.fats = fats;
    }

    public String getSaturatedFats() {
        return saturatedFats;
    }

    public void setSaturatedFats(String saturatedFats) {
        this.saturatedFats = saturatedFats;
    }

    public String getMonoUnSaturatedFats() {
        return monoUnSaturatedFats;
    }

    public void setMonoUnSaturatedFats(String monoUnSaturatedFats) {
        this.monoUnSaturatedFats = monoUnSaturatedFats;
    }

    public String getPolyUnSaturatedFats() {
        return polyUnSaturatedFats;
    }

    public void setPolyUnSaturatedFats(String polyUnSaturatedFats) {
        this.polyUnSaturatedFats = polyUnSaturatedFats;
    }

    public String getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(String cholesterol) {
        this.cholesterol = cholesterol;
    }

    public String getCellulose() {
        return cellulose;
    }

    public void setCellulose(String cellulose) {
        this.cellulose = cellulose;
    }

    public String getSodium() {
        return sodium;
    }

    public void setSodium(String sodium) {
        this.sodium = sodium;
    }

    public String getPottassium() {
        return pottassium;
    }

    public void setPottassium(String pottassium) {
        this.pottassium = pottassium;
    }

    public String getPercentCarbohydrates() {
        return percentCarbohydrates;
    }

    public void setPercentCarbohydrates(String percentCarbohydrates) {
        this.percentCarbohydrates = percentCarbohydrates;
    }

    public String getPercentFats() {
        return percentFats;
    }

    public void setPercentFats(String percentFats) {
        this.percentFats = percentFats;
    }

    public String getPercentProteins() {
        return percentProteins;
    }

    public void setPercentProteins(String percentProteins) {
        this.percentProteins = percentProteins;
    }
}
