package com.bluerizon.transport.requeste;

public class CheckRequest {
    private String url;

    public CheckRequest() {
    }

    public CheckRequest(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
