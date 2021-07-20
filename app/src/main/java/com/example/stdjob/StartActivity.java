package com.example.stdjob;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.example.stdjob.com.example.stdjob.signup.SignupActivity;

public class StartActivity extends PersianActivity {

    private TextView tv;


    @Override
    protected void onStart() {
        super.onStart();

        if(App.prefs.containKey(AppPreferences.PreferencesKeys.USERTYPE)){
            Intent intent=new Intent(StartActivity.this,MainActivity.class);
            intent.putExtra("type",App.prefs.getIntValue(AppPreferences.PreferencesKeys.USERTYPE));
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        tv=findViewById(R.id.intro_company);

        String udata=getResources().getString(R.string.start_company);
        SpannableString content = new SpannableString(udata);
        content.setSpan(new UnderlineSpan(), 0, udata.length(), 0);
        tv.setText(content);


    }

    public void singup(View view) {
        startActivity(new Intent(this, SignupActivity.class));
        overridePendingTransition(R.anim.from_in_right,R.anim.from_out_left);
        finish();
    }

    public void signin(View view) {
//
        Intent intent=new Intent(StartActivity.this,LoginActivity.class);
        intent.putExtra("type",App.STUDENT_TYPE_CODE);
        startActivity(intent);
        overridePendingTransition(R.anim.from_in_right,R.anim.from_out_left);
        finish();
    }

    public void companyLogin(View view) {
//
        Intent intent=new Intent(StartActivity.this,LoginActivity.class);
        intent.putExtra("type",App.COMPANY_TYPE_CODE);
        startActivity(intent);
        overridePendingTransition(R.anim.from_in_right,R.anim.from_out_left);
        finish();

    }
}
