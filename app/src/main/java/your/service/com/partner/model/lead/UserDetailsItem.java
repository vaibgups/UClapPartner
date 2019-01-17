package your.service.com.partner.model.lead;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Generated("com.robohorse.robopojogenerator")
public class UserDetailsItem implements Serializable {

	@SerializedName("user_status")
	private Object userStatus;

	@SerializedName("user_email")
	private String userEmail;

	@SerializedName("user_name")
	private String userName;

	@SerializedName("user_address")
	private Object userAddress;

	@SerializedName("mobile")
	private String mobile;

	@SerializedName("user_lat")
	private Object userLat;

	@SerializedName("verify_code")
	private String verifyCode;

	@SerializedName("country_code")
	private String countryCode;

	@SerializedName("profile_img")
	private Object profileImg;

	@SerializedName("user_lng")
	private Object userLng;

	@SerializedName("user_id")
	private String userId;

	@SerializedName("user_otp")
	private Object userOtp;

	@SerializedName("created_on")
	private String createdOn;

	@SerializedName("user_session_token")
	private Object userSessionToken;

	@SerializedName("user_token")
	private String userToken;

	public void setUserStatus(Object userStatus){
		this.userStatus = userStatus;
	}

	public Object getUserStatus(){
		return userStatus;
	}

	public void setUserEmail(String userEmail){
		this.userEmail = userEmail;
	}

	public String getUserEmail(){
		return userEmail;
	}

	public void setUserName(String userName){
		this.userName = userName;
	}

	public String getUserName(){
		return userName;
	}

	public void setUserAddress(Object userAddress){
		this.userAddress = userAddress;
	}

	public Object getUserAddress(){
		return userAddress;
	}

	public void setMobile(String mobile){
		this.mobile = mobile;
	}

	public String getMobile(){
		return mobile;
	}

	public void setUserLat(Object userLat){
		this.userLat = userLat;
	}

	public Object getUserLat(){
		return userLat;
	}

	public void setVerifyCode(String verifyCode){
		this.verifyCode = verifyCode;
	}

	public String getVerifyCode(){
		return verifyCode;
	}

	public void setCountryCode(String countryCode){
		this.countryCode = countryCode;
	}

	public String getCountryCode(){
		return countryCode;
	}

	public void setProfileImg(Object profileImg){
		this.profileImg = profileImg;
	}

	public Object getProfileImg(){
		return profileImg;
	}

	public void setUserLng(Object userLng){
		this.userLng = userLng;
	}

	public Object getUserLng(){
		return userLng;
	}

	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getUserId(){
		return userId;
	}

	public void setUserOtp(Object userOtp){
		this.userOtp = userOtp;
	}

	public Object getUserOtp(){
		return userOtp;
	}

	public void setCreatedOn(String createdOn){
		this.createdOn = createdOn;
	}

	public String getCreatedOn(){
		return createdOn;
	}

	public void setUserSessionToken(Object userSessionToken){
		this.userSessionToken = userSessionToken;
	}

	public Object getUserSessionToken(){
		return userSessionToken;
	}

	public void setUserToken(String userToken){
		this.userToken = userToken;
	}

	public String getUserToken(){
		return userToken;
	}

	@Override
 	public String toString(){
		return 
			"UserDetailsItem{" + 
			"user_status = '" + userStatus + '\'' + 
			",user_email = '" + userEmail + '\'' + 
			",user_name = '" + userName + '\'' + 
			",user_address = '" + userAddress + '\'' + 
			",mobile = '" + mobile + '\'' + 
			",user_lat = '" + userLat + '\'' + 
			",verify_code = '" + verifyCode + '\'' + 
			",country_code = '" + countryCode + '\'' + 
			",profile_img = '" + profileImg + '\'' + 
			",user_lng = '" + userLng + '\'' + 
			",user_id = '" + userId + '\'' + 
			",user_otp = '" + userOtp + '\'' + 
			",created_on = '" + createdOn + '\'' + 
			",user_session_token = '" + userSessionToken + '\'' + 
			",user_token = '" + userToken + '\'' + 
			"}";
		}
}