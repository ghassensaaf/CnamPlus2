package tn.com.cnamplus.service;

import java.util.List;

import tn.com.cnamplus.entity.User;

public interface IUserService {
	User addUser(User user);

	User editUser(User user, String username);

	void deleteUser(User user);

	User findUser(long userId);

	User findByUsername(String username);

	User findByUsernameAndPassword(String username, String password);

	List<User> findAll();

}
