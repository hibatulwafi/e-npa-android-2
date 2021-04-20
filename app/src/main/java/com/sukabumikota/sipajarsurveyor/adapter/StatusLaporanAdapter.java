package com.sukabumikota.sipajarsurveyor.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sukabumikota.sipajarsurveyor.R;
import com.sukabumikota.sipajarsurveyor.apihelper.koneksi;
import com.sukabumikota.sipajarsurveyor.survey.SurveyDetail;
import com.sukabumikota.sipajarsurveyor.model.SemuaStatusLaporanItem;

import java.util.List;


import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class StatusLaporanAdapter extends RecyclerView.Adapter<StatusLaporanAdapter.StatusLaporanHolder>{
    List<SemuaStatusLaporanItem> semuastatuslaporanItemList;
    Context mContext;

    public StatusLaporanAdapter(Context context, List<SemuaStatusLaporanItem> statuslaporanlist){
        this.mContext = context;
        semuastatuslaporanItemList = statuslaporanlist;
    }
    @NonNull
    @Override
    public StatusLaporanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_rvsurvey, parent, false);
        return new StatusLaporanHolder(itemView);
    }
    @Override
    public void onBindViewHolder(@NonNull StatusLaporanHolder holder, int position){
        final SemuaStatusLaporanItem semuastatuslaporanitem = semuastatuslaporanItemList.get(position);
        if (semuastatuslaporanitem.getFoto_id().equals("")){
            holder.txtop.setText("Data Kosong");
            holder.txtwp.setText("Data Kosong");
            holder.txttanggal.setText("Data Kosong");
        }else {

            // Buat Warna
            if (semuastatuslaporanitem.getStatus().equals("Sedang Proses")){
                holder.txtstatus.setTextColor(ContextCompat.getColor(mContext, R.color.dot_inactive_screen1));
            }else if(semuastatuslaporanitem.getStatus().equals("Ditolak")){
                holder.txtstatus.setTextColor(ContextCompat.getColor(mContext, R.color.red));
            }else if(semuastatuslaporanitem.getStatus().equals("Diterima")){
                holder.txtstatus.setTextColor(ContextCompat.getColor(mContext, R.color.color7));
            }else{

            }

            holder.txtop.setText(semuastatuslaporanitem.getOp_nama());
            holder.txtwp.setText(semuastatuslaporanitem.getWp_nama());
            holder.txtstatus.setText(semuastatuslaporanitem.getStatus());
            holder.txttanggal.setText("Bulan : "+koneksi.nama_bulan(semuastatuslaporanitem.getFoto_bulan())+" - "+semuastatuslaporanitem.getFoto_tahun());
            Glide.with(holder.itemView.getContext())
                    .load(koneksi.GAMBAR_URL
                            + "bukti/"
                            + semuastatuslaporanitem.getNama())
                    .apply(bitmapTransform(new VignetteFilterTransformation(new PointF(0.5f, 0.5f),
                            new float[]{0.0f, 0.0f, 0.0f}, 0.4f, 1.0f)).dontAnimate())
                    .into(holder.img_gambar);
        }

        holder.cvlap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moveToDetail = new Intent(mContext, SurveyDetail.class);
                moveToDetail.putExtra(SurveyDetail.EXTRA_DATA, semuastatuslaporanitem);
                mContext.startActivity(moveToDetail);
            }
        });
    }
    @Override
    public int getItemCount(){
        return semuastatuslaporanItemList.size();
    }

    public class StatusLaporanHolder extends RecyclerView.ViewHolder {
        public TextView txtwp, txtop,txttanggal,txtstatus;
        CardView cvlap;
        ImageView img_gambar;
        public StatusLaporanHolder(@NonNull View itemView){
            super(itemView);
            txtwp = itemView.findViewById(R.id.tvwajibpajak);
            txtop = itemView.findViewById(R.id.tvobjekpajak);
            txttanggal = itemView.findViewById(R.id.tvsurveytanggal);
            img_gambar = itemView.findViewById(R.id.imgtugas);
            cvlap= itemView.findViewById(R.id.cvlaporan);
            txtstatus = itemView.findViewById(R.id.tvstatus);
        }
    }



}
