package com.example.stdjob;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Job implements Parcelable {
    private int jobID;
    private String title;
    private double price;
    private String essentials;
    private Status status;
    private int payStatus;
    private double rate;
    private String description;
    private int empID;
    private String empName;
    private String empAddress;
    private int stdID;
    private List<TimeLine> timeLines=new LinkedList<>();



    public Job(){}

    public Job(int jobID, String title, double price, String essentials
            , String location, Status status, int payStatus, double rate,
               String description, int empID, String empName, String empAddress,
               int stdID, List<TimeLine> timeLines) {
        this.jobID = jobID;
        this.title = title;
        this.price = price;
        this.essentials = essentials;
        this.status = status;
        this.payStatus = payStatus;
        this.rate = rate;
        this.description = description;
        this.empID = empID;
        this.empName = empName;
        this.empAddress = empAddress;
        this.stdID = stdID;
        this.timeLines = timeLines;
    }
    public Job(Parcel parcel){
        this.jobID=parcel.readInt();
        this.title=parcel.readString();
        this.price=parcel.readDouble();
        this.essentials=parcel.readString();
        this.status= (Status) parcel.readSerializable();
        this.payStatus=parcel.readInt();
        this.rate=parcel.readDouble();
        this.description=parcel.readString();
        this.empID=parcel.readInt();
        this.empName=parcel.readString();
        this.empAddress=parcel.readString();
        this.stdID=parcel.readInt();
        parcel.readList(timeLines,TimeLine.class.getClassLoader());
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(jobID);
        dest.writeString(title);
        dest.writeDouble(price);
        dest.writeString(essentials);
        dest.writeSerializable(status);
        dest.writeInt(payStatus);
        dest.writeDouble(rate);
        dest.writeString(description);
        dest.writeInt(empID);
        dest.writeString(empName);
        dest.writeString(empAddress);
        dest.writeInt(stdID);
        dest.writeList(timeLines);

    }


    public int getPayStatus() {
        return payStatus;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }

    public List<TimeLine> getTimeLines() {
        return timeLines;
    }

    public void setTimeLines(List<TimeLine> timeLines) {
        this.timeLines = timeLines;
    }

    public int getJobID() {
        return jobID;
    }

    public void setJobID(int jobID) {
        this.jobID = jobID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getEssentials() {
        return essentials;
    }

    public void setEssentials(String essentials) {
        this.essentials = essentials;
    }


    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int isPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getEmpID() {
        return empID;
    }

    public void setEmpID(int empID) {
        this.empID = empID;
    }

    public int getStdID() {
        return stdID;
    }

    public void setStdID(int stdID) {
        this.stdID = stdID;
    }

    @NonNull
    @Override
    public String toString() {
        return title;
    }

    @Override
    public int describeContents() {
        return 0;
    }




    public static Creator<Job> CREATOR=new Creator<Job>() {
        @Override
        public Job createFromParcel(Parcel source) {
            return new Job(source);
        }

        @Override
        public Job[] newArray(int size) {
            return new Job[size];
        }
    };

    public static enum Status implements Serializable {
        NEW,DOING,DONE;
    }
}
