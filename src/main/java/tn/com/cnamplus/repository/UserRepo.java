package tn.com.cnamplus.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.com.cnamplus.entity.User;

@Repository
public interface UserRepo extends CrudRepository<User, Long> {
	User findByUsername(String username);
	
	@Query("select u from User u where u.username = ?1 and u.password = ?2")
	User findByUsernameAndPassword(String username, String password);
}
