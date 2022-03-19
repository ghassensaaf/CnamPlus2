package tn.com.cnamplus.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.*;
import lombok.experimental.FieldDefaults;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PrisEnCharge implements Serializable {
	
	private static final long serialVersionUID = -9102617832777335400L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	String nDecision;
	int nbrSeance;
	int seanceParSemain;
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern ="yyyy-MM-dd",shape = Shape.STRING)
	Date dateDebut;
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern ="yyyy-MM-dd",shape = Shape.STRING)
	Date dateFin;
	
	@JsonBackReference
	@ManyToOne
	Patient patient;
	
	@JsonBackReference
	@ManyToOne
	Bordereau bordereau;
	
	@OneToOne(mappedBy = "prisEnCharge")
	Facture facture;
	
	@OneToMany(mappedBy = "prisEnCharge")
	List<Consultation> consultations;
	
	@JsonIgnore
	@ManyToOne
	User user;
}
