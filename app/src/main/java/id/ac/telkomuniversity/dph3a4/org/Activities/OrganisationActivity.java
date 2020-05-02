package id.ac.telkomuniversity.dph3a4.org.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import org.parceler.Parcels;

import id.ac.telkomuniversity.dph3a4.org.Adapters.OrganisationAdapter;
import id.ac.telkomuniversity.dph3a4.org.Model.OrganisationItem;
import id.ac.telkomuniversity.dph3a4.org.R;

public class OrganisationActivity extends AppCompatActivity {

    OrganisationItem dataOrganisasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organisation);

        Bundle bundle = getIntent().getBundleExtra(OrganisationAdapter.DATA_EXTRA);
        dataOrganisasi = Parcels.unwrap(bundle.getParcelable(OrganisationAdapter.DATA_ORGANISASI));


    }
}
