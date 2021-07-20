package com.example.stdjob;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class HoursItesm extends LinearLayout {

    private AppCompatTextView date,time;
    private View root;

    public HoursItesm(Context context) {
        super(context);
        init(context);
    }

    public HoursItesm(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        root= LayoutInflater.from(context).inflate(R.layout.hours_items,this);


        date=root.findViewById(R.id.date_job_intro);
        time=root.findViewById(R.id.time_job_id);

    }


    public void setDate(String _date){
        date.setText(_date);
    }

    public void setTime(String _time){
        time.setText(_time);
    }
}
