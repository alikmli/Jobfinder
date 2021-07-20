package com.example.stdjob;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private Student student=new Student();
    private DBHealperForStudentUser dbHealper;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHealper=new DBHealperForStudentUser(getContext());
        student= dbHealper.readStudent();
    }

    public static ProfileFragment getInstance(){
        ProfileFragment profileFragment=new ProfileFragment();

        return profileFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_profile,container,false);

        AppCompatImageView edit_img=view.findViewById(R.id.profile_edit);
        AppCompatTextView edit_tv=view.findViewById(R.id.user_info_edit);

        edit_img.setOnClickListener(this);
        edit_tv.setOnClickListener(this);






        AppCompatTextView email_pro=view.findViewById(R.id.user_email);
        email_pro.setText(student.getEmail());

        AppCompatImageView interest_img=view.findViewById(R.id.user_info_interest_img);
        AppCompatTextView interest=view.findViewById(R.id.user_info_interest);
        interest.setOnClickListener(this);
        interest_img.setOnClickListener(this);



        AppCompatImageView timeLine_img=view.findViewById(R.id.user_info_timeline_img);
        AppCompatTextView timeLine=view.findViewById(R.id.user_info_timeline);
        timeLine_img.setOnClickListener(this);
        timeLine.setOnClickListener(this);


        AppCompatImageView plan_t_img=view.findViewById(R.id.user_info_plan_t_img);
        AppCompatTextView plan_t=view.findViewById(R.id.user_info_plan_t);
        plan_t_img.setOnClickListener(this);
        plan_t.setOnClickListener(this);

        AppCompatImageView support=view.findViewById(R.id.user_info_message_img);
        AppCompatTextView support_img=view.findViewById(R.id.user_info_message);
        support.setOnClickListener(this);
        support_img.setOnClickListener(this);

        AppCompatImageView exit=view.findViewById(R.id.user_info_exit_img);
        AppCompatTextView exit_img=view.findViewById(R.id.user_info_exit);
        exit.setOnClickListener(this);
        exit_img.setOnClickListener(this);

        AppCompatImageView settings=view.findViewById(R.id.user_info_setting_img);
        AppCompatTextView settings_img=view.findViewById(R.id.user_info_setting);
        settings.setOnClickListener(this);
        settings_img.setOnClickListener(this);
        return view;
    }



    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.profile_edit || v.getId()==R.id.user_info_edit) {
            Intent intent=new Intent(getContext(),UpdateInfoActivity.class);
            getActivity().startActivity(intent);
            getActivity().overridePendingTransition(R.anim.from_in_right,R.anim.from_out_left);
        }else if(v.getId() == R.id.user_info_interest || v.getId() == R.id.user_info_interest_img){
            final String[] interest=getActivity().getResources().getStringArray(R.array.interest_list);

            final boolean[] selects=new boolean[interest.length];
            final List<Student.Interests> ins=student.getInterests();
            for(int i=0;i<interest.length;i++){
                boolean falg=true;
                for(Student.Interests item:ins){
                    if(item.getInterestName().equalsIgnoreCase(interest[i].toLowerCase())){
                        selects[i]=true;
                        falg=false;
                        break;
                    }
                }
                if(false){
                    selects[i]=false;
                }

            }

            final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.interest)
                    .setMultiChoiceItems(interest, selects, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            if(isChecked){
                                selects[which]=true;
                            }
                        }
                    }).setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ins.clear();
                    for(int i=0;i<selects.length;i++){
                        if(selects[i]){
                            Student.Interests tmp=new Student.Interests();
                            tmp.setInterestID(i);
                            tmp.setInterestName(interest[i]);
                            ins.add(tmp);
                        }
                    }

                    dbHealper.refineInterests(ins,student.getStdID());
                }
            }).show();


        }else if(v.getId() == R.id.user_info_timeline || v.getId() == R.id.user_info_timeline_img){
            Intent intent=new Intent(getContext(),UpdateTimeLineActivity.class);
            startActivity(intent);
            getActivity().overridePendingTransition(R.anim.from_in_right,R.anim.from_out_left);

        } else if(v.getId() == R.id.user_info_plan_t || v.getId() == R.id.user_info_plan_t_img){
            getActivity().startActivity(new Intent(getContext(),ToturialPlansActivity.class));
            getActivity().overridePendingTransition(R.anim.from_in_right,R.anim.from_out_left);

        }else if(v.getId() == R.id.user_info_message || v.getId() == R.id.user_info_message_img){
            getActivity().startActivity(new Intent(getContext(),SupportActivity.class));
            getActivity().overridePendingTransition(R.anim.from_in_right,R.anim.from_out_left);
        }else if(v.getId() == R.id.user_info_setting || v.getId() == R.id.user_info_setting_img){
            getActivity().startActivity(new Intent(getContext(),SettingsActivity.class));
            getActivity().overridePendingTransition(R.anim.from_in_right,R.anim.from_out_left);
        }else  if(v.getId() == R.id.user_info_exit || v.getId() == R.id.user_info_exit_img){
            getActivity().finish();
        }


    }
}
