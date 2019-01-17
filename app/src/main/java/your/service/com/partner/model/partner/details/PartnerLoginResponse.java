package your.service.com.partner.model.partner.details;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class PartnerLoginResponse{

	@SerializedName("usr")
	private List<UsrItem> usr;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public void setUsr(List<UsrItem> usr){
		this.usr = usr;
	}

	public List<UsrItem> getUsr(){
		return usr;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"PartnerLoginResponse{" + 
			"usr = '" + usr + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}