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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import id.ac.telkomuniversity.dph3a4.org.Adapters.AgendaAdapter;
import id.ac.telkomuniversity.dph3a4.org.ApiHelper.RetrofitClient;
import id.ac.telkomuniversity.dph3a4.org.Model.AgendaOrganisasiItem;
import id.ac.telkomuniversity.dph3a4.org.Model.KegiatanItem;
import id.ac.telkomuniversity.dph3a4.org.Model.ResponseAgenda;
import id.ac.telkomuniversity.dph3a4.org.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AgendaFragment extends Fragment {

    RecyclerView rvAgenda;
    List<AgendaOrganisasiItem> dataAgenda = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        getAgenda();

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_agenda, container, false);

        rvAgenda = rootView.findViewById(R.id.rvAgendaRapat);
        rvAgenda.setAdapter(new AgendaAdapter(getActivity(), dataAgenda));
        rvAgenda.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        return rootView;
    }

    void getAgenda(){

        Call<ResponseAgenda> request = RetrofitClient.getInstance().getApi().getAgendaOrganisasi(HomeFragment.idOrganisasiArray);
        request.enqueue(new Callback<ResponseAgenda>() {
            @Override
            public void onResponse(Call<ResponseAgenda> call, Response<ResponseAgenda> response) {
                if (response.isSuccessful()){
                    if (!response.body().isError()){
                        dataAgenda = response.body().getAgendaOrganisasi();
                        rvAgenda.setAdapter(new AgendaAdapter(getActivity(), dataAgenda));
                    } else {
                        Toast.makeText(getContext(), response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Terjadi Kesalahan pada saat melihat agenda", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseAgenda> call, Throwable t) {
                Log.e("debug", "onFailure: ERROR > " + t.toString());
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
//        String message = Integer.toString(jumlahOrganisasi);
//        tglAgenda.setText(message);
    }
}
