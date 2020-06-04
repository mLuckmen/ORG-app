package id.ac.telkomuniversity.dph3a4.org.Model;

import com.google.gson.annotations.SerializedName;

public class KegiatanItem{

	@SerializedName("tempat")
	private String tempat;

	@SerializedName("harga")
	private String harga;

	@SerializedName("foto")
	private String foto;

	@SerializedName("nama_kegiatan")
	private String namaKegiatan;

	@SerializedName("waktu")
	private String waktu;

	@SerializedName("qr_code")
	private String qrCode;

	@SerializedName("id_kegiatan")
	private String idKegiatan;

	@SerializedName("departemen")
	private String departemen;

	public String getTempat(){
		return tempat;
	}

	public String getHarga(){
		return harga;
	}

	public String getFoto(){
		return foto;
	}

	public String getNamaKegiatan(){
		return namaKegiatan;
	}

	public String getWaktu(){
		return waktu;
	}

	public String getQrCode(){
		return qrCode;
	}

	public String getIdKegiatan(){
		return idKegiatan;
	}

	public String getDepartemen(){
		return departemen;
	}
}