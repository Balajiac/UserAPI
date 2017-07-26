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

public class UpdateUserBeanTest {

	public UpdateUserBeanTest() {
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
		UpdateUserBean user = new UpdateUserBean();
		user.setPinCode("600001");
		user.setBirthDate(pastBirthDate);
		
		Assert.assertEquals("600001", user.getPinCode());
		Assert.assertEquals(pastBirthDate, user.getBirthDate());
		
		Set<ConstraintViolation<UpdateUserBean>> violations = validator.validate(user);
		Assert.assertTrue(violations.isEmpty());
	}

	@Test
	public void testNoPincode() {
		UpdateUserBean user = new UpdateUserBean();
		user.setBirthDate(pastBirthDate);
		Set<ConstraintViolation<UpdateUserBean>> violations = validator.validate(user);
		Assert.assertFalse(violations.isEmpty());
		Assert.assertEquals(1, violations.size());
		Assert.assertEquals(ApplicationConstants.PINCODE_REQUIRED, violations.iterator().next().getMessage());
	}

	@Test
	public void testPinCodeEmpty() {
		UpdateUserBean user = new UpdateUserBean();
		user.setPinCode("");
		user.setBirthDate(pastBirthDate);
		Set<ConstraintViolation<UpdateUserBean>> violations = validator.validate(user);
		Assert.assertFalse(violations.isEmpty());
		Assert.assertEquals(1, violations.size());
		Assert.assertEquals(ApplicationConstants.PINCODE_REQUIRED, violations.iterator().next().getMessage());
	}

	@Test
	public void testPinCodeExceedsMaxLength() {
		UpdateUserBean user = new UpdateUserBean();
		user.setPinCode("12345678901");
		user.setBirthDate(pastBirthDate);
		Set<ConstraintViolation<UpdateUserBean>> violations = validator.validate(user);
		Assert.assertFalse(violations.isEmpty());
		Assert.assertEquals(1, violations.size());
		Assert.assertEquals("length must be between 0 and 10", violations.iterator().next().getMessage());
	}

	@Test
	public void testCurrentBirthDate() {
		UpdateUserBean user = new UpdateUserBean();
		user.setPinCode("600001");
		user.setBirthDate(currentBirthDate);
		Set<ConstraintViolation<UpdateUserBean>> violations = validator.validate(user);
		Assert.assertTrue(violations.isEmpty());
	}

	@Test
	public void testFutureBirthDate() {
		UpdateUserBean user = new UpdateUserBean();
		user.setPinCode("600001");
		user.setBirthDate(futureBirthDate);
		Set<ConstraintViolation<UpdateUserBean>> violations = validator.validate(user);
		Assert.assertFalse(violations.isEmpty());
		Assert.assertEquals(1, violations.size());
		Assert.assertEquals(ApplicationConstants.PAST_BIRTH_DATE, violations.iterator().next().getMessage());
	}
}
