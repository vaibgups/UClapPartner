package your.service.com.partner.model.lead;

import java.io.Serializable;
import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class LeadItem implements Serializable {

	@SerializedName("service_location")
	private String serviceLocation;

	@SerializedName("service")
	private List<ServiceItem> service;

	@SerializedName("service_date")
	private String serviceDate;

	@SerializedName("credit")
	private String credit;

	@SerializedName("uniqe_id")
	private String uniqeId;

	@SerializedName("userid")
	private String userid;

	@SerializedName("userDetails")
	private List<UserDetailsItem> userDetails;

	public void setServiceLocation(String serviceLocation){
		this.serviceLocation = serviceLocation;
	}

	public String getServiceLocation(){
		return serviceLocation;
	}

	public void setService(List<ServiceItem> service){
		this.service = service;
	}

	public List<ServiceItem> getService(){
		return service;
	}

	public void setServiceDate(String serviceDate){
		this.serviceDate = serviceDate;
	}

	public String getServiceDate(){
		return serviceDate;
	}

	public void setCredit(String credit){
		this.credit = credit;
	}

	public String getCredit(){
		return credit;
	}

	public void setUniqeId(String uniqeId){
		this.uniqeId = uniqeId;
	}

	public String getUniqeId(){
		return uniqeId;
	}

	public void setUserid(String userid){
		this.userid = userid;
	}

	public String getUserid(){
		return userid;
	}

	public void setUserDetails(List<UserDetailsItem> userDetails){
		this.userDetails = userDetails;
	}

	public List<UserDetailsItem> getUserDetails(){
		return userDetails;
	}

	@Override
 	public String toString(){
		return 
			"LeadItem{" + 
			"service_location = '" + serviceLocation + '\'' + 
			",service = '" + service + '\'' + 
			",service_date = '" + serviceDate + '\'' + 
			",credit = '" + credit + '\'' + 
			",uniqe_id = '" + uniqeId + '\'' + 
			",userid = '" + userid + '\'' + 
			",userDetails = '" + userDetails + '\'' + 
			"}";
		}
}