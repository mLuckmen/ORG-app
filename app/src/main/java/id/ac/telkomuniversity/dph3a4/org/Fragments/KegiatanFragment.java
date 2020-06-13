package id.ac.telkomuniversity.dph3a4.org.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import id.ac.telkomuniversity.dph3a4.org.Activities.HistoryKegiatanActivity;
import id.ac.telkomuniversity.dph3a4.org.Activities.QrScannerActivity;
import id.ac.telkomuniversity.dph3a4.org.Adapters.KegiatanAdapter;
import id.ac.telkomuniversity.dph3a4.org.ApiHelper.RetrofitClient;
import id.ac.telkomuniversity.dph3a4.org.Model.KegiatanItem;
import id.ac.telkomuniversity.dph3a4.org.Model.ResponseHitungPresensi;
import id.ac.telkomuniversity.dph3a4.org.Model.ResponseKegiatan;
import id.ac.telkomuniversity.dph3a4.org.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KegiatanFragment extends Fragment {

    List<KegiatanItem> dataKegiatan = new ArrayList<>();
    RecyclerView recyclerView;
    FloatingActionButton btnScan;
    ProgressDialog progressDialog;
    TextView jumlahKegiatan;
    CardView cardHistory, cardKegiatanNull;

    SharedPreferences sf;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        sf = getActivity().getSharedPreferences("OrgApp", Context.MODE_PRIVATE);
        String nim = Integer.toString(sf.getInt("nim",0));

        getDataOnline();
        hitungPresensi(nim);

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_kegiatan, container, false);

        recyclerView = rootview.findViewById(R.id.rvKegiatan);
        btnScan = rootview.findViewById(R.id.btnScan);
        jumlahKegiatan = rootview.findViewById(R.id.tvJumlahKegiatan);
        cardHistory = rootview.findViewById(R.id.cardHistory);
        cardKegiatanNull = rootview.findViewById(R.id.cardKegiatanNull);
        cardKegiatanNull.setVisibility(View.GONE);

        cardHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HistoriKegiatanFragment()).addToBackStack(null).commit();
//                Intent pindah = new Intent(getContext(), HistoryKegiatanActivity.class);
//                startActivity(pindah);
            }
        });

        recyclerView.setAdapter(new KegiatanAdapter(getContext(), dataKegiatan));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindah = new Intent(getContext(), QrScannerActivity.class);
                startActivity(pindah);
            }
        });

        return rootview;
    }

    private void getDataOnline() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        Call<ResponseKegiatan> request = RetrofitClient.getInstance().getApi().showKegiatan();
        request.enqueue(new Callback<ResponseKegiatan>() {
            @Override
            public void onResponse(Call<ResponseKegiatan> call, Response<ResponseKegiatan> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (!response.body().isError()){
                        dataKegiatan = response.body().getKegiatan();
                        recyclerView.setAdapter(new KegiatanAdapter(getActivity(), dataKegiatan));
                    } else {
                        cardKegiatanNull.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }
                } else {
                    Toast.makeText(getContext(), "Gagal Melihat Kegiatan", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseKegiatan> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void hitungPresensi(String nim){
        Call<ResponseHitungPresensi> request = RetrofitClient.getInstance().getApi().hitungPresensi(nim);
        request.enqueue(new Callback<ResponseHitungPresensi>() {
            @Override
            public void onResponse(Call<ResponseHitungPresensi> call, Response<ResponseHitungPresensi> response) {
                if (response.isSuccessful()){
                    jumlahKegiatan.setText(response.body().getHitungPresensi());
                }else{
                    Toast.makeText(getActivity(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseHitungPresensi> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
