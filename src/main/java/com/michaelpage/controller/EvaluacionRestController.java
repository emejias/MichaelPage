package com.michaelpage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.michaelpage.models.entity.Evaluacion;
import com.michaelpage.models.services.IEvaluacionService;

@CrossOrigin("*")
@RestController
@RequestMapping("/evaluaciones")
public class EvaluacionRestController {

	@Autowired
	private IEvaluacionService evaluacionService;

	@GetMapping("/listar")
	public List<Evaluacion> listarEvaluaciones() {

		return evaluacionService.findAll();

	}

	@GetMapping("/listar/{id}")
	public ResponseEntity<?> mostrarEvaluacionPorID(@PathVariable Long id) {

		Evaluacion evaluacionActual = null;
		Map<String, Object> response = new HashMap<>();

		try {
			evaluacionActual = evaluacionService.findById(id);

			if (evaluacionActual == null) {
				throw new NoSuchElementException();
			}

		} catch (Exception e) {
			response.put("mensaje", "El registro con el id ".concat(id.toString().concat(" no existe")));
			response.put("error", e.getMessage());
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Evaluacion>(evaluacionActual, HttpStatus.OK);

	}

	@GetMapping("/listar/{f1}/{f2}")
	public ResponseEntity<List<Evaluacion>> listarPorFechas(@PathVariable String f1, @PathVariable String f2) {

		List<Evaluacion> evaluacionActual = null;
		Map<String, Object> response = new HashMap<>();

		try {
			evaluacionActual = evaluacionService.findBetweenDate(f1, f2);

			if (evaluacionActual.get(0) == null) {
				throw new NoSuchElementException();
			}

		} catch (Exception e) {
			response.put("mensaje", "El registro con el id ".concat(f1.toString().concat(" no existe")));
			response.put("error", e.getMessage());
			return new ResponseEntity<List<Evaluacion>>(evaluacionActual, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<List<Evaluacion>>(evaluacionActual, HttpStatus.OK);

	}

	@PostMapping("/registrar")
	public ResponseEntity<?> registrarEvaluacion(@RequestBody Evaluacion evaluacion) {

		Map<String, Object> response = new HashMap<>();
		Evaluacion evaluacionNew = null;

		try {

			if ((evaluacion.getCalificacion() < 1) || (evaluacion.getCalificacion() > 10)) {

				response.put("mensaje", "la calificacion debe estar entre 1 y 10");
				// response.put("error", e.getMessage().toString());

				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

			}

			else {

				evaluacionNew = evaluacionService.save(evaluacion);

			}

		} catch (Exception e) {
			// TODO: handle exception
			response.put("mensaje", "no se pudo realizar la carga de la informacion de manera correcta");
			response.put("error", e.getMessage().toString());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		response.put("mensaje", "la informacion de la evaluacion para el usuario ".concat(evaluacionNew.getNombre()
				.toString().concat(" ").concat(evaluacionNew.getApellido()).concat(" Se inserto con exito")));
		response.put("response", evaluacionNew);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);

	}

	@PutMapping("/editar/{id}")
	public ResponseEntity<?> modificarEvaluacionPorID(@RequestBody Evaluacion evaluacion, @PathVariable Long id) {

		Evaluacion evaluacionActual = null;
		Evaluacion clienteUpdated = null;
		Map<String, Object> response = new HashMap<>();

		try {
			evaluacionActual = evaluacionService.findById(id);

		} catch (Exception e) {
			// TODO: handle exception
			response.put("mensaje", "no se encontró evaluación con el id ".concat(id.toString()).concat(" en la BD"));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		try {
			evaluacionActual.setApellido(evaluacion.getApellido());
			evaluacionActual.setEmail(evaluacion.getEmail());
			evaluacionActual.setNombre(evaluacion.getNombre());
			evaluacionActual.setCalificacion(evaluacion.getCalificacion());
			clienteUpdated = evaluacionService.save(evaluacionActual);

			response.put("mensaje", "los valores para la evaluacion ".concat(evaluacionActual.getNombre()
					.concat(" y con el ID ").concat(id.toString().concat(" fueron editado con exito"))));
			response.put("respuesta", clienteUpdated);

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);

		} catch (Exception e) {
			// TODO: handle exception
			response.put("mensaje", "no se pudo actualizar el registro indicado");
			response.put("error", e.getMessage());
			response.put("detalleError", e.getCause().toString());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	} 

	@DeleteMapping("/eliminar/{id}")
	public ResponseEntity<?> eliminarEvaluacionPorID(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();

		try {
			evaluacionService.delete(id);
			response.put("mensaje",
					"la evaluacion con el id ".concat(id.toString().concat(" se pudo eliminar con exito")));

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.ACCEPTED);

		} catch (Exception e) {

			response.put("mensaje", "no se pudo eliminar la evaluacion con el id ".concat(id.toString()));
			response.put("error", e.getMessage());

			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

	}

}
