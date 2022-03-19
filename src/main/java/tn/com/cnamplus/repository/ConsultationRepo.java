package tn.com.cnamplus.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.com.cnamplus.entity.Consultation;
import tn.com.cnamplus.entity.PrisEnCharge;
@Repository
public interface ConsultationRepo extends CrudRepository<Consultation, Long> {

	Consultation findByIndiceAndPrisEnCharge(int indice, PrisEnCharge pec);
}
