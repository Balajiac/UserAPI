package com.intellect.user.bean;

import java.util.Calendar;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

public class UserBeanTest {

	public UserBeanTest() {
	}

	@Test
	public void testValidUserBean() {
		UserBean user = new UserBean();
		user.setId("id");
		user.setfName("fName");
		user.setlName("lName");
		user.setPinCode("600001");
		user.setEmail("abc@zas.com");

		Calendar cal = Calendar.getInstance();
		Date birthDate = cal.getTime();
		user.setBirthDate(birthDate);
		
		Assert.assertEquals("id", user.getId());
		Assert.assertEquals("fName", user.getfName());
		Assert.assertEquals("lName", user.getlName());
		Assert.assertEquals("600001", user.getPinCode());
		Assert.assertEquals("abc@zas.com", user.getEmail());
		Assert.assertEquals(birthDate, user.getBirthDate());
		Assert.assertTrue(user.getIsActive());

		user.setIsActive(false);
		Assert.assertFalse(user.getIsActive());

		user.setIsActive(true);
		Assert.assertTrue(user.getIsActive());
	}
}
