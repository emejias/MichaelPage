package com.michaelpage.models.services;

import java.util.List;

import com.michaelpage.models.entity.Evaluacion;

public interface IEvaluacionService {

	public List<Evaluacion> findAll();

	public Evaluacion findById(Long id);

	public List<Evaluacion> findBetweenDate(String date1, String date2);

	public Evaluacion save(Evaluacion evaluacion);

	public void delete(Long id);

}
