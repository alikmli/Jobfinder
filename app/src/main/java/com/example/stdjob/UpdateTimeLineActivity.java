package com.example.stdjob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.TimePicker;

import java.util.LinkedList;
import java.util.List;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;


public class UpdateTimeLineActivity extends PersianActivity implements DeleteTimeList {


    PersianDatePickerDialog picker;
    Toolbar mTopToolbar;

    private ListView timeListView;
    private TimePickerDialog pickerDialog;
    private TimeItemAdapter adapter;
    private Dialog fromDialog,toDialog;
    private List<TimeLine> tm;
    DBHealperForStudentUser dbHealper;
    Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_time_line);


        mTopToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(mTopToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mTopToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }
        });

        dbHealper=new DBHealperForStudentUser(this);
        student=dbHealper.readStudent();
        tm=new LinkedList<>();
        for(TimeLine time:student.getTimeLines()){
            tm.add(time);
        }

        timeListView=findViewById(R.id.time_listview);
        adapter=new TimeItemAdapter(this,tm,this,dbHealper);

        timeListView.setAdapter(adapter);

        fromDialog=new Dialog(this);
        toDialog=new Dialog(this);


        fromDialog.setContentView(R.layout.dialog_time_picker_layout);
        toDialog.setContentView(R.layout.dialog_time_picker_layout);




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem add=menu.add(R.string.add);
        add.setIcon(R.drawable.addtime);
        add.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        add.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                picker = new PersianDatePickerDialog(UpdateTimeLineActivity.this)
                        .setPositiveButtonString("باشه")
                        .setNegativeButton("بیخیال")
                        .setTodayButton("امروز")
                        .setTodayButtonVisible(true)
                        .setMinYear(1300)
                        .setMaxYear(PersianDatePickerDialog.THIS_YEAR)
                        .setActionTextColor(Color.GRAY)
                        .setTitleType(PersianDatePickerDialog.WEEKDAY_DAY_MONTH_YEAR)
                        .setShowInBottomSheet(true)
                        .setListener(new Listener() {
                            @Override
                            public void onDateSelected(PersianCalendar persianCalendar) {
                                int tmpday=persianCalendar.getPersianDay();
                                int tmpmonth=persianCalendar.getPersianMonth();
                                String monthName=persianCalendar.getPersianMonthName();
                                String weekDay=persianCalendar.getPersianWeekDayName();
                                TimeLine tmp=null;
                                for(int i=0;i<tm.size();i++){
                                    if(tm.get(i).getDay() == tmpday && tm.get(i).getMonth() == tmpmonth){
                                        tmp=tm.get(i);
                                        tm.remove(i);
                                        break;
                                    }
                                }

                                if(tmp == null){
                                    tmp=new TimeLine();
                                    tmp.setDate(tmpmonth,tmpday,weekDay,monthName);
                                }

                                final  TimeLine timeLine=tmp;
                                fromDialog.show();
                                final AppCompatButton save_btn1=fromDialog.findViewById(R.id.dialog_time_btn);
                                ((AppCompatTextView)fromDialog.findViewById(R.id.time_dialog_text)).setText(R.string.fromHour);
                                final TimePicker picker1=fromDialog.findViewById(R.id.datePicker1);
                                final TimeLine.Hours tmph=new TimeLine.Hours();
                                save_btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        tmph.setFromHour(picker1.getHour());
                                        tmph.setFromMin(picker1.getMinute());
                                        fromDialog.dismiss();
                                        toDialog.show();
                                        AppCompatButton save_btn2=toDialog.findViewById(R.id.dialog_time_btn);
                                        ((AppCompatTextView)toDialog.findViewById(R.id.time_dialog_text)).setText(R.string.toHour);
                                        final TimePicker picker2=toDialog.findViewById(R.id.datePicker1);

                                        save_btn2.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                tmph.setToHour(picker2.getHour());
                                                tmph.setToMin(picker2.getMinute());
                                                toDialog.dismiss();

                                                timeLine.addHour(tmph);
                                                tm.add(timeLine);
                                                dbHealper.insetTimeline(timeLine,student.getStdID());

                                                adapter=new TimeItemAdapter(UpdateTimeLineActivity.this,tm,UpdateTimeLineActivity.this,dbHealper);
                                                timeListView.setAdapter(adapter);


                                            }
                                        });
                                    }
                                });




                            }

                            @Override
                            public void onDismissed() {

                            }
                        });

                picker.show();


                return false;
            }
        });


        MenuItem save=menu.add(R.string.save);
        save.setIcon(R.drawable.save);
        save.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        save.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                //sync with server
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void deleteAt(int position) {
        List<TimeLine> tmp=new LinkedList<TimeLine>();
        for(int i=0;i<tm.size();i++){
            if(i != position)
                tmp.add(tm.get(i));
        }


        tm.clear();
        tm.addAll(tmp);

        adapter=new TimeItemAdapter(UpdateTimeLineActivity.this,tm,this,dbHealper);
        timeListView.setAdapter(adapter);


    }
}
