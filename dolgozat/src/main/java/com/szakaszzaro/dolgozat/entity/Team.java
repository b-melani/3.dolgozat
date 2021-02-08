package com.szakaszzaro.dolgozat.entity;

import com.szakaszzaro.dolgozat.model.Kind;
import com.szakaszzaro.dolgozat.model.Universe;

public class Team {
    private String id;
    private String name;
    private Universe universe;
    private Kind kind;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Universe getUniverse() {
        return universe;
    }

    public void setUniverse(Universe universe) {
        this.universe = universe;
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", universe=" + universe +
                ", kind=" + kind +
                '}';
    }
}
