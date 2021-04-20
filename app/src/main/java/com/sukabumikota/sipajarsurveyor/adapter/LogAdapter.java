package com.sukabumikota.sipajarsurveyor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sukabumikota.sipajarsurveyor.R;
import com.sukabumikota.sipajarsurveyor.model.SemuaLogItem;

import java.util.List;

public class LogAdapter extends RecyclerView.Adapter<LogAdapter.LogHolder>{
    List<SemuaLogItem> semualogItemList;
    Context mContext;

    public LogAdapter(Context context, List<SemuaLogItem> loglist){
        this.mContext = context;
        semualogItemList = loglist;
    }
    @NonNull
    @Override
    public LogAdapter.LogHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_rvlog, parent, false);
        return new LogAdapter.LogHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull LogAdapter.LogHolder holder, int position){
        final SemuaLogItem semualogitem = semualogItemList.get(position);
        if (semualogitem.getNama().equals("")){
            holder.tvlog.setText("Data Kosong");
            holder.tvtanggal.setText("Data Kosong");
        }else {
            holder.tvlog.setText(semualogitem.getNama());
            holder.tvtanggal.setText(semualogitem.getTanggal());
        }
    }
    @Override
    public int getItemCount(){
        return semualogItemList.size();
    }

    public class LogHolder extends RecyclerView.ViewHolder {
        public TextView tvlog, tvtanggal;
        public LogHolder(@NonNull View itemView){
            super(itemView);
            tvlog = itemView.findViewById(R.id.tvlog);
            tvtanggal = itemView.findViewById(R.id.tvtanggal);
        }
    }

}