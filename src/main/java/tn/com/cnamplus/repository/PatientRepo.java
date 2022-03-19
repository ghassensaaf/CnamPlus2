package tn.com.cnamplus.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.com.cnamplus.entity.Patient;
import tn.com.cnamplus.entity.User;
@Repository
public interface PatientRepo extends CrudRepository<Patient, Long> {

	List<Patient> findAllByUser(User user);
}
