package com.example.stdjob;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Student implements Serializable {
    private int stdID;

    private String firstName;
    private String surName;
    private String username;
    private String password;
    private String email;
    private String phone;
    private String nationality;
    private Gender gender;
    private List<Interests> interests=new LinkedList<>();
    private List<TimeLine> timeLines=new LinkedList<>();
    private String activationCode;
    private boolean activeStatus;


    public Student(){}

    public Student(int stdID, String activationCode, boolean activeStatus,
                   String firstName, String surName, String username,
                   String password, String email, String phone, String nationality
            , Gender gender, List<Interests> interests, List<TimeLine> timeLines) {
        this.stdID = stdID;
        this.activationCode = activationCode;
        this.activeStatus = activeStatus;
        this.firstName = firstName;
        this.surName = surName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.nationality = nationality;
        this.gender = gender;
        this.interests = interests;
        this.timeLines = timeLines;
    }

    public void addTimeLine(TimeLine item){
        timeLines.add(item);
    }


    public int getStdID() {
        return stdID;
    }

    public void setStdID(int stdID) {
        this.stdID = stdID;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public boolean isActiveStatus() {
        return activeStatus;
    }

    public void setActiveStatus(boolean activeStatus) {
        this.activeStatus = activeStatus;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<Interests> getInterests() {
        return interests;
    }

    public void setInterests(List<Interests> interests) {
        this.interests = interests;
    }

    public List<TimeLine> getTimeLines() {
        return timeLines;
    }

    public void setTimeLines(List<TimeLine> timeLines) {
        this.timeLines = timeLines;
    }

    public static class Interests implements Serializable {
        private int interestID;
        private String interestName;
        public Interests(){}

        public Interests(int interestID, String interestName) {
            this.interestID = interestID;
            this.interestName = interestName;
        }

        public int getInterestID() {
            return interestID;
        }

        public void setInterestID(int interestID) {
            this.interestID = interestID;
        }

        public String getInterestName() {
            return interestName;
        }

        public void setInterestName(String interestName) {
            this.interestName = interestName;
        }

        @NonNull
        @Override
        public String toString() {
            return interestID + " : " + interestName;
        }
    }
    public static enum  Gender implements Serializable{
        MALE,FRMALE;
    }

    @Override
    public String toString() {
        return "Student{" +
                "stdID=" + stdID +
                ", firstName='" + firstName + '\'' +
                ", surName='" + surName + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", nationality='" + nationality + '\'' +
                ", activationCode='" + activationCode + '\'' +
                ", activeStatus=" + activeStatus +
                '}';
    }
}
