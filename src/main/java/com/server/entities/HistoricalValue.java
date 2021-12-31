package com.server.entities;

import javax.persistence.*;
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

    @Column(name = "mean_buy_value", nullable = false)
    private Integer meanBuyValue;

    @Column(name = "mean_sell_value", nullable = false)
    private Integer meanSellValue;

    @Column(name = "date", nullable = false)
    private Date date;

    @ManyToOne(optional = false)
    @JoinColumn(name = "source_id", nullable = false)
    private Source source;

    @ManyToOne(optional = false)
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    public HistoricalValue() {
    }

    public HistoricalValue(Integer meanValue, Integer meanBuyValue, Integer meanSellValue, Date date, Source source, Currency currency) {
        this.meanValue = meanValue;
        this.meanBuyValue = meanBuyValue;
        this.meanSellValue = meanSellValue;
        this.date = date;
        this.source = source;
        this.currency = currency;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getMeanSellValue() {
        return meanSellValue;
    }

    public void setMeanSellValue(Integer meanSellValue) {
        this.meanSellValue = meanSellValue;
    }

    public Integer getMeanBuyValue() {
        return meanBuyValue;
    }

    public void setMeanBuyValue(Integer meanBuyValue) {
        this.meanBuyValue = meanBuyValue;
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

    @Override
    public String toString() {
        return "HistoricalValue{" +
                "id=" + id +
                ", meanValue=" + meanValue +
                ", meanBuyValue=" + meanBuyValue +
                ", meanSellValue=" + meanSellValue +
                ", date=" + date +
                ", source=" + source +
                ", currency=" + currency +
                '}';
    }
}