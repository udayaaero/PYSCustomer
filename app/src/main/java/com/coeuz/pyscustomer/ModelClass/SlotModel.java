package com.coeuz.pyscustomer.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vjy on 04-May-18.
 */

public class SlotModel {

    @SerializedName("slotId")
    @Expose
    private Integer slotId;
        @SerializedName("slotStartTime")
        @Expose
        private String slotStartTime;
    @SerializedName("offerPercentage")
    @Expose
    private String offerPercentage;
        @SerializedName("slotEndTime")
        @Expose
        private String slotEndTime;
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
        private Integer subActivityId;
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
        private String currencyCode;
        @SerializedName("slotReccurence")
        @Expose
        private String slotReccurence;
        @SerializedName("openingTime")
        @Expose
        private Object openingTime;
        @SerializedName("closingTime")
        @Expose
        private Object closingTime;
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
        private Object timings;
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
        @SerializedName("courseendDate")
        @Expose
        private Object courseendDate;

    public SlotModel(Integer slotId, String slotStartTime, String slotEndTime, Integer maxAllowed, Integer bookingSlotCost, Integer personCount) {

        this.slotId=slotId;
        this.slotStartTime=slotStartTime;
        this.slotEndTime=slotEndTime;
        this.maxAllowed=maxAllowed;
        this.bookingCost=bookingSlotCost;
        this.personCount=personCount;

    }

  public SlotModel(Integer slotId, String slotStartTime, String slotEndTime, Integer maxAllowed, Integer bookingSlotCost,
                     Integer personCount, String offerPersentage) {
        this.slotId=slotId;
        this.slotStartTime=slotStartTime;
        this.slotEndTime=slotEndTime;
        this.maxAllowed=maxAllowed;
        this.bookingCost=bookingSlotCost;
        this.personCount=personCount;
        this.offerPercentage=offerPersentage;
    }

    public Integer getSlotId() {
            return slotId;
        }

        public void setSlotId(Integer slotId) {
            this.slotId = slotId;
        }

        public String getSlotStartTime() {
            return slotStartTime;
        }

        public void setSlotStartTime(String slotStartTime) {
            this.slotStartTime = slotStartTime;
        }

    public String getOfferPercentage() {
        return offerPercentage;
    }

    public void setOfferPercentage(String offerPercentage) {
        this.offerPercentage = offerPercentage;
    }

        public String getSlotEndTime() {
            return slotEndTime;
        }

        public void setSlotEndTime(String slotEndTime) {
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

        public Integer getSubActivityId() {
            return subActivityId;
        }

        public void setSubActivityId(Integer subActivityId) {
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

        public String getCurrencyCode() {
            return currencyCode;
        }

        public void setCurrencyCode(String currencyCode) {
            this.currencyCode = currencyCode;
        }

        public String getSlotReccurence() {
            return slotReccurence;
        }

        public void setSlotReccurence(String slotReccurence) {
            this.slotReccurence = slotReccurence;
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

        public Object getTimings() {
            return timings;
        }

        public void setTimings(Object timings) {
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

        public Object getCourseendDate() {
            return courseendDate;
        }

        public void setCourseendDate(Object courseendDate) {
            this.courseendDate = courseendDate;
        }

}