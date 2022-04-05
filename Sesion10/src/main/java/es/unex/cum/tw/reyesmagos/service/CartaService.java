package es.unex.cum.tw.reyesmagos.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import es.unex.cum.tw.reyesmagos.model.Carta;

public interface CartaService {

	public boolean anadirCarta(String idP, String id) throws IOException, SQLException;

	public boolean eliminarCarta(String idP, String id) throws IOException, SQLException;

	public List<Carta> verCarta(String idP, String id) throws IOException, SQLException;
}
