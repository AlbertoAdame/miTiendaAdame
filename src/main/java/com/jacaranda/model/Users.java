package com.jacaranda.model;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;

//CREATE TABLE USUARIO(
//		USUARIO VARCHAR(50) UNIQUE,
//		PASSWORD VARCHAR(50),
//		NOMBRE_APELLIDO VARCHAR(75),
//		FECHA_NACIMIENTO DATE,
//		GENERO BOOLEAN,
//		ADMINISTRADOR BOOLEAN,
//		CONSTRAINT PK_USUARIO PRIMARY KEY(USUARIO)
//		);

@Entity(name = "USUARIO")
public class Users {
	@Id
	@Column(name = "USUARIO")
	private String usuario;
	@Column(name = "PASSWORD")
	private String password;
	@Column(name = "NOMBRE_APELLIDO")
	private String nombre_apellido;
	@Column(name = "FECHA_NACIMIENTO")
	private LocalDateTime fecha_nacimiento;
	@Column(name = "GENERO")
	private boolean genero;
	@Column(name = "ADMINISTRADOR")
	private boolean administrador;
	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Venta> listaVenta;

	public Users(String usuario, String password, String nombre_apellido, LocalDateTime fecha_nacimiento, boolean genero,
			boolean administrador) {
		super();
		this.usuario = usuario;
		this.password = password;
		this.nombre_apellido = nombre_apellido;
		this.fecha_nacimiento = fecha_nacimiento;
		this.genero = genero;
		this.administrador = administrador;
	}

	public Users() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre_apellido() {
		return nombre_apellido;
	}

	public void setNombre_apellido(String nombre_apellido) {
		this.nombre_apellido = nombre_apellido;
	}

	public LocalDateTime getFecha_nacimiento() {
		return fecha_nacimiento;
	}

	public void setFecha_nacimiento(LocalDateTime fecha_nacimiento) {
		this.fecha_nacimiento = fecha_nacimiento;
	}

	public boolean isGenero() {
		return genero;
	}

	public void setGenero(boolean genero) {
		this.genero = genero;
	}

	public boolean isAdministrador() {
		return administrador;
	}

	public void setAdministrador(boolean administrador) {
		this.administrador = administrador;
	}

	public List<Venta> getListaVenta() {
		return listaVenta;
	}

	public void setListaVenta(List<Venta> listaVenta) {
		this.listaVenta = listaVenta;
	}

	@Override
	public int hashCode() {
		return Objects.hash(usuario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Users other = (Users) obj;
		return Objects.equals(usuario, other.usuario);
	}

	@Override
	public String toString() {
		return "Users [usuario=" + usuario + ", password=" + password + ", nombre_apellido=" + nombre_apellido
				+ ", fecha_nacimiento=" + fecha_nacimiento + ", genero=" + genero + ", administrador=" + administrador
			+ ", listaVenta=]"; //+ 	listaVenta  "]";
	}

	

}
