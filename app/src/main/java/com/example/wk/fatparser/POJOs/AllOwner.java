package com.example.wk.fatparser.POJOs;

import java.io.Serializable;
import java.util.List;

public class AllOwner implements Serializable {
    private String name;
    private List<Owner> owners;

    public AllOwner() {
    }

    public AllOwner(String name, List<Owner> owners) {
        this.name = name;
        this.owners = owners;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Owner> getOwners() {
        return owners;
    }

    public void setOwners(List<Owner> owners) {
        this.owners = owners;
    }
}
