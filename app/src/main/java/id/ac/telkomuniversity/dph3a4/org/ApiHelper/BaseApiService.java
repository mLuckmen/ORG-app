package id.ac.telkomuniversity.dph3a4.org.ApiHelper;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface BaseApiService {
    @FormUrlEncoded
    @POST("login/users")
    Call<ResponseBody> loginRequest(
            @Field("username") String username,
            @Field("password") String password
    );
}
