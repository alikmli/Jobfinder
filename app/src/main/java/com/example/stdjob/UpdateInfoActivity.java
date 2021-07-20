package com.example.stdjob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputEditText;

public class UpdateInfoActivity extends PersianActivity {

    Student user;
    TextInputEditText name,surname,email,phone,username,passwd;
    RadioButton r1,r2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_info);


        name=findViewById(R.id.update_name);
        surname=findViewById(R.id.update_surname);
        email=findViewById(R.id.update_email);
        phone=findViewById(R.id.update_phone);
        username=findViewById(R.id.update_username);
        passwd=findViewById(R.id.update_password);
        r2=findViewById(R.id.radioButton);
        r1=findViewById(R.id.radioButton2);






            DBHealperForStudentUser healper=new DBHealperForStudentUser(this);
            user = healper.readStudent();
            Log.i("DDD",user.toString());
            if(user != null) {
                name.setText(user.getFirstName());
                surname.setText(user.getSurName());
                email.setText(user.getEmail());
                phone.setText(user.getPhone());
                username.setText(user.getUsername());
                passwd.setText(user.getPassword());

                if (user.getGender() == Student.Gender.MALE) {
                    r1.setChecked(true);
                } else {
                    r2.setChecked(true);
                }


        }
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem item=menu.add("ذخیره");
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                DBHealperForStudentUser healper=new DBHealperForStudentUser(UpdateInfoActivity.this);
                ContentValues values=new ContentValues();
                values.put(DBHealperForStudentUser.StudetKEYS.FIRSTNAME,name.getText().toString().trim());
                values.put(DBHealperForStudentUser.StudetKEYS.SURNAME,surname.getText().toString().trim());
                values.put(DBHealperForStudentUser.StudetKEYS.EMAIL,email.getText().toString().trim());
                values.put(DBHealperForStudentUser.StudetKEYS.PHONE,phone.getText().toString().trim());
                values.put(DBHealperForStudentUser.StudetKEYS.USERNAME,username.getText().toString().trim());
                values.put(DBHealperForStudentUser.StudetKEYS.PASSWORD,passwd.getText().toString().trim());
                int gender=0;
                if(r1.isChecked()){
                    gender=1;
                }
                values.put(DBHealperForStudentUser.StudetKEYS.GENDER,gender);
                healper.updateStudent(values,user.getStdID());
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


}
