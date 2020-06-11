package id.ac.telkomuniversity.dph3a4.org.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import id.ac.telkomuniversity.dph3a4.org.ApiHelper.RetrofitClient;
import id.ac.telkomuniversity.dph3a4.org.Model.KegiatanItem;
import id.ac.telkomuniversity.dph3a4.org.R;

public class PresensiSuccessActivity extends AppCompatActivity {

    KegiatanItem dataKegiatan;
    Context context;

    ImageView ivPosterPresensi;
    TextView tvKegiatanPresensi, tvNamaPresensi, tvTanggalPresensi;
    String waktuSubmit, nama, poster, kegiatan;
    Button btnGoHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presensi_success);
        initComponent();

        tvTanggalPresensi.setText(waktuSubmit);
        tvKegiatanPresensi.setText(kegiatan);
        tvNamaPresensi.setText(nama);
        if (!poster.equals("")){
            String img_url = RetrofitClient.IP_URL + "asset/images/" + poster;
            Glide.with(context).load(img_url).into(ivPosterPresensi);
        }

        btnGoHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindah = new Intent(context, DashboardActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(pindah);
            }
        });
    }

    private void initComponent(){
        Bundle bundle = getIntent().getBundleExtra(QrScannerActivity.DATA_EXTRA);
        dataKegiatan = Parcels.unwrap(bundle.getParcelable(QrScannerActivity.DATA_KEGIATAN_PRESENSI));

        context = this;

        ivPosterPresensi = findViewById(R.id.ivPosterPresensi);
        tvKegiatanPresensi = findViewById(R.id.tvKegiatanPresensi);
        tvNamaPresensi = findViewById(R.id.tvNamaPresensi);
        tvTanggalPresensi = findViewById(R.id.tvTanggalPresensi);
        btnGoHome = findViewById(R.id.btnGoHome);

        waktuSubmit = bundle.getString("waktuSubmit");
        kegiatan = bundle.getString("kegiatan");
        poster = bundle.getString("poster");
        nama = bundle.getString("nama");
    }

    @Override
    public void onBackPressed() {
        Intent pindah = new Intent(this, DashboardActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(pindah);
    }
}
