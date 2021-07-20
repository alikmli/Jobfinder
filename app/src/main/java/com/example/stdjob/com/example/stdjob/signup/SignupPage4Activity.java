package com.example.stdjob.com.example.stdjob.signup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.stdjob.App;
import com.example.stdjob.R;

public class SignupPage4Activity extends AppCompatActivity {
    TextView alarm3;
    EditText edtxt1,edtxt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page4);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        alarm3=(TextView)findViewById( R.id.alam3 );
        edtxt1=(EditText)findViewById( R.id.edtxt1 );
        edtxt2=(EditText)findViewById( R.id.edtxt2 );
    }

    @Override
    public boolean onSupportNavigateUp(){

        onBackPressed();

        super.onBackPressed();
        startActivity(new Intent(this, SignupPage2Activity.class));
        finish();

        return true;

    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.from_out_right,R.anim.from_in_left);
    }

    public void nextactivity4(View view) {

        if (edtxt1.getText().toString().isEmpty() && edtxt2.getText().toString().isEmpty()){
            alarm3.setText( "جای خالی را پر کنید" );
        }
        else {
            Intent intent1 = new Intent(this, SignupPage5Activity.class);
            intent1.putExtra("type", App.STUDENT_TYPE_CODE);
            startActivity( intent1 );
            overridePendingTransition(R.anim.from_in_right,R.anim.from_out_left);
        }
    }
}
