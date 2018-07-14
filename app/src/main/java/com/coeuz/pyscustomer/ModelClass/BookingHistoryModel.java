package com.coeuz.pyscustomer.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vjy on 13-Apr-18.
 */

public class BookingHistoryModel
    {

        @SerializedName("bookingId")
        @Expose
        private Integer bookingId;
        @SerializedName("vendorId")
        @Expose
        private Integer vendorId;
        @SerializedName("userId")
        @Expose
        private Integer userId;
        @SerializedName("slotId")
        @Expose
        private Integer slotId;
        @SerializedName("bookingStatus")
        @Expose
        private String bookingStatus;
        @SerializedName("bookedForDate")
        @Expose
        private Object bookedForDate;
        @SerializedName("bookingTimeStamp")
        @Expose
        private Object bookingTimeStamp;
        @SerializedName("subActivityId")
        @Expose
        private Integer subActivityId;
        @SerializedName("paymentsDTO")
        @Expose
        private Object paymentsDTO;
        @SerializedName("paymentId")
        @Expose
        private Object paymentId;
        @SerializedName("bookingType")
        @Expose
        private String bookingType;
        @SerializedName("personCount")
        @Expose
        private Integer personCount;
        @SerializedName("days")
        @Expose
        private Integer days;
        @SerializedName("dates")
        @Expose
        private Object dates;
        @SerializedName("type")
        @Expose
        private Object type;
        @SerializedName("slotReccurence")
        @Expose
        private Object slotReccurence;
        @SerializedName("startTime")
        @Expose
        private Object startTime;
        @SerializedName("endTime")
        @Expose
        private Object endTime;
        @SerializedName("amount")
        @Expose
        private Double amount;
        @SerializedName("booedforDate")
        @Expose
        private String booedforDate;
        @SerializedName("bookingtimeStamp")
        @Expose
        private String bookingtimeStamp;
        @SerializedName("bookingid")
        @Expose
        private String bookingid;
        @SerializedName("personcount")
        @Expose
        private String personcount;
        @SerializedName("amount1")
        @Expose
        private String amount1;
        @SerializedName("slotid")
        @Expose
        private String slotid;
        @SerializedName("bookedforDate")
        @Expose
        private Object bookedforDate;
        @SerializedName("vendorName")
        @Expose
        private String vendorName;
        @SerializedName("subActivityType")
        @Expose
        private String subActivityType;
        @SerializedName("courseStartDate")
        @Expose
        private Object courseStartDate;
        @SerializedName("courseEndDate")
        @Expose
        private Object courseEndDate;
        @SerializedName("slotStartTime")
        @Expose
        private Object slotStartTime;
        @SerializedName("slotEndTime")
        @Expose
        private Object slotEndTime;
        @SerializedName("memberShipType")
        @Expose
        private Object memberShipType;


        public BookingHistoryModel(String bookingStatus, String bookingType, String booedforDate, String bookingtimeStamp,
                                   String bookingid, String personcount, String amount1, String slotid, String subActivityType, String vendorName) {
            this.bookingStatus=bookingStatus;
            this.bookingType=bookingType;
            this.booedforDate=booedforDate;
            this.bookingtimeStamp=bookingtimeStamp;
            this.bookingid=bookingid;
            this.personcount=personcount;
            this.amount1=amount1;
            this.slotid=slotid;
            this.subActivityType=subActivityType;
            this.vendorName=vendorName;

        }

        public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Integer getVendorId() {
        return vendorId;
    }

    public void setVendorId(Integer vendorId) {
        this.vendorId = vendorId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getSlotId() {
        return slotId;
    }

    public void setSlotId(Integer slotId) {
        this.slotId = slotId;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public Object getBookedForDate() {
        return bookedForDate;
    }

    public void setBookedForDate(Object bookedForDate) {
        this.bookedForDate = bookedForDate;
    }

    public Object getBookingTimeStamp() {
        return bookingTimeStamp;
    }

    public void setBookingTimeStamp(Object bookingTimeStamp) {
        this.bookingTimeStamp = bookingTimeStamp;
    }

    public Integer getSubActivityId() {
        return subActivityId;
    }

    public void setSubActivityId(Integer subActivityId) {
        this.subActivityId = subActivityId;
    }

    public Object getPaymentsDTO() {
        return paymentsDTO;
    }

    public void setPaymentsDTO(Object paymentsDTO) {
        this.paymentsDTO = paymentsDTO;
    }

    public Object getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Object paymentId) {
        this.paymentId = paymentId;
    }

    public String getBookingType() {
        return bookingType;
    }

    public void setBookingType(String bookingType) {
        this.bookingType = bookingType;
    }

    public Integer getPersonCount() {
        return personCount;
    }

    public void setPersonCount(Integer personCount) {
        this.personCount = personCount;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public Object getDates() {
        return dates;
    }

    public void setDates(Object dates) {
        this.dates = dates;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public Object getSlotReccurence() {
        return slotReccurence;
    }

    public void setSlotReccurence(Object slotReccurence) {
        this.slotReccurence = slotReccurence;
    }

    public Object getStartTime() {
        return startTime;
    }

    public void setStartTime(Object startTime) {
        this.startTime = startTime;
    }

    public Object getEndTime() {
        return endTime;
    }

    public void setEndTime(Object endTime) {
        this.endTime = endTime;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getBooedforDate() {
        return booedforDate;
    }

    public void setBooedforDate(String booedforDate) {
        this.booedforDate = booedforDate;
    }

    public String getBookingtimeStamp() {
        return bookingtimeStamp;
    }

    public void setBookingtimeStamp(String bookingtimeStamp) {
        this.bookingtimeStamp = bookingtimeStamp;
    }

    public String getBookingid() {
        return bookingid;
    }

    public void setBookingid(String bookingid) {
        this.bookingid = bookingid;
    }

    public String getPersoncount() {
        return personcount;
    }

    public void setPersoncount(String personcount) {
        this.personcount = personcount;
    }

    public String getAmount1() {
        return amount1;
    }

    public void setAmount1(String amount1) {
        this.amount1 = amount1;
    }

    public String getSlotid() {
        return slotid;
    }

    public void setSlotid(String slotid) {
        this.slotid = slotid;
    }

    public Object getBookedforDate() {
        return bookedforDate;
    }

    public void setBookedforDate(Object bookedforDate) {
        this.bookedforDate = bookedforDate;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getSubActivityType() {
        return subActivityType;
    }

    public void setSubActivityType(String subActivityType) {
        this.subActivityType = subActivityType;
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

    public Object getMemberShipType() {
        return memberShipType;
    }

    public void setMemberShipType(Object memberShipType) {
        this.memberShipType = memberShipType;
    }

}



