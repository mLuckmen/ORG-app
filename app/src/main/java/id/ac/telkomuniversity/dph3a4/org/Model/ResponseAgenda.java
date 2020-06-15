package id.ac.telkomuniversity.dph3a4.org.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseAgenda{

	@SerializedName("agendaOrganisasi")
	private List<AgendaOrganisasiItem> agendaOrganisasi;

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	public List<AgendaOrganisasiItem> getAgendaOrganisasi(){
		return agendaOrganisasi;
	}

	public boolean isError(){
		return error;
	}

	public String getMessage(){
		return message;
	}
}