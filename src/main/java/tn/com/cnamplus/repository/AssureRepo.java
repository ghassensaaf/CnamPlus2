package tn.com.cnamplus.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.com.cnamplus.entity.Assure;
@Repository
public interface AssureRepo extends CrudRepository<Assure, Long> {

	Assure findBynAssure(String nAssure);
}
