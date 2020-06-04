package id.ac.telkomuniversity.dph3a4.org.Fragments;

import android.os.Bundle;
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

import id.ac.telkomuniversity.dph3a4.org.Adapters.KegiatanAdapter;
import id.ac.telkomuniversity.dph3a4.org.Adapters.OrganisationAdapter2;
import id.ac.telkomuniversity.dph3a4.org.ApiHelper.RetrofitClient;
import id.ac.telkomuniversity.dph3a4.org.Model.KegiatanItem;
import id.ac.telkomuniversity.dph3a4.org.Model.ResponseKegiatan;
import id.ac.telkomuniversity.dph3a4.org.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KegiatanFragment extends Fragment {

    List<KegiatanItem> dataKegiatan = new ArrayList<>();
    RecyclerView recyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        getDataOnline();

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_kegiatan, container, false);

        recyclerView = rootview.findViewById(R.id.rvKegiatan);

        recyclerView.setAdapter(new KegiatanAdapter(getContext(), dataKegiatan));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        return rootview;
    }

    private void getDataOnline() {
        Call<ResponseKegiatan> request = RetrofitClient.getInstance().getApi().showKegiatan();
        request.enqueue(new Callback<ResponseKegiatan>() {
            @Override
            public void onResponse(Call<ResponseKegiatan> call, Response<ResponseKegiatan> response) {
                if (response.isSuccessful()) {
                    dataKegiatan = response.body().getKegiatan();
                    recyclerView.setAdapter(new KegiatanAdapter(getActivity(), dataKegiatan));
                } else {
                    Toast.makeText(getContext(), "Gagal Melihat Kegiatan", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseKegiatan> call, Throwable t) {
                Toast.makeText(getContext(), "Koneksi Gagal", Toast.LENGTH_LONG).show();
            }
        });
    }
}
