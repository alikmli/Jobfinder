package com.example.stdjob;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.LinkedList;
import java.util.List;

public class StudentHistoryFragment extends Fragment {

    private List<Job> jobList=new LinkedList<>();
    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private AppCompatImageView im;
    private LinearLayout emptyLayout;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_studenthistory,container,false);

        emptyLayout=view.findViewById(R.id.empy_history);
        swipeRefreshLayout=view.findViewById(R.id.swiperefresh_history);
        recyclerView=view.findViewById(R.id.history_items);
        im = view.findViewById(R.id.empty_history_image);

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


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.search_menu,menu);
    }

    public void settingRequest(){
        HttpUtils.RequestData requestData=new HttpUtils.RequestData(HttpUtils.JOB_ALL_URI,"GET");
        new JobSync().execute(requestData);

        adapter=new HistoryAdapter(jobList);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
                emptyLayout.setVisibility(View.VISIBLE);
                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);
                swipeRefreshLayout.setVisibility(View.GONE);
                im.setVisibility(View.VISIBLE);
                im.startAnimation(animation);

            }else{


                emptyLayout.setVisibility(View.GONE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);

                adapter=new HistoryAdapter(jobList);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            }

        }
    }


    class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>{

        private List<Job> jobList;

        public HistoryAdapter(List<Job> jobList){
            this.jobList=jobList;
            if(jobList.isEmpty())
                this.jobList=new LinkedList<>();
        }


        @NonNull
        @Override
        public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new HistoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item_layout,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return jobList.size();
        }

        class HistoryViewHolder extends RecyclerView.ViewHolder{
            private AppCompatTextView hist_title,hist_requester,hits_price,hist_rate;
            public HistoryViewHolder(@NonNull View itemView) {
                super(itemView);
                hist_title=itemView.findViewById(R.id.hist_title);
                hist_requester=itemView.findViewById(R.id.hist_requester);
                hits_price=itemView.findViewById(R.id.hist_price);
                hist_rate=itemView.findViewById(R.id.hist_rate);
            }

            public void bind(int position){
                Job item=jobList.get(position);

                hist_title.setText(item.getTitle());
                hist_requester.setText(item.getEmpName());
                String rate="";
                if(item.getRate() > 0){
                    rate= String.format("%1.1f از %1.1f", item.getRate(),5f);
                }else {
                    rate=getActivity().getResources().getString(R.string.rate_message);
                }
                hist_rate.setText(rate);
                hits_price.setText(String.valueOf(item.getPrice()) + " هزار تومان ");

            }
        }
    }

}
