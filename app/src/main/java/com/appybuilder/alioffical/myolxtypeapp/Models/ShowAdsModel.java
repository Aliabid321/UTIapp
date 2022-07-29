package com.appybuilder.alioffical.myolxtypeapp.Models;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

public class ShowAdsModel {

private Integer status;
private String message;
private User user;
private String token;
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

public Integer getStatus() {
return status;
}

public void setStatus(Integer status) {
this.status = status;
}

public String getMessage() {
return message;
}

public void setMessage(String message) {
this.message = message;
}

public User getUser() {
return user;
}

public void setUser(User user) {
this.user = user;
}

public String getToken() {
return token;
}

public void setToken(String token) {
this.token = token;
}

public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}
