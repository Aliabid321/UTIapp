package com.appybuilder.alioffical.myolxtypeapp.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserModel {
     @SerializedName("email")
     @Expose
     String email;
     @SerializedName("password")
     @Expose
     String password;
     @SerializedName("status")
     @Expose
     String status;

     public UserModel(String email, String password, String status) {
          this.email = email;
          this.password = password;
          this.status = status;
     }

     public String getEmail() {
          return email;
     }

     public void setEmail(String email) {
          this.email = email;
     }

     public String getPassword() {
          return password;
     }

     public void setPassword(String password) {
          this.password = password;
     }

     public String getStatus() {
          return status;
     }

     public void setStatus(String status) {
          this.status = status;
     }
}
