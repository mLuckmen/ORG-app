package id.ac.telkomuniversity.dph3a4.org.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import id.ac.telkomuniversity.dph3a4.org.Activities.LoginActivity;
import id.ac.telkomuniversity.dph3a4.org.R;
import id.ac.telkomuniversity.dph3a4.org.Utils.SharedPrefManager;


public class UserFragment extends Fragment implements View.OnClickListener{

    Button btnLogout;
    SharedPrefManager sharedPrefManager;

    int nim;
    String nama, prodi, noWA, noHP, foto, idLine;
    TextView etNim, etNama, etProdi, etNoWA, etNoHP, etIDline;
    ImageView foto_profil;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        sharedPrefManager = new SharedPrefManager(getActivity());
        SharedPreferences sf = getActivity().getSharedPreferences("OrgApp", Context.MODE_PRIVATE);
        nim = sf.getInt("nim", 0);
        nama = sf.getString("nama", "");
        prodi = sf.getString("prodi", "");
        noWA = sf.getString("noWA", "");
        noHP = sf.getString("noHP", "");
        idLine = sf.getString("idLine", "");
        foto = sf.getString("foto", "");

        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);

        // Component
        etNama = rootView.findViewById(R.id.etNama);
        etNim = rootView.findViewById(R.id.etNim);
        etProdi = rootView.findViewById(R.id.etProdi);
        etNoHP = rootView.findViewById(R.id.etHP);
        etNoWA = rootView.findViewById(R.id.etWa);
        etIDline = rootView.findViewById(R.id.etIDline);
        foto_profil = rootView.findViewById(R.id.foto_profil);

        etNim.setText(String.valueOf(nim));
        etNama.setText(nama);
        etProdi.setText(prodi);
        etNoHP.setText(noHP);
        etNoWA.setText(noWA);
        etIDline.setText(idLine);

        String img_url = "http://10.0.2.2/pa/asset/images/foto/" + foto; // localhost
//        String img_url = "http://192.168.1.9/pa/asset/images/foto/" + foto; // localhost
//        String img_url = "http://org-web.ml/pa/asset/images/foto/" + foto; // hosting
        Picasso.get().load(img_url).into(foto_profil);

        btnLogout = rootView.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(this);

        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnLogout:
                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_LOGGED_IN, false);
                startActivity(new Intent(getActivity(), LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                getActivity().finish();
                break;
            default:
                break;
        }
    }
}
