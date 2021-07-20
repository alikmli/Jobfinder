package com.example.stdjob;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;

import java.util.LinkedList;
import java.util.List;

public class TimeItemAdapter extends ArrayAdapter<TimeLine> {
    private List<TimeLine> items;
    private DeleteTimeList deleteListener;
    private DBHealperForStudentUser healper;

    public TimeItemAdapter(@NonNull Context context,List<TimeLine> items,DeleteTimeList listener,DBHealperForStudentUser dbHealper) {
        super(context, R.layout.listview_time_item_adapter,items);
        this.items=items;
        this.deleteListener=listener;
        this.healper=dbHealper;
    }




    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(convertView == null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.listview_time_item_adapter,parent,false);
            viewHolder=new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        }else{
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.fill(position);

        return convertView;
    }




    class ViewHolder{
        private AppCompatTextView textView;
        private AppCompatTextView texth;
        private AppCompatImageView iv;
        public ViewHolder(View view){
            textView=view.findViewById(R.id.date_timeline);
            texth=view.findViewById(R.id.time_hours);
            iv=view.findViewById(R.id.delete_time_iconn);
        }


        public void fill(final int position){
            final TimeLine timeLine=items.get(position);
            String day= String.format("%s %d %s", timeLine.getWeekDay(),timeLine.getDay(),timeLine.getMonthName());

            textView.setText(day);
            List<String> times=new LinkedList<>();
            for(TimeLine.Hours item:timeLine.getHours()){
                String tm= String.format("%s %s %s %s", "از ساعت",item.FromTime(),"تا ساعت",item.toTime());
                texth.append(tm + "\n");
            }

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   deleteListener.deleteAt(position);

                   healper.deleteTimeLine(healper.getTimeLineID(timeLine));
                }
            });


        }


    }
}
