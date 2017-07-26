package com.intellect.user.bean;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.intellect.utils.ApplicationConstants;

/**
 * The bean class to get the create user request details
 * 
 * @author Balaji, Chandrasekaran
 *
 */
public class NewUserBean {

	@NotEmpty(message = ApplicationConstants.FIRST_NAME_REQUIRED)
	@Length(max = ApplicationConstants.USER_NAME_MAXLEN)
	private String fName;
	
	@NotEmpty(message = ApplicationConstants.LAST_NAME_REQUIRED)
	@Length(max = ApplicationConstants.USER_NAME_MAXLEN)
	private String lName;
	
	@NotNull(message = ApplicationConstants.EMAIL_REQUIRED)
	@Length(max = ApplicationConstants.USER_EMAIL_MAXLEN)
	@Pattern(regexp="[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\."
            +"[a-zA-Z0-9!#$%&'*+/=?^_`{|}~-]+)*@"
            +"(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?",
                 message = ApplicationConstants.INVALID_EMAIL)
	private String email;
	
	@NotEmpty(message = ApplicationConstants.PINCODE_REQUIRED)
	@Length(max = ApplicationConstants.USER_PINCODE_MAXLEN)
	private String pinCode;
	
	@NotNull(message = ApplicationConstants.BIRTH_DATE_REQUIRED)
	@JsonFormat(pattern = ApplicationConstants.DATE_FORMAT)
	@Past(message = ApplicationConstants.PAST_BIRTH_DATE)
	private Date birthDate;

	public NewUserBean() {
	}

	/**
	 * @return the fName
	 */
	public String getfName() {
		return fName;
	}

	/**
	 * @param fName
	 *            the fName to set
	 */
	public void setfName(String fName) {
		this.fName = fName;
	}

	/**
	 * @return the lName
	 */
	public String getlName() {
		return lName;
	}

	/**
	 * @param lName
	 *            the lName to set
	 */
	public void setlName(String lName) {
		this.lName = lName;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 *            the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the pinCode
	 */
	public String getPinCode() {
		return pinCode;
	}

	/**
	 * @param pinCode
	 *            the pinCode to set
	 */
	public void setPinCode(String pinCode) {
		this.pinCode = pinCode;
	}

	/**
	 * @return the birthDate
	 */
	public Date getBirthDate() {
		return birthDate;
	}

	/**
	 * @param birthDate
	 *            the birthDate to set
	 */
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
}
