package com.coeuz.pyscustomer.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vjy on 17-Apr-18.
 */

public class AmenitiesModel {

    @SerializedName("amenityId")
    @Expose
    private Integer amenityId;
    @SerializedName("amenityType")
    @Expose
    private String amenityType;
    @SerializedName("activityId")
    @Expose
    private Object activityId;
    @SerializedName("amenities")
    @Expose
    private Object amenities;
    @SerializedName("activity")
    @Expose
    private Object activity;
    @SerializedName("availableAmenities")
    @Expose
    private Object availableAmenities;
    @SerializedName("status")
    @Expose
    private Boolean status;

    public AmenitiesModel(Integer amenityId, String amenityType) {
        this.amenityId=amenityId;
        this.amenityType=amenityType;
    }


    public Integer getAmenityId() {
        return amenityId;
    }

    public void setAmenityId(Integer amenityId) {
        this.amenityId = amenityId;
    }

    public String getAmenityType() {
        return amenityType;
    }

    public void setAmenityType(String amenityType) {
        this.amenityType = amenityType;
    }

    public Object getActivityId() {
        return activityId;
    }

    public void setActivityId(Object activityId) {
        this.activityId = activityId;
    }

    public Object getAmenities() {
        return amenities;
    }

    public void setAmenities(Object amenities) {
        this.amenities = amenities;
    }

    public Object getActivity() {
        return activity;
    }

    public void setActivity(Object activity) {
        this.activity = activity;
    }

    public Object getAvailableAmenities() {
        return availableAmenities;
    }

    public void setAvailableAmenities(Object availableAmenities) {
        this.availableAmenities = availableAmenities;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

}