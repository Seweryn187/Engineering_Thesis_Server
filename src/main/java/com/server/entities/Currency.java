package com.server.entities;

import javax.persistence.*;

@Entity
@Table(name = "currencies")
public class Currency {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "abbr", nullable = false, length = 3)
    private String abbr;

    @ManyToOne(optional = false)
    @JoinColumn(name = "current_value_id", nullable = false)
    private CurrentValue currentValue;

    public CurrentValue getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(CurrentValue currentValue) {
        this.currentValue = currentValue;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", abbr='" + abbr + '\'' +
                ", currentValue=" + currentValue +
                '}';
    }
}