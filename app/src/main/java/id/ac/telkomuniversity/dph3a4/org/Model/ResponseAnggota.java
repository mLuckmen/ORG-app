package id.ac.telkomuniversity.dph3a4.org.Model;

import java.util.List;

public class ResponseAnggota{
	private List<AnggotaItem> anggota;
	private boolean error;
	private String message;

	public List<AnggotaItem> getAnggota(){
		return anggota;
	}

	public boolean isError(){
		return error;
	}

	public String getMessage(){
		return message;
	}
}