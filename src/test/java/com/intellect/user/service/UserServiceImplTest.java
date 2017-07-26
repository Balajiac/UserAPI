package com.intellect.user.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.intellect.user.bean.NewUserBean;
import com.intellect.user.bean.UserBean;

public class UserServiceImplTest {
	
	private UserServiceImpl impl;
	private static Date birthDate;

	private NewUserBean user;

	@BeforeClass
	public static void setUpClass() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		birthDate = cal.getTime();
	}

	@Before
	public void setUp() {
		user = new NewUserBean();
		user.setfName("fName");
		user.setlName("lName");
		user.setPinCode("600001");
		user.setEmail("abc@zas.com");
		user.setBirthDate(birthDate);

		impl = new UserServiceImpl();
	}

	@Test
	public void testAddUser() {
		List<UserBean> users = impl.getAllUsers();
		Assert.assertTrue(users.isEmpty());

		impl.addUser(user);

		users = impl.getAllUsers();
		Assert.assertFalse(users.isEmpty());
		Assert.assertEquals(1, users.size());
		UserBean userBean = users.get(0);
		Assert.assertNotNull(userBean);
		Assert.assertNotNull(userBean.getId());
		Assert.assertTrue(userBean.getIsActive());
		Assert.assertEquals(user.getfName(), userBean.getfName());
		Assert.assertEquals(user.getlName(), userBean.getlName());
		Assert.assertEquals(user.getPinCode(), userBean.getPinCode());
		Assert.assertEquals(user.getEmail(), userBean.getEmail());
		Assert.assertEquals(user.getBirthDate(), userBean.getBirthDate());
	}

	@Test
	public void testNoUserExists() {
		List<UserBean> users = impl.getAllUsers();
		Assert.assertTrue(users.isEmpty());

		Assert.assertFalse(impl.isUserExist(user));
	}

	@Test
	public void testIsUserExists() {
		List<UserBean> users = impl.getAllUsers();
		Assert.assertTrue(users.isEmpty());

		impl.addUser(user);

		Assert.assertTrue(impl.isUserExist(user));
	}

	@Test
	public void testIsUserNotExists() {
		List<UserBean> users = impl.getAllUsers();
		Assert.assertTrue(users.isEmpty());

		impl.addUser(user);

		user.setEmail("abc1@zas.com");
		Assert.assertFalse(impl.isUserExist(user));
	}

	@Test
	public void testUpdateUser_NoUserExists() {
		List<UserBean> users = impl.getAllUsers();
		Assert.assertTrue(users.isEmpty());

		Assert.assertFalse(impl.updateUser("1", "600001", user.getBirthDate()));
	}

	@Test
	public void testUpdateUser() {
		List<UserBean> users = impl.getAllUsers();
		Assert.assertTrue(users.isEmpty());

		impl.addUser(user);

		users = impl.getAllUsers();
		Assert.assertFalse(users.isEmpty());
		Assert.assertEquals(1, users.size());
		UserBean userBean = users.get(0);
		String id = userBean.getId();
		Assert.assertNotNull(id);

		Calendar cal = Calendar.getInstance();
		Date birthDate = cal.getTime();
		Assert.assertTrue(impl.updateUser(id, "600002", birthDate));


		users = impl.getAllUsers();
		Assert.assertFalse(users.isEmpty());
		Assert.assertEquals(1, users.size());
		userBean = users.get(0);
		Assert.assertNotNull(userBean);
		Assert.assertNotNull(userBean.getId());
		Assert.assertTrue(userBean.getIsActive());
		Assert.assertEquals(user.getfName(), userBean.getfName());
		Assert.assertEquals(user.getlName(), userBean.getlName());
		Assert.assertNotEquals(user.getPinCode(), userBean.getPinCode());
		Assert.assertEquals(user.getEmail(), userBean.getEmail());
		Assert.assertNotEquals(user.getBirthDate(), userBean.getBirthDate());
		Assert.assertEquals(id, userBean.getId());
		Assert.assertEquals("600002", userBean.getPinCode());
		Assert.assertEquals(birthDate, userBean.getBirthDate());
	}

	@Test
	public void testUpdateUser_NotExists() {
		List<UserBean> users = impl.getAllUsers();
		Assert.assertTrue(users.isEmpty());

		impl.addUser(user);

		users = impl.getAllUsers();
		Assert.assertFalse(users.isEmpty());
		Assert.assertEquals(1, users.size());
		UserBean userBean = users.get(0);
		String id = userBean.getId() + "_Invalid";
		Assert.assertNotNull(id);

		Calendar cal = Calendar.getInstance();
		Date birthDate = cal.getTime();
		Assert.assertFalse(impl.updateUser(id, "600002", birthDate));


		users = impl.getAllUsers();
		Assert.assertFalse(users.isEmpty());
		Assert.assertEquals(1, users.size());
		userBean = users.get(0);
		Assert.assertNotNull(userBean);
		Assert.assertNotNull(userBean.getId());
		Assert.assertTrue(userBean.getIsActive());
		Assert.assertEquals(user.getfName(), userBean.getfName());
		Assert.assertEquals(user.getlName(), userBean.getlName());
		Assert.assertEquals(user.getPinCode(), userBean.getPinCode());
		Assert.assertEquals(user.getEmail(), userBean.getEmail());
		Assert.assertEquals(user.getBirthDate(), userBean.getBirthDate());
		Assert.assertNotEquals(id, userBean.getId());
		Assert.assertNotEquals("600002", userBean.getPinCode());
		Assert.assertNotEquals(birthDate, userBean.getBirthDate());
	}

	@Test
	public void testFindUserById_NoUserExists() {
		List<UserBean> users = impl.getAllUsers();
		Assert.assertTrue(users.isEmpty());

		Assert.assertFalse(impl.findUserById("1"));
	}

	@Test
	public void testFindUserById() {
		List<UserBean> users = impl.getAllUsers();
		Assert.assertTrue(users.isEmpty());

		impl.addUser(user);

		users = impl.getAllUsers();
		Assert.assertFalse(users.isEmpty());
		Assert.assertEquals(1, users.size());
		UserBean userBean = users.get(0);
		String id = userBean.getId();
		Assert.assertNotNull(id);

		Assert.assertTrue(impl.findUserById(id));
	}

	@Test
	public void testFindUserById_NotExists() {
		List<UserBean> users = impl.getAllUsers();
		Assert.assertTrue(users.isEmpty());

		impl.addUser(user);

		users = impl.getAllUsers();
		Assert.assertFalse(users.isEmpty());
		Assert.assertEquals(1, users.size());
		UserBean userBean = users.get(0);
		String id = userBean.getId() + "_Invalid";
		Assert.assertNotNull(id);

		Assert.assertFalse(impl.findUserById(id));
	}

	@Test
	public void testDeleteUser_NoUserExists() {
		List<UserBean> users = impl.getAllUsers();
		Assert.assertTrue(users.isEmpty());

		Assert.assertFalse(impl.deleteUser("1"));
	}

	@Test
	public void testDeleteUser() {
		List<UserBean> users = impl.getAllUsers();
		Assert.assertTrue(users.isEmpty());

		impl.addUser(user);

		users = impl.getAllUsers();
		Assert.assertFalse(users.isEmpty());
		Assert.assertEquals(1, users.size());
		UserBean userBean = users.get(0);
		String id = userBean.getId();
		Assert.assertNotNull(id);
		Assert.assertTrue(userBean.getIsActive());

		Assert.assertTrue(impl.deleteUser(id));

		users = impl.getAllUsers();
		Assert.assertFalse(users.isEmpty());
		Assert.assertEquals(1, users.size());
		userBean = users.get(0);
		Assert.assertNotNull(userBean);
		Assert.assertNotNull(userBean.getId());
		Assert.assertFalse(userBean.getIsActive());
	}

	@Test
	public void testDeleteUser_NotExists() {
		List<UserBean> users = impl.getAllUsers();
		Assert.assertTrue(users.isEmpty());

		impl.addUser(user);

		users = impl.getAllUsers();
		Assert.assertFalse(users.isEmpty());
		Assert.assertEquals(1, users.size());
		UserBean userBean = users.get(0);
		String id = userBean.getId() + "_Invalid";
		Assert.assertNotNull(id);
		Assert.assertTrue(userBean.getIsActive());

		Assert.assertFalse(impl.deleteUser(id));

		users = impl.getAllUsers();
		Assert.assertFalse(users.isEmpty());
		Assert.assertEquals(1, users.size());
		userBean = users.get(0);
		Assert.assertNotNull(userBean);
		Assert.assertNotNull(userBean.getId());
		Assert.assertTrue(userBean.getIsActive());
	}

	@Test
	public void testDeleteAllUsers_NoUserExists() {
		List<UserBean> users = impl.getAllUsers();
		Assert.assertTrue(users.isEmpty());

		Assert.assertTrue(impl.deleteAllUsers());

		users = impl.getAllUsers();
		Assert.assertTrue(users.isEmpty());
	}

	@Test
	public void testDeleteAllUsers_OneUserExists() {
		List<UserBean> users = impl.getAllUsers();
		Assert.assertTrue(users.isEmpty());

		impl.addUser(user);

		users = impl.getAllUsers();
		Assert.assertFalse(users.isEmpty());
		Assert.assertEquals(1, users.size());
		UserBean userBean = users.get(0);
		String id = userBean.getId();
		Assert.assertTrue(userBean.getIsActive());
		Assert.assertNotNull(id);

		Assert.assertTrue(impl.deleteAllUsers());

		users = impl.getAllUsers();
		Assert.assertFalse(users.isEmpty());
		Assert.assertEquals(1, users.size());
		userBean = users.get(0);
		Assert.assertNotNull(userBean);
		Assert.assertNotNull(userBean.getId());
		Assert.assertFalse(userBean.getIsActive());
	}

	@Test
	public void testDeleteAllUsers_MoreThanOneUserExists() {
		List<UserBean> users = impl.getAllUsers();
		Assert.assertTrue(users.isEmpty());

		impl.addUser(user);
		
		user.setEmail("email@abc.com");
		impl.addUser(user);

		users = impl.getAllUsers();
		Assert.assertFalse(users.isEmpty());
		Assert.assertEquals(2, users.size());
		UserBean userBean = users.get(0);
		Assert.assertTrue(userBean.getIsActive());
		Assert.assertNotNull(userBean.getId());
		
		userBean = users.get(1);
		Assert.assertTrue(userBean.getIsActive());
		Assert.assertNotNull(userBean.getId());

		Assert.assertTrue(impl.deleteAllUsers());

		users = impl.getAllUsers();
		Assert.assertFalse(users.isEmpty());
		Assert.assertEquals(2, users.size());

		userBean = users.get(0);
		Assert.assertNotNull(userBean);
		Assert.assertNotNull(userBean.getId());
		Assert.assertFalse(userBean.getIsActive());
		
		userBean = users.get(1);
		Assert.assertNotNull(userBean);
		Assert.assertNotNull(userBean.getId());
		Assert.assertFalse(userBean.getIsActive());
	}

}
