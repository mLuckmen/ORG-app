package id.ac.telkomuniversity.dph3a4.org.ApiHelper;

import id.ac.telkomuniversity.dph3a4.org.Model.ResponseOrganisation;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BaseApiService {
    @FormUrlEncoded
    @POST("login/users")
    Call<ResponseBody> loginRequest(
            @Field("username") String username,
            @Field("password") String password
    );

    @GET("organisasi")
    Call<ResponseOrganisation> getOrganisation(
            @Query("nim") int nim
    );
}
