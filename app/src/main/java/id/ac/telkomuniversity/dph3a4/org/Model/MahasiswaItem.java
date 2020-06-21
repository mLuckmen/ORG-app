package id.ac.telkomuniversity.dph3a4.org.Model;

import com.google.gson.annotations.SerializedName;

public class MahasiswaItem{

	@SerializedName("password")
	private String password;

	@SerializedName("nim")
	private String nim;

	@SerializedName("nama")
	private String nama;

	@SerializedName("foto")
	private String foto;

	@SerializedName("noWA")
	private String noWA;

	@SerializedName("noHP")
	private String noHP;

	@SerializedName("idLine")
	private String idLine;

	@SerializedName("prodi")
	private String prodi;

	@SerializedName("username")
	private String username;

	public String getPassword(){
		return password;
	}

	public String getNim(){
		return nim;
	}

	public String getNama(){
		return nama;
	}

	public String getFoto(){
		return foto;
	}

	public String getNoWA(){
		return noWA;
	}

	public String getNoHP(){
		return noHP;
	}

	public String getIdLine(){
		return idLine;
	}

	public String getProdi(){
		return prodi;
	}

	public String getUsername(){
		return username;
	}
}