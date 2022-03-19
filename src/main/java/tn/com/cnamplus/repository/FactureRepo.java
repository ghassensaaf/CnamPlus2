package tn.com.cnamplus.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.com.cnamplus.entity.Facture;
@Repository
public interface FactureRepo extends CrudRepository<Facture, Long> {

	@Query("select count(f) from Facture f where f.annee = ?1")
	int countFacturePerYear(int year);
}
