package com.example.stdjob;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import java.util.LinkedList;
import java.util.List;

public class JobSearchFragment extends Fragment {
    private RecyclerView recyclerView;
    private JobsAdapter adapter;
    private List<Job> jobs;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        jobs=new LinkedList<>();


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_jobsearch,container,false);


        swipeRefreshLayout=v.findViewById(R.id.swiperefresh);
        recyclerView=v.findViewById(R.id.recycler_view);
        settingRequest();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                settingRequest();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return v;
    }

    public void settingRequest(){
        HttpUtils.RequestData requestData=new HttpUtils.RequestData(HttpUtils.JOB_ALL_URI,"GET");
        new JobSync().execute(requestData);

        adapter=new JobsAdapter(jobs);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.search_menu,menu);
    }

    class JobSync extends AsyncTask<HttpUtils.RequestData,String,String>{


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            swipeRefreshLayout.setRefreshing(true);
        }

        @Override
        protected String doInBackground(HttpUtils.RequestData... requestData) {
            String result=HttpUtils.RequestData(requestData[0]);
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("DDD",s);

            swipeRefreshLayout.setRefreshing(false);
            jobs.clear();

            jobs.addAll(ParserUtil.parsJobs(s));
            adapter=new JobsAdapter(jobs);

            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        }
    }


    public class JobsAdapter extends RecyclerView.Adapter<JobsAdapter.JobViewHolder>{
        private List<Job> jobs;

        public JobsAdapter(List<Job> jobs){
            this.jobs=jobs;
        }

        @NonNull
        @Override
        public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new JobViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.intro_job_layout,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return jobs.size();
        }

        public class JobViewHolder extends RecyclerView.ViewHolder{
            private AppCompatTextView title_tv, price_tv, hourPrice_tv, prevLocation_tv,
                    cityLocation_tv;
            private CardView mainLayout;
            private LinearLayout layout;
            public JobViewHolder(@NonNull View itemView) {
                super(itemView);

                title_tv =itemView.findViewById(R.id.title_intro);
                price_tv =itemView.findViewById(R.id.time_price);
                hourPrice_tv =itemView.findViewById(R.id.time_intro);
                prevLocation_tv =itemView.findViewById(R.id.location_prev_intro);
                cityLocation_tv =itemView.findViewById(R.id.location_city_intro);

                mainLayout=itemView.findViewById(R.id.job_intro_layout);
                layout=itemView.findViewById(R.id.layout_items_times);
            }

            public void bind(int position){
                final Job job=jobs.get(position);


                title_tv.setText(job.getTitle());
                price_tv.setText(String.valueOf(job.getPrice()) + " هزار تومان ");
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
                hourPrice_tv.setText(String.valueOf(priceForHour) + " در ساعت ");

                String[] locations=job.getEmpAddress().split("-");
                prevLocation_tv.setText(locations[0]);
                cityLocation_tv.setText(locations[1]);

                for(TimeLine timeLine:job.getTimeLines()) {

                    HoursItesm tmp=new HoursItesm(getContext());

                    String tim= String.format("%s %d %s", timeLine.getWeekDay(),timeLine.getDay(),timeLine.getMonthName());
                    tmp.setDate(tim);
                    String hFormat="";
                    for(TimeLine.Hours hours:timeLine.getHours()) {
                        String hoursFormat = String.format("%d:%d - %d:%d%n", hours.getFromHour(), hours.getFromMin()
                                , hours.getToHour(), hours.getToMin());
                        hFormat += hoursFormat;
                    }

                    tmp.setTime(hFormat);

                    layout.addView(tmp);


                }


                mainLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(getActivity(),JobDetailsActivity.class);
                        intent.putExtra("job",job);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.from_in_right,R.anim.from_out_left);
                    }
                });

            }
        }
    }
}
