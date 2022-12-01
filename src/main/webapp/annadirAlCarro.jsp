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
<title>Añadir al Carro</title>
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
	

	int id_articulo = Integer.parseInt(request.getParameter("id_articulo"));
	

	int numeroProducto=c.cantidadProductos(c.getItem(id_articulo));
	
	
	//We need product and category at this point
	Producto p = CRUDProducto.readProducto(id_articulo);
	Categoria cat = CRUDCategoria.readCategoria(p.getId_categoria());
	
	int cantidad = Integer.parseInt(request.getParameter("cantidad"));
	if(cantidad <= 0 || p.getStock()<(numeroProducto+cantidad) || cantidad>p.getStock()){
		response.sendRedirect("Main");//Hacer que cuando pase al main salga mensaje de erro
	}
	else{
		
	float precio = Float.parseFloat(request.getParameter("precio"));
	int contadorProductos = 0;//We use this variable to show it to the user
	

	ItemCarrito i = new ItemCarrito(id_articulo, cantidad, precio, date);

			if (c.carrito.contains(i)){
				c.updateItem(i, cantidad);
				contadorProductos = c.cantidadProductosTotales();
			}
			else{
				c.addItem(i);
				contadorProductos = c.cantidadProductosTotales();
				sesion.setAttribute("carrito", c);
			}
			
			
	

	%>
	<header id="main-header">
		<a id="title" href="Main">CAR</a> <a id="titleBlue" href="Main">Buy</a>
		<a id="logo-header" href="Main"><img src="Images/logor.png"></a>
		<nav>
			<ul>
				<li class='imgDollar tooltip';> 
                    <a href='ventas.jsp'> 
                        <img src="Images/dollar.svg" width="30px"> 
                    </a>
                </li> 
				<li class='imgCarro tooltip'><a href='carrito.jsp'> <img
						src="Images/carro.svg"> <span class='tooltiptext'><%=contadorProductos%>
					</span>
				</a></li>
				<li class='imgUser tooltip'><a href='index.jsp'> <img
						src="Images/user.svg"> <span class="tooltiptext"><%=usuarioCadena%>
					</span>
				</a></li>
			</ul>
		</nav>
	</header>

	<div id="cuerpo">
		<div class="fondoCarrito">

			<div class="photo">
				<img src="Images/Ford3.png" class="photo_sure">
			</div>

			<div class="description">
				<a class="titulitos"><%=p.getNombre() %></a> <small><%=cat.getNombre() %></small> <br>
				<%=p.getPrecio() %><br> <a class="descripcion"><%=p.getDescripcion() %></a> <br>
				<hr>

				<p class="colorAzul"> + <%= cantidad %> Artículos Añadidos</p>

				<div id="introducir">
					<a href="Main">
						<button class="join">
							<h2>Seguir comprando</h2>
						</button>
					</a>
				</div>
				<div id="registrarse">
					<a href="carrito.jsp">
						<button class="registerButton" type="button">
							<h2>Ir al carrito</h2>
						</button>
					</a>
				</div>
			</div>
		</div>
	</div>
<% } %>
</body>
</html>