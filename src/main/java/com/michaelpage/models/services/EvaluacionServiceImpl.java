package com.michaelpage.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.michaelpage.models.dao.IEvaluacionDao;
import com.michaelpage.models.entity.Evaluacion;

@Service
public class EvaluacionServiceImpl implements IEvaluacionService {

	@Autowired
	private IEvaluacionDao evaluacionDao;

//	@Override
//	@Transactional(readOnly = true)
//	public List<Evaluacion> findAll() {
//		// TODO Auto-generated method stub
//		return (List<Evaluacion>) evaluacionDao.findAll();
//	}

	@Override
	public List<Evaluacion> findAll() {
		// TODO Auto-generated method stub
		return evaluacionDao.findAll();
	}

	@Override
	public Evaluacion findById(Long id) {
		// TODO Auto-generated method stub
		return evaluacionDao.findById(id).get();
	}

	@Override
	public List<Evaluacion> findBetweenDate(String date1, String date2) {
		// TODO Auto-generated method stub
		return evaluacionDao.findBetweenDate(date1, date2);
	}

	@Override
	public Evaluacion save(Evaluacion evaluacion) {
		// TODO Auto-generated method stub
		return evaluacionDao.save(evaluacion);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub

		evaluacionDao.deleteById(id);

	}

}
