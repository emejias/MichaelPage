package ControllerTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.michaelpage.controller.EvaluacionRestController;
import com.michaelpage.models.entity.Evaluacion;
import com.michaelpage.models.services.IEvaluacionService;

public class EvaluacionControllerTest {

	// simulamos los valores devueltos por la consulta al servicio
	private static final Evaluacion EVALUACION = new Evaluacion();

	// devolvemos un objeto con el parametro de calificaciones incorrecto
	private static final Evaluacion EVALUACION_ERROR_CAL = new Evaluacion();

	// devolvemos un objeto con parametros invalidos
	private static final Evaluacion EVALUACION_PARAM_INVALIDO = new Evaluacion();

	// devolvemos un objeto vacio
	private static final Evaluacion EVALUACION_NULL = null;

	public static final String NOMBRE = "Eleazar";
	public static final String APELLIDO = "Mejias";
	public static final String EMAIL = "Eleazar1805@gmail.com";
	public static final int CALIFICACION = 9;
	public static final int CALIFICACION_ERROR = 90;

	public static final String FECHA_UNO = "2020-12-01";
	public static final String FECHA_DOS = "2020-12-10";

	// este es el valor que se envia como parámetro para el método que hace la
	// busqueda por ID
	private static long ID = 2;
	private static long ID_ERROR = 200;

	@Mock
	private IEvaluacionService evaluacionService;

	@InjectMocks
	EvaluacionRestController evaluacionRestController;

	@Before
	public void init() {

		MockitoAnnotations.initMocks(this);

		EVALUACION.setNombre(NOMBRE);
		EVALUACION.setApellido(APELLIDO);
		EVALUACION.setEmail(EMAIL);
		EVALUACION.setCalificacion(CALIFICACION);

		EVALUACION_ERROR_CAL.setNombre(NOMBRE);
		EVALUACION_ERROR_CAL.setApellido(APELLIDO);
		EVALUACION_ERROR_CAL.setEmail(EMAIL);
		EVALUACION_ERROR_CAL.setCalificacion(CALIFICACION_ERROR);

		EVALUACION_PARAM_INVALIDO.setApellido(APELLIDO);
		EVALUACION_PARAM_INVALIDO.setEmail(EMAIL);
		EVALUACION_PARAM_INVALIDO.setCalificacion(CALIFICACION);

	}

	// prueba para el método que lista todas las evaluaciones

	@Test
	public void listarEvaluacionesTest() {

		final Evaluacion evaluacion = new Evaluacion();

		Mockito.when(evaluacionService.findAll()).thenReturn(Arrays.asList(evaluacion));

		final List<Evaluacion> response = evaluacionRestController.listarEvaluaciones();
		assertNotNull(response);
		assertFalse(response.isEmpty());
		assertEquals(response.size(), 1);

	}

	// prueba para el método que lista evaluaciones por ID

	@Test
	public void mostrarEvaluacionPorIDTest() {

		Mockito.when(evaluacionService.findById(ID)).thenReturn(EVALUACION);

		ResponseEntity<Evaluacion> response = (ResponseEntity<Evaluacion>) evaluacionRestController
				.mostrarEvaluacionPorID(ID);
		assertEquals(response.getBody().getNombre(), NOMBRE);
		assertEquals(response.getBody().getApellido(), APELLIDO);
		assertEquals(response.getBody().getEmail(), EMAIL);
		assertEquals(response.getBody().getCalificacion(), CALIFICACION);

	}

	// prueba para el método que lista las evaluaciones con un ID invalido

	@Test
	public void mostrarEvaluacionPorIDNotFoundTest() {

		Mockito.when(evaluacionService.findById(ID_ERROR)).thenReturn(EVALUACION_NULL);

		ResponseEntity<Evaluacion> response = (ResponseEntity<Evaluacion>) evaluacionRestController
				.mostrarEvaluacionPorID(ID_ERROR);

		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

	}

	// prueba para el método que lista las evaluaciones por fecha
	@Test
	public void listarPorFechasTest() {

		final Evaluacion evaluacion = new Evaluacion();

		Mockito.when(evaluacionService.findBetweenDate(FECHA_UNO, FECHA_DOS)).thenReturn(Arrays.asList(evaluacion));

		ResponseEntity<List<Evaluacion>> response = (ResponseEntity<List<Evaluacion>>) evaluacionRestController
				.listarPorFechas(FECHA_UNO, FECHA_DOS);

		assertEquals(response.getStatusCode(), HttpStatus.OK);

	}

	// prueba para el método que lista evaluaciones con intervalo de fechas
	// invalidos
	@Test
	public void listarPorFechasNotFoundTest() {

		final Evaluacion evaluacion = null;

		Mockito.when(evaluacionService.findBetweenDate(FECHA_UNO, FECHA_DOS)).thenReturn(Arrays.asList(evaluacion));

		ResponseEntity<List<Evaluacion>> response = (ResponseEntity<List<Evaluacion>>) evaluacionRestController
				.listarPorFechas(FECHA_UNO, FECHA_DOS);

		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);

	}

	// prueba para el método que registra evaluaciones

	@Test
	public void registrarEvaluacionTest() {

		Mockito.when(evaluacionService.save(EVALUACION)).thenReturn((EVALUACION));

		ResponseEntity<?> response = evaluacionRestController.registrarEvaluacion(EVALUACION);

		assertEquals(response.getStatusCode(), HttpStatus.CREATED);

	}

	// prueba para el método que registra evaluaciones con calificaciones invalidas

	@Test
	public void registrarEvaluacionCalificacionErrorTest() {

		Mockito.when(evaluacionService.save(EVALUACION_ERROR_CAL)).thenReturn((EVALUACION_ERROR_CAL));

		ResponseEntity<?> response = evaluacionRestController.registrarEvaluacion(EVALUACION_ERROR_CAL);

		assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);

	}

	// prueba para el método que registra evaluaciones con parametros invalidos

	@Test
	public void registrarEvaluacionParametrosInvalidosTest() {

		Mockito.when(evaluacionService.save(EVALUACION_PARAM_INVALIDO)).thenReturn((null));

		ResponseEntity<?> response = evaluacionRestController.registrarEvaluacion(EVALUACION_PARAM_INVALIDO);

		assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);

	}

	// prueba para el método que modifica evaluaciones por ID
	@Test
	public void modificarEvaluacionPorIDTest() {

		Mockito.when(evaluacionService.findById(ID)).thenReturn((EVALUACION));
		Mockito.when(evaluacionService.save(EVALUACION)).thenReturn((EVALUACION));

		ResponseEntity<?> response = evaluacionRestController.modificarEvaluacionPorID(EVALUACION, ID);

		assertEquals(response.getStatusCode(), HttpStatus.OK);

	}

	// prueba para el método que modifica evaluaciones con ID no válido

	@Test
	public void modificarEvaluacionPorIDNotFoundTest() {

		Mockito.when(evaluacionService.findById(ID_ERROR)).thenReturn(null);

		ResponseEntity<?> response = evaluacionRestController.modificarEvaluacionPorID(EVALUACION, ID_ERROR);

		assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);

	}

	// prueba para el método que modifica evaluaciones por ID con el objeto
	// evaluaciones invalido

	@Test
	public void modificarEvaluacionPorIDErrorParamTest() {

		Mockito.when(evaluacionService.findById(ID)).thenReturn(EVALUACION_PARAM_INVALIDO);
		Mockito.when(evaluacionService.save(EVALUACION_PARAM_INVALIDO)).thenReturn((null));

		ResponseEntity<?> response = evaluacionRestController.modificarEvaluacionPorID(EVALUACION_PARAM_INVALIDO, ID);

		assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);

	}

	// prueba para el método que elimina evaluaciones con por ID
	@Test
	public void eliminarEvaluacionPorIDTest() {

		Mockito.when(evaluacionService.findById(ID)).thenReturn(EVALUACION);

		ResponseEntity<?> response = evaluacionRestController.eliminarEvaluacionPorID(ID);

		assertEquals(response.getStatusCode(), HttpStatus.ACCEPTED);

	}

	// prueba para el método que elimina evaluaciones con ID no valida

	@Test
	public void eliminarEvaluacionPorIDNotFoundTest() {

		Mockito.when(evaluacionService.findById(ID_ERROR)).thenReturn(EVALUACION_NULL);

		ResponseEntity<?> response = evaluacionRestController.eliminarEvaluacionPorID(ID_ERROR);

		assertEquals(response.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);

	}

}
