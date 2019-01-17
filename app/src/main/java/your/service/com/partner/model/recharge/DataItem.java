package your.service.com.partner.model.recharge;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DataItem{

	@SerializedName("s_point")
	private String sPoint;

	@SerializedName("s_price")
	private String sPrice;

	@SerializedName("created_on")
	private String createdOn;

	@SerializedName("bid_id")
	private String bidId;

	@SerializedName("status")
	private String status;

	public void setSPoint(String sPoint){
		this.sPoint = sPoint;
	}

	public String getSPoint(){
		return sPoint;
	}

	public void setSPrice(String sPrice){
		this.sPrice = sPrice;
	}

	public String getSPrice(){
		return sPrice;
	}

	public void setCreatedOn(String createdOn){
		this.createdOn = createdOn;
	}

	public String getCreatedOn(){
		return createdOn;
	}

	public void setBidId(String bidId){
		this.bidId = bidId;
	}

	public String getBidId(){
		return bidId;
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
			"TransactionData{" +
			"s_point = '" + sPoint + '\'' + 
			",s_price = '" + sPrice + '\'' + 
			",created_on = '" + createdOn + '\'' + 
			",bid_id = '" + bidId + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}