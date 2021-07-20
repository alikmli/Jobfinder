package com.example.stdjob;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.LinkedList;
import java.util.List;

public class CompanyHistoryFragment extends Fragment {

    private List<Job> jobList=new LinkedList<>();
    private RecyclerView recyclerView;
    private HistoryRequestAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout layout;
    private AppCompatImageView im;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.layout_company_history_fragment,container,false);

        im = view.findViewById(R.id.empty_history_image_request);
        recyclerView=view.findViewById(R.id.history_requests);
        layout=view.findViewById(R.id.empy_history_request_layout);
        swipeRefreshLayout=view.findViewById(R.id.swiperefresh_comp_hist);


        HttpUtils.RequestData requestData=new HttpUtils.RequestData(HttpUtils.JOB_ALL_URI,"GET");
        new JobSync().execute(requestData);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                settingRequest();
            }
        });
        return view;
    }

    public void settingRequest(){
        HttpUtils.RequestData requestData=new HttpUtils.RequestData(HttpUtils.JOB_ALL_URI,"GET");
        new JobSync().execute(requestData);

        adapter=new HistoryRequestAdapter(jobList);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.search_menu,menu);
    }

    class JobSync extends AsyncTask<HttpUtils.RequestData,String,String> {


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
            jobList.clear();

            jobList.addAll(ParserUtil.parsJobs(s));

            if(jobList.isEmpty()) {
                layout.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                swipeRefreshLayout.setVisibility(View.GONE);
                im.setVisibility(View.VISIBLE);
                im.startAnimation(animation);
            }else{
                layout.setVisibility(View.GONE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);

                adapter=new HistoryRequestAdapter(jobList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }


        }
    }



    class HistoryRequestAdapter extends RecyclerView.Adapter<HistoryRequestAdapter.HistoryRequestViewHolder> {
        private List<Job> jobList=new LinkedList<>();

        public HistoryRequestAdapter(List<Job> jobs){
            this.jobList=jobs;
        }

        @NonNull
        @Override
        public HistoryRequestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new HistoryRequestViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.company_history_layout_item,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull HistoryRequestViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return jobList.size();
        }

        class HistoryRequestViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
            AppCompatTextView req_title,req_price,req_date;
            AppCompatImageView req_more;
            LinearLayout layout;
            public HistoryRequestViewHolder(@NonNull View itemView) {
                super(itemView);

                this.req_title=itemView.findViewById(R.id.req_title);
                this.req_date=itemView.findViewById(R.id.req_times);

                this.req_price=itemView.findViewById(R.id.req_price);

                this.req_more=itemView.findViewById(R.id.req_more);
                this.layout=itemView.findViewById(R.id.layout_items_times_company);
            }


            public void bind(int position){
                Job job=jobList.get(position);
                req_title.setText(job.getTitle());
                req_price.setText(String.valueOf(job.getPrice()));
                req_date.setText(String.valueOf(job.getStdID()));
                for(TimeLine timeLine:job.getTimeLines()) {

                    CompanyItemTimes tmp = new CompanyItemTimes(getContext());

                    String tim = String.format("%s %d %s", timeLine.getWeekDay(), timeLine.getDay(), timeLine.getMonthName());
                    tmp.setDate(tim);
                    String hFormat = "";
                    for (TimeLine.Hours hours : timeLine.getHours()) {
                        String hoursFormat = String.format("%d:%d - %d:%d%n", hours.getFromHour(), hours.getFromMin()
                                , hours.getToHour(), hours.getToMin());
                        hFormat += hoursFormat;
                    }

                    tmp.setTime(hFormat);

                    layout.addView(tmp);

                }


                req_more.setOnClickListener(this);

            }

            @Override
            public void onClick(View v) {
                Context myContext = new ContextThemeWrapper(getContext(),R.style.menuStyle);
                PopupMenu popupMenu = new PopupMenu(myContext , req_more);
                popupMenu.inflate(R.menu.job_request_layout);
                popupMenu.show();


                popupMenu.setOnMenuItemClickListener(this);

            }

            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getItemId() == R.id.rate_request){
                    final Dialog dialog=new Dialog(getContext());
                    dialog.setContentView(R.layout.layout_rating_request);


                    dialog.setCancelable(false);
                    dialog.show();

                    RatingBar r=dialog.findViewById(R.id.ratingBar);
                    r.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                        @Override
                        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                            dialog.dismiss();
                        }
                    });
                }
                return false;
            }
        }
    }
}
