package id.ac.telkomuniversity.dph3a4.org.Model;

import com.google.gson.annotations.SerializedName;

public class DataPresensiItem{

	@SerializedName("waktu_submit")
	private String waktuSubmit;

	@SerializedName("nim")
	private String nim;

	@SerializedName("foto")
	private String foto;

	@SerializedName("nama_kegiatan")
	private String namaKegiatan;

	@SerializedName("id_kegiatan")
	private String idKegiatan;

	@SerializedName("tempat")
	private String tempat;

	public String getWaktuSubmit(){
		return waktuSubmit;
	}

	public String getNim(){
		return nim;
	}

	public String getFoto(){
		return foto;
	}

	public String getNamaKegiatan(){
		return namaKegiatan;
	}

	public String getIdKegiatan(){
		return idKegiatan;
	}

	public String getTempat() {
		return tempat;
	}
}