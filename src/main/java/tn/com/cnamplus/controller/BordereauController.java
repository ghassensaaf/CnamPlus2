package tn.com.cnamplus.controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tn.com.cnamplus.entity.Bordereau;
import tn.com.cnamplus.service.BordereauService;
import tn.com.cnamplus.service.IQrCodeService;
@RestController
@RequestMapping("/bordereau")
public class BordereauController {

	@Autowired
	BordereauService bs;
	@Autowired
	IQrCodeService qrs;
	@PostMapping("/add")
	Bordereau addbordereau(@RequestBody List<Long> pecIds) throws IOException {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username;
		if (principal instanceof UserDetails) {
			username = ((UserDetails) principal).getUsername();
		} else {
			username = principal.toString();
		}
		return bs.addBordereau(pecIds, username);
	}
	
	
}
