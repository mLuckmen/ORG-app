package id.ac.telkomuniversity.dph3a4.org.ApiHelper;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    //    10.0.2.2 ---> IP localhost
    public static final String BASE_URL = "http://10.0.2.2/pa/index.php/api/";

    //    192.168.137.1 ---> IP ...
//    public static final String BASE_URL = "http://192.168.137.1/pa/index.php/api/";

    //    192.168.1.5 ---> IP Wifi rumah (KenariTony_plus)
//    private static final String BASE_URL = "http://192.168.1.5/pa/index.php/api/";

    private static RetrofitClient mInstance;
    private static Retrofit retrofit;

    private RetrofitClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance(){
        if (mInstance == null) {
            mInstance = new RetrofitClient();
        }

        return mInstance;
    }

    public BaseApiService getApi() {
        return retrofit.create(BaseApiService.class);
    }

//    private static Retrofit retrofit = null;
//
//    public static Retrofit getClient(String baseUrl){
//        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
//
//        if (retrofit == null) {
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(baseUrl)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .client(client)
//                    .build();
//        }
//        return retrofit;
//    }
}
