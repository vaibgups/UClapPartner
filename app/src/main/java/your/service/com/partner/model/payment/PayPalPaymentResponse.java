package your.service.com.partner.model.payment;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class PayPalPaymentResponse{

	@SerializedName("response")
	private Response response;

	@SerializedName("client")
	private Client client;

	@SerializedName("response_type")
	private String responseType;

	public void setResponse(Response response){
		this.response = response;
	}

	public Response getResponse(){
		return response;
	}

	public void setClient(Client client){
		this.client = client;
	}

	public Client getClient(){
		return client;
	}

	public void setResponseType(String responseType){
		this.responseType = responseType;
	}

	public String getResponseType(){
		return responseType;
	}

	@Override
 	public String toString(){
		return 
			"PayPalPaymentResponse{" + 
			"response = '" + response + '\'' + 
			",client = '" + client + '\'' + 
			",response_type = '" + responseType + '\'' + 
			"}";
		}
}