package com.example.mohit.b3.POJOS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class UserSignUp {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("errorMessage")
    @Expose
    private Object errorMessage;
    @SerializedName("totalCoins")
    @Expose
    private Integer totalCoins;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getTotalCoins() {
        return totalCoins;
    }

    public void setTotalCoins(Integer totalCoins) {
        this.totalCoins = totalCoins;
    }

}

