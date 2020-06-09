package id.ac.telkomuniversity.dph3a4.org.Model;

import com.google.gson.annotations.SerializedName;

public class BeritaItem{

	@SerializedName("tanggal")
	private String tanggal;

	@SerializedName("judul")
	private String judul;

	@SerializedName("id_berita")
	private String idBerita;

	@SerializedName("gambar")
	private String gambar;

	@SerializedName("isi")
	private String isi;

	public String getTanggal(){
		return tanggal;
	}

	public String getJudul(){
		return judul;
	}

	public String getIdBerita(){
		return idBerita;
	}

	public String getGambar(){
		return gambar;
	}

	public String getIsi(){
		return isi;
	}
}