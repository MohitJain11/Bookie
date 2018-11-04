package com.example.mohit.b3.POJOS;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetUserInfo {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("errorMessage")
    @Expose
    private Object errorMessage;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("userName")
    @Expose
    private String userName;

    @SerializedName("userPic")
    @Expose
    private String userPic;
    @SerializedName("userMobile")
    @Expose
    private String userMobile;


    public void setAddress(String address) {
        this.address = address;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserPic(String userPic) {
        this.userPic = userPic;
    }

    public String getAddress() {
        return address;
    }

    public String getCity() {
        return city;
    }

    public String getLanguage() {
        return language;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPic() {
        return userPic;
    }

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

    public String getUserAddress() {
        return address;
    }

    public void setUserAddressd(String address) {
        this.address = address;
    }
}
