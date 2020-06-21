package id.ac.telkomuniversity.dph3a4.org.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseMahasiswa{

	@SerializedName("mahasiswa")
	private List<MahasiswaItem> mahasiswa;

	@SerializedName("error")
	private boolean error;

	public List<MahasiswaItem> getMahasiswa(){
		return mahasiswa;
	}

	public boolean isError(){
		return error;
	}
}