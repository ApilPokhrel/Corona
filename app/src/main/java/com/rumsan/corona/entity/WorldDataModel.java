package com.rumsan.corona.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class WorldDataModel implements Serializable {
    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("totalCases")
    @Expose
    private Integer totalCases;
    @SerializedName("newCases")
    @Expose
    private Integer newCases;
    @SerializedName("totalDeaths")
    @Expose
    private Integer totalDeaths;
    @SerializedName("newDeaths")
    @Expose
    private Integer newDeaths;
    @SerializedName("activeCases")
    @Expose
    private Integer activeCases;
    @SerializedName("totalRecovered")
    @Expose
    private Integer totalRecovered;
    @SerializedName("criticalCases")
    @Expose
    private Integer criticalCases;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getTotalCases() {
        return totalCases;
    }

    public void setTotalCases(Integer totalCases) {
        this.totalCases = totalCases;
    }

    public Integer getNewCases() {
        return newCases;
    }

    public void setNewCases(Integer newCases) {
        this.newCases = newCases;
    }

    public Integer getTotalDeaths() {
        return totalDeaths;
    }

    public void setTotalDeaths(Integer totalDeaths) {
        this.totalDeaths = totalDeaths;
    }

    public Integer getNewDeaths() {
        return newDeaths;
    }

    public void setNewDeaths(Integer newDeaths) {
        this.newDeaths = newDeaths;
    }

    public Integer getActiveCases() {
        return activeCases;
    }

    public void setActiveCases(Integer activeCases) {
        this.activeCases = activeCases;
    }

    public Integer getTotalRecovered() {
        return totalRecovered;
    }

    public void setTotalRecovered(Integer totalRecovered) {
        this.totalRecovered = totalRecovered;
    }

    public Integer getCriticalCases() {
        return criticalCases;
    }

    public void setCriticalCases(Integer criticalCases) {
        this.criticalCases = criticalCases;
    }

    @Override
    public String toString() {
        return "WorldDataModel{" +
                "id='" + id + '\'' +
                ", country='" + country + '\'' +
                ", totalCases=" + totalCases +
                ", newCases=" + newCases +
                ", totalDeaths=" + totalDeaths +
                ", newDeaths=" + newDeaths +
                ", activeCases=" + activeCases +
                ", totalRecovered=" + totalRecovered +
                ", criticalCases=" + criticalCases +
                '}';
    }
}
