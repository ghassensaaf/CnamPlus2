package tn.com.cnamplus.entity;

import org.springframework.security.core.GrantedAuthority;
public enum RoleName implements GrantedAuthority {
	admin,kine,ortho;
	@Override
	public String getAuthority() {
	return "ROLE_" + name();
	}
}