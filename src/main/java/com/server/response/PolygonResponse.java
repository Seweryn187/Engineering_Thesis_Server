package com.server.response;

import java.util.List;

public class PolygonResponse {
    private String ticker;
    private int queryCount;
    private int resultsCount;
    private boolean adjusted;
    private String status;
    private String request_id;
    private int count;
    private List<PolygonResult> results;

    public PolygonResponse() {
    }

    public PolygonResponse(String ticker, int queryCount, int resultsCount, boolean adjusted, String status, String request_id, int count, List<PolygonResult> results) {
        this.ticker = ticker;
        this.queryCount = queryCount;
        this.resultsCount = resultsCount;
        this.adjusted = adjusted;
        this.status = status;
        this.request_id = request_id;
        this.count = count;
        this.results = results;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public int getQueryCount() {
        return queryCount;
    }

    public void setQueryCount(int queryCount) {
        this.queryCount = queryCount;
    }

    public int getResultsCount() {
        return resultsCount;
    }

    public void setResultsCount(int resultsCount) {
        this.resultsCount = resultsCount;
    }

    public boolean isAdjusted() {
        return adjusted;
    }

    public void setAdjusted(boolean adjusted) {
        this.adjusted = adjusted;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<PolygonResult> getResults() {
        return results;
    }

    public void setResults(List<PolygonResult> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "PolygonResponse{" +
                "ticker='" + ticker + '\'' +
                ", queryCount=" + queryCount +
                ", resultsCount=" + resultsCount +
                ", adjusted=" + adjusted +
                ", status='" + status + '\'' +
                ", request_id='" + request_id + '\'' +
                ", count=" + count +
                ", results=" + results +
                '}';
    }
}
