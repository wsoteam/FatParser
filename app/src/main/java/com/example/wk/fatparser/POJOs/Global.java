package com.example.wk.fatparser.POJOs;

import java.io.Serializable;
import java.util.List;

public class Global implements Serializable {
    private String name;
    private List<AllOwner> letters;

    public Global() {
    }

    public Global(String name, List<AllOwner> letters) {
        this.name = name;
        this.letters = letters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AllOwner> getLetters() {
        return letters;
    }

    public void setLetters(List<AllOwner> letters) {
        this.letters = letters;
    }
}
