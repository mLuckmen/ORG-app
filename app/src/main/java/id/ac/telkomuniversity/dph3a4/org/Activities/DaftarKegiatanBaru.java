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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import id.ac.telkomuniversity.dph3a4.org.Adapters.HintAdapter;
import id.ac.telkomuniversity.dph3a4.org.Adapters.KegiatanAdapter;
import id.ac.telkomuniversity.dph3a4.org.ApiHelper.RetrofitClient;
import id.ac.telkomuniversity.dph3a4.org.Model.KegiatanItem;
import id.ac.telkomuniversity.dph3a4.org.Model.MahasiswaItem;
import id.ac.telkomuniversity.dph3a4.org.Model.ResponseCekTiket;
import id.ac.telkomuniversity.dph3a4.org.Model.ResponseJumlahTiket;
import id.ac.telkomuniversity.dph3a4.org.Model.ResponseMahasiswa;
import id.ac.telkomuniversity.dph3a4.org.Model.ResponsePesanTiket;
import id.ac.telkomuniversity.dph3a4.org.R;
import id.ac.telkomuniversity.dph3a4.org.Utils.SharedPrefManager;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DaftarKegiatanBaru extends AppCompatActivity {
    List<MahasiswaItem> dataMahasiswa = new ArrayList<>();
    List<String> listSpinner = new ArrayList<String>();

    Spinner spinnerPembayaran;
    String[] pilihanPembayaran = {"Transfer", "Cash", "Pilih metode pembayaran"};

    KegiatanItem dataKegiatan;
    EditText cari;
    SpinnerDialog spinnerDialog;
    Context context;

    ProgressDialog progressDialog;
    SharedPrefManager sharedPrefManager;
    SharedPreferences sf;
    Dialog myDialog;

    TextView harga, namaKegiatan, tempatPelaksanaan,waktuPelaksanaan,jumlahTiket;
    String nama, nim, nimAkun, jurusan, email, total, metode_pembayaran, status, id_kegiatan;
    ImageView posterKegiatan;
    Button btnPesan;

//    LinearLayout emailContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kegiatan_baru);

        Bundle bundle = getIntent().getBundleExtra(KegiatanAdapter.DATA_EXTRA);
        dataKegiatan = Parcels.unwrap(bundle.getParcelable(KegiatanAdapter.DATA_KEGIATAN));

        initComponent();
        bindData();
        getMahasiswa();
        jumlahTiket();
        progressDialog.show();

        btnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnPesanAction();
            }
        });

    }

    public void initComponent(){
        context = this;

        sharedPrefManager = new SharedPrefManager(context);
        sf = context.getSharedPreferences("OrgApp", Context.MODE_PRIVATE);

        cari = findViewById(R.id.btnSearch);
        cari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                spinnerDialog.showSpinerDialog();
            }
        });

        harga = findViewById(R.id.newHarga);
        posterKegiatan = findViewById(R.id.newPoster);
        namaKegiatan = findViewById(R.id.newNamaKegiatan);
        tempatPelaksanaan = findViewById(R.id.newTempat);
        waktuPelaksanaan = findViewById(R.id.newWaktu);
        jumlahTiket = findViewById(R.id.newJumlah);
        spinnerPembayaran = findViewById(R.id.spinnerPembayaran);
        btnPesan = findViewById(R.id.newBtnPesan);
        myDialog = new Dialog(this);

//        emailContainer = findViewById(R.id.emailContainer);

        spinnerDialog = new SpinnerDialog(DaftarKegiatanBaru.this, (ArrayList<String>) listSpinner, "Select Item");
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
//                Toast.makeText(context, dataMahasiswa.get(position).getNama(), Toast.LENGTH_SHORT).show();
                cari.setText(dataMahasiswa.get(position).getNama());
                cari.setError(null);

                nama = dataMahasiswa.get(position).getNama();
                nimAkun = Integer.toString(sf.getInt("nim", 0));
                nim = dataMahasiswa.get(position).getNim();
                jurusan = dataMahasiswa.get(position).getProdi();
                email = "";
                status = "Menunggu";
                id_kegiatan = dataKegiatan.getIdKegiatan();
                total = dataKegiatan.getHarga();
            }
        });

        HintAdapter hintPembayaran = new HintAdapter(this, android.R.layout.simple_spinner_dropdown_item, pilihanPembayaran);
        spinnerPembayaran.setAdapter(hintPembayaran);
        spinnerPembayaran.setSelection(hintPembayaran.getCount());

        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading");
    }

    public void btnPesanAction() {

        metode_pembayaran = spinnerPembayaran.getSelectedItem().toString();

        if (TextUtils.isEmpty(cari.getText()) || metode_pembayaran.equals("Pilih metode pembayaran")) {
            if (TextUtils.isEmpty(cari.getText())) {
                cari.setError("Pilih mahasiswa untuk memesan tiket");
                cari.requestFocus();
            }
            if (metode_pembayaran.equals("Pilih metode pembayaran")){
                ((TextView)spinnerPembayaran.getSelectedView()).setError("Error message");
            }
        } else {
            showConfirmationDialog();
        }

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
        waktuPelaksanaan.setText(tanggalKegiatan);

        namaKegiatan.setText(dataKegiatan.getNamaKegiatan());
        tempatPelaksanaan.setText(dataKegiatan.getTempat());
        if (dataKegiatan.getHarga().equals("Free")){
            harga.setText(dataKegiatan.getHarga());
        } else {
            harga.setText("Rp." + dataKegiatan.getHarga());
        }

        if (!dataKegiatan.getFoto().equals("")){
            String img_url = RetrofitClient.IP_URL + "asset/images/" + dataKegiatan.getFoto();
            Glide.with(context).load(img_url).into(posterKegiatan);
        }
    }

    public void getMahasiswa(){
        Call<ResponseMahasiswa> request = RetrofitClient.getInstance().getApi().getMahasiswa();
        request.enqueue(new Callback<ResponseMahasiswa>() {
            @Override
            public void onResponse(Call<ResponseMahasiswa> call, Response<ResponseMahasiswa> response) {
                if(response.isSuccessful()){
                    progressDialog.dismiss();
                    dataMahasiswa = response.body().getMahasiswa();
                    for (int i = 0; i < dataMahasiswa.size(); i++){
                        listSpinner.add(dataMahasiswa.get(i).getNama());
                    }
                } else {
                    Toast.makeText(context, "Gagal mengambil data mahasiswa", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseMahasiswa> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
                Toast.makeText(context, t.toString(), Toast.LENGTH_LONG).show();
            }
        });
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

    public void showConfirmationDialog() {
        ImageView close;
        Button konfirmasi, batal;
        TextView id2, kegiatan2, nama2, nim2, jurusan2, email2, jumlahTiket2, metodePembayaran2, total2;

        myDialog.setContentView(R.layout.layout_popup_detail_tiket);
//        emailContainer.setVisibility(View.GONE);

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
                        DaftarKegiatanBaru.super.onBackPressed();
                    }
                }
        );

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
