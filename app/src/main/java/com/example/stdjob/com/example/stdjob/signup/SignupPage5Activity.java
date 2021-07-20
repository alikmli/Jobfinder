package com.example.stdjob.com.example.stdjob.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.stdjob.App;
import com.example.stdjob.R;

public class SignupPage5Activity extends AppCompatActivity {
    TextView alarm4;
    EditText edtxt3,edtxt4;
    String emailPattern = "\\w{1,}(\\d*\\w*)*@\\w{3,}\\.\\w{2,}";
    private CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page5);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        alarm4 = (TextView) findViewById( R.id.alam4 );
        edtxt3 = (EditText) findViewById( R.id.edtxt3 );
        edtxt4 = (EditText) findViewById( R.id.edtxt4 );

        checkBox=findViewById(R.id.check_plan);

        Bundle t=getIntent().getExtras();
        int type= App.STUDENT_TYPE_CODE;
        if(t !=null){
            type=t.getInt("type");
            if(type == App.STUDENT_TYPE_CODE){
                checkBox.setText(getResources().getString(R.string.student_sigup_message));
            }else{
                checkBox.setText(getResources().getString(R.string.company_signup_message));

            }
        }
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_out_right,R.anim.from_in_left);
    }
    @Override
    public boolean onSupportNavigateUp(){

        onBackPressed();

        super.onBackPressed();
        startActivity(new Intent(this, SignupPage3Activity.class));
        finish();

        return true;

    }
    public void nextactivity5(View view) {
        if (edtxt3.getText().toString().isEmpty()&& edtxt4.getText().toString().isEmpty()){
            alarm4.setText( "جای خالی را پر کنید" );
        }else if (!edtxt3.getText().toString().trim().matches( emailPattern )){
            alarm4.setText( "ایمیل را درست وارد کنید" );

        }
        else {
            final Intent intent1 = new Intent(this, SignupPage6Activity.class);
            startActivity( intent1 );
            overridePendingTransition(R.anim.from_in_right,R.anim.from_out_left);
        }
    }
}
