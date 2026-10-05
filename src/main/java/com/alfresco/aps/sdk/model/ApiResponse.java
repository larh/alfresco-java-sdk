package com.alfresco.aps.sdk.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ApiResponse<T> {
    @JsonProperty("data")
    private T data;

    @JsonProperty("error")
    private String error;

    @JsonProperty("total")
    private Integer total;

    @JsonProperty("size")
    private Integer size;

    @JsonProperty("start")
    private Integer start;

    public ApiResponse() {}

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "data=" + data +
                ", error='" + error + '\'' +
                ", total=" + total +
                ", size=" + size +
                ", start=" + start +
                '}';
    }
}
