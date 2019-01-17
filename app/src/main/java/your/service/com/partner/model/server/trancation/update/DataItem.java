package your.service.com.partner.model.server.trancation.update;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class DataItem{

	@SerializedName("transaction_id")
	private String transactionId;

	@SerializedName("transaction_date")
	private String transactionDate;

	@SerializedName("amount")
	private String amount;

	@SerializedName("transaction_status")
	private String transactionStatus;

	@SerializedName("transaction_report")
	private String transactionReport;

	@SerializedName("total_credit")
	private String totalCredit;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("partner_id")
	private String partnerId;

	@SerializedName("credits")
	private String credits;

	@SerializedName("transaction_paypal_id")
	private String transactionPaypalId;

	@SerializedName("created_on")
	private String createdOn;

	@SerializedName("id")
	private String id;

	@SerializedName("transaction_com_date")
	private String transactionComDate;

	public void setTransactionId(String transactionId){
		this.transactionId = transactionId;
	}

	public String getTransactionId(){
		return transactionId;
	}

	public void setTransactionDate(String transactionDate){
		this.transactionDate = transactionDate;
	}

	public String getTransactionDate(){
		return transactionDate;
	}

	public void setAmount(String amount){
		this.amount = amount;
	}

	public String getAmount(){
		return amount;
	}

	public void setTransactionStatus(String transactionStatus){
		this.transactionStatus = transactionStatus;
	}

	public String getTransactionStatus(){
		return transactionStatus;
	}

	public void setTransactionReport(String transactionReport){
		this.transactionReport = transactionReport;
	}

	public String getTransactionReport(){
		return transactionReport;
	}

	public void setTotalCredit(String totalCredit){
		this.totalCredit = totalCredit;
	}

	public String getTotalCredit(){
		return totalCredit;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setPartnerId(String partnerId){
		this.partnerId = partnerId;
	}

	public String getPartnerId(){
		return partnerId;
	}

	public void setCredits(String credits){
		this.credits = credits;
	}

	public String getCredits(){
		return credits;
	}

	public void setTransactionPaypalId(String transactionPaypalId){
		this.transactionPaypalId = transactionPaypalId;
	}

	public String getTransactionPaypalId(){
		return transactionPaypalId;
	}

	public void setCreatedOn(String createdOn){
		this.createdOn = createdOn;
	}

	public String getCreatedOn(){
		return createdOn;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setTransactionComDate(String transactionComDate){
		this.transactionComDate = transactionComDate;
	}

	public String getTransactionComDate(){
		return transactionComDate;
	}

	@Override
 	public String toString(){
		return 
			"TransactionData{" +
			"transaction_id = '" + transactionId + '\'' + 
			",transaction_date = '" + transactionDate + '\'' + 
			",amount = '" + amount + '\'' + 
			",transaction_status = '" + transactionStatus + '\'' + 
			",transaction_report = '" + transactionReport + '\'' + 
			",total_credit = '" + totalCredit + '\'' + 
			",mobile = '" + mobile + '\'' + 
			",partner_id = '" + partnerId + '\'' + 
			",credits = '" + credits + '\'' + 
			",transaction_paypal_id = '" + transactionPaypalId + '\'' + 
			",created_on = '" + createdOn + '\'' + 
			",id = '" + id + '\'' + 
			",transaction_com_date = '" + transactionComDate + '\'' + 
			"}";
		}
}