package id.ac.telkomuniversity.dph3a4.org.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import id.ac.telkomuniversity.dph3a4.org.Adapters.AnggotaAdapter;
import id.ac.telkomuniversity.dph3a4.org.Adapters.PengurusAdapter;
import id.ac.telkomuniversity.dph3a4.org.ApiHelper.RetrofitClient;
import id.ac.telkomuniversity.dph3a4.org.Model.AnggotaItem;
import id.ac.telkomuniversity.dph3a4.org.Model.PengurusItem;
import id.ac.telkomuniversity.dph3a4.org.Model.ResponseAnggota;
import id.ac.telkomuniversity.dph3a4.org.Model.ResponsePengurus;
import id.ac.telkomuniversity.dph3a4.org.R;
import id.ac.telkomuniversity.dph3a4.org.Utils.CustomListView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailOrganisasiActivity extends AppCompatActivity {

    ArrayList<PengurusItem> dataPengurus = new ArrayList<>();
    ArrayList<AnggotaItem> dataAnggota = new ArrayList<>();
    CustomListView listViewPengurus, listViewAnggota;
    String idOrganisasi, namaOrg, logoText;
    Context context;
    ImageView ivLogo;
    TextView tvOrg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_organisasi);
        context = this;

        initComponents();

        idOrganisasi = (String) getIntent().getExtras().get("idOrganisasi");
        namaOrg = (String) getIntent().getExtras().get("namaOrg");
        logoText = (String) getIntent().getExtras().get("logo");
        Log.i("INFO_", "org : " + namaOrg + " | logo : " + logoText);
        String img_url = "http://10.0.2.2/pa/asset/images/ormawa/" + logoText;

        tvOrg.setText(namaOrg);
        Glide.with(context).load(img_url).into(ivLogo);

        getDataPengurus();
        getDataAnggota();
    }

    private void getDataPengurus(){
        Call<ResponsePengurus> request = RetrofitClient.getInstance().getApi().getPengurus(idOrganisasi);
        request.enqueue(new Callback<ResponsePengurus>() {
            @Override
            public void onResponse(Call<ResponsePengurus> call, Response<ResponsePengurus> response) {
                if (response.isSuccessful()){
                    dataPengurus = (ArrayList<PengurusItem>) response.body().getPengurus();
                    listViewPengurus.setAdapter(new PengurusAdapter(dataPengurus, context));
                }else {
                    Toast.makeText(context, "Request not success", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePengurus> call, Throwable t) {
                Toast.makeText(context, "Request failure", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getDataAnggota(){
        Call<ResponseAnggota> request = RetrofitClient.getInstance().getApi().getAnggota(idOrganisasi);
        request.enqueue(new Callback<ResponseAnggota>() {
            @Override
            public void onResponse(Call<ResponseAnggota> call, Response<ResponseAnggota> response) {
                if (response.isSuccessful()){
                    dataAnggota = (ArrayList<AnggotaItem>) response.body().getAnggota();
                    listViewAnggota.setAdapter(new AnggotaAdapter(dataAnggota, context));
                } else {
                    Toast.makeText(context, "Request not success", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseAnggota> call, Throwable t) {
                Toast.makeText(context, "Request failure", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void initComponents() {
        listViewPengurus = findViewById(R.id.listPengurus);
        listViewAnggota = findViewById(R.id.listAnggota);
        ivLogo = findViewById(R.id.logo);
        tvOrg = findViewById(R.id.namaOrg);
    }
}
