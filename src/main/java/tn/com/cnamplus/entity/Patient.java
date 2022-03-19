package tn.com.cnamplus.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
public class Patient implements Serializable {/**
	 * 
	 */
	private static final long serialVersionUID = -2689374642080406940L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	String nom;
	String prenom;
	@Enumerated(EnumType.STRING)
	Qualite qualite;
	
	@JsonBackReference
	@ManyToOne
	User user;
	
	@ManyToOne
	@JoinColumn(name = "assure_id")
	Assure assure;
	
	@OneToMany(mappedBy = "patient")
	List<PrisEnCharge> prisEnCharges;
	
}
