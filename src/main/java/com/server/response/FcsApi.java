package com.server.response;

import java.util.Arrays;

public class FcsApi {
    private String status;
    private FcsApiResponse[] response;

    public FcsApi(String status, FcsApiResponse[] response) {
        this.status = status;
        this.response = response;
    }

    public FcsApi() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public FcsApiResponse[] getResponse() {
        return response;
    }

    public void setResponse(FcsApiResponse[] response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "FcsApi{" +
                "status='" + status + '\'' +
                ", response=" + Arrays.toString(response) +
                '}';
    }
}
