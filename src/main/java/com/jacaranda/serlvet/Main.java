package com.jacaranda.serlvet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jacaranda.crud.CRUDCategoria;
import com.jacaranda.crud.CRUDProducto;
import com.jacaranda.crud.CRUDUsers;
import com.jacaranda.crud.Utilities;
import com.jacaranda.model.Categoria;
import com.jacaranda.model.Producto;
import com.jacaranda.model.Users;

/**
 * Servlet implementation class Main
 */
@WebServlet("/Main")
public class Main extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Main() {
        super();
        // TODO Auto-generated constructor stub
    }

    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.sendRedirect("errorPage.jsp?error?=1");
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.getWriter().append("<!DOCTYPE html>\n"
				+ "<html lang=\"en\">\n"
				+ "<head>\n"
				+ "    <meta charset=\"UTF-8\">\n"
				+ "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n"
				+ "    <link rel=\"stylesheet\" href=\"style.css\">\n"
				+ "    <title>Document</title>\n"
				+ "</head>\n"
				+ "<body>"
				+ "<header id=\"main-header\">\n"
				+ "		<a id=\"title\" href=\"index.jsp\">CAR</a>\n"
				+ "		<a id=\"titleBlue\" href=\"index.jsp\">Buy</a>\n"
				+ "		<a id=\"logo-header\" href=\"index.jsp\"><img src=\"Images/logor.png\"></a>"
				+ "\n"
				+ "		<nav>\n"
				+ "			<ul>\n"
				+ "\n"
				+ "				<li style=\"border-bottom: 2px solid #f0f2f1;\"><a\n"
				+ "					href=\"index.jsp\">Productos</a></li>\n"
				+ "			</ul>\n"
				+ "		</nav>\n"
				+ "\n"
				+ "	</header>\n"
				+ "	<div id=\"cuerpo\">");
		
		HttpSession sesion=request.getSession();
		

		String usuarioCadena= (String)sesion.getAttribute("usuario");
		String password= (String)sesion.getAttribute("password");
		Users u = CRUDUsers.readUser(usuarioCadena);
		
		if(u==null) {
			usuarioCadena= request.getParameter("usuario");
			password= request.getParameter("password");
			u = CRUDUsers.readUser(usuarioCadena);
			sesion.setAttribute("login", "True");
			sesion.setAttribute("usuario", usuarioCadena);
			sesion.setAttribute("password", password);
			
		}
		boolean usuarioAdmin=u.isAdministrador();

		
   		if (u != null && (Utilities.getMD5(password).equals(u.getPassword()))){

   			if(usuarioAdmin) {
   				response.getWriter().append(
   						"<br>"+
   						"<a href='annadirProducto.jsp'><button class='registerButton' id='bold'>A&ntildeadir Producto</button></a>"
   						);
   			}
   			
   			List<Producto> listaProducto = CRUDProducto.loadList();

   			response.getWriter().append("<table border=\"1\" class='tabla'>"
   					+ "				<tr>"
   					+ "				<td class='tdId' id='bold'>ID</td>\n"
   					+ "            <td class='tdName' id='bold'>Nombre</td>\n"
   					+ "            <td class='tdPrice' id='bold'>Precio</td>\n"
   					+ "            <td class='tdDescription' id='bold'>Descripcion</td>\n"
   					+ "            <td class='tdCategoria' id='bold'>Categoria</td>"
   					+ "				</tr>");
   			for(Producto p : listaProducto) {
   	   			Categoria c = CRUDCategoria.readCategoria(p.getId_categoria());
   				response.getWriter().append("<tr>");
   				response.getWriter().append("<td>" + p.getId() + "</td>");
   				response.getWriter().append("<td>" + p.getNombre() + "</td>");
   				response.getWriter().append("<td>" + p.getPrecio() + " $</td>");
   				response.getWriter().append("<td>" + p.getDescripcion() + "</td>");
   				response.getWriter().append("<td>" + c.getNombre()+ "</td>");
   				response.getWriter().append("</tr>");
   			}
   			response.getWriter().append("</table>"
   					+ "</div>");
   			response.getWriter().append("</body>");
   			

  			


//			response.getWriter().append("correcto");
//			response.getWriter().append("<a href='index.html'");
			

   		}

   		else
   			response.sendRedirect("errorPage.jsp?error=2");
		
   
	}

}
