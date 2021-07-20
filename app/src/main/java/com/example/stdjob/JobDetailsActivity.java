package com.example.stdjob;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class JobDetailsActivity extends AppCompatActivity {


    private AppCompatTextView title,price,priceInHour,essentials,anotherPrice;
    private AppCompatTextView emp_tv,desc_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        title=findViewById(R.id.title_paper);
        price=findViewById(R.id.time_price_paper);
        priceInHour=findViewById(R.id.time_paper);
        essentials=findViewById(R.id.essential_ex_paper);
        anotherPrice=findViewById(R.id.price_paper);

        emp_tv=findViewById(R.id.company_name_paper);
        desc_tv=findViewById(R.id.description_paper);

        Bundle bundle=getIntent().getExtras();

        if(bundle !=null){

            Job job=bundle.getParcelable("job");

            title.setText(job.getTitle());
            price.setText(String.valueOf(job.getPrice()) + " هزار تومان ");


            int numberHour=0;
            for(TimeLine items:job.getTimeLines()) {
                double toMins=0;
                double fromMins=0;
                for(TimeLine.Hours item:items.getHours()){
                    int tmph=item.getToHour() - item.getFromHour();

                    int tmp=item.getToMin() - item.getFromMin();



                    numberHour += (tmph*60 +tmp);
                }

                numberHour /=60;

            }

            double priceForHour=job.getPrice() / numberHour;


            priceInHour.setText(String.valueOf(priceForHour) + " در ساعت ");
            essentials.setText(job.getEssentials());
            emp_tv.setText(job.getEmpName());
            desc_tv.setText(job.getDescription());

            anotherPrice.setText(String.valueOf(job.getPrice()) + " هزار تومان ");

        }

    }

    public void submitForJob(View view) {

    }
}
