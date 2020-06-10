package id.ac.telkomuniversity.dph3a4.org.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
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

import id.ac.telkomuniversity.dph3a4.org.Adapters.ResponseCekKegiatan;
import id.ac.telkomuniversity.dph3a4.org.ApiHelper.RetrofitClient;
import id.ac.telkomuniversity.dph3a4.org.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class QrScannerActivity extends AppCompatActivity {
    private ImageView bgQrScanner;
    private CodeScanner mCodeScanner;
    private CodeScannerView scannerView;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scanner);

        context = this;

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
                String cekKegiatan;
                if (response.isSuccessful()){
                    if (response.body().isError()){
                        // jika tidak ada
                        cekKegiatan = response.body().getMessage(); // cekKegiatan ga keganti
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        showAlertDialog(response.body().getMessage());
                    } else {
                        // jika ada
                        cekKegiatan = response.body().getMessage(); // cekKegiatan ga keganti
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
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
}
