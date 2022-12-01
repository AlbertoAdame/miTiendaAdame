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
<title>Carrito</title>
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
	List<ItemCarrito> listaItems = null;
	

	
	int contadorProductos = 0;
	if(c != null){
		contadorProductos=c.cantidadProductosTotales();
		listaItems= c.carrito;
	}
	
	
	
%>

	<header id="main-header">		
        <a id="title" href="Main">CAR</a>
        <a id="titleBlue" href="Main">Buy</a>
        <a id="logo-header" href="Main"><img src="Images/logor.png"></a>
        <nav>		
            <ul>
            <li class='imgDollar tooltip';> 
                    <a href='ventas.jsp'> 
                        <img src="Images/dollar.svg" width="30px"> 
                    </a>
                </li> 
                <li class='imgCarroIn tooltip';> 
                    <a href='#'> 
                        <img src="Images/carro.svg" width="50px"> 
                        <span class='tooltiptext'><%= contadorProductos %></span>
                    </a>
                </li> 
                <li class='imgUser tooltip'>
                    <a href='index.jsp'> 
                        <img src="Images/user.svg" width="38.5px"> 
                        <span class="tooltiptext"><%=usuarioCadena%>
                        </span> 
                    </a>
                </li>		
            </ul>
        </nav>
    </header>	
	<div id="cuerpo">
	<h1 class="titulo">CARRO DE COMPRA</h1><br><br><br><br><br><br>
<%
	if(c != null){//Si carrito está vacío no pondrá entrar por aquí, ya que dará error, si está vaćio solo indicaremos que está vacío
		%>
		
		<div class="fondoCarritoDerecha">
	
	        <div class="infoProducto">
	            <a class="letrasFinal">Subtotal ( <%=contadorProductos%> productos ): <%= c.precioTotal() %> €</a>   <br>
	        </div>
	
	        <br>
	        
	        <div id="introducir">
	            <a href="comprar.jsp">
	                <button class="realizarCompra">
	                    <h2>Terminar Pedido</h2>
	                </button>
	            </a>
	        </div>
	    </div>	
		
		<%
		for(ItemCarrito i : listaItems){
			Producto p = CRUDProducto.readProducto(i.getId_articulo());
			Categoria cat = CRUDCategoria.readCategoria(p.getId_categoria());
			
	
%>
	    
	
	    <!-- -------------------------------- Bucle Empieza Aquí ------------------------------------ -->
	    
	    <div class="CarritoIzquierda">
	        <div class="fondoCarritoIzquierda">
	
	            <div class="photoCarritoFinal">
	                <img src="Images/Ford3.png" class="photo_sure">
	            </div>
	
	            <div class="infoProducto">
	                <a class="titulitos"><%= p.getNombre() %> <%= p.getPrecio() %> €</a>   <br>
	                <small> Cant: <%= c.cantidadProductos(c.getItem(i.getId_articulo())) %> </small>
	            </div>
	            <div id="introducir">
	                <a href="borrarItem.jsp?id=<%=i.getId_articulo()%>">
	                    <button class="cancelar">
	                        <h2>Borrar Item</h2>
	                    </button>
	                </a>
	            </div>
	
	        </div>	
	    </div>
	
	    <!-- -------------------------------- Bucle Acaba Aquí ------------------------------------ -->
<% }}

%>


	</div>
</body>
</html>