package com.example.stdjob;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.LinkedList;
import java.util.List;

public class StudentResponsibilitiesFragment extends Fragment {

    private List<Job> jobList=new LinkedList<>();
    private RecyclerView responRecycleView;
    private ResponseAdapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout emptyLayout;
    private AppCompatImageView im;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_studentresponsibilities,container,false);


        emptyLayout=view.findViewById(R.id.empy_respons);
        responRecycleView=view.findViewById(R.id.respons_items);
        swipeRefreshLayout=view.findViewById(R.id.swiperefresh_response);
        im = view.findViewById(R.id.empty_respns_image);

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

        adapter=new ResponseAdapter(jobList);

        responRecycleView.setAdapter(adapter);
        responRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));
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

                emptyLayout.setVisibility(View.VISIBLE);
                swipeRefreshLayout.setVisibility(View.GONE);



                Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.bounce);

                im.setVisibility(View.VISIBLE);
                im.startAnimation(animation);

            }else{
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                emptyLayout.setVisibility(View.GONE);


                adapter=new ResponseAdapter(jobList);
                responRecycleView.setAdapter(adapter);
                responRecycleView.setLayoutManager(new LinearLayoutManager(getContext()));

            }


        }
    }

    class ResponseAdapter extends RecyclerView.Adapter<ResponseAdapter.ResponseViewHolder>{
        private List<Job> jobList;

        public ResponseAdapter(List<Job> jobs){
            this.jobList=jobs;
            if(jobList.isEmpty()){
                jobList=new LinkedList<>();
            }
        }

        @NonNull
        @Override
        public ResponseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ResponseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.response_item_layout,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull ResponseViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return jobList.size();
        }

        class ResponseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private AppCompatTextView res_title,res_requester,rese_price;
            private CardView cardView;
            public ResponseViewHolder(@NonNull View itemView) {
                super(itemView);
                res_title=itemView.findViewById(R.id.response_title);
                res_requester=itemView.findViewById(R.id.response_requester);
                rese_price=itemView.findViewById(R.id.response_price);
                cardView=itemView.findViewById(R.id.response_main_layout);
            }

            public void bind(int position){
                Job item=jobList.get(position);
                Log.i("DDD",item.toString());
                res_title.setText(item.getTitle());
                res_requester.setText(item.getEmpName());

                rese_price.setText(String.valueOf(item.getPrice()) + " هزار تومان ");

                cardView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                Context myContext = new ContextThemeWrapper(getContext(),R.style.menuStyle);
                final PopupMenu menu = new PopupMenu(myContext , cardView);
                menu.inflate(R.menu.pop_up_menu_delete_request);


                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        menu.dismiss();
                        return false;
                    }
                });

                menu.setGravity(Gravity.END);
                menu.show();
            }
        }
    }
}
