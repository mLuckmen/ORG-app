package id.ac.telkomuniversity.dph3a4.org.Model;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel

public class OrganisationItem {

	@SerializedName("nim")
	private String nim;

	@SerializedName("nama")
	private String nama;

	@SerializedName("ketua")
	private String ketua;

	@SerializedName("logo")
	private String logo;

	@SerializedName("idOrganisasi")
	private String idOrganisasi;

	@SerializedName("namaOrganisasi")
	private String namaOrganisasi;

	@SerializedName("deskripsi")
	private String deskripsi;

	public void setNim(String nim){
		this.nim = nim;
	}

	public String getNim(){
		return nim;
	}

	public void setNama(String nama){
		this.nama = nama;
	}

	public String getNama(){
		return nama;
	}

	public void setKetua(String ketua){
		this.ketua = ketua;
	}

	public String getKetua(){
		return ketua;
	}

	public void setLogo(String logo){
		this.logo = logo;
	}

	public String getLogo(){
		return logo;
	}

	public void setIdOrganisasi(String idOrganisasi){
		this.idOrganisasi = idOrganisasi;
	}

	public String getIdOrganisasi(){
		return idOrganisasi;
	}

	public void setNamaOrganisasi(String namaOrganisasi){
		this.namaOrganisasi = namaOrganisasi;
	}

	public String getNamaOrganisasi(){
		return namaOrganisasi;
	}

	public void setDeskripsi(String deskripsi){
		this.deskripsi = deskripsi;
	}

	public String getDeskripsi(){
		return deskripsi;
	}

	@Override
 	public String toString(){
		return 
			"OrganisationItem{" +
			"nim = '" + nim + '\'' + 
			",nama = '" + nama + '\'' + 
			",ketua = '" + ketua + '\'' + 
			",logo = '" + logo + '\'' + 
			",idOrganisasi = '" + idOrganisasi + '\'' + 
			",namaOrganisasi = '" + namaOrganisasi + '\'' + 
			",deskripsi = '" + deskripsi + '\'' + 
			"}";
		}
}