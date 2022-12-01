package com.jacaranda.serlvet;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jacaranda.crud.CRUDUsers;
import com.jacaranda.crud.Utilities;
import com.jacaranda.model.Users;

/**
 * Servlet implementation class RegisterExec
 */
@WebServlet("/RegisterExec")
public class RegisterExec extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterExec() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("errorPage.jsp?error=entrado por get");// poner con mensaje de error 404, o borrar el get
																		// directamente
		// El rollback tiene que ir en el catch
		// OPara las session, tendremos que hacer httpSession = request.getSession()
		// Ahora preguntamos por el getSession() o hacemos el setSession(login,
		// password)

		// Pondemos poner un contador

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		boolean existe = false;
		String redirect = null;
		int noValido = 0;
		boolean error = false;

		String usuarioCadena = request.getParameter("username");
		if (usuarioCadena == null || usuarioCadena.isEmpty()) {
			noValido = 3;// usuario es nuloo
			error = true;
		} else if (!error) {
			for (int i = 0; i < usuarioCadena.length(); i++) {
				if (usuarioCadena.charAt(i) == ' ') {	
					noValido = 2;// usuario tiene espacios
					error = true;
				}
			}
			
			Users u = CRUDUsers.readUser(usuarioCadena);
			if (u != null) {
				noValido = 1;// usuario existe
				error = true;
			}
		}

		String password = request.getParameter("password");

		if (password == null || password.isEmpty()) {
			noValido = 4;// contraseña es nuloo
			error = true;
		}
		String nombreCompleto = request.getParameter("nombre_apellido");

		if (nombreCompleto == null || nombreCompleto.isEmpty()) {
			noValido = 5;// nombre es nuloo
			error = true;
		}
		
		String dateString = request.getParameter("fecha_nacimiento");
		
		if (dateString == null || dateString.isEmpty()) {
			noValido = 6;// fecha es nuloo
			error = true;
		}
		
		String sexString = request.getParameter("sex");
		
		if (sexString == null || sexString.isEmpty()) {
			noValido = 7;// sexo es nuloo
			error = true;
		}
		
		if (error) {
			redirect = "register.jsp?noValido=" + noValido;
		} else {
			boolean sex = Boolean.parseBoolean(sexString);

			LocalDateTime date = LocalDateTime.of(LocalDate.parse(dateString),
					LocalTime.now());
			Users newU = new Users(usuarioCadena, Utilities.getMD5(password), nombreCompleto, date, sex, false);
			CRUDUsers.addUser(newU);
			redirect = "index.jsp";
		}

		response.sendRedirect(redirect);

	}

}
