package com.example.wk.fatparser.POJOsForConvert;

import com.example.wk.fatparser.POJOs.AllOwner;

import java.io.Serializable;
import java.util.List;

public class CGlobal implements Serializable {
    private String name;
    private List<CAllOwner> letters;

    public CGlobal() {
    }

    public CGlobal(String name, List<CAllOwner> letters) {
        this.name = name;
        this.letters = letters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CAllOwner> getLetters() {
        return letters;
    }

    public void setLetters(List<CAllOwner> letters) {
        this.letters = letters;
    }
}
