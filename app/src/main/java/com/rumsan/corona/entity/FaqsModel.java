package com.rumsan.corona.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FaqsModel {

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("lang")
    @Expose
    private String lang;
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("question_np")
    @Expose
    private String questionNp;
    @SerializedName("answer")
    @Expose
    private String answer;
    @SerializedName("answer_np")
    @Expose
    private String answerNp;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionNp() {
        return questionNp;
    }

    public void setQuestionNp(String questionNp) {
        this.questionNp = questionNp;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getAnswerNp() {
        return answerNp;
    }

    public void setAnswerNp(String answerNp) {
        this.answerNp = answerNp;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
