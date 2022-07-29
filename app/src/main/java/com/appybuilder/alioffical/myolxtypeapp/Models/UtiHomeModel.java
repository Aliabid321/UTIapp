package com.appybuilder.alioffical.myolxtypeapp.Models;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UtiHomeModel {
    public class AdsImage {
        @SerializedName("id")
        private Integer id;
        @SerializedName("ads_id")
        private Integer adsId;
        @SerializedName("image_url")
        private String imageUrl;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("updated_at")
        private String updatedAt;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Integer getAdsId() {
            return adsId;
        }

        public void setAdsId(Integer adsId) {
            this.adsId = adsId;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
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

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

    public class AdsInfo {

        private Integer id;
        @SerializedName("item_name")
        private String itemName;
        @SerializedName("item_description")
        private String itemDescription;
        @SerializedName("location")
        private String location;
        @SerializedName("category_id")
        private Integer categoryId;
        @SerializedName("has_admin_created")
        private Integer hasAdminCreated;
        @SerializedName("creator_id")
        private Integer creatorId;
        @SerializedName("created_at")
        private String createdAt;
        @SerializedName("updated_at")
        private String updatedAt;
        @SerializedName("category_name")
        private String categoryName;
        @SerializedName("creator_name")
        private String creatorName;
        @SerializedName("is_favorite")
        private Object isFavorite;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getItemDescription() {
            return itemDescription;
        }

        public void setItemDescription(String itemDescription) {
            this.itemDescription = itemDescription;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(Integer categoryId) {
            this.categoryId = categoryId;
        }

        public Integer getHasAdminCreated() {
            return hasAdminCreated;
        }

        public void setHasAdminCreated(Integer hasAdminCreated) {
            this.hasAdminCreated = hasAdminCreated;
        }

        public Integer getCreatorId() {
            return creatorId;
        }

        public void setCreatorId(Integer creatorId) {
            this.creatorId = creatorId;
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

        public String getCategoryName() {
            return categoryName;
        }

        public void setCategoryName(String categoryName) {
            this.categoryName = categoryName;
        }

        public String getCreatorName() {
            return creatorName;
        }

        public void setCreatorName(String creatorName) {
            this.creatorName = creatorName;
        }

        public Object getIsFavorite() {
            return isFavorite;
        }

        public void setIsFavorite(Object isFavorite) {
            this.isFavorite = isFavorite;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

    public class Datum {
        @SerializedName("ads_info")
        private AdsInfo adsInfo;
        @SerializedName("ads_images")
        private List<AdsImage> adsImages = null;
        private Map<String, Object> additionalProperties = new HashMap<String, Object>();

        public AdsInfo getAdsInfo() {
            return adsInfo;
        }

        public void setAdsInfo(AdsInfo adsInfo) {
            this.adsInfo = adsInfo;
        }

        public List<AdsImage> getAdsImages() {
            return adsImages;
        }

        public void setAdsImages(List<AdsImage> adsImages) {
            this.adsImages = adsImages;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

    public class NewHomeModel {
        @SerializedName("status")
        private Integer status;
        @SerializedName("message")
        private String message;
        @SerializedName("total_records")
        private Integer totalRecords;
        private List<Datum> data = null;
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

        public Integer getTotalRecords() {
            return totalRecords;
        }

        public void setTotalRecords(Integer totalRecords) {
            this.totalRecords = totalRecords;
        }

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }

        public Map<String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        public void setAdditionalProperty(String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }
}
