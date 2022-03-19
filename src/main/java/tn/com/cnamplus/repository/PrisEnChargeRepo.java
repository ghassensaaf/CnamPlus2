package tn.com.cnamplus.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.com.cnamplus.entity.Patient;
import tn.com.cnamplus.entity.PrisEnCharge;
import tn.com.cnamplus.entity.User;
@Repository
public interface PrisEnChargeRepo extends CrudRepository<PrisEnCharge, Long> {

	List<PrisEnCharge> findAllByUser(User user);
	List<PrisEnCharge> findAllByPatient(Patient patient);
	PrisEnCharge findBynDecision(String nDecision);

}
