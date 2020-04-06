package id.ac.telkomuniversity.dph3a4.org.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.app.ActionBar;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import id.ac.telkomuniversity.dph3a4.org.Fragments.CalendarFragment;
import id.ac.telkomuniversity.dph3a4.org.Fragments.EventFragment;
import id.ac.telkomuniversity.dph3a4.org.Fragments.HomeFragment;
import id.ac.telkomuniversity.dph3a4.org.Fragments.UserFragment;
import id.ac.telkomuniversity.dph3a4.org.R;

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
                            selectedFragment = new CalendarFragment();
                            break;
                        case R.id.nav_event:
                            selectedFragment = new EventFragment();
                            break;
                        case R.id.nav_account:
                            selectedFragment = new UserFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true;
                }
            };

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
