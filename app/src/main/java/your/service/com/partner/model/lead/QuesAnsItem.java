package your.service.com.partner.model.lead;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("com.robohorse.robopojogenerator")
public class QuesAnsItem implements Serializable {

	@SerializedName("question")
	private String question;

	@SerializedName("attribute")
	private String attribute;

	public void setQuestion(String question){
		this.question = question;
	}

	public String getQuestion(){
		return question;
	}

	public void setAttribute(String attribute){
		this.attribute = attribute;
	}

	public String getAttribute(){
		return attribute;
	}

	@Override
 	public String toString(){
		return 
			"QuesAnsItem{" + 
			"question = '" + question + '\'' + 
			",attribute = '" + attribute + '\'' + 
			"}";
		}
}