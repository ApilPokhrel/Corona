package com.rumsan.corona.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NepWorldP {
    @SerializedName("data")
    @Expose
    private List<NepWorldModel> data;

    @SerializedName("total")
    @Expose
    long total;

    @SerializedName("limit")
    @Expose
    int limit;

    @SerializedName("start")
    @Expose
    int start;

    @SerializedName("page")
    @Expose
    int page;

    public List<NepWorldModel> getData() {
        return data;
    }

    public void setData(List<NepWorldModel> data) {
        this.data = data;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
