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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import id.ac.telkomuniversity.dph3a4.org.ApiHelper.BaseApiService;
import id.ac.telkomuniversity.dph3a4.org.ApiHelper.UtilsApi;
import id.ac.telkomuniversity.dph3a4.org.Fragments.HomeFragment;
import id.ac.telkomuniversity.dph3a4.org.R;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity  implements View.OnClickListener{

    EditText etUsername, etPassword;
    ProgressDialog loading;

    Context mContext;
    BaseApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mContext = this;
        mApiService = UtilsApi.getAPIService(); // init yang ada di package api helper
        initComponents();

//        btnLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
//            }
//        });
    }

    public void initComponents(){
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);

        findViewById(R.id.btnLogin).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                loading = ProgressDialog.show(mContext, null, "Harap tunggu...", true, false);
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

        mApiService.loginRequest(username, password)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()){
                            loading.dismiss();
                            try {
                                JSONObject jsonRESULTS = new JSONObject(response.body().string());
                                if (jsonRESULTS.getBoolean("error") == false){
                                    // Jika login berhasil maka data nama yang ada di response API
                                    // akan diparsing ke activity selanjutnya
                                    Toast.makeText(mContext, "Berhasil Login", Toast.LENGTH_LONG).show();
//                                    String nama = jsonRESULTS.getJSONObject("data").getString("nama");
                                    Intent intent = new Intent(mContext, DashboardActivity.class);
//                                    intent.putExtra("result_nama", nama);
                                    startActivity(intent);
                                } else {
                                    // Jika login gagal
                                    String error_message = jsonRESULTS.getString("message");
                                    Toast.makeText(mContext, error_message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } else {
                            loading.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("debug", "onFailure: ERROR > " + t.toString());
                        loading.dismiss();
                    }
                });

//        Toast.makeText(mContext, "username = " + username + ", password = " + password, Toast.LENGTH_LONG).show();

//        if (password.length() < 6){
//            etPassword.setError("Kata Sandi minimal 6 karakter");
//            etPassword.requestFocus();
//            return;
//        }

//        Call<LoginResponse> call = RetrofitClient
//                .getInstance()
//                .getApi()
//                .loginRequest(username, password);
//
//        call.enqueue(new Callback<LoginResponse>() {
//            @Override
//            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
//                LoginResponse loginResponse = response.body();
//
//                if (!loginResponse.isError()){
//
//                    // save user
//                    // open homepage
//                    Toast.makeText(mContext, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(mContext, loginResponse.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<LoginResponse> call, Throwable t) {
//
//            }
//        });

    }
}
