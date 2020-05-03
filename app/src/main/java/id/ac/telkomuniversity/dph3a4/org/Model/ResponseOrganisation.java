package id.ac.telkomuniversity.dph3a4.org.Model;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResponseOrganisation{

	@SerializedName("data")
	private List<OrganisationItem> data;

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	public void setData(List<OrganisationItem> data){
		this.data = data;
	}

	public List<OrganisationItem> getData(){
		return data;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"ResponseOrganisation{" + 
			"data = '" + data + '\'' + 
			",error = '" + error + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}