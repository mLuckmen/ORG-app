package id.ac.telkomuniversity.dph3a4.org.Model;

import com.google.gson.annotations.SerializedName;

public class AgendaOrganisasiItem{

	@SerializedName("tempat")
	private String tempat;

	@SerializedName("waktu")
	private String waktu;

	@SerializedName("id_organisasi")
	private String idOrganisasi;

	@SerializedName("kategori")
	private String kategori;

	@SerializedName("namaOrganisasi")
	private String namaOrganisasi;

	@SerializedName("id_rapat")
	private String idRapat;

	@SerializedName("tanggal")
	private String tanggal;

	@SerializedName("perihal")
	private String perihal;

	public String getTempat(){
		return tempat;
	}

	public String getWaktu(){
		return waktu;
	}

	public String getIdOrganisasi(){
		return idOrganisasi;
	}

	public String getKategori(){
		return kategori;
	}

	public String getNamaOrganisasi(){
		return namaOrganisasi;
	}

	public String getIdRapat(){
		return idRapat;
	}

	public String getTanggal(){
		return tanggal;
	}

	public String getPerihal(){
		return perihal;
	}
}