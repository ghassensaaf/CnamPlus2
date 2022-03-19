package tn.com.cnamplus.service;

import java.io.IOException;
import java.util.List;

import tn.com.cnamplus.entity.Bordereau;

public interface IBordereauService {
	Bordereau addBordereau(List<Long> pecs, String username) throws IOException;
}
