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

public class Bordereau implements Serializable {

	private static final long serialVersionUID = 5515719274956207404L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	int annee;
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern ="yyyy-MM-dd",shape = Shape.STRING)
	Date date;
	float totalTtc;
	String nBord;
	
	@JsonBackReference
	@ManyToOne
	User user;
	
	@OneToMany(mappedBy = "bordereau")
	List<PrisEnCharge> PrisEnCharges;
	

}
