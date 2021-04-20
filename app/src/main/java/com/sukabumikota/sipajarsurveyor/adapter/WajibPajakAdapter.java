package com.sukabumikota.sipajarsurveyor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.sukabumikota.sipajarsurveyor.R;
import com.sukabumikota.sipajarsurveyor.model.SemuaWajibPajakItem;
import com.sukabumikota.sipajarsurveyor.wajibpajak.WPDetail;

import java.util.List;
import java.util.Random;

public class WajibPajakAdapter extends RecyclerView.Adapter<WajibPajakAdapter.WajibPajakHolder>{
    List<SemuaWajibPajakItem> semuawajibpajakItemList;
    Context mContext;
    public int[] gambar = {R.drawable.ava1,R.drawable.ava2,R.drawable.ava3,R.drawable.ava4};
    public WajibPajakAdapter(Context context, List<SemuaWajibPajakItem> wajibpajaklist){
        this.mContext = context;
        semuawajibpajakItemList = wajibpajaklist;
    }
    @NonNull
    @Override
    public WajibPajakAdapter.WajibPajakHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_rvwp, parent, false);
        return new WajibPajakAdapter.WajibPajakHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull WajibPajakAdapter.WajibPajakHolder holder, int position){
        final SemuaWajibPajakItem semuawajibpajakitem = semuawajibpajakItemList.get(position);
        if (semuawajibpajakitem.getWp_id().equals("")){
            holder.tvwpnama.setText("Data Kosong");
            holder.tvwpemail.setText("Data Kosong");
        }else {
            holder.tvwpnama.setText(semuawajibpajakitem.getNama());
            holder.tvwpemail.setText(semuawajibpajakitem.getEmail());
            Random rand = new Random();
            holder.ivava.setImageResource(gambar[rand.nextInt(gambar.length)]);
        }

        holder.cvwp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToDetail = new Intent(mContext, WPDetail.class);
                moveToDetail.putExtra(WPDetail.EXTRA_DATA, semuawajibpajakitem);
                mContext.startActivity(moveToDetail);
            }
        });
    }
    @Override
    public int getItemCount(){
        return semuawajibpajakItemList.size();
    }

    public class WajibPajakHolder extends RecyclerView.ViewHolder {
        public TextView tvwpnama, tvwpemail;
        public ImageView ivava;
        CardView cvwp;
        public WajibPajakHolder(@NonNull View itemView){
            super(itemView);
            tvwpnama = itemView.findViewById(R.id.tvwpnama);
            tvwpemail = itemView.findViewById(R.id.tvwpemail);
            cvwp = itemView.findViewById(R.id.cvwp);
            ivava = itemView.findViewById(R.id.imgava);
        }
    }
    

}