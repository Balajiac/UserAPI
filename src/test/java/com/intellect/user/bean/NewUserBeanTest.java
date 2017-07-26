package com.intellect.user.bean;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.intellect.utils.ApplicationConstants;

public class NewUserBeanTest {

	public NewUserBeanTest() {
	}

	private static Validator validator;
	
	private static Date pastBirthDate;
	private static Date currentBirthDate;
	private static Date futureBirthDate;

	@BeforeClass
	public static void setUpClass() {
		Calendar cal = Calendar.getInstance();
		currentBirthDate = cal.getTime();
		
		cal.add(Calendar.DATE, -1);
		pastBirthDate = cal.getTime();
		
		cal.add(Calendar.DATE, 2);
		futureBirthDate = cal.getTime();

		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Before
	public void setUp() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();
	}

	@Test
	public void testValidUserBean() {
		NewUserBean user = new NewUserBean();
		user.setfName("fName");
		user.setlName("lName");
		user.setPinCode("600001");
		user.setEmail("abc@zas.com");
		user.setBirthDate(pastBirthDate);
		
		Assert.assertEquals("fName", user.getfName());
		Assert.assertEquals("lName", user.getlName());
		Assert.assertEquals("600001", user.getPinCode());
		Assert.assertEquals("abc@zas.com", user.getEmail());
		Assert.assertEquals(pastBirthDate, user.getBirthDate());

		Set<ConstraintViolation<NewUserBean>> violations = validator.validate(user);
		Assert.assertTrue(violations.isEmpty());
	}

	@Test
	public void testNoFirstName() {
		NewUserBean user = new NewUserBean();
		user.setlName("lName");
		user.setPinCode("600001");
		user.setEmail("abc@zas.com");
		user.setBirthDate(pastBirthDate);
		Set<ConstraintViolation<NewUserBean>> violations = validator.validate(user);
		Assert.assertFalse(violations.isEmpty());
		Assert.assertEquals(1, violations.size());
		Assert.assertEquals(ApplicationConstants.FIRST_NAME_REQUIRED, violations.iterator().next().getMessage());
	}

	@Test
	public void testFirstNameEmpty() {
		NewUserBean user = new NewUserBean();
		user.setfName("");
		user.setlName("lName");
		user.setPinCode("600001");
		user.setEmail("abc@zas.com");
		user.setBirthDate(pastBirthDate);
		Set<ConstraintViolation<NewUserBean>> violations = validator.validate(user);
		Assert.assertFalse(violations.isEmpty());
		Assert.assertEquals(1, violations.size());
		Assert.assertEquals(ApplicationConstants.FIRST_NAME_REQUIRED, violations.iterator().next().getMessage());
	}

	@Test
	public void testFirstNameExceedsMaxLength() {
		NewUserBean user = new NewUserBean();
		user.setfName("12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901");
		user.setlName("lName");
		user.setPinCode("600001");
		user.setEmail("abc@zas.com");
		user.setBirthDate(pastBirthDate);
		Set<ConstraintViolation<NewUserBean>> violations = validator.validate(user);
		Assert.assertFalse(violations.isEmpty());
		Assert.assertEquals(1, violations.size());
		Assert.assertEquals("length must be between 0 and 100", violations.iterator().next().getMessage());
	}

	@Test
	public void testNoLastName() {
		NewUserBean user = new NewUserBean();
		user.setfName("fName");
		user.setPinCode("600001");
		user.setEmail("abc@zas.com");
		user.setBirthDate(pastBirthDate);
		Set<ConstraintViolation<NewUserBean>> violations = validator.validate(user);
		Assert.assertFalse(violations.isEmpty());
		Assert.assertEquals(1, violations.size());
		Assert.assertEquals(ApplicationConstants.LAST_NAME_REQUIRED, violations.iterator().next().getMessage());
	}

	@Test
	public void testLastNameEmpty() {
		NewUserBean user = new NewUserBean();
		user.setfName("fName");
		user.setlName("");
		user.setPinCode("600001");
		user.setEmail("abc@zas.com");
		user.setBirthDate(pastBirthDate);
		Set<ConstraintViolation<NewUserBean>> violations = validator.validate(user);
		Assert.assertFalse(violations.isEmpty());
		Assert.assertEquals(1, violations.size());
		Assert.assertEquals(ApplicationConstants.LAST_NAME_REQUIRED, violations.iterator().next().getMessage());
	}

	@Test
	public void testLastNameExceedsMaxLength() {
		NewUserBean user = new NewUserBean();
		user.setfName("fName");
		user.setlName("12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901");
		user.setPinCode("600001");
		user.setEmail("abc@zas.com");
		user.setBirthDate(pastBirthDate);
		Set<ConstraintViolation<NewUserBean>> violations = validator.validate(user);
		Assert.assertFalse(violations.isEmpty());
		Assert.assertEquals(1, violations.size());
		Assert.assertEquals("length must be between 0 and 100", violations.iterator().next().getMessage());
	}

	@Test
	public void testNoPincode() {
		NewUserBean user = new NewUserBean();
		user.setfName("fName");
		user.setlName("lName");
		user.setEmail("abc@zas.com");
		user.setBirthDate(pastBirthDate);
		Set<ConstraintViolation<NewUserBean>> violations = validator.validate(user);
		Assert.assertFalse(violations.isEmpty());
		Assert.assertEquals(1, violations.size());
		Assert.assertEquals(ApplicationConstants.PINCODE_REQUIRED, violations.iterator().next().getMessage());
	}

	@Test
	public void testPinCodeEmpty() {
		NewUserBean user = new NewUserBean();
		user.setfName("fName");
		user.setlName("lName");
		user.setPinCode("");
		user.setEmail("abc@zas.com");
		user.setBirthDate(pastBirthDate);
		Set<ConstraintViolation<NewUserBean>> violations = validator.validate(user);
		Assert.assertFalse(violations.isEmpty());
		Assert.assertEquals(1, violations.size());
		Assert.assertEquals(ApplicationConstants.PINCODE_REQUIRED, violations.iterator().next().getMessage());
	}

	@Test
	public void testPinCodeExceedsMaxLength() {
		NewUserBean user = new NewUserBean();
		user.setfName("fName");
		user.setlName("lName");
		user.setPinCode("12345678901");
		user.setEmail("abc@zas.com");
		user.setBirthDate(pastBirthDate);
		Set<ConstraintViolation<NewUserBean>> violations = validator.validate(user);
		Assert.assertFalse(violations.isEmpty());
		Assert.assertEquals(1, violations.size());
		Assert.assertEquals("length must be between 0 and 10", violations.iterator().next().getMessage());
	}

	@Test
	public void testNoEmail() {
		NewUserBean user = new NewUserBean();
		user.setfName("fName");
		user.setlName("lName");
		user.setPinCode("600001");
		user.setBirthDate(pastBirthDate);
		Set<ConstraintViolation<NewUserBean>> violations = validator.validate(user);
		Assert.assertFalse(violations.isEmpty());
		Assert.assertEquals(1, violations.size());
		Assert.assertEquals(ApplicationConstants.EMAIL_REQUIRED, violations.iterator().next().getMessage());
	}

	@Test
	public void testEmailEmpty() {
		NewUserBean user = new NewUserBean();
		user.setfName("fName");
		user.setlName("lName");
		user.setPinCode("600001");
		user.setEmail("");
		user.setBirthDate(pastBirthDate);
		Set<ConstraintViolation<NewUserBean>> violations = validator.validate(user);
		Assert.assertFalse(violations.isEmpty());
		Assert.assertEquals(1, violations.size());
		Assert.assertEquals(ApplicationConstants.INVALID_EMAIL, violations.iterator().next().getMessage());
	}

	@Test
	public void testInvalidEmail1() {
		NewUserBean user = new NewUserBean();
		user.setfName("fName");
		user.setlName("lName");
		user.setPinCode("600001");
		user.setEmail("Jackyahoo.com");
		user.setBirthDate(pastBirthDate);
		Set<ConstraintViolation<NewUserBean>> violations = validator.validate(user);
		Assert.assertFalse(violations.isEmpty());
		Assert.assertEquals(1, violations.size());
		Assert.assertEquals(ApplicationConstants.INVALID_EMAIL, violations.iterator().next().getMessage());
	}

	@Test
	public void testInvalidEmail2() {
		NewUserBean user = new NewUserBean();
		user.setfName("fName");
		user.setlName("lName");
		user.setPinCode("600001");
		user.setEmail("Jack@yahoocom");
		user.setBirthDate(pastBirthDate);
		Set<ConstraintViolation<NewUserBean>> violations = validator.validate(user);
		Assert.assertFalse(violations.isEmpty());
		Assert.assertEquals(1, violations.size());
		Assert.assertEquals(ApplicationConstants.INVALID_EMAIL, violations.iterator().next().getMessage());
	}

	@Test
	public void testCurrentBirthDate() {
		NewUserBean user = new NewUserBean();
		user.setfName("fName");
		user.setlName("lName");
		user.setPinCode("600001");
		user.setEmail("abc@zas.com");
		user.setBirthDate(currentBirthDate);
		Set<ConstraintViolation<NewUserBean>> violations = validator.validate(user);
		Assert.assertTrue(violations.isEmpty());
	}

	@Test
	public void testFutureBirthDate() {
		NewUserBean user = new NewUserBean();
		user.setfName("fName");
		user.setlName("lName");
		user.setPinCode("600001");
		user.setEmail("abc@zas.com");
		user.setBirthDate(futureBirthDate);
		Set<ConstraintViolation<NewUserBean>> violations = validator.validate(user);
		Assert.assertFalse(violations.isEmpty());
		Assert.assertEquals(1, violations.size());
		Assert.assertEquals(ApplicationConstants.PAST_BIRTH_DATE, violations.iterator().next().getMessage());
	}
}
