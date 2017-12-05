package com.simon.bigfiledownload;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
   private RecyclerView mRecyclerView;
   private ArrayList<String> downloadTasks;
   private LinearLayoutManager mLayoutManager;
   private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView =findViewById(R.id.rv_list);
        downloadTasks=new ArrayList();
        downloadTasks.add("a");
        downloadTasks.add("b");
        downloadTasks.add("c");
        downloadTasks.add("d");
        mLayoutManager=new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        myAdapter =new MyAdapter(downloadTasks);
        mRecyclerView.setAdapter(myAdapter);
    }


   public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
       ArrayList<String> downloadTasks;

       public MyAdapter(ArrayList<String> downloadTasks) {
           if(downloadTasks==null){
               downloadTasks=new ArrayList<>();
           }
           this.downloadTasks = downloadTasks;
       }

       public  class ViewHolder extends RecyclerView.ViewHolder{
            private ProgressBar progressBar;
            private TextView tvName;
            private Button btnStart;
            private Button btnPause;
           public ViewHolder(View itemView) {
               super(itemView);
               progressBar=itemView.findViewById(R.id.pb_progress);
               tvName=itemView.findViewById(R.id.tv_name);
               btnStart=itemView.findViewById(R.id.btn_start);
               btnPause=itemView.findViewById(R.id.btn_pause);
           }

           public void bind(String url){
               tvName.setText(url);
               btnStart.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

                   }
               });

               btnPause.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

                   }
               });
           }
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
            ViewHolder viewHolder=new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind(downloadTasks.get(position));
        }

        @Override
        public int getItemCount() {
            return downloadTasks.size();
        }
    }
}
