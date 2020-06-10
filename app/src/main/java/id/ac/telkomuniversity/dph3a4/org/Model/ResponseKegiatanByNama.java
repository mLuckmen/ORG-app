package id.ac.telkomuniversity.dph3a4.org.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseKegiatanByNama{

	@SerializedName("kegiatan")
	private List<KegiatanItem> kegiatan;

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	public List<KegiatanItem> getKegiatan(){
		return kegiatan;
	}

	public boolean isError(){
		return error;
	}

	public String getMessage(){
		return message;
	}
}