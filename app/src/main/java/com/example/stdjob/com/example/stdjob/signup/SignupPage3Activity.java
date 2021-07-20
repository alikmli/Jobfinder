package com.example.stdjob.com.example.stdjob.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.stdjob.App;
import com.example.stdjob.R;

public class SignupPage3Activity extends AppCompatActivity {


    EditText edtxt6;
    TextView alarm1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page3);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtxt6=findViewById(R.id.edtxt6);
        alarm1=findViewById(R.id.alarm1);
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

    public void nextactivity1(View view) {
        if (edtxt6.getText().toString().isEmpty()){
            alarm1.setText( "جای خالی را پر کنید" );
        }
        else {
            Intent intent1 = new Intent(this, SignupPage5Activity.class);
            intent1.putExtra("type", App.COMPANY_TYPE_CODE);
            startActivity( intent1 );
            overridePendingTransition(R.anim.from_in_right,R.anim.from_out_left);
        }
    }
}
