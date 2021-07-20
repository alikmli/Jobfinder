package com.example.stdjob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.content.ContentValues;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.textfield.TextInputEditText;

public class UpdateComapnyInfo extends PersianActivity {
    TextInputEditText name,address,email,phone,username,passwrod;
    private DBHealperForStudentUser healper;
    Employee employee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_comapny_info);

        healper=new DBHealperForStudentUser(this);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        name=findViewById(R.id.company_update_name);
        address=findViewById(R.id.company_address);
        email=findViewById(R.id.company_update_email);
        phone=findViewById(R.id.company_update_phone);
        username=findViewById(R.id.company_update_username);
        passwrod=findViewById(R.id.company_update_password);

        employee=healper.readEmployee();

        name.setText(employee.getName());
        address.setText(employee.getAddress());
        email.setText(employee.getEmail());
        phone.setText(employee.getPhone());
        username.setText(employee.getUsername());
        passwrod.setText(employee.getPassword());




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
                ContentValues values=new ContentValues();
                values.put(DBHealperForStudentUser.EmployeeKEYS.NAME,name.getText().toString().trim());
                values.put(DBHealperForStudentUser.EmployeeKEYS.ADDRESS,address.getText().toString().trim());
                values.put(DBHealperForStudentUser.EmployeeKEYS.EMAIL,email.getText().toString().trim());
                values.put(DBHealperForStudentUser.EmployeeKEYS.PHONE,phone.getText().toString().trim());
                values.put(DBHealperForStudentUser.EmployeeKEYS.USERNAME,username.getText().toString().trim());
                values.put(DBHealperForStudentUser.EmployeeKEYS.PASSWORD,passwrod.getText().toString().trim());
                healper.updateEmployee(values,employee.getEmpID());
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}
