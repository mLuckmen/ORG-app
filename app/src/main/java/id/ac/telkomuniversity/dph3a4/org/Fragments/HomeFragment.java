package id.ac.telkomuniversity.dph3a4.org.Fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import id.ac.telkomuniversity.dph3a4.org.Adapters.OrganisationAdapter;
import id.ac.telkomuniversity.dph3a4.org.ApiHelper.RetrofitClient;
import id.ac.telkomuniversity.dph3a4.org.Model.DataItem;
import id.ac.telkomuniversity.dph3a4.org.Model.OrganisationModel;
import id.ac.telkomuniversity.dph3a4.org.Model.ResponseOrganisation;
import id.ac.telkomuniversity.dph3a4.org.R;
import id.ac.telkomuniversity.dph3a4.org.Utils.SharedPrefManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

    String nama;
    TextView headerNama;
    List<DataItem> dataOrganisasi = new ArrayList<>();
    RecyclerView recycler;
    SharedPreferences sf;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        sf = getActivity().getSharedPreferences("OrgApp", Context.MODE_PRIVATE);
        nama = sf.getString("nama","");

        // layout per item
        // model data
        // adapter
        // layout manager

//        OrganisationModel organisasi1 = new OrganisationModel();
//        organisasi1.setIdOrganisasi(123);
//        organisasi1.setNamaOrganisasi("HMDSI");
//        organisasi1.setDeskripsi("Himpunan Mahasiswa Diploma Sistem Informasi");
//        organisasi1.setKetua("Kelvin");
//        organisasi1.setLogo("https://ketringan.com/assets/img/partner/hmdsi.jpg");
//
//        for (int i = 0; i < 10; i++) {
//            dataOrganisasi.add(organisasi1);
//        }

        getDataOnline();

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void getDataOnline() {
        Call<ResponseOrganisation> request = RetrofitClient.getInstance().getApi().getOrganisation(sf.getInt("nim", 0));
        request.enqueue(new Callback<ResponseOrganisation>() {
            @Override
            public void onResponse(Call<ResponseOrganisation> call, Response<ResponseOrganisation> response) {
                if (response.isSuccessful()){
                    dataOrganisasi = response.body().getData();
                    recycler.setAdapter(new OrganisationAdapter(getActivity(), dataOrganisasi));
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        // Custom Toolbar
        Toolbar mainToolbar = rootView.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(mainToolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        // init Component
        headerNama = rootView.findViewById(R.id.tvNamaUser);
        headerNama.setText(nama);
        recycler = rootView.findViewById(R.id.rvOrganisasi);

        // set Adapter
        recycler.setAdapter(new OrganisationAdapter(getActivity(), dataOrganisasi));
        recycler.setLayoutManager(new GridLayoutManager(getActivity(), 1));

        return rootView;
    }

    // Activate menu
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Action Handler
        switch (item.getItemId()){
            case R.id.action_notification:
                Toast.makeText(getActivity(), "Notification Clicked", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

}
