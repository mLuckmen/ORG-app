package id.ac.telkomuniversity.dph3a4.org.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import id.ac.telkomuniversity.dph3a4.org.Activities.LoginActivity;
import id.ac.telkomuniversity.dph3a4.org.R;
import id.ac.telkomuniversity.dph3a4.org.Utils.SharedPrefManager;


public class UserFragment extends Fragment implements View.OnClickListener{

    Button btnLogout;
    SharedPrefManager sharedPrefManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        sharedPrefManager = new SharedPrefManager(getActivity());

        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user, container, false);
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
