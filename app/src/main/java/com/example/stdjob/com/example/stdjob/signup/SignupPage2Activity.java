package com.example.stdjob.com.example.stdjob.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;

import com.example.stdjob.R;

public class SignupPage2Activity extends AppCompatActivity {
    Button btn4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page2);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        RadioGroup rg2 = (RadioGroup) findViewById( R.id.rg2 );
        btn4=(Button)findViewById( R.id.btn4 );
        rg2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId){
                switch (checkedId) {
                    case R.id.rdb3:

                        btn4.setVisibility( View.VISIBLE);
                        break;
                    case R.id.rdb4:

                        btn4.setVisibility(View.VISIBLE);
                        break;


                }
            }
        } );
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
        startActivity(new Intent(this, SignupActivity.class));
        finish();

        return true;

    }

    public void nextactivity3(View view) {

        startActivity( new Intent(this, SignupPage4Activity.class) );
        overridePendingTransition(R.anim.from_in_right,R.anim.from_out_left);

    }
}
