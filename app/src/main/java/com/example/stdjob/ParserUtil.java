package com.example.stdjob;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class ParserUtil {

    public static Student parseJson(String content){
        Student student=new Student();

        try {
            JSONObject object=new JSONObject(content);
            student.setStdID(object.getInt("stdID"));
            student.setFirstName(object.getString("firstName"));
            student.setSurName(object.getString("surname"));
            student.setUsername(object.getString("username"));
            student.setPassword(object.getString("password"));
            student.setEmail(object.getString("email"));
            student.setPhone(object.getString("phone"));
            student.setNationality(object.getString("nationality"));

            int tmpGender=object.getInt("gender");
            if(tmpGender == 1){
                student.setGender(Student.Gender.MALE);
            }else{
                student.setGender(Student.Gender.FRMALE);
            }

            student.setActivationCode(object.getString("activationCode"));
            student.setActiveStatus((object.getInt("activeStatus") == 1)?true:false);


            JSONArray interestArray=object.getJSONArray("interests");

            List<Student.Interests> interests=new LinkedList<>();
            for (int i=0;i<interestArray.length();i++){
                Student.Interests tmp=new Student.Interests();

                JSONArray item=interestArray.getJSONArray(i);
                tmp.setInterestID(item.getInt(0));
                tmp.setInterestName(item.getString(1));

                interests.add(tmp);
            }
            student.setInterests(interests);

//            JSONArray timelines=object.getJSONArray("timeLines");
//            JSONArray tmpArr=timelines.getJSONArray(0);
//
//            TimeLine t=new TimeLine();
//            t.setDay(tmpArr.getInt(0));
//            t.setMonth(tmpArr.getInt(1));
//            t.setMonthName(tmpArr.getString(2));
//            t.setWeekDay(tmpArr.getString(3));
//            JSONArray hours=tmpArr.getJSONArray(4);
//
//            for (int i=0;i<hours.length();i++){
//                JSONArray item=hours.getJSONArray(i);
//                int toh=item.getInt(0);
//                int tom=item.getInt(1);
//                int fromh=item.getInt(2);
//                int fromm=item.getInt(3);
//
//
//                TimeLine.Hours h=new TimeLine.Hours(fromh,toh,fromm,tom);
//                t.addHour(h);
//            }
//
//            List<TimeLine> timeLineList=new LinkedList<>();
//            timeLineList.add(t);
//            student.setTimeLines(timeLineList);

            List<TimeLine> timeLineList = new LinkedList<>();
            JSONArray timelines=object.getJSONArray("timeLines");
            for(int j=0;j<timelines.length();j++) {
                JSONArray tmpArr = timelines.getJSONArray(j);

                TimeLine t = new TimeLine();
                t.setDay(tmpArr.getInt(0));
                t.setMonth(tmpArr.getInt(1));
                t.setMonthName(tmpArr.getString(2));
                t.setWeekDay(tmpArr.getString(3));
                JSONArray hours = tmpArr.getJSONArray(4);

                for (int k = 0; k < hours.length(); k++) {
                    JSONArray item = hours.getJSONArray(k);
                    int toh = item.getInt(0);
                    int tom = item.getInt(1);
                    int fromh = item.getInt(2);
                    int fromm = item.getInt(3);


                    TimeLine.Hours h = new TimeLine.Hours(fromh, toh, fromm, tom);
                    t.addHour(h);
                }


                timeLineList.add(t);
            }

            student.setTimeLines(timeLineList);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return student;
    }
    public static Employee parseJsonEmployee(String content){
        Employee employee=new Employee();

        try {
            JSONObject object=new JSONObject(content);
            employee.setEmpID(object.getInt("empID"));
            employee.setName(object.getString("name"));
            employee.setPassword(object.getString("password"));
            employee.setAddress(object.getString("address"));
            employee.setEmail(object.getString("email"));
            int interest=object.getInt("isInterested");
            boolean interested=(interest == 1)?true:false;
            employee.setInterested(interested);
            employee.setPhone(object.getString("phone"));
            employee.setUsername(object.getString("username"));



        } catch (JSONException e) {
            e.printStackTrace();
        }


        return employee;
    }

    public static List<Job> parsJobs(String content) {
        List<Job> jobList = new LinkedList<>();

        try {
            JSONArray array = new JSONArray(content);

            for (int i = 0; i < array.length(); i++) {
                JSONObject object = array.getJSONObject(i);

                Job tmp = new Job();
                tmp.setJobID(object.getInt("jobID"));
                tmp.setTitle(object.getString("title"));
                tmp.setPrice(object.getDouble("price"));
                tmp.setEssentials(object.getString("essentials"));
                String st = object.getString("status");
                Job.Status status = null;
                switch (st.toLowerCase()) {
                    case "new":
                        status = Job.Status.NEW;
                        break;
                    case "doing":
                        status = Job.Status.DOING;
                        break;
                    case "done":
                        status = Job.Status.DONE;
                        break;
                }
                tmp.setStatus(status);
                tmp.setPayStatus(object.getInt("payStatus"));
                tmp.setRate(object.getDouble("rate"));
                tmp.setDescription(object.getString("description"));
                tmp.setEmpID(object.getInt("empID"));
                tmp.setEmpName(object.getString("empName"));
                tmp.setEmpAddress(object.getString("empAddress"));
                tmp.setStdID(object.getInt("stdID"));

                List<TimeLine> timeLineList = new LinkedList<>();
                JSONArray timelines = object.getJSONArray("timeLines");
                for (int j = 0; j < timelines.length(); j++) {
                    JSONArray tmpArr = timelines.getJSONArray(j);

                    TimeLine t = new TimeLine();
                    t.setDay(tmpArr.getInt(0));
                    t.setMonth(tmpArr.getInt(1));
                    t.setMonthName(tmpArr.getString(2));
                    t.setWeekDay(tmpArr.getString(3));
                    JSONArray hours = tmpArr.getJSONArray(4);

                    for (int k = 0; k < hours.length(); k++) {
                        JSONArray item = hours.getJSONArray(k);
                        int toh = item.getInt(2);
                        int tom = item.getInt(3);
                        int fromh = item.getInt(0);
                        int fromm = item.getInt(1);


                        TimeLine.Hours h = new TimeLine.Hours(fromh, toh, fromm, tom);
                        t.addHour(h);
                    }


                    timeLineList.add(t);
                }

                tmp.setTimeLines(timeLineList);
                jobList.add(tmp);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jobList;
    }


}
