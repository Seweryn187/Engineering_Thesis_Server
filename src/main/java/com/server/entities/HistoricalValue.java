package com.server.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "historical_value")
public class HistoricalValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "mean_value", nullable = false)
    private Integer meanValue;

    @Column(name = "mean_bid_value", nullable = false)
    private Integer meanBidValue;

    @Column(name = "mean_ask_value", nullable = false)
    private Integer meanAskValue;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @ManyToOne(optional = false)
    @JoinColumn(name = "source_id", nullable = false)
    private Source source;

    @ManyToOne(optional = false)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @Column(name = "spread")
    private Integer spread;

    public HistoricalValue() {
    }

    public HistoricalValue(Integer id, Integer meanValue, Integer meanBidValue, Integer meanAskValue, LocalDate date, Source source, Currency currency, Integer spread) {
        this.id = id;
        this.meanValue = meanValue;
        this.meanBidValue = meanBidValue;
        this.meanAskValue = meanAskValue;
        this.date = date;
        this.source = source;
        this.currency = currency;
        this.spread = spread;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Integer getMeanBidValue() {
        return meanBidValue;
    }

    public void setMeanBidValue(Integer meanBidValue) {
        this.meanBidValue = meanBidValue;
    }

    public Integer getMeanAskValue() {
        return meanAskValue;
    }

    public void setMeanAskValue(Integer meanAskValue) {
        this.meanAskValue = meanAskValue;
    }

    public Integer getMeanValue() {
        return meanValue;
    }

    public void setMeanValue(Integer meanValue) {
        this.meanValue = meanValue;
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

    @Override
    public String toString() {
        return "HistoricalValue{" +
                "id=" + id +
                ", meanValue=" + meanValue +
                ", meanBidValue=" + meanBidValue +
                ", meanAskValue=" + meanAskValue +
                ", date=" + date +
                ", source=" + source +
                ", currency=" + currency +
                ", spread=" + spread +
                '}';
    }
}