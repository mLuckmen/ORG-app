package id.ac.telkomuniversity.dph3a4.org.Model;

import java.util.List;

public class ResponsePengurus{
	private List<PengurusItem> pengurus;
	private boolean error;
	private String message;

	public List<PengurusItem> getPengurus(){
		return pengurus;
	}

	public boolean isError(){
		return error;
	}

	public String getMessage(){
		return message;
	}
}