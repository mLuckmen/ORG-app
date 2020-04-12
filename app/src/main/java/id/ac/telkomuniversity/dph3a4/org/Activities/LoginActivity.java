package id.ac.telkomuniversity.dph3a4.org.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import id.ac.telkomuniversity.dph3a4.org.ApiHelper.BaseApiService;
import id.ac.telkomuniversity.dph3a4.org.ApiHelper.RetrofitClient;
import id.ac.telkomuniversity.dph3a4.org.Model.LoginResponse;
import id.ac.telkomuniversity.dph3a4.org.Model.User;
import id.ac.telkomuniversity.dph3a4.org.R;
import id.ac.telkomuniversity.dph3a4.org.Utils.SharedPrefManager;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener{

    EditText etUsername, etPassword;
    ProgressDialog loading;

    Context mContext;
    BaseApiService mApiService;

    SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initComponents();
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (SharedPrefManager.getInstance(this).getSpLoggedIn()) {
            Intent intent = new Intent(mContext, DashboardActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    public void initComponents(){
        mContext = this;
//        mApiService = UtilsApi.getAPIService(); // init yang ada di package api helper
        sharedPrefManager = new SharedPrefManager(this);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        findViewById(R.id.btnLogin).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                UserLogin();
                break;
        }
    }

    public void UserLogin(){
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        // INPUT VALIDATION //
        if (username.isEmpty()){
            etUsername.setError("Nama Pengguna tidak boleh kosong");
            etUsername.requestFocus();
            return;
        }

        // Format email
//        if (Patterns.EMAIL_ADDRESS.matcher(etEmail).matches()){
//            etUsername.setError("Format Email belum sesuai");
//            etUsername.requestFocus();
//            return;
//        }
        if (password.isEmpty()){
            etPassword.setError("Kata Sandi tidak boleh kosong");
            etPassword.requestFocus();
            return;
        }

//        if (password.length() < 6){
//            etPassword.setError("Kata Sandi minimal 6 karakter");
//            etPassword.requestFocus();
//            return;
//        }

        if (password != "" && username != ""){
            loading = ProgressDialog.show(mContext, null, "Harap tunggu...", true, false);

            Call<ResponseBody> call = RetrofitClient.getInstance().getApi().loginRequest(username, password);

            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        loading.dismiss();

                        try {
                            JSONObject data = new JSONObject(response.body().string());

                            if (!data.getBoolean("error")) {
                                JSONArray user = data.getJSONArray("user");
                                JSONObject userdata = user.getJSONObject(0);

                                int nim = userdata.getInt("nim");
                                String username = userdata.getString("username");
                                String password = userdata.getString("password");
                                String nama = userdata.getString("nama");
                                String noWA = userdata.getString("noWA");
                                String noHP = userdata.getString("noHP");
                                String idLine = userdata.getString("idLine");
                                String foto = userdata.getString("foto");
                                String prodi = userdata.getString("prodi");

//                                Toast.makeText(mContext, data.getString("message"), Toast.LENGTH_LONG).show();

                                sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_LOGGED_IN, true);
                                sharedPrefManager.saveUser(nim, username, password, nama, noWA, noHP, idLine, foto, prodi);

                                Intent intent = new Intent(mContext, DashboardActivity.class)
                                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            } else {
                                Toast.makeText(mContext, data.getString("message"), Toast.LENGTH_LONG).show();
                            }

                        } catch (JSONException | IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        loading.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e("debug", "onFailure: ERROR > " + t.toString());
                    Toast.makeText(mContext, t.toString(), Toast.LENGTH_LONG).show();
                    loading.dismiss();
                }
            });

//            mApiService.loginRequest(username, password)
//                    .enqueue(new Callback<LoginResponse>() {
//                        @Override
//                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                            LoginResponse loginResponse = response.body();
//                            if (response.isSuccessful()){
//                                loading.dismiss();
//                                try {
//                                    JSONObject jsonRESULTS = new JSONObject(response.body().string());
//                                    if (!jsonRESULTS.getBoolean("error")){
//                                        // Jika login berhasil maka data nama yang ada di response API
//                                        // akan diparsing ke activity selanjutnya
//                                        Toast.makeText(mContext, jsonRESULTS.getString("message"), Toast.LENGTH_LONG).show();
//
//                                        // set shared preferences
//                                        sharedPrefManager.saveSPBoolean(SharedPrefManager.SP_LOGGED_IN, true);
//
////                                        String nama = jsonRESULTS.getJSONObject("data").getString("nama");
//                                        Intent intent = new Intent(mContext, DashboardActivity.class);
//                                        startActivity(intent);
//                                        finish();
//                                    } else {
//                                        // Jika login gagal
//                                        String error_message = jsonRESULTS.getString("message");
//                                        Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
//                                    }
//                                } catch (IOException | JSONException e) {
//                                    e.printStackTrace();
//                                }
//                            } else {
//                                loading.dismiss();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<LoginResponse> call, Throwable t) {
//                            Log.e("debug", "onFailure: ERROR > " + t.toString());
//                            loading.dismiss();
//                        }
//                    });
        }


//        Toast.makeText(mContext, "username = " + username + ", password = " + password, Toast.LENGTH_LONG).show();

    }
}
