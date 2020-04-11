package com.rumsan.corona.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HospitalP {
    @SerializedName("data")
    @Expose
    private List<HospitalModel> hospitals;

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

    public List<HospitalModel> getHospitals() {
        return hospitals;
    }

    public void setHospitals(List<HospitalModel> hospitals) {
        this.hospitals = hospitals;
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
