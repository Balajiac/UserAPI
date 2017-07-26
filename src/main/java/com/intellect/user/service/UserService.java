package com.intellect.user.service;

import java.util.Date;
import java.util.List;

import com.intellect.user.bean.NewUserBean;
import com.intellect.user.bean.UserBean;

/**
 * This interface contains the methods used to get, create, modify and delete the user details 
 * 
 * @author Balaji, Chandrasekaran
 *
 */
public interface UserService {
	List<UserBean> getAllUsers();
	UserBean getUser(String id);
	String addUser(NewUserBean newUserBean);
	boolean isUserExist(NewUserBean newUserBean);
	boolean findUserById(String id);
	boolean updateUser(String id, String pincode, Date birthDate);
	boolean deleteUser(String id);
	boolean deleteAllUsers();
}
