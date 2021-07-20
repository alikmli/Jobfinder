package com.example.stdjob;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

public class SupportActivity extends AppCompatActivity implements View.OnClickListener {


    private RecyclerView recyclerView;
    private MessageAdapter adapter;
    private List<Message> messages=new LinkedList<>();
    private AppCompatImageButton send;
    private AppCompatEditText sendAre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);


        if(getSupportActionBar() != null )
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        send=findViewById(R.id.btn_send);
        send.setOnClickListener(this);
        sendAre=findViewById(R.id.send_area);


        recyclerView=findViewById(R.id.mesg_recycle);
        adapter=new MessageAdapter(messages);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(R.string.user_message).setEnabled(false).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onClick(View v) {
        String mesg=sendAre.getText().toString().trim();
        sendAre.setText("");
        //send to server
        messages.add(new Message(mesg,true));
        adapter.notifyDataSetChanged();

    }

    class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder>{
        private List<Message> messages;


        public MessageAdapter(List<Message> list){
            this.messages=list;
        }

        @NonNull
        @Override
        public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MessageViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_message_item,parent,false));
        }

        @Override
        public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
            holder.bind(position);
        }

        @Override
        public int getItemCount() {
            return messages.size();
        }

        class MessageViewHolder extends RecyclerView.ViewHolder{
            AppCompatTextView send,recv;
            public MessageViewHolder(@NonNull View itemView) {
                super(itemView);
                send=itemView.findViewById(R.id.message_sender);
                recv=itemView.findViewById(R.id.message_reciever);
            }


            public void bind(int position){
                Message item=messages.get(position);
                if(item.getMine()){
                    send.setVisibility(View.VISIBLE);
                    send.setText(item.getMessage());
                    recv.setVisibility(View.GONE);
                }else{
                    recv.setVisibility(View.VISIBLE);
                    recv.setText(item.getMessage());
                    send.setVisibility(View.GONE);
                }
            }
        }
    }
}
