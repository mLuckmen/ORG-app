package id.ac.telkomuniversity.dph3a4.org.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.parceler.Parcels;

import id.ac.telkomuniversity.dph3a4.org.Adapters.OrganisationAdapter;
import id.ac.telkomuniversity.dph3a4.org.ApiHelper.RetrofitClient;
import id.ac.telkomuniversity.dph3a4.org.Model.OrganisationItem;
import id.ac.telkomuniversity.dph3a4.org.R;

public class OrganisationActivity extends AppCompatActivity {

    OrganisationItem dataOrganisasi;
    TextView NamaOrganisasi, Deskripsi, Ketua;
    LinearLayout jumlah;
    ImageView logo;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organisation);

        Bundle bundle = getIntent().getBundleExtra(OrganisationAdapter.DATA_EXTRA);
        dataOrganisasi = Parcels.unwrap(bundle.getParcelable(OrganisationAdapter.DATA_ORGANISASI));

        initComponents();

        NamaOrganisasi.setText(dataOrganisasi.getNamaOrganisasi());
        Deskripsi.setText(dataOrganisasi.getDeskripsi());
        Ketua.setText(dataOrganisasi.getKetua());
        String img_url = RetrofitClient.IP_URL  + "asset/images/ormawa/" + dataOrganisasi.getLogo();
//        String img_url = "http://192.168.1.11/pa/asset/images/ormawa/" + dataOrganisasi.getLogo();
        Glide.with(context).load(img_url).into(logo);

        jumlah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindah = new Intent(context, DetailOrganisasiActivity.class);
                String idOrganisasi = dataOrganisasi.getIdOrganisasi();
                pindah.putExtra("idOrganisasi", idOrganisasi);
                pindah.putExtra("namaOrg", dataOrganisasi.getNamaOrganisasi());
                pindah.putExtra("logo", dataOrganisasi.getLogo());
                startActivity(pindah);
            }
        });

    }

    public void initComponents(){
        context = this;
        NamaOrganisasi = findViewById(R.id.tvNamaOrganisasi);
        Deskripsi = findViewById(R.id.tvDeskripsi);
        Ketua = findViewById(R.id.tvNamaKetua);
        logo = findViewById(R.id.logo);
        jumlah = findViewById(R.id.jumlahAnggota);
    }
}
