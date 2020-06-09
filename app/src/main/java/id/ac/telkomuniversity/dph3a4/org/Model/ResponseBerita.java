package id.ac.telkomuniversity.dph3a4.org.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseBerita{

	@SerializedName("berita")
	private List<BeritaItem> berita;

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	public List<BeritaItem> getBerita(){
		return berita;
	}

	public boolean isError(){
		return error;
	}

	public String getMessage(){
		return message;
	}
}