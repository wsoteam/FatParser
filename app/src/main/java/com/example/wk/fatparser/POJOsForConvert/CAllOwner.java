package com.example.wk.fatparser.POJOsForConvert;

import com.example.wk.fatparser.POJOs.Owner;

import java.io.Serializable;
import java.util.List;

public class CAllOwner implements Serializable {
    private String name;
    private List<COwner> owners;

    public CAllOwner() {
    }

    public CAllOwner(String name, List<COwner> owners) {
        this.name = name;
        this.owners = owners;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<COwner> getOwners() {
        return owners;
    }

    public void setOwners(List<COwner> owners) {
        this.owners = owners;
    }
}
