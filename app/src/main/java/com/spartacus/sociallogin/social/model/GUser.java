package com.lenvosoft.offers.social.model;

import android.accounts.Account;
import android.graphics.Bitmap;
import android.net.Uri;

public class GUser {
    private Bitmap pic;
    private String photoUrl;
    private Account account;
    private String givenName;
    private String displayName;
    private String familyName;
    private String phonenumber;
    private String idToken;
    private String id;

    @Override
    public String toString() {
        return "GUser{" +
                "pic=" + pic +
                ", photoUrl='" + photoUrl + '\'' +
                ", account=" + account +
                ", givenName='" + givenName + '\'' +
                ", displayName='" + displayName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", idToken='" + idToken + '\'' +
                ", id='" + id + '\'' +
                ", Email='" + Email + '\'' +
                '}';
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    private String Email;

    public GUser() {
    }

    public GUser(Account account, String givenName, String displayName, String familyName, Bitmap pic, String phonenumber, String photoUrl, String idToken, String id,String Email) {
        this.pic = pic;
        this.photoUrl = photoUrl;
        this.account = account;
        this.givenName = givenName;
        this.displayName = displayName;
        this.familyName = familyName;
        this.phonenumber = phonenumber;
        this.idToken = idToken;
        this.id = id;
        this.Email=Email;
    }

    public Bitmap getPic() {
        return pic;
    }

    public void setPic(Bitmap pic) {
        this.pic = pic;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
