package com.coeuz.pyscustomer.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vjy on 06-May-18.
 */

public class OfferModel {

    @SerializedName("groupId")
    @Expose
    private Integer groupId;
    @SerializedName("vendorId")
    @Expose
    private Integer vendorId;
    @SerializedName("subActivityId")
    @Expose
    private Object subActivityId;
    @SerializedName("startDate")
    @Expose
    private Integer startDate;
    @SerializedName("expiryDate")
    @Expose
    private Integer expiryDate;
    @SerializedName("offerCategorytype")
    @Expose
    private Object offerCategorytype;
    @SerializedName("benefits")
    @Expose
    private String benefits;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("offerId")
    @Expose
    private Object offerId;
    @SerializedName("groupName")
    @Expose
    private Object groupName;
    @SerializedName("vendorName")
    @Expose
    private Object vendorName;
    @SerializedName("subActivityType")
    @Expose
    private Object subActivityType;
    @SerializedName("offerTypeId")
    @Expose
    private Object offerTypeId;
    @SerializedName("categoryId")
    @Expose
    private Object categoryId;
    @SerializedName("publishId")
    @Expose
    private Object publishId;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("spending")
    @Expose
    private Object spending;
    @SerializedName("offerType")
    @Expose
    private Object offerType;
    @SerializedName("matos")
    @Expose
    private Object matos;
    @SerializedName("cyclic")
    @Expose
    private Object cyclic;
    @SerializedName("eligibleFee")
    @Expose
    private Object eligibleFee;
    @SerializedName("description")
    @Expose
    private Object description;
    @SerializedName("discount")
    @Expose
    private Object discount;
    @SerializedName("amount")
    @Expose
    private Object amount;
    @SerializedName("publisherName")
    @Expose
    private Object publisherName;
    @SerializedName("type")
    @Expose
    private Object type;

    public OfferModel(Integer startDate, Integer expiryDate, String offerCategorytype, String benefits) {

        this.startDate=startDate;
        this.expiryDate=expiryDate;
        this.offerCategorytype=offerCategorytype;
        this.benefits=benefits;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Object getSubActivityId() {
        return subActivityId;
    }

    public void setSubActivityId(Object subActivityId) {
        this.subActivityId = subActivityId;
    }

    public Integer getStartDate() {
        return startDate;
    }

    public void setStartDate(Integer startDate) {
        this.startDate = startDate;
    }

    public Integer getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Integer expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Object getOfferCategorytype() {
        return offerCategorytype;
    }

    public void setOfferCategorytype(Object offerCategorytype) {
        this.offerCategorytype = offerCategorytype;
    }

    public String getBenefits() {
        return benefits;
    }

    public void setBenefits(String benefits) {
        this.benefits = benefits;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Object getOfferId() {
        return offerId;
    }

    public void setOfferId(Object offerId) {
        this.offerId = offerId;
    }

    public Object getGroupName() {
        return groupName;
    }

    public void setGroupName(Object groupName) {
        this.groupName = groupName;
    }

    public Object getVendorName() {
        return vendorName;
    }

    public void setVendorName(Object vendorName) {
        this.vendorName = vendorName;
    }

    public Object getSubActivityType() {
        return subActivityType;
    }

    public void setSubActivityType(Object subActivityType) {
        this.subActivityType = subActivityType;
    }

    public Object getOfferTypeId() {
        return offerTypeId;
    }

    public void setOfferTypeId(Object offerTypeId) {
        this.offerTypeId = offerTypeId;
    }

    public Object getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Object categoryId) {
        this.categoryId = categoryId;
    }

    public Object getPublishId() {
        return publishId;
    }

    public void setPublishId(Object publishId) {
        this.publishId = publishId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Object getSpending() {
        return spending;
    }

    public void setSpending(Object spending) {
        this.spending = spending;
    }

    public Object getOfferType() {
        return offerType;
    }

    public void setOfferType(Object offerType) {
        this.offerType = offerType;
    }

    public Object getMatos() {
        return matos;
    }

    public void setMatos(Object matos) {
        this.matos = matos;
    }

    public Object getCyclic() {
        return cyclic;
    }

    public void setCyclic(Object cyclic) {
        this.cyclic = cyclic;
    }

    public Object getEligibleFee() {
        return eligibleFee;
    }

    public void setEligibleFee(Object eligibleFee) {
        this.eligibleFee = eligibleFee;
    }

    public Object getDescription() {
        return description;
    }

    public void setDescription(Object description) {
        this.description = description;
    }

    public Object getDiscount() {
        return discount;
    }

    public void setDiscount(Object discount) {
        this.discount = discount;
    }

    public Object getAmount() {
        return amount;
    }

    public void setAmount(Object amount) {
        this.amount = amount;
    }

    public Object getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(Object publisherName) {
        this.publisherName = publisherName;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }
}
