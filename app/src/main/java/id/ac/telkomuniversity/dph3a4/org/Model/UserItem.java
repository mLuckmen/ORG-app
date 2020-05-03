package id.ac.telkomuniversity.dph3a4.org.Model;

import com.google.gson.annotations.SerializedName;

public class UserItem{

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

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

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

	public void setFoto(String foto){
		this.foto = foto;
	}

	public String getFoto(){
		return foto;
	}

	public void setNoWA(String noWA){
		this.noWA = noWA;
	}

	public String getNoWA(){
		return noWA;
	}

	public void setNoHP(String noHP){
		this.noHP = noHP;
	}

	public String getNoHP(){
		return noHP;
	}

	public void setIdLine(String idLine){
		this.idLine = idLine;
	}

	public String getIdLine(){
		return idLine;
	}

	public void setProdi(String prodi){
		this.prodi = prodi;
	}

	public String getProdi(){
		return prodi;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	@Override
 	public String toString(){
		return 
			"UserItem{" + 
			"password = '" + password + '\'' + 
			",nim = '" + nim + '\'' + 
			",nama = '" + nama + '\'' + 
			",foto = '" + foto + '\'' + 
			",noWA = '" + noWA + '\'' + 
			",noHP = '" + noHP + '\'' + 
			",idLine = '" + idLine + '\'' + 
			",prodi = '" + prodi + '\'' + 
			",username = '" + username + '\'' + 
			"}";
		}
}