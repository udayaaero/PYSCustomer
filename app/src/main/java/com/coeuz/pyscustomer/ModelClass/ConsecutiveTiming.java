package com.coeuz.pyscustomer.ModelClass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by vjy on 06-Jul-18.
 */

public class ConsecutiveTiming {

    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("startTime")
    @Expose
    private String startTime;
    @SerializedName("endTime")
    @Expose
    private Object endTime;
    @SerializedName("status")
    @Expose
    private Boolean status;



    public ConsecutiveTiming(String slotStartTime) {
        this.startTime = slotStartTime;

    }


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Object getEndTime() {
        return endTime;
    }

    public void setEndTime(Object endTime) {
        this.endTime = endTime;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}