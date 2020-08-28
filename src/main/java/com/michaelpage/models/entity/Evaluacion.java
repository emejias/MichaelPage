package com.michaelpage.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "evaluaciones")
public class Evaluacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8973290219103167329L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ApiModelProperty(notes = "Nombre")
	@NotEmpty
	@Size(min = 4, max = 12)
	@Column(nullable = false, unique = true)
	private String nombre;

	@ApiModelProperty(notes = "Apellido")
	@NotEmpty
	// @Column(nullable = true, unique = true)
	private String apellido;

	@ApiModelProperty(notes = "Email")
	@NotEmpty
	@Email
	private String email;

	@ApiModelProperty(notes = "La calificacion debe estar entre 1 y 10")
	@NotEmpty
	private int calificacion;

	@ApiModelProperty(notes = "El formato para al consulta por fecha debe ser de la siguiente manera f1= 'yyyy-MM-dd', f2= 'yyyy-MM-dd' ejemplo: f1 ='2020-08-20', f2 = '2020-08-25' ")
	@Column(columnDefinition = "DATETIME default CURRENT_TIMESTAMP")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createAt = new Date();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

}
