package id.ac.telkomuniversity.dph3a4.org.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import id.ac.telkomuniversity.dph3a4.org.Adapters.BeritaAdapter;
import id.ac.telkomuniversity.dph3a4.org.ApiHelper.RetrofitClient;
import id.ac.telkomuniversity.dph3a4.org.Model.BeritaItem;
import id.ac.telkomuniversity.dph3a4.org.R;

public class LihatBeritaActivity extends AppCompatActivity {
    BeritaItem dataBerita;
    Context context;

    ImageView ivFotoLihatBerita;
    TextView tvTanggalLihatBerita, tvJudulLihatBerita, tvIsiLihatBerita;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lihat_berita);
        Bundle bundle = getIntent().getBundleExtra(BeritaAdapter.DATA_EXTRA);
        dataBerita = Parcels.unwrap(bundle.getParcelable(BeritaAdapter.DATA_BERITA));

        initComponent();

        if (!dataBerita.getGambar().equals("")){
            String img_url = RetrofitClient.IP_URL + "asset/images/" + dataBerita.getGambar();
            Glide.with(context).load(img_url).centerCrop().into(ivFotoLihatBerita);
        }

        tvJudulLihatBerita.setText(dataBerita.getJudul());
        tvIsiLihatBerita.setText(dataBerita.getIsi());

        String pattern = "EEEE, d MMMM yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, new Locale("id", "ID"));
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd H:m:s").parse(dataBerita.getTanggal());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String tanggalBerita = simpleDateFormat.format(date);
        tvTanggalLihatBerita.setText(tanggalBerita);
    }

    private void initComponent(){
        ivFotoLihatBerita = findViewById(R.id.ivFotoLihatBerita);
        tvJudulLihatBerita = findViewById(R.id.tvJudulLihatBerita);
        tvTanggalLihatBerita = findViewById(R.id.tvTanggalLihatBerita);
        tvIsiLihatBerita = findViewById(R.id.tvIsiLihatBerita);
        context = this;
    }
}
