package com.coeuz.pyscustomer.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class SubActivityModel {@SerializedName("vendorId")
@Expose
private Integer vendorId;
    @SerializedName("vendorName")
    @Expose
    private String vendorName;
    @SerializedName("vendorType")
    @Expose
    private Object vendorType;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("area")
    @Expose
    private String area;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("contact1")
    @Expose
    private Object contact1;
    @SerializedName("categoryType")
    @Expose
    private Object categoryType;
    @SerializedName("activityId")
    @Expose
    private Integer activityId;
    @SerializedName("groupId")
    @Expose
    private Integer groupId;
    @SerializedName("status")
    @Expose
    private Object status;
    @SerializedName("latitude")
    @Expose
    private Double latitude;
    @SerializedName("longitude")
    @Expose
    private Double longitude;
    @SerializedName("availableAmenities")
    @Expose
    private Object availableAmenities;
    @SerializedName("subActivities")
    @Expose
    private String subActivities;
    @SerializedName("name")
    @Expose
    private Object name;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("mobile")
    @Expose
    private Object mobile;
    @SerializedName("gender")
    @Expose
    private Object gender;
    @SerializedName("dob")
    @Expose
    private Object dob;
    @SerializedName("state")
    @Expose
    private Object state;
    @SerializedName("authority")
    @Expose
    private Object authority;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("statusCode")
    @Expose
    private Integer statusCode;
    @SerializedName("userId")
    @Expose
    private Object userId;
    @SerializedName("activity")
    @Expose
    private String activity;
    @SerializedName("subActivitiesList")
    @Expose
    private Object subActivitiesList;
    @SerializedName("subActivityId")
    @Expose
    private Object subActivityId;
    @SerializedName("supervisors")
    @Expose
    private Object supervisors;
    @SerializedName("amenities")
    @Expose
    private Object amenities;
    @SerializedName("openingTime")
    @Expose
    private Object openingTime;
    @SerializedName("closingTime")
    @Expose
    private Object closingTime;
    @SerializedName("bookingCost")
    @Expose
    private Integer bookingCost;
    @SerializedName("vendorid")
    @Expose
    private Object vendorid;
    @SerializedName("groupid")
    @Expose
    private Object groupid;
    @SerializedName("vendorShopImage")
    @Expose
    private String vendorShopImage;



   /* public SubActivityModel(String vendorName, String area, Integer vendorId) {
        this.vendorName=vendorName;
        this.area=area;
        this.vendorId=vendorId;
    }*/

 /*   public SubActivityModel(String vendorName, String area, Integer vendorId, String subActivityId) {
        this.vendorName=vendorName;
        this.area=area;
        this.vendorId=vendorId;
        this.subActivityId=subActivityId;
    }*/

    public SubActivityModel(String vendorName, String area, Integer vendorId, String subActivityId, String vendorShopImage) {
        this.vendorName=vendorName;
        this.area=area;
        this.vendorId=vendorId;
        this.subActivityId=subActivityId;
        this.vendorShopImage=vendorShopImage;
    }

    public SubActivityModel(String vendorName, String area, Integer vendorId, String vendorShopImage) {
        this.vendorName=vendorName;
        this.area=area;
        this.vendorId=vendorId;
        this.vendorShopImage=vendorShopImage;
    }


    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public Object getVendorType() {
        return vendorType;
    }

    public void setVendorType(Object vendorType) {
        this.vendorType = vendorType;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Object getContact1() {
        return contact1;
    }

    public void setContact1(Object contact1) {
        this.contact1 = contact1;
    }

    public Object getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(Object categoryType) {
        this.categoryType = categoryType;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Object getAvailableAmenities() {
        return availableAmenities;
    }

    public void setAvailableAmenities(Object availableAmenities) {
        this.availableAmenities = availableAmenities;
    }

    public String getSubActivities() {
        return subActivities;
    }

    public void setSubActivities(String subActivities) {
        this.subActivities = subActivities;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getMobile() {
        return mobile;
    }

    public void setMobile(Object mobile) {
        this.mobile = mobile;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public Object getDob() {
        return dob;
    }

    public void setDob(Object dob) {
        this.dob = dob;
    }

    public Object getState() {
        return state;
    }

    public void setState(Object state) {
        this.state = state;
    }

    public Object getAuthority() {
        return authority;
    }

    public void setAuthority(Object authority) {
        this.authority = authority;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Object getUserId() {
        return userId;
    }

    public void setUserId(Object userId) {
        this.userId = userId;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getVendorShopImage() {
        return vendorShopImage;
    }

    public void setVendorShopImage(String vendorShopImage) {
        this.vendorShopImage = vendorShopImage;
    }

    public Object getSubActivitiesList() {
        return subActivitiesList;
    }

    public void setSubActivitiesList(Object subActivitiesList) {
        this.subActivitiesList = subActivitiesList;
    }

    public Object getSubActivityId() {
        return subActivityId;
    }

    public void setSubActivityId(Object subActivityId) {
        this.subActivityId = subActivityId;
    }

    public Object getSupervisors() {
        return supervisors;
    }

    public void setSupervisors(Object supervisors) {
        this.supervisors = supervisors;
    }

    public Object getAmenities() {
        return amenities;
    }

    public void setAmenities(Object amenities) {
        this.amenities = amenities;
    }

    public Object getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Object openingTime) {
        this.openingTime = openingTime;
    }

    public Object getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(Object closingTime) {
        this.closingTime = closingTime;
    }

    public Integer getBookingCost() {
        return bookingCost;
    }

    public void setBookingCost(Integer bookingCost) {
        this.bookingCost = bookingCost;
    }

    public Object getVendorid() {
        return vendorid;
    }

    public void setVendorid(Object vendorid) {
        this.vendorid = vendorid;
    }

    public Object getGroupid() {
        return groupid;
    }

    public void setGroupid(Object groupid) {
        this.groupid = groupid;
    }

}