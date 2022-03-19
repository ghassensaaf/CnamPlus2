package tn.com.cnamplus.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

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
public class User implements Serializable{

	/**
	 * 
	 */
	static final long serialVersionUID = -6437675813548675092L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	String username;
	String password;
	long cin;
	String codeCnam;
	String matriculeFiscale;
	String adresse;
	String email;
	long tel;
	String nom;
	String prenom;
	String centreRef;
	int codePres;
	String banque;
	String rib;
	@Enumerated(EnumType.STRING)
	Genre genre;
	private Boolean active;
	
	@ManyToMany(cascade = CascadeType.PERSIST,fetch = FetchType.EAGER)
	private Set<Role> roles;
	
	@OneToMany(mappedBy = "user")
	List<Patient> patients;
	@OneToMany(mappedBy = "user")
	List<Bordereau> bordereaux;
	
	@OneToMany(mappedBy = "user")
	List<PrisEnCharge> pecs;
	
}
