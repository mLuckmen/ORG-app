package id.ac.telkomuniversity.dph3a4.org.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.ac.telkomuniversity.dph3a4.org.Adapters.BeritaAdapter;
import id.ac.telkomuniversity.dph3a4.org.ApiHelper.RetrofitClient;
import id.ac.telkomuniversity.dph3a4.org.Model.BeritaItem;
import id.ac.telkomuniversity.dph3a4.org.Model.ResponseBerita;
import id.ac.telkomuniversity.dph3a4.org.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SemuaBeritaFragment extends Fragment {
    List<BeritaItem> dataBerita = new ArrayList<>();
    RecyclerView rvAllBerita;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        getAllBerita();
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_semua_berita, container, false);

        // init Component
        rvAllBerita = rootView.findViewById(R.id.rvAllBerita);

        // set adapter RecyclerView
        rvAllBerita.setAdapter(new BeritaAdapter(getActivity(), dataBerita));
        rvAllBerita.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        return rootView;
    }

    private void getAllBerita() {
        Call<ResponseBerita> request = RetrofitClient.getInstance().getApi().getAllBerita();
        request.enqueue(new Callback<ResponseBerita>() {
            @Override
            public void onResponse(Call<ResponseBerita> call, Response<ResponseBerita> response) {
                if (response.isSuccessful()) {
                    dataBerita = response.body().getBerita();
                    rvAllBerita.setAdapter(new BeritaAdapter(getActivity(), dataBerita));
                } else {
                    Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBerita> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }
    
}
