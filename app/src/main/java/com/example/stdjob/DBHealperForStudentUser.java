package com.example.stdjob;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DBHealperForStudentUser extends SQLiteOpenHelper {
    public static final String DB_NAME="stdJob";
    public static final int DB_VERSION=1;

    public static final String CREATE_INTEREST="CREATE TABLE `interest` (" +
            "`interestID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "`interestName` TEXT NOT NULL,"+
            "stdFKI INTEGER,"+
            "FOREIGN KEY(`stdFKI`) REFERENCES `student`(`stdID`));";

    public static final String CRETE_HOURS="CREATE TABLE `studentHours` (" +
            "`hoursID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "`fromHour` INTEGER NOT NULL," +
            "`fromMin` INTEGER NOT NULL," +
            "`toHour` INTEGER NOT NULL," +
            "`toMin` INTEGER NOT NULL," +
            "`dayFK` INTEGER," +
            "FOREIGN KEY(`dayFK`) REFERENCES `studentFreeDay`(`dayID`) );";

    public static final String CRETE_DAYS="CREATE TABLE `studentFreeDay` (" +
            "`dayID` INTEGER NOT NULL UNIQUE," +
            "`day` INTEGER NOT NULL," +
            "`month` INTEGER NOT NULL," +
            "`weekDay` TEXT NOT NULL," +
            "`monthName` TEXT NOT NULL," +
            "stdFK INTEGER,"+
            "PRIMARY KEY(`dayID`)," +
            "FOREIGN KEY(`stdFK`) REFERENCES `student`(`stdID`));";


    public static final String CREATE_STUDENT="CREATE TABLE `student` (" +
            "`stdID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "`firstName` TEXT NOT NULL," +
            "`surName` TEXT NOT NULL," +
            "`username` TEXT NOT NULL," +
            "`password` TEXT NOT NULL," +
            "`email` TEXT NOT NULL," +
            "`phone` TEXT NOT NULL," +
            "`nationality` TEXT NOT NULL," +
            "`gender` INTEGER NOT NULL," +
            "`activationCode` TEXT NOT NULL," +
            "`activeStatus` INTEGER NOT NULL);";


    public static final String CREATE_EMPLOYEE="CREATE TABLE `employee` (" +
            "`empID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," +
            "`name` TEXT NOT NULL," +
            "`username` TEXT NOT NULL," +
            "`password` TEXT NOT NULL," +
            "`email` TEXT NOT NULL," +
            "`phone` TEXT NOT NULL," +
            "`address` TEXT NOT NULL," +
            "`isInterested` TEXT NOT NULL);";


    public DBHealperForStudentUser(@Nullable Context context) {
        super(context, DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        int userType=App.prefs.getIntValue(AppPreferences.PreferencesKeys.USERTYPE);

        if(userType == App.STUDENT_TYPE_CODE){
            db.execSQL(CREATE_INTEREST);
            db.execSQL(CRETE_HOURS);
            db.execSQL(CRETE_DAYS);
            db.execSQL(CREATE_STUDENT);
        }else{
            db.execSQL(CREATE_EMPLOYEE);
        }


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        int userType=App.prefs.getIntValue(AppPreferences.PreferencesKeys.USERTYPE);

        if(userType == App.STUDENT_TYPE_CODE){
            db.execSQL("DROP TABLE IF EXISTS " + Tables.INTEREST);
            db.execSQL("DROP TABLE IF EXISTS " + Tables.HOURS);
            db.execSQL("DROP TABLE IF EXISTS " + Tables.FREEDAY);
            db.execSQL("DROP TABLE IF EXISTS " + Tables.STUDENT);
            onCreate(db);
        }else{
            db.execSQL("DROP TABLE IF EXISTS " + Tables.EMPLOYEE);
            onCreate(db);
        }


    }


    public void insertEmployee(Employee emp){
        try(SQLiteDatabase db=getWritableDatabase()){
            ContentValues values=new ContentValues();
            values.put(EmployeeKEYS.EMPID,emp.getEmpID());
            values.put(EmployeeKEYS.USERNAME,emp.getUsername());
            values.put(EmployeeKEYS.PASSWORD,emp.getPassword());
            values.put(EmployeeKEYS.ADDRESS,emp.getAddress());
            values.put(EmployeeKEYS.EMAIL,emp.getEmail());
            int interested=(emp.isInterested())?1:0;
            values.put(EmployeeKEYS.ISINTERESTED,interested);
            values.put(EmployeeKEYS.NAME,emp.getName());
            values.put(EmployeeKEYS.PHONE,emp.getPhone());

            db.insert(Tables.EMPLOYEE,null,values);
        }
    }

    public void insertStudent(Student student){
        SQLiteDatabase db=getWritableDatabase();


        ContentValues tmpStu=new ContentValues();
        tmpStu.put(StudetKEYS.ID,student.getStdID());
        tmpStu.put(StudetKEYS.FIRSTNAME,student.getFirstName());
        tmpStu.put(StudetKEYS.SURNAME,student.getSurName());
        tmpStu.put(StudetKEYS.USERNAME,student.getUsername());
        tmpStu.put(StudetKEYS.PASSWORD,student.getPassword());
        tmpStu.put(StudetKEYS.EMAIL,student.getEmail());
        int gender= (student.getGender() == Student.Gender.MALE)?1:0;
        tmpStu.put(StudetKEYS.GENDER,gender);
        tmpStu.put(StudetKEYS.PHONE,student.getPhone());
        tmpStu.put(StudetKEYS.NATIONALITY,student.getNationality());
        tmpStu.put(StudetKEYS.ACTIVATIONCODE,student.getActivationCode());
        tmpStu.put(StudetKEYS.STATUSCODE,student.isActiveStatus());

        db.insert(Tables.STUDENT,null,tmpStu);


        for(Student.Interests in:student.getInterests()){
            ContentValues tmp=new ContentValues();
            tmp.put(InterestKEYS.INTERESTID,in.getInterestID());
            tmp.put(InterestKEYS.INTERESTNAME,in.getInterestName());
            tmp.put(InterestKEYS.STUDENTFKI,student.getStdID());
            db.insert(Tables.INTEREST,null,tmp);
        }

        TimeLine t=student.getTimeLines().get(0);
        ContentValues tmpDay=new ContentValues();
        tmpDay.put(FreeDayKEYS.DAYID,1);
        tmpDay.put(FreeDayKEYS.DAY,t.getDay());
        tmpDay.put(FreeDayKEYS.MONTH,t.getMonth());
        tmpDay.put(FreeDayKEYS.WEEKDAY,t.getWeekDay());
        tmpDay.put(FreeDayKEYS.MONTHNAME,t.getMonthName());
        tmpDay.put(FreeDayKEYS.STUDENTFK,student.getStdID());
        db.insert(Tables.FREEDAY,null,tmpDay);

        for(TimeLine.Hours in:t.getHours()){
            ContentValues tmpHour=new ContentValues();
            tmpHour.put(HoursKEYS.TOHOURS,in.getToHour());
            tmpHour.put(HoursKEYS.TOMIN,in.getToMin());
            tmpHour.put(HoursKEYS.FROMHOURS,in.getFromHour());
            tmpHour.put(HoursKEYS.FROMMIN,in.getFromMin());
            tmpHour.put(HoursKEYS.DAYFK,1);
            db.insert(Tables.HOURS,null,tmpHour);
        }

        db.close();

    }

    public Employee readEmployee(){
        Employee employee=new Employee();
        try(SQLiteDatabase db=getReadableDatabase();) {
            Cursor emp_cursor = db.query(Tables.EMPLOYEE,EmployeeKEYS.getCols(), null, null,
                    null, null, null);


            if (emp_cursor.moveToFirst()) {
                do {

                    employee.setEmpID(emp_cursor.getInt(emp_cursor.getColumnIndex(EmployeeKEYS.EMPID)));
                    employee.setUsername(emp_cursor.getString(emp_cursor.getColumnIndex(EmployeeKEYS.USERNAME)));
                    employee.setPassword(emp_cursor.getString(emp_cursor.getColumnIndex(EmployeeKEYS.PASSWORD)));
                    employee.setPhone(emp_cursor.getString(emp_cursor.getColumnIndex(EmployeeKEYS.PHONE)));
                    employee.setEmail(emp_cursor.getString(emp_cursor.getColumnIndex(EmployeeKEYS.EMAIL)));

                    boolean isInt=(emp_cursor.getInt(emp_cursor.getColumnIndex(EmployeeKEYS.ISINTERESTED)) == 1)?true:false;

                    employee.setInterested(isInt);
                    employee.setAddress(emp_cursor.getString(emp_cursor.getColumnIndex(EmployeeKEYS.ADDRESS)));
                    employee.setName(emp_cursor.getString(emp_cursor.getColumnIndex(EmployeeKEYS.NAME)));
                } while (emp_cursor.moveToNext());
            }

        }


        return employee;
    }

    public Student readStudent(){
        Student student=new Student();
        try(SQLiteDatabase db=getReadableDatabase();) {


            Cursor stu_cursor = db.query(Tables.STUDENT, StudetKEYS.getColumns(), null, null,
                    null, null, null);

            if (stu_cursor.moveToFirst()) {
                do {
                    student.setStdID(stu_cursor.getInt(stu_cursor.getColumnIndex(StudetKEYS.ID)));
                    student.setFirstName(stu_cursor.getString(stu_cursor.getColumnIndex(StudetKEYS.FIRSTNAME)));
                    student.setSurName(stu_cursor.getString(stu_cursor.getColumnIndex(StudetKEYS.SURNAME)));
                    student.setUsername(stu_cursor.getString(stu_cursor.getColumnIndex(StudetKEYS.USERNAME)));
                    student.setPassword(stu_cursor.getString(stu_cursor.getColumnIndex(StudetKEYS.PASSWORD)));
                    student.setEmail(stu_cursor.getString(stu_cursor.getColumnIndex(StudetKEYS.EMAIL)));
                    Student.Gender gender = (stu_cursor.getInt(stu_cursor.getColumnIndex(StudetKEYS.GENDER)) == 1) ? Student.Gender.MALE : Student.Gender.FRMALE;
                    student.setGender(gender);
                    student.setPhone(stu_cursor.getString(stu_cursor.getColumnIndex(StudetKEYS.PHONE)));
                    student.setNationality(stu_cursor.getString(stu_cursor.getColumnIndex(StudetKEYS.NATIONALITY)));
                    student.setActivationCode(stu_cursor.getString(stu_cursor.getColumnIndex(StudetKEYS.ACTIVATIONCODE)));
                    student.setActiveStatus((stu_cursor.getInt(stu_cursor.getColumnIndex(StudetKEYS.STATUSCODE)) == 1) ? true : false);


                } while (stu_cursor.moveToNext());
            }

            List<Student.Interests> interests = new LinkedList<>();


            Cursor int_cursor = db.query(Tables.INTEREST, InterestKEYS.getCols(), null, null,
                    null, null, null);

            if (int_cursor.moveToFirst()) {
                do {
                    Student.Interests tm = new Student.Interests();
                    tm.setInterestID(int_cursor.getInt(int_cursor.getColumnIndex(InterestKEYS.INTERESTID)));
                    tm.setInterestName(int_cursor.getString(int_cursor.getColumnIndex(InterestKEYS.INTERESTNAME)));
                    interests.add(tm);
                } while (int_cursor.moveToNext());
            }

            student.setInterests(interests);


            Cursor day_cursor = db.query(Tables.FREEDAY, FreeDayKEYS.getCols(), null, null,
                    null, null, null);

            Map<Integer,TimeLine> timeLineList = new HashMap<>();

            if (day_cursor.moveToFirst()) {
                do {
                    TimeLine timeLine=new TimeLine();
                    timeLine.setDay(day_cursor.getInt(day_cursor.getColumnIndex(FreeDayKEYS.DAY)));
                    timeLine.setMonth(day_cursor.getInt(day_cursor.getColumnIndex(FreeDayKEYS.MONTH)));
                    timeLine.setWeekDay(day_cursor.getString(day_cursor.getColumnIndex(FreeDayKEYS.WEEKDAY)));
                    timeLine.setMonthName(day_cursor.getString(day_cursor.getColumnIndex(FreeDayKEYS.MONTHNAME)));
                    int id=day_cursor.getInt(day_cursor.getColumnIndex(FreeDayKEYS.DAYID));

                    timeLineList.put(id,timeLine);
                } while (day_cursor.moveToNext());
            }


            for(Integer key:timeLineList.keySet()) {
                Cursor hour_cursor = db.query(Tables.HOURS, HoursKEYS.getCols(),
                        HoursKEYS.DAYFK +" = "+ key,
                        null,null, null, null);

                if (hour_cursor.moveToFirst()) {
                    do {
                        TimeLine.Hours tmp = new TimeLine.Hours();
                        tmp.setToHour(hour_cursor.getInt(hour_cursor.getColumnIndex(HoursKEYS.TOHOURS)));
                        tmp.setToMin(hour_cursor.getInt(hour_cursor.getColumnIndex(HoursKEYS.TOMIN)));
                        tmp.setFromHour(hour_cursor.getInt(hour_cursor.getColumnIndex(HoursKEYS.FROMHOURS)));
                        tmp.setFromMin(hour_cursor.getInt(hour_cursor.getColumnIndex(HoursKEYS.FROMMIN)));

                        timeLineList.get(key).addHour(tmp);
                    } while (hour_cursor.moveToNext());
                }
            }


            for(TimeLine item:timeLineList.values()){
                student.addTimeLine(item);
            }

        }
        return student;
    }

    public void updateStudent(ContentValues values,int stdID){
        try(SQLiteDatabase db=getWritableDatabase()) {
            db.update(Tables.STUDENT,values," stdID = "+stdID,null);
        }
    }

    public void updateEmployee(ContentValues values,int empID){
        try(SQLiteDatabase db=getWritableDatabase()) {
            db.update(Tables.EMPLOYEE,values," empID = "+ empID,null);
        }
    }

    public void insetTimeline(TimeLine timeLine,int stdID){
        deleteTimeLine(getTimeLineID(timeLine));
        try (SQLiteDatabase db=getWritableDatabase()){
            ContentValues freeDayValue=new ContentValues();
            freeDayValue.put(FreeDayKEYS.STUDENTFK,stdID);
            freeDayValue.put(FreeDayKEYS.MONTHNAME,timeLine.getMonthName());
            freeDayValue.put(FreeDayKEYS.MONTH,timeLine.getMonth());
            freeDayValue.put(FreeDayKEYS.WEEKDAY,timeLine.getWeekDay());
            freeDayValue.put(FreeDayKEYS.DAY,timeLine.getDay());

            int id= (int) db.insert(Tables.FREEDAY,null,freeDayValue);

            for(TimeLine.Hours item:timeLine.getHours()){
                ContentValues hValues=new ContentValues();
                hValues.put(HoursKEYS.DAYFK,id);
                hValues.put(HoursKEYS.TOHOURS,item.getToHour());
                hValues.put(HoursKEYS.TOMIN,item.getToMin());
                hValues.put(HoursKEYS.FROMHOURS,item.getFromHour());
                hValues.put(HoursKEYS.FROMMIN,item.getFromMin());

                db.insert(Tables.HOURS,null,hValues);
            }
        }
    }

    public int getTimeLineID(TimeLine timeLine){
        try(SQLiteDatabase db=getReadableDatabase()){
            String query=FreeDayKEYS.DAY +" =? and " + FreeDayKEYS.MONTH +" =? and " + FreeDayKEYS.WEEKDAY +  " =? and "
                    +FreeDayKEYS.MONTHNAME + " =? ";

            String[] args={String.valueOf(timeLine.getDay()),String.valueOf(timeLine.getMonth()),
                    timeLine.getWeekDay(),timeLine.getMonthName()};

            Cursor cursor=db.query(Tables.FREEDAY,FreeDayKEYS.getCols(),query,args,null,null,null);

            int resultID=0;
            if(cursor.moveToNext()){
                do{
                    resultID=cursor.getInt(cursor.getColumnIndex(FreeDayKEYS.DAYID));
                }while (cursor.moveToNext());
            }

            return resultID;

        }
    }

    public void deleteTimeLine(int timeLineID){
        String deleteFromHours="DELETE FROM "+Tables.HOURS +" WHERE " + HoursKEYS.DAYFK +" = " + timeLineID;
        try (SQLiteDatabase db=getWritableDatabase()){
            db.execSQL(deleteFromHours);

            String deleteFromDays="DELETE FROM "+Tables.FREEDAY +" WHERE " + FreeDayKEYS.DAYID +" = " + timeLineID;
            db.execSQL(deleteFromDays);
        }

    }


    public void refineInterests(List<Student.Interests> interests,int stdID){
        String delete_int="DELETE FROM "+Tables.INTEREST + ";";
        SQLiteDatabase db=getWritableDatabase();
        db.execSQL(delete_int);
        String query="INSERT INTO "+Tables.INTEREST +"("+InterestKEYS.INTERESTID+","+
                InterestKEYS.INTERESTNAME + ","+InterestKEYS.STUDENTFKI +")VALUES";
        for(Student.Interests item:interests){
            query+= ("("+item.getInterestID() + ",'"+ item.getInterestName() +"',"+stdID +"),");
        }

        query=query.substring(0,query.length()-1);
        query+=";";

        db.execSQL(query);

    }




    public static class Tables{
        public static final String STUDENT="student";
        public static final String INTEREST="interest";
        public static final String HOURS="studentHours";
        public static final String FREEDAY="studentFreeDay";
        public static final String EMPLOYEE="employee";

    }

    public static class StudetKEYS{
        public static final String ID="stdID";
        public static final String FIRSTNAME="firstName";
        public static final String SURNAME="surName";
        public static final String USERNAME="username";
        public static final String PASSWORD="password";
        public static final String EMAIL="email";
        public static final String PHONE="phone";
        public static final String NATIONALITY="nationality";
        public static final String GENDER="gender";
        public static final String ACTIVATIONCODE="activationCode";
        public static final String STATUSCODE="activeStatus";

        public static String[] getColumns(){
            String[] cols={ID,FIRSTNAME,SURNAME,USERNAME,PASSWORD,EMAIL,PHONE,NATIONALITY,GENDER,ACTIVATIONCODE,
                    STATUSCODE};
            return cols;
        }

    }
    public static class InterestKEYS{
        public static final String INTERESTID="interestID";
        public static final String INTERESTNAME="interestName";
        public static final String STUDENTFKI="stdFKI";

        public static String[] getCols(){
            return new String[]{INTERESTID,INTERESTNAME,STUDENTFKI};
        }
    }
    public static class HoursKEYS{
        public static final String HOURSID="hoursID";
        public static final String FROMHOURS="fromHour";
        public static final String FROMMIN="fromMin";
        public static final String TOHOURS="toHour";
        public static final String TOMIN="toMin";
        public static final String DAYFK="dayFK";


        public static String[] getCols(){
            return new String[]{HOURSID,FROMHOURS,FROMMIN,TOHOURS,TOMIN,DAYFK};
        }
    }

    public static class FreeDayKEYS{
        public static final String DAYID="dayID";
        public static final String DAY="day";
        public static final String MONTH="month";
        public static final String WEEKDAY="weekDay";
        public static final String MONTHNAME="monthName";
        public static final String STUDENTFK="stdFK";

        public static String[] getCols(){
            return new String[]{DAYID,DAY,MONTH,WEEKDAY,MONTHNAME,STUDENTFK};
        }
    }

    public static class EmployeeKEYS{
        public static final String EMPID="empID";
        public static final String NAME="name";
        public static final String USERNAME="username";
        public static final String PASSWORD="password";
        public static final String EMAIL="email";
        public static final String PHONE="phone";
        public static final String ADDRESS="address";
        public static final String ISINTERESTED="isInterested";

        public static String[] getCols(){
            return new String[]{EMPID,NAME,USERNAME,PASSWORD,EMAIL,PHONE,ADDRESS,ISINTERESTED};
        }
    }

}
