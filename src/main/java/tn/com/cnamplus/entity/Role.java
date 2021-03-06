package tn.com.cnamplus.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Role implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1002129572285269707L;
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private int id;
	@Enumerated(EnumType.STRING)
	private RoleName role;
}