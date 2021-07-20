package com.example.stdjob;

import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class TimeLine implements Serializable {
    private int day;
    private int month;
    private String weekDay;
    private String monthName;
    private List<Hours> hours=new LinkedList<>();


    public TimeLine(){}

    public void setDate(int month,int day,String weekDay,String monthName){
        this.month=month;
        this.day=day;
        this.weekDay=weekDay;
        this.monthName=monthName;
    }



    public void setDay(int day) {
        this.day = day;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setHours(List<Hours> hours) {
        this.hours = hours;
    }

    public void setMonthName(String monthName) {
        this.monthName = monthName;
    }

    public void setWeekDay(String weekDay) {
        this.weekDay = weekDay;
    }

    public String getMonthName() {
        return monthName;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public void addHour(Hours hour){
        hours.add(hour);
    }


    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public List<Hours> getHours() {
        return hours;
    }

    public static class Hours implements Serializable{
        int fromHour;
        int toHour;
        int fromMin;
        int toMin;

        public Hours(int fromHour, int toHour, int fromMin, int toMin) {
            this.fromHour = fromHour;
            this.toHour = toHour;
            this.fromMin = fromMin;
            this.toMin = toMin;
        }
        public Hours(){}

        public void setFromHour(int fromHour) {
            this.fromHour = fromHour;
        }

        public void setToHour(int toHour) {
            this.toHour = toHour;
        }

        public void setFromMin(int fromMin) {
            this.fromMin = fromMin;
        }

        public void setToMin(int toMin) {
            this.toMin = toMin;
        }

        public String FromTime(){
            String from=fromHour + ":" +fromMin;
            return from;
        }

        public int getFromHour() {
            return fromHour;
        }

        public int getToHour() {
            return toHour;
        }

        public int getFromMin() {
            return fromMin;
        }

        public int getToMin() {
            return toMin;
        }

        public String toTime(){
            String to=toHour + ":"+toMin;
            return to;
        }
    }

    @Override
    public String toString() {
        return "TimeLine{" +
                "day=" + day +
                ", month=" + month +
                ", weekDay='" + weekDay + '\'' +
                ", monthName='" + monthName +
                '}';
    }
}
