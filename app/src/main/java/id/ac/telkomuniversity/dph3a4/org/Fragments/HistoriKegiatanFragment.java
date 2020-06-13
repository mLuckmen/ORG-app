package id.ac.telkomuniversity.dph3a4.org.Fragments;

import android.content.Context;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.ac.telkomuniversity.dph3a4.org.Adapters.HistoriKegiatanAdapter;
import id.ac.telkomuniversity.dph3a4.org.ApiHelper.RetrofitClient;
import id.ac.telkomuniversity.dph3a4.org.Model.DataPresensiItem;
import id.ac.telkomuniversity.dph3a4.org.Model.ResponseHistoriPresensi;
import id.ac.telkomuniversity.dph3a4.org.R;
import id.ac.telkomuniversity.dph3a4.org.Utils.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoriKegiatanFragment extends Fragment {
    List<DataPresensiItem> dataPresensi = new ArrayList<>();
    RecyclerView rvHistory;
    TextView tvDataNull;
    SharedPreferences sf;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        sf = getActivity().getSharedPreferences("OrgApp", Context.MODE_PRIVATE);
        String nim = Integer.toString(sf.getInt("nim",0));

        getDataPresensi(nim);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_kegiatan_dihadiri, container,false);

        rvHistory = rootView.findViewById(R.id.rvHistory);
        tvDataNull = rootView.findViewById(R.id.tvDataNull);
        tvDataNull.setVisibility(View.INVISIBLE);
        rvHistory.setAdapter(new HistoriKegiatanAdapter(getContext(), dataPresensi));
        rvHistory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        return rootView;
    }

    private void getDataPresensi(String nim){
        Call<ResponseHistoriPresensi> request = RetrofitClient.getInstance().getApi().historiPresensi(nim);
        request.enqueue(new Callback<ResponseHistoriPresensi>() {
            @Override
            public void onResponse(Call<ResponseHistoriPresensi> call, Response<ResponseHistoriPresensi> response) {
                if (response.isSuccessful()){
                    if (!response.body().isError()){
                        dataPresensi = response.body().getDataPresensi();
                        rvHistory.setAdapter(new HistoriKegiatanAdapter(getActivity(), dataPresensi));
                    } else {
                        tvDataNull.setVisibility(View.VISIBLE);
                        rvHistory.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseHistoriPresensi> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
