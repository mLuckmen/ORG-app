package id.ac.telkomuniversity.dph3a4.org.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.google.zxing.Result;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.parceler.Parcels;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import id.ac.telkomuniversity.dph3a4.org.Adapters.ResponseCekKegiatan;
import id.ac.telkomuniversity.dph3a4.org.ApiHelper.RetrofitClient;
import id.ac.telkomuniversity.dph3a4.org.Model.KegiatanItem;
import id.ac.telkomuniversity.dph3a4.org.Model.ResponseKegiatanByNama;
import id.ac.telkomuniversity.dph3a4.org.Model.ResponsePresensi;
import id.ac.telkomuniversity.dph3a4.org.R;
import id.ac.telkomuniversity.dph3a4.org.Utils.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QrScannerActivity extends AppCompatActivity {
    private ImageView bgQrScanner;
    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;
    List<KegiatanItem> dataKegiatan;
    Context context;
    ProgressDialog progressDialog;
    SharedPrefManager sharedPrefManager;
    SharedPreferences sf;
    Calendar calendar;

    public static final String DATA_KEGIATAN_PRESENSI = "dataKegiatan";
    public static final String DATA_EXTRA = "dataExtra";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scanner);

        context = this;
        progressDialog = new ProgressDialog(context);
        sharedPrefManager = new SharedPrefManager(context);
        sf = context.getSharedPreferences("OrgApp", Context.MODE_PRIVATE);
        calendar = Calendar.getInstance();

        bgQrScanner = findViewById(R.id.bgQrScanner);
        scannerView = findViewById(R.id.scannerView);

        bgQrScanner.bringToFront();

        mCodeScanner = new CodeScanner(this, scannerView);
        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        cekKegiatan(result.getText());
//                        String message = "result : \n" + result.getText() + "\n" + cekKegiatan;
//                        showAlertDialog(message);
                    }
                });
            }
        });

        checkCameraPermission();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkCameraPermission();
    }

    @Override
    protected void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    private void checkCameraPermission() {
        Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        mCodeScanner.startPreview();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                })
                .check();
    }

    private void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(false);

        builder.setPositiveButton(
                "Pindai Ulang",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        mCodeScanner.startPreview();
                    }
                });
        builder.setNegativeButton(
                "Kembali",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        QrScannerActivity.super.onBackPressed();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void cekKegiatan(String namaKegiatan){
        Call<ResponseCekKegiatan> request = RetrofitClient.getInstance().getApi().cekKegiatan(namaKegiatan);
        request.enqueue(new Callback<ResponseCekKegiatan>() {
            @Override
            public void onResponse(Call<ResponseCekKegiatan> call, Response<ResponseCekKegiatan> response) {
                String message;
                if (response.isSuccessful()){
                    if (response.body().isError()){
                        // jika tidak ada
                        message = response.body().getMessage(); // cekKegiatan ga keganti
//                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                        showAlertDialog(message);
                    } else {
                        // jika ada
                        message = response.body().getMessage(); // cekKegiatan ga keganti
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
                        progressDialog.setMessage("Harap Menunggu");
//                        progressDialog.show();
                        getKegiatanByName(namaKegiatan);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseCekKegiatan> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getKegiatanByName(String namaKegiatan){
        Call<ResponseKegiatanByNama> request = RetrofitClient.getInstance().getApi().getKegiatanByName(namaKegiatan);
        request.enqueue(new Callback<ResponseKegiatanByNama>() {
            @Override
            public void onResponse(Call<ResponseKegiatanByNama> call, Response<ResponseKegiatanByNama> response) {
                String waktuSubmit, status, nim, idKegiatan;
                if (response.isSuccessful()){
                    dataKegiatan = response.body().getKegiatan();
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    waktuSubmit = dateFormat.format(new Date());
                    status = "Hadir";
                    nim = Integer.toString(sf.getInt("nim", 0));
                    idKegiatan = dataKegiatan.get(0).getIdKegiatan();
//                    String message = waktuSubmit + "\n" + status + "\n" + nim + "\n" + idKegiatan;
//                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                    presensiKegiatan(waktuSubmit, status, nim, idKegiatan);
                } else {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseKegiatanByNama> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void presensiKegiatan(String waktuSubmit, String status, String nim, String idKegiatan){
        Call<ResponsePresensi> request = RetrofitClient.getInstance().getApi().insertPresensi(waktuSubmit, status, nim, idKegiatan);
        request.enqueue(new Callback<ResponsePresensi>() {
            @Override
            public void onResponse(Call<ResponsePresensi> call, Response<ResponsePresensi> response) {
                if (response.isSuccessful()) {
//                    Toast.makeText(context, "Sukses BOYYY", Toast.LENGTH_LONG).show();

                    Bundle bundle = new Bundle();
                    bundle.putString("waktuSubmit", waktuSubmit);
                    bundle.putString("nama", sf.getString("nama", ""));
                    bundle.putString("poster", dataKegiatan.get(0).getFoto());
                    bundle.putString("kegiatan", dataKegiatan.get(0).getNamaKegiatan());
//                    bundle.putParcelable(DATA_KEGIATAN_PRESENSI, Parcels.wrap(dataKegiatan));

                    Intent pindah = new Intent(context, PresensiSuccessActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    pindah.putExtras(bundle);
                    pindah.putExtra(DATA_EXTRA, bundle);
                    startActivity(pindah);
                }
            }

            @Override
            public void onFailure(Call<ResponsePresensi> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
