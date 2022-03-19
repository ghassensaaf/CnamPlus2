package tn.com.cnamplus.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
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
public class Consultation implements Serializable {

	private static final long serialVersionUID = 6895912396578069106L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	long id;
	int indice;
	@Temporal(TemporalType.DATE)
	@JsonFormat(pattern ="yyyy-MM-dd",shape = Shape.STRING)
	Date date;
	
	@JsonBackReference
	@ManyToOne
	PrisEnCharge prisEnCharge;

}
