package com.sukabumikota.sipajarsurveyor.objekpajak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.sukabumikota.sipajarsurveyor.MainActivity;
import com.sukabumikota.sipajarsurveyor.R;
import com.sukabumikota.sipajarsurveyor.adapter.ObjekPajakAdapter;
import com.sukabumikota.sipajarsurveyor.apihelper.BaseApiService;
import com.sukabumikota.sipajarsurveyor.apihelper.koneksi;
import com.sukabumikota.sipajarsurveyor.model.ResponseObjekPajak;
import com.sukabumikota.sipajarsurveyor.model.SemuaObjekPajakItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OPActivity extends AppCompatActivity {
    ProgressDialog loading;
    Context mContext;
    List<SemuaObjekPajakItem> semuaobjekpajakItemList = new ArrayList<>();
    ObjekPajakAdapter objekpajakAdapter;
    BaseApiService mApiiService;
    RecyclerView recyclerView;
    //Buat Pencarian
    private ImageButton SearchBtn;
    private EditText SearchField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_o_p);
        recyclerView = findViewById(R.id.rvop);
        //Buat Pencarian
        SearchBtn = findViewById(R.id.search_btn);
        SearchField = findViewById(R.id.search_field);
        mContext = this;
        mApiiService = koneksi.getAPIService();
        objekpajakAdapter = new ObjekPajakAdapter(this, semuaobjekpajakItemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getResultListObjekPajak();
         SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = SearchField.getText().toString();
                CariData(searchText);

            }
        });

    }

    private void getResultListObjekPajak() {
        loading = ProgressDialog.show(this, null, "Harap Tunggu.....", true, false);

        mApiiService.getObjekPajak().enqueue(new Callback<ResponseObjekPajak>() {
            @Override
            public void onResponse(Call<ResponseObjekPajak> call, Response<ResponseObjekPajak> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    Boolean error = response.body().isError();
                    semuaobjekpajakItemList = response.body().getObjekpajak();
                    if(error.equals(true)){
                        String msg = "Tidak ada data !";
                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                    }else{
                        if(semuaobjekpajakItemList==null){
                            String msg = "Kesalahan Mengambil Data";
                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                        }else{
                            recyclerView.setAdapter(new ObjekPajakAdapter(mContext, semuaobjekpajakItemList));
                            objekpajakAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseObjekPajak> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void CariData(String searchText) {
        loading = ProgressDialog.show(this, null, "Harap Tunggu.....", true, false);

        mApiiService.cariObjekPajak(searchText).enqueue(new Callback<ResponseObjekPajak>() {
            @Override
            public void onResponse(Call<ResponseObjekPajak> call, Response<ResponseObjekPajak> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    Boolean error = response.body().isError();
                    semuaobjekpajakItemList = response.body().getObjekpajak();
                    if(error.equals(true)){
                        String msg = "Tidak ada data !";
                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                    }else{
                        if(semuaobjekpajakItemList==null){
                            String msg = "Kesalahan Mengambil Data";
                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                        }else{
                            recyclerView.setAdapter(new ObjekPajakAdapter(mContext, semuaobjekpajakItemList));
                            objekpajakAdapter.notifyDataSetChanged();
                        }
                    }
                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseObjekPajak> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void refresh(View view) {
        Intent i = new Intent(OPActivity.this, OPActivity.class);
        finish();
        overridePendingTransition(0, 0);
        startActivity(i);
        overridePendingTransition(0, 0);
    }

    public void onBackClick(View view) {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        finish();
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.stay);
        finish();
    }
}