package com.example.stdjob;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class CompanyItemTimes extends LinearLayout {
    private View root;
    private AppCompatTextView title,time;
    public CompanyItemTimes(Context context) {
        super(context);
        init(context);
    }

    public CompanyItemTimes(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }


    public void init(Context context){
        root= LayoutInflater.from(context).inflate(R.layout.layout_company_items_times,this);


        title=root.findViewById(R.id.time_job_title);
        time=root.findViewById(R.id.date_job_time);

    }


    public void setDate(String _date){
        title.setText(_date);
    }

    public void setTime(String _time){
        time.setText(_time);
    }
}
