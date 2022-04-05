package es.unex.cum.tw.reyesmagos.service;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import util.ConexionUtil;
import es.unex.cum.tw.reyesmagos.controller.CartaReyesController;
import es.unex.cum.tw.reyesmagos.controller.UsuarioController;
import es.unex.cum.tw.reyesmagos.model.Carta;

public class CartaServiceBD implements CartaService{

	public boolean anadirCarta(String idP, String id) throws IOException,
			SQLException {
	  //TODO
		return false;
	}

	public boolean eliminarCarta(String idP, String id)
			throws IOException, SQLException {
	  //TODO
		return false;
	}

	public List<Carta> verCarta(String idP, String id)
			throws IOException, SQLException {
			
	  //TODO
		return null;
	}

}
