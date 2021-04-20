package com.sukabumikota.sipajarsurveyor.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.sukabumikota.sipajarsurveyor.R;
import com.sukabumikota.sipajarsurveyor.model.SemuaObjekPajakItem;
import com.sukabumikota.sipajarsurveyor.objekpajak.OPDetail;

import java.util.List;

public class ObjekPajakAdapter extends RecyclerView.Adapter<ObjekPajakAdapter.ObjekPajakHolder>{
        List<SemuaObjekPajakItem> semuaobjekpajakItemList;
        Context mContext;

public ObjekPajakAdapter(Context context, List<SemuaObjekPajakItem> objekpajaklist){
        this.mContext = context;
        semuaobjekpajakItemList = objekpajaklist;
        }
@NonNull
@Override
public ObjekPajakHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_rvop, parent, false);
        return new ObjekPajakHolder(itemView);
        }
@Override
public void onBindViewHolder(@NonNull ObjekPajakHolder holder, int position){
    final SemuaObjekPajakItem semuaobjekpajakitem = semuaobjekpajakItemList.get(position);
    if (semuaobjekpajakitem.getOp_id().equals("")){
        holder.txtnamaop.setText("Data Kosong");
        holder.txtnamawp.setText("Data Kosong");
    }else {
        holder.txtnamaop.setText(semuaobjekpajakitem.getOp_nama());
        holder.txtnamawp.setText(semuaobjekpajakitem.getNama_wajib_pajak());
    }

    holder.cvop.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent moveToDetail = new Intent(mContext, OPDetail.class);
            moveToDetail.putExtra(OPDetail.EXTRA_DATA, semuaobjekpajakitem);
            mContext.startActivity(moveToDetail);
        }
    });
}
@Override
public int getItemCount(){
        return semuaobjekpajakItemList.size();
        }

public class ObjekPajakHolder extends RecyclerView.ViewHolder {
    public TextView txtnamaop, txtnamawp;
    CardView cvop;
    public ObjekPajakHolder(@NonNull View itemView){
        super(itemView);
        txtnamaop = itemView.findViewById(R.id.tvnamaop);
        txtnamawp = itemView.findViewById(R.id.tvnamawp);
        cvop = itemView.findViewById(R.id.cvop);
    }
}

}