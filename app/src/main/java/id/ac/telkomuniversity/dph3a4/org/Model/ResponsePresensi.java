package id.ac.telkomuniversity.dph3a4.org.Model;

import com.google.gson.annotations.SerializedName;

public class ResponsePresensi{

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	@SerializedName("dataPendaftar")
	private DataPendaftar dataPendaftar;

	public boolean isError(){
		return error;
	}

	public String getMessage(){
		return message;
	}

	public DataPendaftar getDataPendaftar(){
		return dataPendaftar;
	}
}