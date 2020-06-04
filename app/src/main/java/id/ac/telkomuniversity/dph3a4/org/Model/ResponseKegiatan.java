package id.ac.telkomuniversity.dph3a4.org.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseKegiatan{

	@SerializedName("kegiatan")
	private List<KegiatanItem> kegiatan;

	@SerializedName("error")
	private boolean error;

	public List<KegiatanItem> getKegiatan(){
		return kegiatan;
	}

	public boolean isError(){
		return error;
	}
}