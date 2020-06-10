package id.ac.telkomuniversity.dph3a4.org.ApiHelper;

import id.ac.telkomuniversity.dph3a4.org.Adapters.ResponseCekKegiatan;
import id.ac.telkomuniversity.dph3a4.org.Model.ResponseAnggota;
import id.ac.telkomuniversity.dph3a4.org.Model.ResponseBerita;
import id.ac.telkomuniversity.dph3a4.org.Model.ResponseKegiatan;
import id.ac.telkomuniversity.dph3a4.org.Model.ResponseKegiatanByNama;
import id.ac.telkomuniversity.dph3a4.org.Model.ResponseOrganisation;
import id.ac.telkomuniversity.dph3a4.org.Model.ResponsePengurus;
import id.ac.telkomuniversity.dph3a4.org.Model.ResponsePesanTiket;
import id.ac.telkomuniversity.dph3a4.org.Model.ResponsePresensi;
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

    @GET("organisasi/pengurus")
    Call<ResponsePengurus> getPengurus(
            @Query("idOrganisasi") String idOrganisasi
     );

    @GET("organisasi/anggota")
    Call<ResponseAnggota> getAnggota(
            @Query("idOrganisasi") String idOrganisasi
    );

    @GET("kegiatan/showKegiatan")
    Call<ResponseKegiatan> showKegiatan();

    @FormUrlEncoded
    @POST("kegiatan/daftarKegiatan")
    Call<ResponsePesanTiket> daftarKegiatan(
            @Field("nama") String nama,
            @Field("nim") String nim,
            @Field("jurusan") String jurusan,
            @Field("email") String email,
            @Field("jumlah") String jumlah,
            @Field("total") String total,
            @Field("metode_pembayaran") String metode_pembayaran,
            @Field("status") String status,
            @Field("id_kegiatan") String id_kegiatan
    );

    @GET("berita/allBerita")
    Call<ResponseBerita> getAllBerita();

    @GET("kegiatan/cekKegiatan")
    Call<ResponseCekKegiatan> cekKegiatan(
            @Query("nama_kegiatan") String namaKegiatan
    );

    @GET("kegiatan/getByName")
    Call<ResponseKegiatanByNama> getKegiatanByName(
            @Query("nama_kegiatan") String namaKegiatan
    );

    @FormUrlEncoded
    @POST("kegiatan/insertPresensi")
    Call<ResponsePresensi> insertPresensi(
            @Field("waktu_submit") String waktuSubmit,
            @Field("status") String status,
            @Field("nim") String nim,
            @Field("id_kegiatan") String idKegiatan
    );


}
