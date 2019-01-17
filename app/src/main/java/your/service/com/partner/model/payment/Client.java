package your.service.com.partner.model.payment;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Client{

	@SerializedName("environment")
	private String environment;

	@SerializedName("product_name")
	private String productName;

	@SerializedName("platform")
	private String platform;

	@SerializedName("paypal_sdk_version")
	private String paypalSdkVersion;

	public void setEnvironment(String environment){
		this.environment = environment;
	}

	public String getEnvironment(){
		return environment;
	}

	public void setProductName(String productName){
		this.productName = productName;
	}

	public String getProductName(){
		return productName;
	}

	public void setPlatform(String platform){
		this.platform = platform;
	}

	public String getPlatform(){
		return platform;
	}

	public void setPaypalSdkVersion(String paypalSdkVersion){
		this.paypalSdkVersion = paypalSdkVersion;
	}

	public String getPaypalSdkVersion(){
		return paypalSdkVersion;
	}

	@Override
 	public String toString(){
		return 
			"Client{" + 
			"environment = '" + environment + '\'' + 
			",product_name = '" + productName + '\'' + 
			",platform = '" + platform + '\'' + 
			",paypal_sdk_version = '" + paypalSdkVersion + '\'' + 
			"}";
		}
}