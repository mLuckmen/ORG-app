package id.ac.telkomuniversity.dph3a4.org.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseHistoriPresensi{

	@SerializedName("dataPresensi")
	private List<DataPresensiItem> dataPresensi;

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	public List<DataPresensiItem> getDataPresensi(){
		return dataPresensi;
	}

	public boolean isError(){
		return error;
	}

	public String getMessage(){
		return message;
	}
}