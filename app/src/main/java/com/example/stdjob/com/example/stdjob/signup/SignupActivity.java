package com.example.stdjob.com.example.stdjob.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.stdjob.R;
import com.example.stdjob.StartActivity;

public class SignupActivity extends AppCompatActivity {


    Button btn3;
    RadioButton rdb1,rdb2;
    RadioGroup rg1;
    Boolean flag=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_signup );



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn3=(Button)findViewById( R.id.btn3 );
        rg1=(RadioGroup)findViewById( R.id.rg1 );
        rg1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId){
                switch (checkedId) {
                    case R.id.rdb1:

                        btn3.setVisibility(View.VISIBLE);
                        flag=false;
                        break;
                    case R.id.rdb2:

                        btn3.setVisibility(View.VISIBLE);
                        flag=true;
                        break;


                }
            }
        } );




    }

    @Override
    public boolean onSupportNavigateUp(){

        onBackPressed();

        super.onBackPressed();
        startActivity(new Intent(this, StartActivity.class));
        finish();

        return true;

    }
    public void nextactivity2(View view) {
        if (flag){
            final Intent intent1 = new Intent(this, SignupPage2Activity.class);
            startActivity( intent1 );
            overridePendingTransition(R.anim.from_in_right,R.anim.from_out_left);
        }else {
            final Intent intent1 = new Intent(this, SignupPage3Activity.class);
            startActivity( intent1 );
            overridePendingTransition(R.anim.from_in_right,R.anim.from_out_left);
        }

    }


}
