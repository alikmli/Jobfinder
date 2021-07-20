package com.example.stdjob.com.example.stdjob.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.stdjob.R;

public class SignupPage6Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page6);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        startActivity(new Intent(this, SignupPage5Activity.class));
        finish();

        return true;

    }
}
