package id.ac.telkomuniversity.dph3a4.org.Model;

import com.google.gson.annotations.SerializedName;

public class DataPendaftar{

	@SerializedName("waktu_submit")
	private String waktuSubmit;

	@SerializedName("nim")
	private String nim;

	@SerializedName("id_kegiatan")
	private String idKegiatan;

	@SerializedName("status")
	private String status;

	public String getWaktuSubmit(){
		return waktuSubmit;
	}

	public String getNim(){
		return nim;
	}

	public String getIdKegiatan(){
		return idKegiatan;
	}

	public String getStatus(){
		return status;
	}
}