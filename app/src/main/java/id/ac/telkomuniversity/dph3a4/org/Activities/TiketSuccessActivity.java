package id.ac.telkomuniversity.dph3a4.org.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import id.ac.telkomuniversity.dph3a4.org.R;

public class TiketSuccessActivity extends AppCompatActivity {

    Button toHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tiket_success);

        toHome = findViewById(R.id.btnKeHome);
        toHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pindah = new Intent(TiketSuccessActivity.this, DashboardActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(pindah);
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent pindah = new Intent(TiketSuccessActivity.this, DashboardActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(pindah);
    }
}
