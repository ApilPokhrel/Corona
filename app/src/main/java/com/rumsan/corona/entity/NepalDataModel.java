package com.rumsan.corona.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NepalDataModel {

    @SerializedName("tested_positive")
    @Expose
    private String testedPositive;
    @SerializedName("tested_negative")
    @Expose
    private String testedNegative;
    @SerializedName("tested_total")
    @Expose
    private String testedTotal;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("lastest_sit_report")
    @Expose
    private LastestSitReport lastestSitReport;

    public String getTestedPositive() {
        return testedPositive;
    }

    public void setTestedPositive(String testedPositive) {
        this.testedPositive = testedPositive;
    }

    public String getTestedNegative() {
        return testedNegative;
    }

    public void setTestedNegative(String testedNegative) {
        this.testedNegative = testedNegative;
    }

    public String getTestedTotal() {
        return testedTotal;
    }

    public void setTestedTotal(String testedTotal) {
        this.testedTotal = testedTotal;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public LastestSitReport getLastestSitReport() {
        return lastestSitReport;
    }

    public void setLastestSitReport(LastestSitReport lastestSitReport) {
        this.lastestSitReport = lastestSitReport;
    }


    public class LastestSitReport {

        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("_id")
        @Expose
        private String id;
        @SerializedName("no")
        @Expose
        private Integer no;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getNo() {
            return no;
        }

        public void setNo(Integer no) {
            this.no = no;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }
}
