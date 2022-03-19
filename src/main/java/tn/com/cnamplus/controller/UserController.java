package tn.com.cnamplus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tn.com.cnamplus.entity.*;
import tn.com.cnamplus.service.*;

@RestController
public class UserController {

	@Autowired
	IUserService us;

	@Autowired
	MyUserDetailsService ss;

	@Autowired
	AuthenticationManager authenticationManager;

	BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

	@PostMapping("/login")
	String performLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
		User user = us.findByUsername(username);
		if (user != null) {
			if (BCrypt.checkpw(password, user.getPassword())) {
				UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(username,password);
				Authentication auth = authenticationManager.authenticate(authReq);
				SecurityContext sc = SecurityContextHolder.getContext();
				sc.setAuthentication(auth);
				return "logged in";
			}
			return "wrong password";
		}
		return "User not found";
	}

	@PostMapping("/logout")
	void performLogout() {
		SecurityContext sc = SecurityContextHolder.getContext();
		sc.setAuthentication(null);
	}

	@PostMapping("loggedin-user")
	User loggedin() {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		return us.findByUsername(username);
	}

	@PostMapping("/registration")
	User registration(@RequestBody User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(true);
		return us.addUser(user);
	}

	@PostMapping("/update-user")
	User updateUser(@RequestBody User user) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		return us.editUser(user, username);
	}
}
