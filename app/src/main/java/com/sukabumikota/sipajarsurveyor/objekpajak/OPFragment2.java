package com.sukabumikota.sipajarsurveyor.objekpajak;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sukabumikota.sipajarsurveyor.R;
import com.sukabumikota.sipajarsurveyor.adapter.ObjekPajakAdapter;
import com.sukabumikota.sipajarsurveyor.adapter.StatusLaporanAdapter;
import com.sukabumikota.sipajarsurveyor.apihelper.BaseApiService;
import com.sukabumikota.sipajarsurveyor.apihelper.koneksi;
import com.sukabumikota.sipajarsurveyor.model.ResponseObjekPajak;
import com.sukabumikota.sipajarsurveyor.model.ResponseStatusLaporan;
import com.sukabumikota.sipajarsurveyor.model.SemuaObjekPajakItem;
import com.sukabumikota.sipajarsurveyor.model.SemuaStatusLaporanItem;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

public class OPFragment2 extends Fragment {
    public static final String EXTRA_DATA= "extra_data";
    ProgressDialog loading;
    Context mContext;
    List<SemuaStatusLaporanItem> semuastatuslaporanItemList = new ArrayList<>();
    StatusLaporanAdapter statuslaporanAdapter;
    BaseApiService mApiiService;
    RecyclerView recyclerView;
    String op_id;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_op2, container, false);
        recyclerView = rootView.findViewById(R.id.rvlaporan);
        mContext = getActivity();
        mApiiService = koneksi.getAPIService();
        statuslaporanAdapter = new StatusLaporanAdapter(getActivity(), semuastatuslaporanItemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        SemuaObjekPajakItem semuaobjekpajakitem = getActivity().getIntent().getParcelableExtra(EXTRA_DATA);
        op_id = semuaobjekpajakitem.getOp_id();
        getResultListStatusLaporan(op_id);
        return rootView;
    }
    private void getResultListStatusLaporan(String op_id) {
        loading = ProgressDialog.show(getActivity(), null, "Harap Tunggu.....", true, false);
        mApiiService.getStatus(op_id).enqueue(new Callback<ResponseStatusLaporan>() {
            @Override
            public void onResponse(Call<ResponseStatusLaporan> call, Response<ResponseStatusLaporan> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    Boolean error = response.body().isError();
                    semuastatuslaporanItemList = response.body().getStatuslaporan();
                    if(error.equals(true)){
                        String msg = "Tidak ada data !";
                        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
                    }else{
                        if(semuastatuslaporanItemList==null){
                            String msg = "Kesalahan Mengambil Data";
                            Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
                        }else{
                            recyclerView.setAdapter(new StatusLaporanAdapter(mContext, semuastatuslaporanItemList));
                            statuslaporanAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseStatusLaporan> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
