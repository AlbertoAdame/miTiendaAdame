<%@page import="com.jacaranda.model.Venta"%>
<%@page import="com.jacaranda.crud.CRUDVenta"%>
<%@page import="com.jacaranda.model.Categoria"%>
<%@page import="com.jacaranda.crud.CRUDCategoria"%>
<%@page import="com.jacaranda.crud.CRUDProducto"%>
<%@page import="com.jacaranda.model.Producto"%>
<%@page import="java.util.List"%>
<%@page import="java.time.LocalDateTime"%>
<%@page import="com.jacaranda.model.ItemCarrito"%>
<%@page import="com.jacaranda.crud.Utilities"%>
<%@page import="com.jacaranda.crud.CRUDUsers"%>
<%@page import="com.jacaranda.model.Users"%>
<%@page import="com.jacaranda.model.Carrito"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Realizando Compra</title>
<link rel="stylesheet" href="style.css">
<link rel="shortcut icon" href="Images/logor.png">
</head>
<body>
<%
	String redirect = "";
	HttpSession sesion = request.getSession();
	String usuarioCadena = (String) sesion.getAttribute("usuario");
	String password = (String) sesion.getAttribute("password");
	LocalDateTime date = LocalDateTime.now();
	Users u = CRUDUsers.readUser(usuarioCadena);
	if (u != null) {
		if (Utilities.getMD5(password).equals(u.getPassword())) {
			sesion.setAttribute("login", "True");
			sesion.setAttribute("usuario", usuarioCadena);
			sesion.setAttribute("password", password);
		} else {
			
			response.sendRedirect("errorPage.jsp?error=4");
		}
	}

	Carrito c = (Carrito) sesion.getAttribute("carrito");
	List<ItemCarrito> lista = c.carrito;
	

	if(c != null){
		for(ItemCarrito i : lista){
			Producto p = CRUDProducto.readProducto(i.getId_articulo());
			Venta v = new Venta(u, p, LocalDateTime.now(), i.getCantidad(), (i.getPrecio()));
			CRUDVenta.addVenta(v);
			p.setStock(p.getStock()-i.getCantidad());
			
		}
		c.delCarrito();
	}
	
	response.sendRedirect("carrito.jsp");
	
	
	
%>

</body>
</html>