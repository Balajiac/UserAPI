package com.intellect.user.bean;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.intellect.utils.ApplicationConstants;

/**
 * the bean class to get the update user request details
 * 
 * @author Balaji, Chandrasekaran
 *
 */
public class UpdateUserBean {
	
	@NotEmpty(message = ApplicationConstants.PINCODE_REQUIRED)
	@Length(max = ApplicationConstants.USER_PINCODE_MAXLEN)
	private String pinCode;
	
	@NotNull(message = ApplicationConstants.BIRTH_DATE_REQUIRED)
	@JsonFormat(pattern = ApplicationConstants.DATE_FORMAT)
	@Past(message = ApplicationConstants.PAST_BIRTH_DATE)
	private Date birthDate;

	public UpdateUserBean() {
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
