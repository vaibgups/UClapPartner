package your.service.com.partner.model.lead;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("com.robohorse.robopojogenerator")
public class ServiceItem implements Serializable {

	@SerializedName("question")
	private String question;

	@SerializedName("lng")
	private String lng;

	@SerializedName("cate_id")
	private String cateId;

	@SerializedName("userid")
	private String userid;

	@SerializedName("question_id")
	private String questionId;

	@SerializedName("created_on")
	private String createdOn;

	@SerializedName("service")
	private String service;

	@SerializedName("attribute_id")
	private String attributeId;

	@SerializedName("service_status")
	private String serviceStatus;

	@SerializedName("location")
	private String location;

	@SerializedName("id")
	private String id;

	@SerializedName("attribute")
	private String attribute;

	@SerializedName("uniqe_id")
	private String uniqeId;

	@SerializedName("lat")
	private String lat;

	@SerializedName("status")
	private String status;

	public void setQuestion(String question){
		this.question = question;
	}

	public String getQuestion(){
		return question;
	}

	public void setLng(String lng){
		this.lng = lng;
	}

	public String getLng(){
		return lng;
	}

	public void setCateId(String cateId){
		this.cateId = cateId;
	}

	public String getCateId(){
		return cateId;
	}

	public void setUserid(String userid){
		this.userid = userid;
	}

	public String getUserid(){
		return userid;
	}

	public void setQuestionId(String questionId){
		this.questionId = questionId;
	}

	public String getQuestionId(){
		return questionId;
	}

	public void setCreatedOn(String createdOn){
		this.createdOn = createdOn;
	}

	public String getCreatedOn(){
		return createdOn;
	}

	public void setService(String service){
		this.service = service;
	}

	public String getService(){
		return service;
	}

	public void setAttributeId(String attributeId){
		this.attributeId = attributeId;
	}

	public String getAttributeId(){
		return attributeId;
	}

	public void setServiceStatus(String serviceStatus){
		this.serviceStatus = serviceStatus;
	}

	public String getServiceStatus(){
		return serviceStatus;
	}

	public void setLocation(String location){
		this.location = location;
	}

	public String getLocation(){
		return location;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setAttribute(String attribute){
		this.attribute = attribute;
	}

	public String getAttribute(){
		return attribute;
	}

	public void setUniqeId(String uniqeId){
		this.uniqeId = uniqeId;
	}

	public String getUniqeId(){
		return uniqeId;
	}

	public void setLat(String lat){
		this.lat = lat;
	}

	public String getLat(){
		return lat;
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
			"ServiceItem{" + 
			"question = '" + question + '\'' + 
			",lng = '" + lng + '\'' + 
			",cate_id = '" + cateId + '\'' + 
			",userid = '" + userid + '\'' + 
			",question_id = '" + questionId + '\'' + 
			",created_on = '" + createdOn + '\'' + 
			",service = '" + service + '\'' + 
			",attribute_id = '" + attributeId + '\'' + 
			",service_status = '" + serviceStatus + '\'' + 
			",location = '" + location + '\'' + 
			",id = '" + id + '\'' + 
			",attribute = '" + attribute + '\'' + 
			",uniqe_id = '" + uniqeId + '\'' + 
			",lat = '" + lat + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}