package com.coeuz.pyscustomer.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ConsecutiveSlotModel {


    @SerializedName("slotId")
    @Expose
    private Integer slotId;
    @SerializedName("slotStartTime")
    @Expose
    private Object slotStartTime;
    @SerializedName("slotEndTime")
    @Expose
    private Object slotEndTime;
    @SerializedName("slotStatus")
    @Expose
    private Object slotStatus;
    @SerializedName("type")
    @Expose
    private Object type;
    @SerializedName("days")
    @Expose
    private Object days;
    @SerializedName("effectiveFrom")
    @Expose
    private Object effectiveFrom;
    @SerializedName("effectiveTill")
    @Expose
    private Object effectiveTill;
    @SerializedName("maxAllowed")
    @Expose
    private Integer maxAllowed;
    @SerializedName("subActivityId")
    @Expose
    private Object subActivityId;
    @SerializedName("date")
    @Expose
    private Integer date;
    @SerializedName("vendorId")
    @Expose
    private Integer vendorId;
    @SerializedName("bookingCost")
    @Expose
    private Integer bookingCost;
    @SerializedName("currencyCode")
    @Expose
    private Object currencyCode;
    @SerializedName("slotReccurence")
    @Expose
    private Object slotReccurence;
    @SerializedName("openingTime")
    @Expose
    private String openingTime;
    @SerializedName("closingTime")
    @Expose
    private String closingTime;
    @SerializedName("courseStartDate")
    @Expose
    private Object courseStartDate;
    @SerializedName("courseEndDate")
    @Expose
    private Object courseEndDate;
    @SerializedName("courseRegistrationEndDate")
    @Expose
    private Object courseRegistrationEndDate;
    @SerializedName("bookingType")
    @Expose
    private Object bookingType;
    @SerializedName("membershipType")
    @Expose
    private Object membershipType;
    @SerializedName("timings")
    @Expose
    private List<ConsecutiveTiming> timings = null;
    @SerializedName("slotID")
    @Expose
    private Integer slotID;
    @SerializedName("personCount")
    @Expose
    private Integer personCount;
    @SerializedName("subActivityID")
    @Expose
    private Integer subActivityID;
    @SerializedName("vendorName")
    @Expose
    private Object vendorName;
    @SerializedName("coursestartDate")
    @Expose
    private Object coursestartDate;
    @SerializedName("membershipTYPE")
    @Expose
    private Integer membershipTYPE;
    @SerializedName("bookedForDate")
    @Expose
    private Object bookedForDate;
    @SerializedName("vendorID")
    @Expose
    private Object vendorID;
    @SerializedName("subActivityType")
    @Expose
    private Object subActivityType;
    @SerializedName("bookingId")
    @Expose
    private Integer bookingId;
    @SerializedName("bookingTimeStamp")
    @Expose
    private Object bookingTimeStamp;
    @SerializedName("courseendDate")
    @Expose
    private Object courseendDate;

    public ConsecutiveSlotModel(Integer slotId, Integer maxAllowed, Integer bookingSlotCost) {
        this.slotId=slotId;
        this.maxAllowed=maxAllowed;
        this.bookingCost=bookingSlotCost;
    }


    public Integer getSlotId() {
        return slotId;
    }

    public void setSlotId(Integer slotId) {
        this.slotId = slotId;
    }

    public Object getSlotStartTime() {
        return slotStartTime;
    }

    public void setSlotStartTime(Object slotStartTime) {
        this.slotStartTime = slotStartTime;
    }

    public Object getSlotEndTime() {
        return slotEndTime;
    }

    public void setSlotEndTime(Object slotEndTime) {
        this.slotEndTime = slotEndTime;
    }

    public Object getSlotStatus() {
        return slotStatus;
    }

    public void setSlotStatus(Object slotStatus) {
        this.slotStatus = slotStatus;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public Object getDays() {
        return days;
    }

    public void setDays(Object days) {
        this.days = days;
    }

    public Object getEffectiveFrom() {
        return effectiveFrom;
    }

    public void setEffectiveFrom(Object effectiveFrom) {
        this.effectiveFrom = effectiveFrom;
    }

    public Object getEffectiveTill() {
        return effectiveTill;
    }

    public void setEffectiveTill(Object effectiveTill) {
        this.effectiveTill = effectiveTill;
    }

    public Integer getMaxAllowed() {
        return maxAllowed;
    }

    public void setMaxAllowed(Integer maxAllowed) {
        this.maxAllowed = maxAllowed;
    }

    public Object getSubActivityId() {
        return subActivityId;
    }

    public void setSubActivityId(Object subActivityId) {
        this.subActivityId = subActivityId;
    }

    public Integer getDate() {
        return date;
    }

    public void setDate(Integer date) {
        this.date = date;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Integer getBookingCost() {
        return bookingCost;
    }

    public void setBookingCost(Integer bookingCost) {
        this.bookingCost = bookingCost;
    }

    public Object getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(Object currencyCode) {
        this.currencyCode = currencyCode;
    }

    public Object getSlotReccurence() {
        return slotReccurence;
    }

    public void setSlotReccurence(Object slotReccurence) {
        this.slotReccurence = slotReccurence;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getClosingTime() {
        return closingTime;
    }

    public void setClosingTime(String closingTime) {
        this.closingTime = closingTime;
    }

    public Object getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(Object courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public Object getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(Object courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public Object getCourseRegistrationEndDate() {
        return courseRegistrationEndDate;
    }

    public void setCourseRegistrationEndDate(Object courseRegistrationEndDate) {
        this.courseRegistrationEndDate = courseRegistrationEndDate;
    }

    public Object getBookingType() {
        return bookingType;
    }

    public void setBookingType(Object bookingType) {
        this.bookingType = bookingType;
    }

    public Object getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(Object membershipType) {
        this.membershipType = membershipType;
    }

    public List<ConsecutiveTiming> getTimings() {
        return timings;
    }

    public void setTimings(List<ConsecutiveTiming> timings) {
        this.timings = timings;
    }

    public Integer getSlotID() {
        return slotID;
    }

    public void setSlotID(Integer slotID) {
        this.slotID = slotID;
    }

    public Integer getPersonCount() {
        return personCount;
    }

    public void setPersonCount(Integer personCount) {
        this.personCount = personCount;
    }

    public Integer getSubActivityID() {
        return subActivityID;
    }

    public void setSubActivityID(Integer subActivityID) {
        this.subActivityID = subActivityID;
    }

    public Object getVendorName() {
        return vendorName;
    }

    public void setVendorName(Object vendorName) {
        this.vendorName = vendorName;
    }

    public Object getCoursestartDate() {
        return coursestartDate;
    }

    public void setCoursestartDate(Object coursestartDate) {
        this.coursestartDate = coursestartDate;
    }

    public Integer getMembershipTYPE() {
        return membershipTYPE;
    }

    public void setMembershipTYPE(Integer membershipTYPE) {
        this.membershipTYPE = membershipTYPE;
    }

    public Object getBookedForDate() {
        return bookedForDate;
    }

    public void setBookedForDate(Object bookedForDate) {
        this.bookedForDate = bookedForDate;
    }

    public Object getVendorID() {
        return vendorID;
    }

    public void setVendorID(Object vendorID) {
        this.vendorID = vendorID;
    }

    public Object getSubActivityType() {
        return subActivityType;
    }

    public void setSubActivityType(Object subActivityType) {
        this.subActivityType = subActivityType;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Object getBookingTimeStamp() {
        return bookingTimeStamp;
    }

    public void setBookingTimeStamp(Object bookingTimeStamp) {
        this.bookingTimeStamp = bookingTimeStamp;
    }

    public Object getCourseendDate() {
        return courseendDate;
    }

    public void setCourseendDate(Object courseendDate) {
        this.courseendDate = courseendDate;
    }


    }







