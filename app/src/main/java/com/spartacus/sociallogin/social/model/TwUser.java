package com.lenvosoft.offers.social.model;

import android.graphics.Bitmap;

public class TwUser {
    private String profileImage;
    private String createdAt;
    private String lang;
    private String name;
    private String screenName;
    private String timeZone;
    private String verified;
    private String email;

    public String getTwitterProfileUrl() {
        return TwitterProfileUrl;
    }

    public void setTwitterProfileUrl(String twitterProfileUrl) {
        TwitterProfileUrl = twitterProfileUrl;
    }

    private String TwitterProfileUrl;

    public String getTwitterId() {
        return TwitterId;
    }

    public void setTwitterId(String twitterId) {
        TwitterId = twitterId;
    }

    private String TwitterId;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    private String phonenumber;

    public Bitmap getPic() {
        return pic;
    }

    public void setPic(Bitmap pic) {
        this.pic = pic;
    }

    private Bitmap pic;

    public TwUser() {
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenName() {
        return screenName;
    }

    public void setScreenName(String screenName) {
        this.screenName = screenName;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getVerified() {
        return verified;
    }

    public void setVerified(String verified) {
        this.verified = verified;
    }
}
