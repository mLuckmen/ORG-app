package id.ac.telkomuniversity.dph3a4.org.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import id.ac.telkomuniversity.dph3a4.org.Activities.DashboardActivity;
import id.ac.telkomuniversity.dph3a4.org.Adapters.BeritaAdapter;
import id.ac.telkomuniversity.dph3a4.org.Adapters.OrganisationAdapter;
import id.ac.telkomuniversity.dph3a4.org.ApiHelper.RetrofitClient;
import id.ac.telkomuniversity.dph3a4.org.Model.BeritaItem;
import id.ac.telkomuniversity.dph3a4.org.Model.OrganisationItem;
import id.ac.telkomuniversity.dph3a4.org.Model.ResponseBerita;
import id.ac.telkomuniversity.dph3a4.org.Model.ResponseOrganisation;
import id.ac.telkomuniversity.dph3a4.org.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements View.OnClickListener{

    String nama;
    TextView headerNama, tvLihatOrganisasi, tvLihatAgenda, tvLihatKegiatan;
    List<OrganisationItem> dataOrganisasi = new ArrayList<>();
    List<BeritaItem> dataBerita = new ArrayList<>();
    RecyclerView recycler, rvBerita;
    SharedPreferences sf;
    BottomNavigationView bottomNavigationView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        sf = getActivity().getSharedPreferences("OrgApp", Context.MODE_PRIVATE);
        nama = sf.getString("nama","");

        getDataOnline();
        getDataBerita();

        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private void getDataOnline() {
        ProgressDialog progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Mengambil data dari server");
        progressDialog.show();

        Call<ResponseOrganisation> request = RetrofitClient.getInstance().getApi().getOrganisation(sf.getInt("nim", 0));
        request.enqueue(new Callback<ResponseOrganisation>() {
            @Override
            public void onResponse(Call<ResponseOrganisation> call, Response<ResponseOrganisation> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()){
                    dataOrganisasi = response.body().getData();
                    recycler.setAdapter(new OrganisationAdapter(getActivity(), dataOrganisasi));
                } else {
                    Toast.makeText(getContext(), "Request not success", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseOrganisation> call, Throwable t) {
                progressDialog.dismiss();
                Log.e("debug", "onFailure: ERROR > " + t.toString());
                Toast.makeText(getContext(), t.toString(), Toast.LENGTH_LONG).show();
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
        tvLihatOrganisasi = rootView.findViewById(R.id.tvLihatOrganisasi);
        tvLihatAgenda = rootView.findViewById(R.id.tvLihatAgenda);
        tvLihatKegiatan = rootView.findViewById(R.id.tvLihatKegiatan);
        headerNama = rootView.findViewById(R.id.tvNamaUser);
        bottomNavigationView = getActivity().findViewById(R.id.bottom_navigation); // untuk pindah menu
        recycler = rootView.findViewById(R.id.rvOrganisasi);
        rvBerita = rootView.findViewById(R.id.rvBerita);

        tvLihatOrganisasi.setOnClickListener(this);
        tvLihatAgenda.setOnClickListener(this);
        tvLihatKegiatan.setOnClickListener(this);
        headerNama.setText(nama);

        // set Adapter
        recycler.setAdapter(new OrganisationAdapter(getActivity(), dataOrganisasi));
        recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvBerita.setAdapter(new BeritaAdapter(getActivity(), dataBerita));
        rvBerita.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvLihatOrganisasi:
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new ListOrganisasiFragment()).addToBackStack("organisasi").commit();
                break;
            case R.id.tvLihatAgenda:
                bottomNavigationView.setSelectedItemId(R.id.nav_calendar);
                break;
            case R.id.tvLihatKegiatan:
                bottomNavigationView.setSelectedItemId(R.id.nav_event);
                break;
        }
    }

    private void getDataBerita() {
        Call<ResponseBerita> request = RetrofitClient.getInstance().getApi().getAllBerita();
        request.enqueue(new Callback<ResponseBerita>() {
            @Override
            public void onResponse(Call<ResponseBerita> call, Response<ResponseBerita> response) {
                if (response.isSuccessful()){
                    dataBerita = response.body().getBerita();
                    rvBerita.setAdapter(new BeritaAdapter(getActivity(), dataBerita));
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
