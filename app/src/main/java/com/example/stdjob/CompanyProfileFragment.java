package com.example.stdjob;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

public class CompanyProfileFragment extends Fragment implements View.OnClickListener {
    private DBHealperForStudentUser dbHealper;
    private  Employee employee;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHealper=new DBHealperForStudentUser(getContext());
        employee=dbHealper.readEmployee();

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_company_profile,container,false);

        AppCompatTextView profile=view.findViewById(R.id.company_info_edit);
        AppCompatImageView profile_img=view.findViewById(R.id.company_profile_edit);

        profile.setOnClickListener(this);
        profile_img.setOnClickListener(this);

        AppCompatTextView message=view.findViewById(R.id.company_info_message);
        AppCompatImageView message_img=view.findViewById(R.id.company_info_message_img);

        message.setOnClickListener(this);
        message_img.setOnClickListener(this);

        AppCompatTextView suppert=view.findViewById(R.id.company_info_setting);
        AppCompatImageView suppert_img=view.findViewById(R.id.company_info_setting_img);

        suppert.setOnClickListener(this);
        suppert_img.setOnClickListener(this);

        AppCompatTextView exit=view.findViewById(R.id.company_info_exit);
        AppCompatImageView exit_img=view.findViewById(R.id.company_info_exit_img);

        exit.setOnClickListener(this);
        exit_img.setOnClickListener(this);


        AppCompatTextView email=view.findViewById(R.id.company_email);
        email.setText(employee.getEmail());

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.company_info_edit || v.getId() == R.id.company_profile_edit){
            getActivity().startActivity(new Intent(getContext(),UpdateComapnyInfo.class));
            getActivity().overridePendingTransition(R.anim.from_in_right,R.anim.from_out_left);
        }else  if(v.getId() == R.id.company_info_message || v.getId() == R.id.company_info_message_img){
            getActivity().startActivity(new Intent(getContext(),SupportActivity.class));
            getActivity().overridePendingTransition(R.anim.from_in_right,R.anim.from_out_left);
        }else  if(v.getId() == R.id.company_info_setting || v.getId() == R.id.company_info_setting_img){
            getActivity().startActivity(new Intent(getContext(),SettingsActivity.class));
            getActivity().overridePendingTransition(R.anim.from_in_right,R.anim.from_out_left);
        }else  if(v.getId() == R.id.company_info_exit || v.getId() == R.id.company_info_exit_img){
            getActivity().finish();
        }
    }
}
