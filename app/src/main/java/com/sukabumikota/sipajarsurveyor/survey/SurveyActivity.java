package com.sukabumikota.sipajarsurveyor.survey;

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

import com.sukabumikota.sipajarsurveyor.R;
import com.sukabumikota.sipajarsurveyor.adapter.StatusLaporanAdapter;
import com.sukabumikota.sipajarsurveyor.apihelper.BaseApiService;
import com.sukabumikota.sipajarsurveyor.apihelper.koneksi;
import com.sukabumikota.sipajarsurveyor.model.ResponseStatusLaporan;
import com.sukabumikota.sipajarsurveyor.model.SemuaStatusLaporanItem;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SurveyActivity extends AppCompatActivity {
    ProgressDialog loading;
    Context mContext;
    List<SemuaStatusLaporanItem> semuastatuslaporanItemList = new ArrayList<>();
    StatusLaporanAdapter statuslaporanAdapter;
    BaseApiService mApiiService;
    RecyclerView recyclerView;
    //Buat Pencarian
    private ImageButton SearchBtn;
    private EditText SearchField;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);
        recyclerView = findViewById(R.id.rvlaporan);
        //Buat Pencarian
        SearchBtn = findViewById(R.id.search_btn);
        SearchField = findViewById(R.id.search_field);
        mContext = this;
        mApiiService = koneksi.getAPIService();
        statuslaporanAdapter = new StatusLaporanAdapter(this, semuastatuslaporanItemList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        getResultListStatusLaporan();
       /* SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = SearchField.getText().toString();
                CariData(searchText);

            }
        });*/

    }

    private void getResultListStatusLaporan() {
        loading = ProgressDialog.show(this, null, "Harap Tunggu.....", true, false);

        mApiiService.getStatus().enqueue(new Callback<ResponseStatusLaporan>() {
            @Override
            public void onResponse(Call<ResponseStatusLaporan> call, Response<ResponseStatusLaporan> response) {

                if (response.isSuccessful()) {
                    Boolean error = response.body().isError();
                    loading.dismiss();
                    semuastatuslaporanItemList = response.body().getStatuslaporan();
                    if(error.equals(true)){
                        String msg = "Tidak Ada Data";
                        Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                    }else{
                        if(semuastatuslaporanItemList==null){
                            String msg = "Tidak Ada Data";
                            Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
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
    /*private void CariData(String searchText) {
        loading = ProgressDialog.show(this, null, "Harap Tunggu.....", true, false);

        mApiiService.CariStatusLaporan(wp_id,searchText).enqueue(new Callback<ResponseStatusLaporan>() {
            @Override
            public void onResponse(Call<ResponseStatusLaporan> call, Response<ResponseStatusLaporan> response) {
                if (response.isSuccessful()) {
                    loading.dismiss();
                    final List<SemuaStatusLaporanItem> semuaStatusLaporanItems = response.body().getStatuslaporan();
                    recyclerView.setAdapter(new StatusLaporanAdapter(mContext, semuaStatusLaporanItems));


                } else {
                    loading.dismiss();
                    Toast.makeText(mContext, "Gagal mengambil data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseStatusLaporan> call, Throwable t) {
                loading.dismiss();
                Toast.makeText(mContext, "Koneksi Internet Bermasalah", Toast.LENGTH_SHORT).show();
                Intent noinet = new Intent(statuslaporandata.this, InternetDialogActivity.class);
                startActivity(noinet);
            }
        });
    }*/
    public void refresh(View view) {
        Intent i = new Intent(SurveyActivity.this, SurveyActivity.class);
        finish();
        overridePendingTransition(0, 0);
        startActivity(i);
        overridePendingTransition(0, 0);
    }
}