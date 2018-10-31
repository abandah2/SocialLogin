package com.lenvosoft.offers.social.model;

import android.graphics.Bitmap;

/**
 * Created by Abandah on 2/12/2018.
 */

public class FBUser {
    private String first_name;
    private String last_name;
    private String email;
    private String gender;
    private String link;
    private String id;
    private Bitmap Pic;

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    private String phonenumber;


    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Bitmap getPic() {
        return Pic;
    }

    public void setPic(Bitmap pic) {
        Pic = pic;
    }
}
