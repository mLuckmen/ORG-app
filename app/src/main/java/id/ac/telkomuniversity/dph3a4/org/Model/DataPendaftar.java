package id.ac.telkomuniversity.dph3a4.org.Model;

import com.google.gson.annotations.SerializedName;

public class DataPendaftar{

	@SerializedName("metode_pembayaran")
	private String metodePembayaran;

	@SerializedName("total")
	private String total;

	@SerializedName("nim")
	private String nim;

	@SerializedName("nama")
	private String nama;

	@SerializedName("jumlah")
	private String jumlah;

	@SerializedName("jurusan")
	private String jurusan;

	@SerializedName("email")
	private String email;

	@SerializedName("id_kegiatan")
	private String idKegiatan;

	@SerializedName("status")
	private String status;

	public String getMetodePembayaran(){
		return metodePembayaran;
	}

	public String getTotal(){
		return total;
	}

	public String getNim(){
		return nim;
	}

	public String getNama(){
		return nama;
	}

	public String getJumlah(){
		return jumlah;
	}

	public String getJurusan(){
		return jurusan;
	}

	public String getEmail(){
		return email;
	}

	public String getIdKegiatan(){
		return idKegiatan;
	}

	public String getStatus(){
		return status;
	}
}