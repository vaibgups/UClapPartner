package your.service.com.partner.model.partner.profile;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class AllSubProfileResponse{

	@SerializedName("subcategory")
	private List<SubcategoryItem> subcategory;


	@SerializedName("status")
	private String status;

	public void setSubcategory(List<SubcategoryItem> subcategory){
		this.subcategory = subcategory;
	}

	public List<SubcategoryItem> getSubcategory(){
		return subcategory;
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
			"AllSubProfileResponse{" + 
			"subcategory = '" + subcategory + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}