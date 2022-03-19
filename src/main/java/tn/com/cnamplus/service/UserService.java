package tn.com.cnamplus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.com.cnamplus.entity.User;
import tn.com.cnamplus.repository.UserRepo;
@Service
public class UserService implements IUserService {

	@Autowired
	UserRepo ur;
	@Override
	public User addUser(User user) {
		// TODO Auto-generated method stub
		return ur.save(user);
	}

	@Override
	public User editUser(User user , String username) {
		// TODO Auto-generated method stub
		User u = ur.findByUsername(username);
		user.setId(u.getId());
		user.setUsername(username);
		return ur.save(user);
	}

	@Override
	public void deleteUser(User user) {
		ur.delete(user);
	}

	@Override
	public User findUser(long userId) {
		// TODO Auto-generated method stub
		return ur.findById(userId).orElse(null);
	}
	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return ur.findByUsername(username);
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return (List<User>) ur.findAll();
	}

	@Override
	public User findByUsernameAndPassword(String username, String password) {
		// TODO Auto-generated method stub
		return ur.findByUsernameAndPassword(username, password);
	}

	

}
