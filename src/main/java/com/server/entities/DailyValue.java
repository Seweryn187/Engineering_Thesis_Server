package com.server.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "daily_values")
public class DailyValue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "bid_value", nullable = false)
    private Integer bidValue;

    @Column(name = "ask_value", nullable = false)
    private Integer askValue;

    @ManyToOne(optional = false)
    @JoinColumn(name = "source_id", nullable = false)
    private Source source;

    @ManyToOne(optional = false)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @Column(name = "spread", nullable = false)
    private Integer spread;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    public DailyValue() {
    }

    public DailyValue(Integer id, Integer bidValue, Integer askValue, Source source, Currency currency, Integer spread, LocalDate date) {
        this.id = id;
        this.bidValue = bidValue;
        this.askValue = askValue;
        this.source = source;
        this.currency = currency;
        this.spread = spread;
        this.date = date;
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

    public Integer getBidValue() {
        return bidValue;
    }

    public void setBidValue(Integer bidValue) {
        this.bidValue = bidValue;
    }

    public Integer getAskValue() {
        return askValue;
    }

    public void setAskValue(Integer askValue) {
        this.askValue = askValue;
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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "DailyValue{" +
                "id=" + id +
                ", bidValue=" + bidValue +
                ", askValue=" + askValue +
                ", source=" + source +
                ", currency=" + currency +
                ", spread=" + spread +
                ", date=" + date +
                '}';
    }
}