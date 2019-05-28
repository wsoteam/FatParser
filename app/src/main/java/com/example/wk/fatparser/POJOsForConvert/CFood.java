package com.example.wk.fatparser.POJOsForConvert;

import com.orm.SugarRecord;

import java.io.Serializable;

public class CFood extends SugarRecord implements Serializable {
    private String name;
    private String brend;
    private String url;
    private double portion;

    private boolean isLiquid;

    private double kilojoules;
    private double calories;
    private double proteins;
    private double carbohydrates;
    private double sugar;
    private double fats;
    private double saturatedFats;
    private double monoUnSaturatedFats;
    private double polyUnSaturatedFats;
    private double cholesterol;
    private double cellulose;
    private double sodium;
    private double pottassium;


    private int percentCarbohydrates;
    private int percentFats;
    private int percentProteins;

    public CFood() {
    }

    public CFood(String name, String brend, String url, double portion, boolean isLiquid, double kilojoules, double calories, double proteins, double carbohydrates, double sugar, double fats, double saturatedFats, double monoUnSaturatedFats, double polyUnSaturatedFats, double cholesterol, double cellulose, double sodium, double pottassium, int percentCarbohydrates, int percentFats, int percentProteins) {
        this.name = name;
        this.brend = brend;
        this.url = url;
        this.portion = portion;
        this.isLiquid = isLiquid;
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

    public double getPortion() {
        return portion;
    }

    public void setPortion(double portion) {
        this.portion = portion;
    }

    public boolean isLiquid() {
        return isLiquid;
    }

    public void setLiquid(boolean liquid) {
        isLiquid = liquid;
    }

    public double getKilojoules() {
        return kilojoules;
    }

    public void setKilojoules(double kilojoules) {
        this.kilojoules = kilojoules;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getProteins() {
        return proteins;
    }

    public void setProteins(double proteins) {
        this.proteins = proteins;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public double getSugar() {
        return sugar;
    }

    public void setSugar(double sugar) {
        this.sugar = sugar;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public double getSaturatedFats() {
        return saturatedFats;
    }

    public void setSaturatedFats(double saturatedFats) {
        this.saturatedFats = saturatedFats;
    }

    public double getMonoUnSaturatedFats() {
        return monoUnSaturatedFats;
    }

    public void setMonoUnSaturatedFats(double monoUnSaturatedFats) {
        this.monoUnSaturatedFats = monoUnSaturatedFats;
    }

    public double getPolyUnSaturatedFats() {
        return polyUnSaturatedFats;
    }

    public void setPolyUnSaturatedFats(double polyUnSaturatedFats) {
        this.polyUnSaturatedFats = polyUnSaturatedFats;
    }

    public double getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(double cholesterol) {
        this.cholesterol = cholesterol;
    }

    public double getCellulose() {
        return cellulose;
    }

    public void setCellulose(double cellulose) {
        this.cellulose = cellulose;
    }

    public double getSodium() {
        return sodium;
    }

    public void setSodium(double sodium) {
        this.sodium = sodium;
    }

    public double getPottassium() {
        return pottassium;
    }

    public void setPottassium(double pottassium) {
        this.pottassium = pottassium;
    }

    public int getPercentCarbohydrates() {
        return percentCarbohydrates;
    }

    public void setPercentCarbohydrates(int percentCarbohydrates) {
        this.percentCarbohydrates = percentCarbohydrates;
    }

    public int getPercentFats() {
        return percentFats;
    }

    public void setPercentFats(int percentFats) {
        this.percentFats = percentFats;
    }

    public int getPercentProteins() {
        return percentProteins;
    }

    public void setPercentProteins(int percentProteins) {
        this.percentProteins = percentProteins;
    }

    @Override
    public String toString() {
        return "CFood{" +
                "name='" + name + '\'' +
                ", brend='" + brend + '\'' +
                ", url='" + url + '\'' +
                ", portion=" + portion +
                ", isLiquid=" + isLiquid +
                ", kilojoules=" + kilojoules +
                ", calories=" + calories +
                ", proteins=" + proteins +
                ", carbohydrates=" + carbohydrates +
                ", sugar=" + sugar +
                ", fats=" + fats +
                ", saturatedFats=" + saturatedFats +
                ", monoUnSaturatedFats=" + monoUnSaturatedFats +
                ", polyUnSaturatedFats=" + polyUnSaturatedFats +
                ", cholesterol=" + cholesterol +
                ", cellulose=" + cellulose +
                ", sodium=" + sodium +
                ", pottassium=" + pottassium +
                ", percentCarbohydrates=" + percentCarbohydrates +
                ", percentFats=" + percentFats +
                ", percentProteins=" + percentProteins +
                '}';
    }
}
