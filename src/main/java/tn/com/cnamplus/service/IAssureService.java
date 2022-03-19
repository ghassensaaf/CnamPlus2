package tn.com.cnamplus.service;

import java.util.List;

import tn.com.cnamplus.entity.Assure;

public interface IAssureService {

	Assure addAssure(Assure assure);

	Assure editAssure(Assure assure);

	void deleteAssure(long assureId);

	Assure findAssure(long assureId);

	Assure findByNumAssure(String nAssure);

	List<Assure> findAll();
}
