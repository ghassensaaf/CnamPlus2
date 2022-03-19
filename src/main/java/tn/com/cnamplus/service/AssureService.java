package tn.com.cnamplus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.com.cnamplus.entity.Assure;
import tn.com.cnamplus.repository.AssureRepo;
@Service
public class AssureService implements IAssureService {

	@Autowired
	AssureRepo ar;
	
	@Override
	public Assure addAssure(Assure assure) {
		// TODO Auto-generated method stub
		Assure a = ar.findBynAssure(assure.getNAssure());
		if(a == null)
			return ar.save(assure);
		else return a;
	}

	@Override
	public Assure editAssure(Assure assure) {
		// TODO Auto-generated method stub
		return ar.save(assure);
	}

	@Override
	public void deleteAssure(long assureId) {
		// TODO Auto-generated method stub
		ar.deleteById(assureId);
	}

	@Override
	public Assure findAssure(long assureId) {
		// TODO Auto-generated method stub
		return ar.findById(assureId).orElse(null);
	}

	@Override
	public Assure findByNumAssure(String nAssure) {
		// TODO Auto-generated method stub
		return ar.findBynAssure(nAssure);
	}

	@Override
	public List<Assure> findAll() {
		// TODO Auto-generated method stub
		return (List<Assure>) ar.findAll();
	}

}
