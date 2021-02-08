package com.szakaszzaro.dolgozat.entity;


import com.szakaszzaro.dolgozat.model.Universe;

import javax.persistence.Entity;

@Entity
public class SuperHero {

    private String id;
    private String name;
    private Universe universe;
    private Team team;
    private Boolean hero;

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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Boolean getHero() {
        return hero;
    }

    public void setHero(Boolean hero) {
        this.hero = hero;
    }

    @Override
    public String toString() {
        return "SuperHero{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", universe=" + universe +
                ", team=" + team +
                ", hero=" + hero +
                '}';
    }
}
