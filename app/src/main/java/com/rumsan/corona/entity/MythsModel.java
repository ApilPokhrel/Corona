package com.rumsan.corona.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MythsModel {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("myth")
    @Expose
    private String myth;
    @SerializedName("myth_np")
    @Expose
    private String mythNp;
    @SerializedName("reality")
    @Expose
    private String reality;
    @SerializedName("reality_np")
    @Expose
    private String realityNp;
    @SerializedName("image_url")
    @Expose
    private String imageUrl;
    @SerializedName("source_name")
    @Expose
    private String sourceName;
    @SerializedName("source_url")
    @Expose
    private String sourceUrl;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    /**
     * No args constructor for use in serialization
     *
     */
    public MythsModel() {
    }

    /**
     *
     * @param sourceUrl
     * @param createdAt
     * @param myth
     * @param realityNp
     * @param imageUrl
     * @param reality
     * @param id
     * @param sourceName
     * @param type
     * @param lang
     * @param mythNp
     * @param updatedAt
     */
    public MythsModel(String id, String type, String lang, String myth, String mythNp, String reality, String realityNp, String imageUrl, String sourceName, String sourceUrl, String createdAt, String updatedAt) {
        super();
        this.id = id;
        this.type = type;
        this.lang = lang;
        this.myth = myth;
        this.mythNp = mythNp;
        this.reality = reality;
        this.realityNp = realityNp;
        this.imageUrl = imageUrl;
        this.sourceName = sourceName;
        this.sourceUrl = sourceUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getMyth() {
        return myth;
    }

    public void setMyth(String myth) {
        this.myth = myth;
    }

    public String getMythNp() {
        return mythNp;
    }

    public void setMythNp(String mythNp) {
        this.mythNp = mythNp;
    }

    public String getReality() {
        return reality;
    }

    public void setReality(String reality) {
        this.reality = reality;
    }

    public String getRealityNp() {
        return realityNp;
    }

    public void setRealityNp(String realityNp) {
        this.realityNp = realityNp;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
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
