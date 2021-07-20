package com.example.stdjob;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {

    private SharedPreferences prefs;

    public static class PreferencesKeys{
        public static final String SETTINGPLANFORSTUDENTS="student_plan";
        public static final String SETTINGPLANFORCOMAPNIES="company_plan";
        public static final String USERTYPE="user_type";
    }


    public AppPreferences(Context context){
        prefs=context.getSharedPreferences(App.PrefsName,Context.MODE_PRIVATE);

    }

    public boolean containKey(String key){
        return prefs.contains(key);
    }


    public int getIntValue(String key){
        return prefs.getInt(key,App.STUDENT_TYPE_CODE);
    }

    public void setIntValue(String key,int value){
        prefs.edit().putInt(key,value).commit();
    }

    public boolean isTrueBooleanValue(String key){
        return prefs.getBoolean(key,false);
    }



    public void setKeyBoolean(String key,boolean value){
        prefs.edit().putBoolean(key,value).commit();
    }
}
