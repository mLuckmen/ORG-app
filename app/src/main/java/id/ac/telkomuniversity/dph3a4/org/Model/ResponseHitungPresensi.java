package id.ac.telkomuniversity.dph3a4.org.Model;

import com.google.gson.annotations.SerializedName;

public class ResponseHitungPresensi{

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	@SerializedName("hitungPresensi")
	private String hitungPresensi;

	public boolean isError(){
		return error;
	}

	public String getMessage(){
		return message;
	}

	public String getHitungPresensi(){
		return hitungPresensi;
	}
}