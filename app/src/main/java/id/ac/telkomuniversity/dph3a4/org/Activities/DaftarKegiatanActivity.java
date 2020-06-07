package id.ac.telkomuniversity.dph3a4.org.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import id.ac.telkomuniversity.dph3a4.org.Adapters.HintAdapter;
import id.ac.telkomuniversity.dph3a4.org.Adapters.KegiatanAdapter;
import id.ac.telkomuniversity.dph3a4.org.Adapters.RequiredSpinnerAdapter;
import id.ac.telkomuniversity.dph3a4.org.Model.KegiatanItem;
import id.ac.telkomuniversity.dph3a4.org.R;

public class DaftarKegiatanActivity extends AppCompatActivity {

    Spinner spinnerPembayaran, spinnerJumlah;
    String[] pilihanPembayaran = {"Transfer", "Cash", "Pilih metode pembayaran"};
    String[] pilihanJumlahTiket = {"1 Orang", "2 Orang", "Pilih jumlah tiket"};

    KegiatanItem dataKegiatan;
    TextView namaKegiatan, tanggalPelaksanaan, tempatPelaksanaan, harga;

    String nama, nim, jurusan, email, jumlah, total, metode_pembayaran, status, id_kegiatan;
    EditText namaPendaftar, nimPendaftar, jurusanPendaftar, emailPendaftar;
    Button btnPesan;

    ImageView posterKegiatan;
    Context context;

    Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kegiatan);

        Bundle bundle = getIntent().getBundleExtra(KegiatanAdapter.DATA_EXTRA);
        dataKegiatan = Parcels.unwrap(bundle.getParcelable(KegiatanAdapter.DATA_KEGIATAN));

        initComponents();
        bindData();
        setSpinnerForm();

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

        String img_url = "http://10.0.2.2/pa/asset/images/ormawa/" + dataKegiatan.getFoto();
//        Glide.with(context).load(img_url).into(posterKegiatan);
    }

    public void setSpinnerForm() {
        HintAdapter hintPembayaran = new HintAdapter(this, android.R.layout.simple_spinner_dropdown_item, pilihanPembayaran);
        spinnerPembayaran.setAdapter(hintPembayaran);
        spinnerPembayaran.setSelection(hintPembayaran.getCount());

        HintAdapter hintJumlah = new HintAdapter(this, android.R.layout.simple_spinner_dropdown_item, pilihanJumlahTiket);
        spinnerJumlah.setAdapter(hintJumlah);
        spinnerJumlah.setSelection(hintPembayaran.getCount());
    }

    public void initComponents() {
        context = this;

        // detail Kegiatan
        posterKegiatan = findViewById(R.id.ivPosterKegiatan2);
        namaKegiatan = findViewById(R.id.tvNamaKegiatan2);
        tanggalPelaksanaan = findViewById(R.id.tvTanggalPelaksanaan2);
        tempatPelaksanaan = findViewById(R.id.tvTempatPelaksanaan2);
        harga = findViewById(R.id.tvHargaTiket2);

        // Formulir
        namaPendaftar = findViewById(R.id.namaPendaftar);
        nimPendaftar = findViewById(R.id.nimPendaftar);
        jurusanPendaftar = findViewById(R.id.jurusanPendaftar);
        emailPendaftar = findViewById(R.id.emailPendaftar);
        spinnerPembayaran = findViewById(R.id.spinnerPembayaran);
        spinnerJumlah = findViewById(R.id.spinnerJumlah);
        btnPesan = findViewById(R.id.btnPesan2);

        myDialog = new Dialog(this);
    }

    private void daftarKegiatan() {


//        Call<ResponsePesanTiket> request = RetrofitClient.getInstance().getApi().daftarKegiatan()
    }

    private void btnPesanAction() {
        nama = namaPendaftar.getText().toString().trim();
        nim = nimPendaftar.getText().toString().trim();
        jurusan = jurusanPendaftar.getText().toString().trim();
        email = emailPendaftar.getText().toString().trim();
        status = "Menunggu";
        id_kegiatan = dataKegiatan.getIdKegiatan();

        spinnerJumlah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinnerJumlah.getSelectedItem().toString() == "1 Orang"){
                    jumlah = "1";
                    total = dataKegiatan.getHarga();
                } else if (spinnerJumlah.getSelectedItem().toString() == "2 Orang"){
                    jumlah = "2";
                    total = Integer.toString(2*Integer.parseInt(dataKegiatan.getHarga()));
                } else if (spinnerJumlah.getSelectedItem().toString().equals("Pilih jumlah tiket")){
                    Toast.makeText(DaftarKegiatanActivity.this, "Silahkan pilih jumlah tiket!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                RequiredSpinnerAdapter adapter = (RequiredSpinnerAdapter) spinnerJumlah.getAdapter();
                View view1 = spinnerJumlah.getSelectedView();
                adapter.setError(view1, "Silahkan pilih jumlah tiket!");
            }
        });

        spinnerPembayaran.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (spinnerPembayaran.getSelectedItem().toString() != "Pilih metode pembayaran") {
                    metode_pembayaran = spinnerPembayaran.getSelectedItem().toString();
                } else if (spinnerPembayaran.getSelectedItem().toString().equals("Pilih metode pembayaran")){
                    Toast.makeText(DaftarKegiatanActivity.this, "Silahkan pilih metode pembayaran!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                RequiredSpinnerAdapter adapter = (RequiredSpinnerAdapter) spinnerPembayaran.getAdapter();
                View view1 = spinnerPembayaran.getSelectedView();
                adapter.setError(view1, "Silahkan pilih metode pembayaran!");


            }
        });
        formValidation();
//            String nama, nim, jurusan, email, jumlah, total, metode_pembayaran, status, id_kegiatan;
    }

    public void formValidation() {
        if (email.isEmpty()){
            emailPendaftar.setError("Email tidak boleh kosong");
            emailPendaftar.requestFocus();
        }
        if (jurusan.isEmpty()){
            jurusanPendaftar.setError("Jurusan tidak boleh kosong");
            jurusanPendaftar.requestFocus();
        }
        if (nim.isEmpty()){
            nimPendaftar.setError("NIM lengkap tidak boleh kosong");
            nimPendaftar.requestFocus();
        }
        if (nama.isEmpty()){
            namaPendaftar.setError("Nama lengkap tidak boleh kosong");
            namaPendaftar.requestFocus();
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailPendaftar.setError("Format Email belum sesuai");
            emailPendaftar.requestFocus();
        }

        if (!nama.equals("") && !nim.equals("") && !jurusan.equals("") && !email.equals("") && !jumlah.equals("") && !total.equals("") && !metode_pembayaran.equals("") && !status.equals("") && !id_kegiatan.equals("")) {
            showConfirmationDialog();
        }
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
        email2 = myDialog.findViewById(R.id.tvDetailEmail);
        jumlahTiket2 = myDialog.findViewById(R.id.tvDetailJumlahTiket);
        metodePembayaran2 = myDialog.findViewById(R.id.tvDetailMetodePembayaran);
        total2 = myDialog.findViewById(R.id.tvDetailTotal);

        id2.setText(dataKegiatan.getIdKegiatan());
        kegiatan2.setText(dataKegiatan.getNamaKegiatan());
        nama2.setText(nama);
        nim2.setText(nim);
        jurusan2.setText(jurusan);
        email2.setText(email);
        jumlahTiket2.setText(jumlah);
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
                /// method upload
            }
        });

        myDialog.show();
    }

}
