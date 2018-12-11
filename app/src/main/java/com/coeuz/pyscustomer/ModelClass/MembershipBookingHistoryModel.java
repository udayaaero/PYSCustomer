package com.coeuz.pyscustomer.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vjy on 31-Jul-18.
 */

public class MembershipBookingHistoryModel {
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
    private String slotReccurence;
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
    private Object booedforDate;
    @SerializedName("bookingtimeStamp")
    @Expose
    private String bookingtimeStamp;
    @SerializedName("bookingid")
    @Expose
    private Object bookingid;
    @SerializedName("personcount")
    @Expose
    private Object personcount;
    @SerializedName("amount1")
    @Expose
    private Object amount1;
    @SerializedName("slotid")
    @Expose
    private Object slotid;
    @SerializedName("bookedforDate")
    @Expose
    private String bookedforDate;
    @SerializedName("vendorName")
    @Expose
    private String vendorName;
    @SerializedName("subActivityType")
    @Expose
    private String subActivityType;
    @SerializedName("courseStartDate")
    @Expose
    private String courseStartDate;
    @SerializedName("courseEndDate")
    @Expose
    private String courseEndDate;
    @SerializedName("slotStartTime")
    @Expose
    private String slotStartTime;
    @SerializedName("slotEndTime")
    @Expose
    private String slotEndTime;
    @SerializedName("memberShipType")
    @Expose
    private Object memberShipType;
    @SerializedName("errorMessage")
    @Expose
    private Object errorMessage;
    @SerializedName("contactNo")
    @Expose
    private Object contactNo;
    @SerializedName("area")
    @Expose
    private Object area;
    @SerializedName("notes")
    @Expose
    private Object notes;
    @SerializedName("slotStatus")
    @Expose
    private String slotStatus;
    @SerializedName("otp")
    @Expose
    private Object otp;

    public MembershipBookingHistoryModel(String bookingStatus, String bookingType, String bookedforDate, String bookingtimeStamp,
                                     Integer bookingId, Integer personCount, Double amount, String subActivityType,
                                         String vendorName,String memberShipType) {
        this.bookingStatus = bookingStatus;
        this.bookingType = bookingType;
        this.bookedforDate = bookedforDate;
        this.bookingtimeStamp = bookingtimeStamp;
        this.bookingId = bookingId;
        this.personCount = personCount;
        this.amount = amount;
        this.subActivityType = subActivityType;
        this.vendorName = vendorName;
        this.memberShipType = memberShipType;
    }

    public MembershipBookingHistoryModel(String bookingStatus, String bookingType, String bookedforDate, String bookingtimeStamp, Integer bookingId, Integer personCount, Double amount, String subActivityType, String vendorName, String memberShipType, String otp) {
        this.bookingStatus = bookingStatus;
        this.bookingType = bookingType;
        this.bookedforDate = bookedforDate;
        this.bookingtimeStamp = bookingtimeStamp;
        this.bookingId = bookingId;
        this.personCount = personCount;
        this.amount = amount;
        this.subActivityType = subActivityType;
        this.vendorName = vendorName;
        this.memberShipType = memberShipType;
        this.otp = otp;
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

    public String getSlotReccurence() {
        return slotReccurence;
    }

    public void setSlotReccurence(String slotReccurence) {
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

    public Object getBooedforDate() {
        return booedforDate;
    }

    public void setBooedforDate(Object booedforDate) {
        this.booedforDate = booedforDate;
    }

    public String getBookingtimeStamp() {
        return bookingtimeStamp;
    }

    public void setBookingtimeStamp(String bookingtimeStamp) {
        this.bookingtimeStamp = bookingtimeStamp;
    }

    public Object getBookingid() {
        return bookingid;
    }

    public void setBookingid(Object bookingid) {
        this.bookingid = bookingid;
    }

    public Object getPersoncount() {
        return personcount;
    }

    public void setPersoncount(Object personcount) {
        this.personcount = personcount;
    }

    public Object getAmount1() {
        return amount1;
    }

    public void setAmount1(Object amount1) {
        this.amount1 = amount1;
    }

    public Object getSlotid() {
        return slotid;
    }

    public void setSlotid(Object slotid) {
        this.slotid = slotid;
    }

    public String getBookedforDate() {
        return bookedforDate;
    }

    public void setBookedforDate(String bookedforDate) {
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

    public String getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(String courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public String getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(String courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public String getSlotStartTime() {
        return slotStartTime;
    }

    public void setSlotStartTime(String slotStartTime) {
        this.slotStartTime = slotStartTime;
    }

    public String getSlotEndTime() {
        return slotEndTime;
    }

    public void setSlotEndTime(String slotEndTime) {
        this.slotEndTime = slotEndTime;
    }

    public Object getMemberShipType() {
        return memberShipType;
    }

    public void setMemberShipType(Object memberShipType) {
        this.memberShipType = memberShipType;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getContactNo() {
        return contactNo;
    }

    public void setContactNo(Object contactNo) {
        this.contactNo = contactNo;
    }

    public Object getArea() {
        return area;
    }

    public void setArea(Object area) {
        this.area = area;
    }

    public Object getNotes() {
        return notes;
    }

    public void setNotes(Object notes) {
        this.notes = notes;
    }

    public String getSlotStatus() {
        return slotStatus;
    }

    public void setSlotStatus(String slotStatus) {
        this.slotStatus = slotStatus;
    }

    public Object getOtp() {
        return otp;
    }

    public void setOtp(Object otp) {
        this.otp = otp;
    }


}
