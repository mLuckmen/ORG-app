package id.ac.telkomuniversity.dph3a4.org.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import id.ac.telkomuniversity.dph3a4.org.Adapters.HintAdapter;
import id.ac.telkomuniversity.dph3a4.org.Adapters.KegiatanAdapter;
import id.ac.telkomuniversity.dph3a4.org.Model.KegiatanItem;
import id.ac.telkomuniversity.dph3a4.org.R;

public class DaftarKegiatanActivity extends AppCompatActivity {

    Spinner spinnerPembayaran, spinnerJumlah;
    String[] pilihanPembayaran = {"Transfer", "Cash", "Pilih Metode Pembayaran"};
    String[] pilihanJumlahTiket = {"1 Orang", "2 Orang", "Pilih Jumlah tiket"};

    KegiatanItem dataKegiatan;
    TextView namaKegiatan, tanggalPelaksanaan, tempatPelaksanaan, harga;
    ImageView posterKegiatan;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_kegiatan);

        Bundle bundle = getIntent().getBundleExtra(KegiatanAdapter.DATA_EXTRA);
        dataKegiatan = Parcels.unwrap(bundle.getParcelable(KegiatanAdapter.DATA_KEGIATAN));

        initComponents();
        bindData();
        setSpinnerForm();


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
        spinnerPembayaran = findViewById(R.id.spinnerPembayaran);
        spinnerJumlah = findViewById(R.id.spinnerJumlah);

    }
}
