package com.example.stdjob;

import android.app.Application;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;


public class App extends Application {

    public final static String PrefsName="stdjob_pref";
    public final static int STUDENT_TYPE_CODE=1;
    public final static int COMPANY_TYPE_CODE=2;



    public static AppPreferences prefs;


    @Override
    public void onCreate() {
        super.onCreate();


        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/Yekan.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

        prefs=new AppPreferences(getApplicationContext());
    }
}



