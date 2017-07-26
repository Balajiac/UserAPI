package com.intellect.user.controller;

import java.lang.reflect.Field;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.intellect.user.bean.NewUserBean;
import com.intellect.user.bean.UpdateUserBean;
import com.intellect.user.bean.UserBean;
import com.intellect.user.service.UserServiceImpl;

public class UsersControllerTest {
	
	private static UriComponentsBuilder ucBuilder;
	private static Date birthDate;

	private UsersController controller;

	private NewUserBean newUserBean;
	
	private UserServiceImpl serviceImpl;

	@BeforeClass
	public static void setUpClass() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		birthDate = cal.getTime();
	}

	@Before
	public void setUp() throws URISyntaxException  {
		UriComponents uriComponents = Mockito.mock(UriComponents.class);
		Mockito.when(uriComponents.toUri()).thenReturn(new URI("junit/tesst"));

		ucBuilder = Mockito.mock(UriComponentsBuilder.class);
		Mockito.when(ucBuilder.path(Mockito.anyString())).thenReturn(ucBuilder);
		Mockito.when(ucBuilder.buildAndExpand(Mockito.anyString())).thenReturn(uriComponents);
		
		newUserBean = new NewUserBean();
		newUserBean.setfName("fName");
		newUserBean.setlName("lName");
		newUserBean.setPinCode("600001");
		newUserBean.setEmail("abc@zas.com");
		newUserBean.setBirthDate(birthDate);

		controller = new UsersController();
		
		serviceImpl = Mockito.mock(UserServiceImpl.class);
		Field userServiceField = ReflectionUtils.findField(UsersController.class, "userService");
		ReflectionUtils.makeAccessible(userServiceField);
		ReflectionUtils.setField(userServiceField, controller, serviceImpl);
	}

	@Test
    public void testListAllUsers_NoUsers() {
		Mockito.when(serviceImpl.getAllUsers()).thenReturn(null);
		ResponseEntity<List<UserBean>> response = controller.listAllUsers();
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getStatusCode());
		Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
    public void testListAllUsers_NoUsers_EmptyList() {
		Mockito.when(serviceImpl.getAllUsers()).thenReturn(new ArrayList<UserBean>());
		ResponseEntity<List<UserBean>> response = controller.listAllUsers();
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getStatusCode());
		Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
    public void testListAllUsers() {
		ArrayList<UserBean> users = new ArrayList<UserBean>();
		users.add(new UserBean());
		Mockito.when(serviceImpl.getAllUsers()).thenReturn(users);
		
		ResponseEntity<List<UserBean>> response = controller.listAllUsers();
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getStatusCode());
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertEquals(users, response.getBody());
	}

	@Test
    public void testGetUser_NoUsersExists() {
		Mockito.when(serviceImpl.getAllUsers()).thenReturn(null);
		ResponseEntity<UserBean> response = controller.getUser("1");
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getStatusCode());
		Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
    public void testGetUser_NoUsersExists_EmptyList() {
		Mockito.when(serviceImpl.getAllUsers()).thenReturn(new ArrayList<UserBean>());
		ResponseEntity<UserBean> response = controller.getUser("1");
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getStatusCode());
		Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
    public void testGetUser() {
		ArrayList<UserBean> users = new ArrayList<UserBean>();
		UserBean userBean = new UserBean();
		userBean.setId("1");
		users.add(userBean);
		Mockito.when(serviceImpl.getAllUsers()).thenReturn(users);
		Mockito.when(serviceImpl.getUser(userBean.getId())).thenReturn(userBean);
		
		ResponseEntity<UserBean> response = controller.getUser(userBean.getId());
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getStatusCode());
		Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
		Assert.assertEquals(userBean, response.getBody());
	}

	@Test
    public void testGetUser_InvalidId() {
		ArrayList<UserBean> users = new ArrayList<UserBean>();
		UserBean userBean = new UserBean();
		userBean.setId("1");
		users.add(userBean);
		Mockito.when(serviceImpl.getAllUsers()).thenReturn(users);
		Mockito.when(serviceImpl.getUser(userBean.getId())).thenReturn(userBean);
		
		ResponseEntity<UserBean> response = controller.getUser(userBean.getId()+"_Invalid");
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getStatusCode());
		Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
    public void testCreateUser() {
		NewUserBean userBean = new NewUserBean();
		Mockito.when(serviceImpl.isUserExist(userBean)).thenReturn(false);
		
		ResponseEntity<?> response = controller.createUser(userBean, ucBuilder);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getStatusCode());
		Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
    public void testCreateUser_Conflict() {
		NewUserBean userBean = new NewUserBean();
		Mockito.when(serviceImpl.isUserExist(userBean)).thenReturn(true);
		
		ResponseEntity<?> response = controller.createUser(userBean, ucBuilder);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getStatusCode());
		Assert.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
	}

	@Test
    public void testUpdateUser_UserIdNotFound() {
		String id = "1";
		Mockito.when(serviceImpl.findUserById(id)).thenReturn(false);
		
		UpdateUserBean userBean = new UpdateUserBean();
		ResponseEntity<?> response = controller.updateUser(id, userBean);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getStatusCode());
		Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
    public void testUpdateUser() {
		String id = "1";
		Mockito.when(serviceImpl.findUserById(id)).thenReturn(true);
		Mockito.when(serviceImpl.updateUser(Mockito.anyString(), Mockito.anyString(), Mockito.any(Date.class))).thenReturn(true);
		
		UpdateUserBean userBean = new UpdateUserBean();
		ResponseEntity<?> response = controller.updateUser(id, userBean);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getStatusCode());
		Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test
    public void testUpdateUser_UpdateFailed() {
		String id = "1";
		Mockito.when(serviceImpl.findUserById(id)).thenReturn(true);
		Mockito.when(serviceImpl.updateUser(Mockito.anyString(), Mockito.anyString(), Mockito.any(Date.class))).thenReturn(false);
		
		UpdateUserBean userBean = new UpdateUserBean();
		ResponseEntity<?> response = controller.updateUser(id, userBean);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getStatusCode());
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
    public void testDeleteUser_UserIdNotFound() {
		String id = "1";
		Mockito.when(serviceImpl.findUserById(id)).thenReturn(false);
		
		ResponseEntity<?> response = controller.deleteUser(id);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getStatusCode());
		Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
    public void testDeleteUser() {
		String id = "1";
		Mockito.when(serviceImpl.findUserById(id)).thenReturn(true);
		Mockito.when(serviceImpl.deleteUser(Mockito.anyString())).thenReturn(true);
		
		ResponseEntity<?> response = controller.deleteUser(id);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getStatusCode());
		Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test
    public void testDeleteUser_DeleteFailed() {
		String id = "1";
		Mockito.when(serviceImpl.findUserById(id)).thenReturn(true);
		Mockito.when(serviceImpl.deleteUser(Mockito.anyString())).thenReturn(false);
		
		ResponseEntity<?> response = controller.deleteUser(id);
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getStatusCode());
		Assert.assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
	}

	@Test
    public void testDeleteAllUsers() {
		Mockito.when(serviceImpl.deleteAllUsers()).thenReturn(true);
		
		ResponseEntity<?> response = controller.deleteAllUsers();
		Assert.assertNotNull(response);
		Assert.assertNotNull(response.getStatusCode());
		Assert.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

}
