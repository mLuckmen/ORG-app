package id.ac.telkomuniversity.dph3a4.org.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
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

import id.ac.telkomuniversity.dph3a4.org.Adapters.OrganisationAdapter2;
import id.ac.telkomuniversity.dph3a4.org.ApiHelper.RetrofitClient;
import id.ac.telkomuniversity.dph3a4.org.Model.OrganisationItem;
import id.ac.telkomuniversity.dph3a4.org.Model.ResponseOrganisation;
import id.ac.telkomuniversity.dph3a4.org.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListOrganisasiFragment extends Fragment {

    List<OrganisationItem> dataOrganisasi = new ArrayList<>();
    RecyclerView recyclerView;
    SharedPreferences sf;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        sf = getActivity().getSharedPreferences("OrgApp", Context.MODE_PRIVATE);

        getDataOnline();

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_list_organisasi, container, false);

        recyclerView = rootview.findViewById(R.id.recyclerViewListOrganisasi);

        recyclerView.setAdapter(new OrganisationAdapter2(getContext(), dataOrganisasi));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));

        return rootview;
    }

    private void getDataOnline() {
        Call<ResponseOrganisation> request = RetrofitClient.getInstance().getApi().getOrganisation(sf.getInt("nim", 0));
        request.enqueue(new Callback<ResponseOrganisation>() {
            @Override
            public void onResponse(Call<ResponseOrganisation> call, Response<ResponseOrganisation> response) {
                if (response.isSuccessful()){
                    dataOrganisasi = response.body().getData();
                    recyclerView.setAdapter(new OrganisationAdapter2(getActivity(), dataOrganisasi));
                } else {
                    Toast.makeText(getContext(), "Request not success", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseOrganisation> call, Throwable t) {
                Toast.makeText(getContext(), "Request failure", Toast.LENGTH_LONG).show();
            }
        });
    }
}
