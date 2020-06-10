package id.ac.telkomuniversity.dph3a4.org.Model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel

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

	@SerializedName("id_programkerja")
	private String idProgramkerja;

	@SerializedName("qr_code")
	private String qrCode;

	@SerializedName("id_kegiatan")
	private String idKegiatan;

	@SerializedName("upload_lpj")
	private String uploadLpj;

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

	public String getIdProgramkerja(){
		return idProgramkerja;
	}

	public String getQrCode(){
		return qrCode;
	}

	public String getIdKegiatan(){
		return idKegiatan;
	}

	public String getUploadLpj(){
		return uploadLpj;
	}
}