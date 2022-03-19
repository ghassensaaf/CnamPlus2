package tn.com.cnamplus.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Assure implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6751454234252372462L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	String nAssure;
	String nom;
	String prenom;
	
	@JsonIgnore
	@OneToMany(mappedBy = "assure")
	List<Patient> patients;
	
	

}
