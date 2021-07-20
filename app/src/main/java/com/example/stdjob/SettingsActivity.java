package com.example.stdjob;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.annotation.NonNull;


public class SettingsActivity extends PersianActivity implements CompoundButton.OnCheckedChangeListener {

    private Switch aSwitch;
    int userType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        aSwitch=findViewById(R.id.switchWidget);

        userType=App.prefs.getIntValue(AppPreferences.PreferencesKeys.USERTYPE);
        String text="";
        boolean state=false;
        if(userType == App.STUDENT_TYPE_CODE){
            text=getResources().getString(R.string.ask_for_plan);

           state=App.prefs.isTrueBooleanValue(AppPreferences.PreferencesKeys.SETTINGPLANFORSTUDENTS);

        }else if(userType == App.COMPANY_TYPE_CODE){
            text=getResources().getString(R.string.ask_for_plan_company);
            state=App.prefs.isTrueBooleanValue(AppPreferences.PreferencesKeys.SETTINGPLANFORCOMAPNIES);
        }

        aSwitch.setText(text);
        aSwitch.setOnCheckedChangeListener(this);

        aSwitch.setChecked(state);
    }



    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(userType == App.STUDENT_TYPE_CODE){
            App.prefs.setKeyBoolean(AppPreferences.PreferencesKeys.SETTINGPLANFORSTUDENTS,isChecked);
        }else if(userType == App.COMPANY_TYPE_CODE){
            App.prefs.setKeyBoolean(AppPreferences.PreferencesKeys.SETTINGPLANFORCOMAPNIES,isChecked);
        }

        DBHealperForStudentUser healper=new DBHealperForStudentUser(this);
        Employee employee=healper.readEmployee();
        ContentValues values=new ContentValues();
        int isInt=(isChecked)?1:0;
        values.put(DBHealperForStudentUser.EmployeeKEYS.ISINTERESTED,isInt);
        healper.updateEmployee(values,employee.getEmpID());



    }





}
