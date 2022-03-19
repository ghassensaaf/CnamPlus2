package tn.com.cnamplus.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import tn.com.cnamplus.entity.Bordereau;
@Repository
public interface BordereauRepo extends CrudRepository<Bordereau, Long> {
	@Query("select count(b) from Bordereau b where b.annee = ?1")
	int countBordereauPerYear(int year);
}
