package tn.com.cnamplus.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

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
public class Facture implements Serializable {

	private static final long serialVersionUID = 7057484057648085261L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	int annee;
	String numFacture;
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern ="yyyy-MM-dd",shape = Shape.STRING)
	Date dateFacture;
	float prixUnitaire;
	float totalHT;
	int tva;
	float mntTva;
	float totalTtc;
	
	@JsonBackReference
	@OneToOne
	PrisEnCharge prisEnCharge;
	
}
