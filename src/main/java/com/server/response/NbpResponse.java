package com.server.response;

import java.util.List;

public class NbpResponse {
    private String table;
    private String currency;
    private String code;
    private List<NbpRates> rates;

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<NbpRates> getRates() {
        return rates;
    }

    public void setRates(List<NbpRates> rates) {
        this.rates = rates;
    }

    @Override
    public String toString() {
        return "response.NbpResponse{" +
                "table='" + table + '\'' +
                ", currency='" + currency + '\'' +
                ", code='" + code + '\'' +
                ", rates=" + rates +
                '}';
    }
}
