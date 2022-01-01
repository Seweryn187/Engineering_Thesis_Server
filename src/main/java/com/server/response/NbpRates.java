package com.server.response;


import java.time.LocalDate;
import java.util.Date;


public class NbpRates {
    private String no;
    private LocalDate effectiveDate;
    private float bid;
    private float ask;

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public LocalDate getEffectiveDate() {
        return effectiveDate;
    }

    public void setEffectiveDate(LocalDate effectiveDate) {
        this.effectiveDate = effectiveDate;
    }

    public float getBid() {
        return bid;
    }

    public void setBid(float bid) {
        this.bid = bid;
    }

    public float getAsk() {
        return ask;
    }

    public void setAsk(float ask) {
        this.ask = ask;
    }

    @Override
    public String toString() {
        return "response.NbpRates{" +
                "no='" + no + '\'' +
                ", effectiveDate=" + effectiveDate +
                ", bid=" + bid +
                ", ask=" + ask +
                '}';
    }
}
