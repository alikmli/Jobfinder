package com.example.stdjob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class LoginActivity extends PersianActivity {

    private static final int REQ_CODE_PERM = 120;
    private TextInputEditText username,password;
    private AppCompatImageView login_image;
    private ProgressBar progressBar;
    TaskRequest asyncRequest;
    private Handler handler=new Handler();
    private AppCompatButton login_btn;
    private AppCompatTextView wrongMsg;
    private int type;
    String result="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        login_image=findViewById(R.id.login_image);
        username=findViewById(R.id.login_username);
        password=findViewById(R.id.login_password);
        progressBar=findViewById(R.id.load_progress);
        login_btn=findViewById(R.id.login_btn);
        wrongMsg=findViewById(R.id.wrong_login);

        Bundle b=getIntent().getExtras();
        if(b != null){
            type=b.getInt("type");


            if(type == App.STUDENT_TYPE_CODE){
                login_image.setImageResource(R.drawable.user);
            }else if(type == App.COMPANY_TYPE_CODE){
                login_image.setImageResource(R.drawable.group);
            }
        }


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){

                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.INTERNET},REQ_CODE_PERM);

            }
        }


    }




    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,StartActivity.class));
        finish();
    }

    public void login(View view) {

        wrongMsg.setVisibility(View.GONE);
        final String user=username.getText().toString().trim();
        final String pass=password.getText().toString().trim();

        if(user.isEmpty()){
            username.setError(getResources().getString(R.string.username_error_message));
            return;
        }

        if(pass.isEmpty()){
            password.setError(getResources().getString(R.string.password_error_message));
            return;
        }
        HttpUtils.RequestData request=null;
        if(type == App.STUDENT_TYPE_CODE)
            request=new HttpUtils.RequestData(HttpUtils.STUDENT_URI,"GET");
        else
            request=new HttpUtils.RequestData(HttpUtils.EMP_URI,"GET");
        request.setParam("username",user);
        request.setParam("password",pass);
        asyncRequest=asyncRequest= (TaskRequest) new TaskRequest("loginRequest").execute(request);




        progressBar.setVisibility(View.VISIBLE);

    }

    public class TaskRequest extends AsyncTask<HttpUtils.RequestData,String,String>{

        private String taskName;
        public TaskRequest(String taskName){
            this.taskName=taskName;
        }



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            login_btn.setEnabled(false);
        }


        @Override
        protected String doInBackground(HttpUtils.RequestData... requestData) {
            String result=HttpUtils.RequestData(requestData[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            result=s.trim();
            progressBar.setVisibility(View.GONE);


            if(result.equalsIgnoreCase("invalid login")){
                login_btn.setEnabled(true);
                wrongMsg.setVisibility(View.VISIBLE);

            }else{

                if(type == App.STUDENT_TYPE_CODE){
                    Student student=ParserUtil.parseJson(result);
                    App.prefs.setIntValue(AppPreferences.PreferencesKeys.USERTYPE,App.STUDENT_TYPE_CODE);

                    DBHealperForStudentUser healper=new DBHealperForStudentUser(getApplicationContext());
                    SQLiteDatabase db=healper.getWritableDatabase();


                    if(db.isOpen())
                        db.close();


                    healper.insertStudent(student);
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);

                    startActivity(intent);
                    overridePendingTransition(R.anim.from_in_right,R.anim.from_out_left);
                    finish();
                }else{
                    Employee employee=ParserUtil.parseJsonEmployee(result);
                    App.prefs.setIntValue(AppPreferences.PreferencesKeys.USERTYPE,App.COMPANY_TYPE_CODE);


                    Log.i("DDD",employee.toString());

                    DBHealperForStudentUser healper=new DBHealperForStudentUser(getApplicationContext());
                    SQLiteDatabase db=healper.getWritableDatabase();


                    if(db.isOpen())
                        db.close();


                    healper.insertEmployee(employee);
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);

                    startActivity(intent);
                    overridePendingTransition(R.anim.from_in_right,R.anim.from_out_left);
                    finish();
                }




            }
        }
    }
}
