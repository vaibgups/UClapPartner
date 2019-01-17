package your.service.com.partner.model.payment;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("com.robohorse.robopojogenerator")
public class Response{

	@SerializedName("create_time")
	private String createTime;

	@SerializedName("id")
	private String id;

	@SerializedName("state")
	private String state;

	@SerializedName("intent")
	private String intent;

	public void setCreateTime(String createTime){
		this.createTime = createTime;
	}

	public String getCreateTime(){
		return createTime;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setState(String state){
		this.state = state;
	}

	public String getState(){
		return state;
	}

	public void setIntent(String intent){
		this.intent = intent;
	}

	public String getIntent(){
		return intent;
	}

	@Override
 	public String toString(){
		return 
			"Response{" + 
			"create_time = '" + createTime + '\'' + 
			",id = '" + id + '\'' + 
			",state = '" + state + '\'' + 
			",intent = '" + intent + '\'' + 
			"}";
		}
}