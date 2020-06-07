package id.ac.telkomuniversity.dph3a4.org.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import id.ac.telkomuniversity.dph3a4.org.Fragments.AgendaFragment;
import id.ac.telkomuniversity.dph3a4.org.Fragments.KegiatanFragment;
import id.ac.telkomuniversity.dph3a4.org.Fragments.HomeFragment;
import id.ac.telkomuniversity.dph3a4.org.Fragments.UserFragment;
import id.ac.telkomuniversity.dph3a4.org.R;
import id.ac.telkomuniversity.dph3a4.org.Utils.SharedPrefManager;

public class DashboardActivity extends AppCompatActivity {

    Toolbar mainToolbar;
    BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
//        setCustomToolbar();

        initComponents();

    }

    @Override
    protected void onStart() {
        super.onStart();

        if (!SharedPrefManager.getInstance(this).getSpLoggedIn()) {
            Intent intent = new Intent(DashboardActivity.this, LoginActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    // inisiasi komponen
    private void initComponents() {
        bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
    }

    // Bottom navigation action
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.nav_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.nav_calendar:
                            selectedFragment = new AgendaFragment();
                            break;
                        case R.id.nav_event:
                            selectedFragment = new KegiatanFragment();
                            break;
                        case R.id.nav_account:
                            selectedFragment = new UserFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true;
                }
            };

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Anda yakin ingin keluar dari aplikasi ini?")
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DashboardActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

//    // Action bar > Menu
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
//        return true;
//    }
//
//    // Action bar item action
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//
//        switch (id){
//            case R.id.action_notification:
//                Toast.makeText(this, "Notification Clicked", Toast.LENGTH_SHORT).show();
//                break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    // custom toolbar
//    private void setCustomToolbar(){
//        mainToolbar = findViewById(R.id.toolbar);
//
//        setSupportActionBar(mainToolbar);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//    }
}
