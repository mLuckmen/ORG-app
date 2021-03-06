package id.ac.telkomuniversity.dph3a4.org.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import id.ac.telkomuniversity.dph3a4.org.Adapters.HintAdapter;
import id.ac.telkomuniversity.dph3a4.org.Adapters.KegiatanAdapter;
import id.ac.telkomuniversity.dph3a4.org.ApiHelper.RetrofitClient;
import id.ac.telkomuniversity.dph3a4.org.Model.KegiatanItem;
import id.ac.telkomuniversity.dph3a4.org.Model.ResponseCekTiket;
import id.ac.telkomuniversity.dph3a4.org.Model.ResponseJumlahTiket;
import id.ac.telkomuniversity.dph3a4.org.Model.ResponsePesanTiket;
import id.ac.telkomuniversity.dph3a4.org.R;
import id.ac.telkomuniversity.dph3a4.org.Utils.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarKegiatanActivity extends AppCompatActivity {

    Spinner spinnerPembayaran, spinnerJumlah;
    String[] pilihanPembayaran = {"Transfer", "Cash", "Pilih metode pembayaran"};
    String[] pilihanJumlahTiket = {"1 Orang", "2 Orang", "Pilih jumlah tiket"};

    KegiatanItem dataKegiatan;
    TextView namaKegiatan, tanggalPelaksanaan, tempatPelaksanaan, harga, jumlahTiket;

    String nama, nim, nimAkun, jurusan, email, total, metode_pembayaran, status, id_kegiatan;
    EditText namaPendaftar, nimPendaftar, jurusanPendaftar, emailPendaftar;
    Button btnPesan, btnBaru;

    ImageView posterKegiatan;
    Context context;
    SharedPrefManager sharedPrefManager;
    SharedPreferences sf;

    Dialog myDialog;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kegiatan);

        Bundle bundle = getIntent().getBundleExtra(KegiatanAdapter.DATA_EXTRA);
        dataKegiatan = Parcels.unwrap(bundle.getParcelable(KegiatanAdapter.DATA_KEGIATAN));

        initComponents();
        bindData();
        setSpinnerForm();
        jumlahTiket();

        btnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPesanAction();
            }
        });

    }

    public void bindData() {
        String pattern = "EEEE, d MMMM yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("id", "ID"));
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dataKegiatan.getWaktu());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String tanggalKegiatan = simpleDateFormat.format(date);
        tanggalPelaksanaan.setText(tanggalKegiatan);

        namaKegiatan.setText(dataKegiatan.getNamaKegiatan());
        tempatPelaksanaan.setText(dataKegiatan.getTempat());
        harga.setText("Harga Tiket : Rp." + dataKegiatan.getHarga());

        if (!dataKegiatan.getFoto().equals("")){
            String img_url = RetrofitClient.IP_URL + "asset/images/" + dataKegiatan.getFoto();
            Glide.with(context).load(img_url).into(posterKegiatan);
        }
    }

    public void setSpinnerForm() {
        HintAdapter hintPembayaran = new HintAdapter(this, android.R.layout.simple_spinner_dropdown_item, pilihanPembayaran);
        spinnerPembayaran.setAdapter(hintPembayaran);
        spinnerPembayaran.setSelection(hintPembayaran.getCount());

//        HintAdapter hintJumlah = new HintAdapter(this, android.R.layout.simple_spinner_dropdown_item, pilihanJumlahTiket);
//        spinnerJumlah.setAdapter(hintJumlah);
//        spinnerJumlah.setSelection(hintPembayaran.getCount());
    }

    public void initComponents() {
        context = this;

        // detail Kegiatan
        posterKegiatan = findViewById(R.id.ivPosterKegiatan2);
        namaKegiatan = findViewById(R.id.tvNamaKegiatan2);
        tanggalPelaksanaan = findViewById(R.id.tvTanggalPelaksanaan2);
        tempatPelaksanaan = findViewById(R.id.tvTempatPelaksanaan2);
        harga = findViewById(R.id.tvHargaTiket2);
        jumlahTiket = findViewById(R.id.jumlahTiket);

        // Formulir
        namaPendaftar = findViewById(R.id.namaPendaftar);
        nimPendaftar = findViewById(R.id.nimPendaftar);
        jurusanPendaftar = findViewById(R.id.jurusanPendaftar);
        emailPendaftar = findViewById(R.id.emailPendaftar);
        spinnerPembayaran = findViewById(R.id.spinnerPembayaran);
//        spinnerJumlah = findViewById(R.id.spinnerJumlah);
        btnPesan = findViewById(R.id.btnPesan2);
        btnBaru = findViewById(R.id.btnBaru);
        btnBaru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindah = new Intent(context, DaftarKegiatanBaru.class);
                startActivity(pindah);
            }
        });

        sharedPrefManager = new SharedPrefManager(context);
        sf = context.getSharedPreferences("OrgApp", Context.MODE_PRIVATE);

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading");

        myDialog = new Dialog(this);
    }

    private void submitPesanTiket() {
        Call<ResponsePesanTiket> request = RetrofitClient.getInstance().getApi().daftarKegiatan(nama, nimAkun, nim, jurusan, email, total, metode_pembayaran, status, id_kegiatan);

        request.enqueue(new Callback<ResponsePesanTiket>() {
            @Override
            public void onResponse(Call<ResponsePesanTiket> call, Response<ResponsePesanTiket> response) {
                if (response.isSuccessful()){
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    Intent pindah = new Intent(context, TiketSuccessActivity.class)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(pindah);
                } else {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePesanTiket> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void btnPesanAction() {
        nama = namaPendaftar.getText().toString();
        nimAkun = Integer.toString(sf.getInt("nim", 0));
        nim = nimPendaftar.getText().toString();
        jurusan = jurusanPendaftar.getText().toString();
        email = emailPendaftar.getText().toString();
        status = "Menunggu";
        id_kegiatan = dataKegiatan.getIdKegiatan();
        total = dataKegiatan.getHarga();

//        jumlah = spinnerJumlah.getSelectedItem().toString();
//        if (jumlah.equals("1 Orang")) {
//            total = dataKegiatan.getHarga();
//            jumlah = "1";
//        } else if (jumlah.equals("2 Orang")) {
//            total = Integer.toString(2*Integer.parseInt(dataKegiatan.getHarga()));
//            jumlah = "2";
//        }
        metode_pembayaran = spinnerPembayaran.getSelectedItem().toString();

        formValidation();
    }

    public void formValidation() {

        if (TextUtils.isEmpty(emailPendaftar.getText()) ||
                TextUtils.isEmpty(jurusanPendaftar.getText()) ||
                TextUtils.isEmpty(nimPendaftar.getText()) ||
                TextUtils.isEmpty(namaPendaftar.getText()) ) {

            if (TextUtils.isEmpty(emailPendaftar.getText())){
                emailPendaftar.setError("Email tidak boleh kosong");
                emailPendaftar.requestFocus();
            }
            if (TextUtils.isEmpty(jurusanPendaftar.getText())){
                jurusanPendaftar.setError("Jurusan tidak boleh kosong");
                jurusanPendaftar.requestFocus();
            }
            if (TextUtils.isEmpty(nimPendaftar.getText())){
                nimPendaftar.setError("NIM lengkap tidak boleh kosong");
                nimPendaftar.requestFocus();
            }
            if (TextUtils.isEmpty(namaPendaftar.getText())){
                namaPendaftar.setError("Nama lengkap tidak boleh kosong");
                namaPendaftar.requestFocus();
            }
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailPendaftar.setError("Format Email belum sesuai");
            emailPendaftar.requestFocus();
        } else {
            showConfirmationDialog();
        }

    }

    private void cekTiket(String nim, String id_kegiatan){
        Call<ResponseCekTiket> request = RetrofitClient.getInstance().getApi().cekTiket(nim, id_kegiatan);
        request.enqueue(new Callback<ResponseCekTiket>() {
            @Override
            public void onResponse(Call<ResponseCekTiket> call, Response<ResponseCekTiket> response) {
                progressDialog.dismiss();
                Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                if (response.isSuccessful()){
                    if (!response.body().isError()){
//                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                        submitPesanTiket();
                    } else {
                        myDialog.dismiss();
                        showAlertDialog(response.body().getMessage());
//                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseCekTiket> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showConfirmationDialog() {
        ImageView close;
        Button konfirmasi, batal;
        TextView id2, kegiatan2, nama2, nim2, jurusan2, email2, jumlahTiket2, metodePembayaran2, total2;

        myDialog.setContentView(R.layout.layout_popup_detail_tiket);

        id2 = myDialog.findViewById(R.id.tvDetailId);
        kegiatan2 = myDialog.findViewById(R.id.tvDetailNamaKegiatan);
        nama2 = myDialog.findViewById(R.id.tvDetailNama);
        nim2 = myDialog.findViewById(R.id.tvDetailNim);
        jurusan2 = myDialog.findViewById(R.id.tvDetailJurusan);
//        email2 = myDialog.findViewById(R.id.tvDetailEmail);
//        jumlahTiket2 = myDialog.findViewById(R.id.tvDetailJumlahTiket);
        metodePembayaran2 = myDialog.findViewById(R.id.tvDetailMetodePembayaran);
        total2 = myDialog.findViewById(R.id.tvDetailTotal);

        id2.setText(dataKegiatan.getIdKegiatan());
        kegiatan2.setText(dataKegiatan.getNamaKegiatan());
        nama2.setText(nama);
        nim2.setText(nim);
        jurusan2.setText(jurusan);
//        email2.setText(email);
//        jumlahTiket2.setText(jumlah);
        metodePembayaran2.setText(metode_pembayaran);
        total2.setText("Rp. " + total);

        close = myDialog.findViewById(R.id.closeDetail);
        konfirmasi = myDialog.findViewById(R.id.btnKonfirmasiTiket);
        batal = myDialog.findViewById(R.id.btnBatalTiket);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        batal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myDialog.dismiss();
            }
        });
        konfirmasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                cekTiket(nim, dataKegiatan.getIdKegiatan());
            }
        });

        myDialog.show();
    }

    private void showAlertDialog(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setCancelable(false);

        builder.setPositiveButton(
                "Ubah data",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                }
            );

        builder.setNegativeButton(
                "Batal",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                        DaftarKegiatanActivity.super.onBackPressed();
                    }
                }
            );

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void jumlahTiket(){
        Call<ResponseJumlahTiket> request = RetrofitClient.getInstance().getApi().getJumlahTiket(dataKegiatan.getIdKegiatan());
        request.enqueue(new Callback<ResponseJumlahTiket>() {
            @Override
            public void onResponse(Call<ResponseJumlahTiket> call, Response<ResponseJumlahTiket> response) {
                if (response.isSuccessful()){
                    String message = response.body().getMessage() + " Tiket terjual saat ini";
                    jumlahTiket.setText(message);
                }
            }

            @Override
            public void onFailure(Call<ResponseJumlahTiket> call, Throwable t) {

            }
        });
    }

}
