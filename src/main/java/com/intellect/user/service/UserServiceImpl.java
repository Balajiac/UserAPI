package com.intellect.user.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.intellect.user.bean.NewUserBean;
import com.intellect.user.bean.UserBean;

/**
 * Contains the methods to handle the user details (Get, Add, update and deactivate)
 * 
 * @author Balaji, Chandrasekaran
 *
 */
@Service
public class UserServiceImpl implements UserService{
	
	private static final AtomicLong counter = new AtomicLong();
	private List<UserBean> users;

	public UserServiceImpl() {
		users = new ArrayList<UserBean>();
	}

	/* (non-Javadoc)
	 * @see com.intellect.user.service.UserService#getAllUsers()
	 */
	public List<UserBean> getAllUsers() {
		List<UserBean> users = new ArrayList<UserBean>(this.users);
		return users;
	}

	/* (non-Javadoc)
	 * @see com.intellect.user.service.UserService#addUser(com.intellect.user.bean.UserBean)
	 */
	public String addUser(NewUserBean newUserBean) {
		UserBean userBean = createUser(newUserBean); 
		users.add(userBean);
		return userBean.getId();
	}
	
	/**
	 * Create a user bean for the new user bean
	 * @param newUserBean
	 * @return
	 */
	private UserBean createUser(NewUserBean newUserBean) {
		String id = String.valueOf(counter.incrementAndGet());
		UserBean userBean = new UserBean();
		userBean.setId(id);
		userBean.setfName(newUserBean.getfName());
		userBean.setlName(newUserBean.getlName());
		userBean.setEmail(newUserBean.getEmail());
		userBean.setPinCode(newUserBean.getPinCode());
		userBean.setBirthDate(newUserBean.getBirthDate());
		return userBean;
	}
	
	

	/* (non-Javadoc)
	 * @see com.intellect.user.service.UserService#isUserExist(com.intellect.user.bean.NewUserBean)
	 */
	public boolean isUserExist(NewUserBean newUserBean) {
		for(UserBean user : users) {
			if(user.getEmail().equals(newUserBean.getEmail())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * To get the user bean for the user id
	 * @param id the user id
	 * @return
	 */
	public UserBean getUser(String id) {
		for(UserBean user : users) {
			if(user.getId().equals(id)) {
				return user;
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.intellect.user.service.UserService#updateUser(java.lang.String, java.lang.String, java.util.Date)
	 */
	public boolean updateUser(String id, String pincode, Date birthDate) {
		UserBean user = getUser(id);
		if( null == user) {
			return false;
		}
		user.setPinCode(pincode);
		user.setBirthDate(birthDate);
		return true;
	}

	/* (non-Javadoc)
	 * @see com.intellect.user.service.UserService#deleteUser(java.lang.String)
	 */
	public boolean deleteUser(String id) {
		for(UserBean user : users) {
			if(user.getId().equals(id)) {
				user.setIsActive(false);
				return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see com.intellect.user.service.UserService#findUserById(java.lang.String)
	 */
	public boolean findUserById(String id) {
		UserBean user = getUser(id);
		return ( null != user);
	}

	/* (non-Javadoc)
	 * @see com.intellect.user.service.UserService#deleteAllUsers()
	 */
	public synchronized boolean deleteAllUsers() {
		for(UserBean user : users) {
			user.setIsActive(false);
		}
		return true;
	}
}
