package com.sukabumikota.sipajarsurveyor.survey;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sukabumikota.sipajarsurveyor.MainActivity;
import com.sukabumikota.sipajarsurveyor.R;
import com.sukabumikota.sipajarsurveyor.apihelper.BaseApiService;
import com.sukabumikota.sipajarsurveyor.apihelper.koneksi;
import com.sukabumikota.sipajarsurveyor.auth.SharedPrafManager;
import com.sukabumikota.sipajarsurveyor.model.SemuaStatusLaporanItem;
import com.sukabumikota.sipajarsurveyor.objekpajak.OPActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import jp.wasabeef.glide.transformations.gpu.VignetteFilterTransformation;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class SurveyDetail extends AppCompatActivity {
    ImageView ivdetail;
    TextView meter, wp,op,labelwp,labelop,labelmeter;
    public static final String EXTRA_DATA= "extra_data";
    private EditText editmeter;
    String meteran, login_id,foto_id;
    BaseApiService mApiiService;
    ProgressDialog loading;
    Context mContext;
    SharedPrafManager sharedPrefManager; // ini
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_detail);

        meter = findViewById(R.id.txtmeter);
        wp = findViewById(R.id.txtwp);
        op = findViewById(R.id.txtop);
        ivdetail = findViewById(R.id.ivdetail);
        sharedPrefManager = new SharedPrafManager(this); // ini
        mContext = this;
        mApiiService = koneksi.getAPIService();
        labelwp = findViewById(R.id.labelwp);
        labelop = findViewById(R.id.labelop);
        labelmeter = findViewById(R.id.labelmeter);
        SemuaStatusLaporanItem semuastatuslaporanitem = getIntent().getParcelableExtra(EXTRA_DATA);


        meter.setText(semuastatuslaporanitem.getNpa()+" M3");
        wp.setText(semuastatuslaporanitem.getWp_nama());
        op.setText(semuastatuslaporanitem.getOp_nama());
        meteran = semuastatuslaporanitem.getNpa();
        foto_id = semuastatuslaporanitem.getFoto_id();
        labelwp.setText("Wajib Pajak");
        labelop.setText("Titik Sumur");
        labelmeter.setText("Meter (M3)");
        Glide.with(this)
                .load(koneksi.GAMBAR_URL
                        + "bukti/"
                        + semuastatuslaporanitem.getNama())
                .apply(bitmapTransform(new VignetteFilterTransformation(new PointF(0.5f, 0.5f),
                        new float[]{0.0f, 0.0f, 0.0f}, 0.4f, 1.0f)).dontAnimate())
                .into(ivdetail);
    }

    public void validasi(View view) {
        final Dialog dialog = new Dialog(SurveyDetail.this);
        //Memasang Title / Judul pada Custom Dialog
        dialog.setTitle("Kirim Pesan");
        //Memasang Desain Layout untuk Custom Dialog
        dialog.setContentView(R.layout.dialog_validasi);
        //Memasang Listener / Aksi saat tombol OK di Klik
        Button DialogButton = dialog.findViewById(R.id.btnkirim);
        // Dialog
        editmeter = dialog.findViewById(R.id.editmeter);
        editmeter.setText(meteran);
        DialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txmeter = editmeter.getText().toString();
                requestSimpanData(txmeter);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void requestSimpanData(String txtmeter){
        loading = ProgressDialog.show(this, null,
                "Harap Tunggu...", true, false);
        login_id = sharedPrefManager.getSPId();
        mApiiService.terimaLaporan(foto_id, txtmeter, login_id)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getString("error").equals("false")){

                                    Toast.makeText(mContext, "Sukses", Toast.LENGTH_SHORT).show();
                                    Intent redirect = new Intent(SurveyDetail.this, MainActivity.class);
                                    startActivity(redirect);
                                    finish();
                                } else {
                                    // jika login gagal
                                    String error_message = jsonRESULTS.getString("error_msg");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });
    }

    public void decline(View view) {
        SemuaStatusLaporanItem semuastatuslaporanitem = getIntent().getParcelableExtra(EXTRA_DATA);

        String parsingop = semuastatuslaporanitem.getOp_nama();
        String parsingmeteran = semuastatuslaporanitem.getNpa();
        String parsingfoto_id = semuastatuslaporanitem.getFoto_id();

        Intent intent = new Intent(SurveyDetail.this, UploadActivity.class);
        intent.putExtra("foto_id",parsingfoto_id);
        intent.putExtra("nama_op",parsingop);
        intent.putExtra("meter",parsingmeteran);
        startActivity(intent);
    }
}
