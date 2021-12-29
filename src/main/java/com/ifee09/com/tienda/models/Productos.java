package com.ifee09.com.tienda.models;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.hateoas.RepresentationModel;

@Entity
@Table
public class Productos extends RepresentationModel<Productos> implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	private String nombre;
	private String foto;
	private double precio;
	private String descripcionC;
	private String descripcionL;
	private LocalDate fecha;
	/**
	 * @param id
	 * @param nombre
	 * @param foto
	 * @param precio
	 * @param descripcionC
	 * @param descripcionL
	 * @param fecha
	 */
	public Productos() {
		
	}
	public Productos(Integer id, String nombre, String foto, double precio, String descripcionC, String descripcionL,
			LocalDate fecha) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.foto = foto;
		this.precio = precio;
		this.descripcionC = descripcionC;
		this.descripcionL = descripcionL;
		this.fecha = fecha;
	}
	//setters

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the foto
	 */
	public String getFoto() {
		return foto;
	}

	/**
	 * @param foto the foto to set
	 */
	public void setFoto(String foto) {
		this.foto = foto;
	}

	/**
	 * @return the precio
	 */
	public double getPrecio() {
		return precio;
	}

	/**
	 * @param precio the precio to set
	 */
	public void setPrecio(double precio) {
		this.precio = precio;
	}

	/**
	 * @return the descripcionC
	 */
	public String getDescripcionC() {
		return descripcionC;
	}

	/**
	 * @param descripcionC the descripcionC to set
	 */
	public void setDescripcionC(String descripcionC) {
		this.descripcionC = descripcionC;
	}

	/**
	 * @return the descripcionL
	 */
	public String getDescripcionL() {
		return descripcionL;
	}

	/**
	 * @param descripcionL the descripcionL to set
	 */
	public void setDescripcionL(String descripcionL) {
		this.descripcionL = descripcionL;
	}

	/**
	 * @return the fecha
	 */
	public LocalDate getFecha() {
		return fecha;
	}

	/**
	 * @param fecha the fecha to set
	 */
	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "Productos [id=" + id + ", nombre=" + nombre + ", foto=" + foto + ", precio=" + precio
				+ ", descripcionC=" + descripcionC + ", descripcionL=" + descripcionL + ", fecha=" + fecha + "]";
	}

		
	
}
