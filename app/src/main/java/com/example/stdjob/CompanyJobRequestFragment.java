package com.example.stdjob;

import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import java.sql.Time;
import java.util.LinkedList;
import java.util.List;

import ir.hamsaa.persiandatepicker.Listener;
import ir.hamsaa.persiandatepicker.PersianDatePickerDialog;
import ir.hamsaa.persiandatepicker.util.PersianCalendar;


public class CompanyJobRequestFragment extends Fragment implements View.OnClickListener {
    AppCompatTextView job_date,job_time;
    PersianDatePickerDialog picker;
    TextInputEditText job_title,job_price,job_essen,job_desc;
    AppCompatImageView see_all;
    AppCompatButton btn_send,btn_reset;

    Job job=new Job();
    TimeLine timeLine=new TimeLine();
    private Dialog fromDialog,toDialog;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_company_job_request_fragment,container,false);


        fromDialog=new Dialog(getContext());
        toDialog=new Dialog(getContext());


        fromDialog.setContentView(R.layout.dialog_time_picker_layout);
        toDialog.setContentView(R.layout.dialog_time_picker_layout);

        job_date=view.findViewById(R.id.job_req_date);
        job_time=view.findViewById(R.id.job_req_time);
        job_title=view.findViewById(R.id.job_req_title);
        job_price=view.findViewById(R.id.job_req_price);
        see_all=view.findViewById(R.id.see_all_times);
        job_essen=view.findViewById(R.id.job_req_essen);
        job_desc=view.findViewById(R.id.job_req_desc);
        btn_reset=view.findViewById(R.id.job_req_reset);
        btn_send=view.findViewById(R.id.job_req_submit);


        job_date.setOnClickListener(this);
        job_time.setOnClickListener(this);

        btn_send.setOnClickListener(this);
        btn_reset.setOnClickListener(this);

        see_all.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.see_all_times) {
            StringBuilder builder=new StringBuilder();
            String item="";
            for(TimeLine.Hours in:timeLine.getHours()) {
                item= String.format("%02d:%02d %s %02d:%02d\r%n", in.getToHour(),in.getToMin(),"تا",
                        in.getFromHour(),in.getFromMin());

                builder.append(item);
            }

            String message="";
            if(builder.length() > 0){
                message=builder.toString();
            }
            else{
                message=getActivity().getResources().getString(R.string.job_empty_hour);
            }
            AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
            alert.setTitle(getActivity().getResources().getString(R.string.job_req_time))
                    .setMessage(message)
                    .setPositiveButton(getActivity().getResources().getString(R.string.ok),null)
                    .show();


        }else if(v.getId() == R.id.job_req_date) {
            picker = new PersianDatePickerDialog(getContext())
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
                            timeLine.setDate(persianCalendar.getPersianMonth(),persianCalendar.getPersianDay(),
                                    persianCalendar.getPersianWeekDayName(),persianCalendar.getPersianMonthName());

                            String timeDate= String.format("%s %d %s", timeLine.getWeekDay(),timeLine.getDay(),timeLine.getMonthName());

                            job_date.setText(timeDate);
                        }

                        @Override
                        public void onDismissed() {

                        }
                    });

            picker.show();
        }else if(v.getId() == R.id.job_req_time) {
            fromDialog.show();
            AppCompatButton save_btn1=fromDialog.findViewById(R.id.dialog_time_btn);
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

                            if(timeLine.getHours().size() == 1){
                                String item= String.format("%02d:%02d %s %02d:%02d", tmph.getFromHour(),tmph.getFromMin(),"تا",
                                        tmph.getToHour(),tmph.getToMin());
                                job_time.setText(item);
                            }

                        }
                    });
                }
            });
        }else if(v.getId() == R.id.job_req_submit){
            String title=job_title.getText().toString().trim();
            if(title.isEmpty()){
                job_title.setError(getActivity().getResources().getString(R.string.title_error_request));
                return;
            }

            String price=job_price.getText().toString().trim();
            if(price.isEmpty()){
                job_price.setError(getActivity().getResources().getString(R.string.price_error_request));
                return;
            }


            if(timeLine.getMonth() == 0){
                AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
                alert.setTitle(getActivity().getResources().getString(R.string.job_req_time))
                        .setMessage(getActivity().getResources().getString(R.string.time_error_request))
                        .setPositiveButton(getActivity().getResources().getString(R.string.ok),null)
                        .show();
            }


            if(timeLine.getHours().size() == 0){
                AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
                alert.setTitle(getActivity().getResources().getString(R.string.job_req_date))
                        .setMessage(getActivity().getResources().getString(R.string.date_error_request))
                        .setPositiveButton(getActivity().getResources().getString(R.string.ok),null)
                        .show();
            }

            String essentials=job_essen.getText().toString().trim();

            String description=job_desc.getText().toString().trim();


            job.setTitle(title);
            job.setPrice(Double.parseDouble(price));
            job.setEssentials(essentials);
            job.setDescription(description);
            List<TimeLine> list=new LinkedList<>();
            list.add(timeLine);
            job.setTimeLines(list);
            job.setStatus(Job.Status.NEW);
            job.setPayStatus(0);
            DBHealperForStudentUser healper=new DBHealperForStudentUser(getContext());

            Employee employee=healper.readEmployee();
            job.setEmpAddress(employee.getAddress());
            job.setEmpName(employee.getName());
            job.setEmpID(employee.getEmpID());
            job.setRate(-1);
            job.setStdID(-1);
        }else if(v.getId() == R.id.job_req_reset){

            job_title.setText("");
            job_price.setText("");
            job_essen.setText("");
            job_desc.setText("");
            job_date.setText("");
            job_time.setText("");


        }

    }
}
