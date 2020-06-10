package id.ac.telkomuniversity.dph3a4.org.Adapters;

import com.google.gson.annotations.SerializedName;

public class ResponseCekKegiatan{

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	public boolean isError(){
		return error;
	}

	public String getMessage(){
		return message;
	}
}