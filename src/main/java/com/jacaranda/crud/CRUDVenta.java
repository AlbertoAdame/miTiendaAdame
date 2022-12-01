package com.jacaranda.crud;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;

import com.jacaranda.model.Venta;


public class CRUDVenta {
	
	public static Venta readVenta(int id) {
		Session session = ConnectionBD.getSession();
		Venta v = null;
		
		try {
			v = session.get(Venta.class, id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return v;
	}
	
	public static List<Venta> loadList(){
		Session session = ConnectionBD.getSession();
		List<Venta> list = new ArrayList<>();
		Query query = session.createQuery("SELECT v FROM VENTA v ORDER BY FECHA_VENTA DESC");
		list = query.getResultList();
		return list;
		
	}
	
	public static boolean addVenta(Venta v) {
		Session session = ConnectionBD.getSession();
		boolean resultado = false;
		try {
			session.getTransaction().begin();
			session.saveOrUpdate(v);
			session.getTransaction().commit();
			resultado=true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return resultado;
	}
	
}
