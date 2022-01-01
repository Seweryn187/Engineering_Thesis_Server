package com.server.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Entity
@Table(name = "current_value")
public class CurrentValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "ask_value", nullable = false)
    private Integer askValue;

    @Column(name = "bid_value", nullable = false)
    private Integer bidValue;

    @ManyToOne(optional = false)
    @JoinColumn(name = "source_id", nullable = false)
    private Source source;

    @Column(name = "mean_value", nullable = false)
    private Integer meanValue;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "spread")
    private Integer spread;

    @Column(name = "ask_increase")
    private Boolean askIncrease;

    @Column(name = "bid_increase")
    private Boolean bidIncrease;

    public CurrentValue() {

    }

    public CurrentValue(Integer id, Integer askValue, Integer bidValue, Source source, Integer meanValue, LocalDate date, Integer spread, Boolean askIncrease, Boolean bidIncrease) {
        this.id = id;
        this.askValue = askValue;
        this.bidValue = bidValue;
        this.source = source;
        this.meanValue = meanValue;
        this.date = date;
        this.spread = spread;
        this.askIncrease = askIncrease;
        this.bidIncrease = bidIncrease;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getMeanValue() {
        return meanValue;
    }

    public void setMeanValue(Integer meanValue) {
        this.meanValue = meanValue;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Integer getAskValue() {
        return askValue;
    }

    public void setAskValue(Integer askValue) {
        this.askValue = askValue;
    }

    public Integer getBidValue() {
        return bidValue;
    }

    public void setBidValue(Integer bidValue) {
        this.bidValue = bidValue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSpread() {
        return spread;
    }

    public void setSpread(Integer spread) {
        this.spread = spread;
    }

    public Boolean getAskIncrease() {
        return askIncrease;
    }

    public void setAskIncrease(Boolean askIncrease) {
        this.askIncrease = askIncrease;
    }

    public Boolean getBidIncrease() {
        return bidIncrease;
    }

    public void setBidIncrease(Boolean bidIncrease) {
        this.bidIncrease = bidIncrease;
    }

    @Override
    public String toString() {
        return "CurrentValue{" +
                "id=" + id +
                ", askValue=" + askValue +
                ", bidValue=" + bidValue +
                ", source=" + source +
                ", meanValue=" + meanValue +
                ", date=" + date +
                ", spread=" + spread +
                ", askIncrease=" + askIncrease +
                ", bidIncrease=" + bidIncrease +
                '}';
    }
}