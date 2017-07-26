package com.intellect.user.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.intellect.error.bean.CustomErrorType;
import com.intellect.user.bean.NewUserBean;
import com.intellect.user.bean.UpdateUserBean;
import com.intellect.user.bean.UserBean;
import com.intellect.user.service.UserService;

/**
 * All the User API REST Services are contained here
 * 
 * @author Balaji, Chandrasekaran
 *
 */
@RestController
@RequestMapping("/api")
public class UsersController {
	public static final Logger logger = LoggerFactory.getLogger(UsersController.class);
	
	@Autowired
	private UserService userService;

	public UsersController() {
	}
    
	/**
	 * to retrieve all the users 
	 * @return
	 */
    @RequestMapping(value = "/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<UserBean>> listAllUsers() {
        List<UserBean> users = userService.getAllUsers();
        if ((null == users) || users.isEmpty()) {
            return new ResponseEntity<List<UserBean>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<UserBean>>(users, HttpStatus.OK);
    }
    
    /**
     * to retrieve a single user for the particular user id 
     * @param id the user id
     * @return
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserBean> getUser(@PathVariable("id") String id) {
        List<UserBean> users = userService.getAllUsers();
        if ((null == users) || users.isEmpty()) {
            return new ResponseEntity<UserBean>(HttpStatus.NOT_FOUND);
        }
        UserBean user = userService.getUser(id); 
        if (null == user) {
            logger.error("User with id {} not found.", id);
            return new ResponseEntity<UserBean>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<UserBean>(user, HttpStatus.OK);
    }
     
    /**
     * to create a new user  
     * @param user the user details
     * @param ucBuilder to send the user id uri for the next request 
     * @return
     */
    @RequestMapping(value = "/users", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createUser(@Valid @RequestBody NewUserBean user, UriComponentsBuilder ucBuilder) {
        logger.info("Creating User : {}", user);
 
        if (userService.isUserExist(user)) {
            logger.error("Unable to create. A User with Email {} already exist", user.getEmail());
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Unable to create. A User with Email " + 
            user.getEmail() + " already exist."),HttpStatus.CONFLICT);
        }
        String id = userService.addUser(user);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/api/users/{id}").buildAndExpand(id).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }
 
    /**
     * Update a User
     * @param id the user id
     * @param user the user details
     * @return
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateUser(@PathVariable("id") String id, @RequestBody UpdateUserBean user) {
        logger.info("Updating User with id {}", id);
 
        if (!userService.findUserById(id)) {
            logger.error("Unable to update. User with id {} not found.", id);
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Unable to update. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        boolean updated = userService.updateUser(id, user.getPinCode(), user.getBirthDate());
        if(updated) {
        	return new ResponseEntity<UserBean>(HttpStatus.NO_CONTENT);
        } else {
            logger.error("Unable to update. User with id {} not found.", id);
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Unable to update the user with id " + id + "."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
 
    /**
     * Delete a User
     * @param id the user id
     * @return
     */
    @RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteUser(@PathVariable("id") String id) {
        logger.info("Fetching & Deleting User with id {}", id);
 
        if (!userService.findUserById(id)) {
            logger.error("Unable to delete. User with id {} not found.", id);
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Unable to delete. User with id " + id + " not found."),
                    HttpStatus.NOT_FOUND);
        }
        boolean deleted = userService.deleteUser(id);
        if(deleted) {
        	return new ResponseEntity<UserBean>(HttpStatus.NO_CONTENT);
        } else {
            logger.error("Unable to delete the user with id {}.", id);
            return new ResponseEntity<CustomErrorType>(new CustomErrorType("Unable to delete the user with id " + id + "."),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
 
    // ------------------- Delete All Users-----------------------------
 
    /**
     * Delete All Users
     * @return
     */
    @RequestMapping(value = "/users", method = RequestMethod.DELETE)
    public ResponseEntity<UserBean> deleteAllUsers() {
        logger.info("Deleting All Users");
 
        userService.deleteAllUsers();
        return new ResponseEntity<UserBean>(HttpStatus.NO_CONTENT);
    }
}
