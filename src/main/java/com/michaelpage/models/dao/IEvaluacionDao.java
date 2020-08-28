package com.michaelpage.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.michaelpage.models.entity.Evaluacion;

public interface IEvaluacionDao extends JpaRepository<Evaluacion, Long> {

	// metodo para poder retornar la consulta de las evaluaciones con intervalos de
	// fechas.
	@Query(value = "SELECT * FROM evaluaciones u WHERE u.create_at between ?1 and  ?2", nativeQuery = true)
	public List<Evaluacion> findBetweenDate(String date1, String date2);

}
