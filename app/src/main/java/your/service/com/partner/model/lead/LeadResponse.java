package your.service.com.partner.model.lead;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class LeadResponse implements Serializable {

	@SerializedName("lead")
	private List<LeadItem> lead;

	@SerializedName("status")
	private String status;

	public void setLead(List<LeadItem> lead){
		this.lead = lead;
	}

	public List<LeadItem> getLead(){
		return lead;
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
			"LeadResponse{" + 
			"lead = '" + lead + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}