package es.unex.cum.tw.reyesmagos.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import util.ConexionUtil;
import es.unex.cum.tw.reyesmagos.controller.ProductosController;
import es.unex.cum.tw.reyesmagos.model.Usuario;

public class UsuarioServiceBD implements UsuarioService{
	public boolean authenticate(String username, String password) {
		Usuario user = getUserByUsername(username);
		if (user != null && user.getUsername().equals(username)
				&& user.getPassword().equals(password)) {
			return true;
		} else {
			return false;
		}
	}

	public Usuario getUserByUsername(String username) {
		Usuario user = null;
		ResultSet resultados = null;
		try {
			Statement sentencia= ConexionUtil.openStatement();
			synchronized (sentencia) {
				// Cogemos todos los datos de la asignaturas
				resultados = sentencia
						.executeQuery("SELECT * FROM usuarios where username='"
								+ username + "'");
			}
			if (resultados.next() == false) {
				return null;
			} else {
				user = new Usuario(
						Integer.parseInt(resultados.getString("id")),
						resultados.getString("Nombre"),
						resultados.getString("Apellidos"),
						resultados.getString("email"),
						resultados.getString("username"),
						resultados.getString("password"));

				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Se cierra resultSet
			if (resultados != null) {
				try {
					resultados.close();
				} catch (SQLException ex) {
					Logger.getLogger(ProductosController.class.getName()).log(
							Level.SEVERE, "No se pudo cerrar el Resulset", ex);
				}
			}
		}
		return user;
	}

	public Usuario getUserByUserId(String userId) {
		Usuario user = null;
		ResultSet resultados = null;
		try {
			Statement sentencia= ConexionUtil.openStatement();
			synchronized (sentencia) {
				// Cogemos todos los datos de la asignaturas
				resultados = sentencia
						.executeQuery("SELECT * FROM usuarios where userId='"
								+ userId + "'");
			}
			if (resultados.next() == false) {
				return null;
			} else {
				user = new Usuario(
						Integer.parseInt(resultados.getString("id")),
						resultados.getString("Nombre"),
						resultados.getString("Apellidos"),
						resultados.getString("email"),
						resultados.getString("username"),
						resultados.getString("password"));

				return user;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Se cierra resultSet
			if (resultados != null) {
				try {
					resultados.close();
				} catch (SQLException ex) {
					Logger.getLogger(ProductosController.class.getName()).log(
							Level.SEVERE, "No se pudo cerrar el Resulset", ex);
				}
			}
		}
		return user;
	}

	public List<Usuario> getListOfUsers() {
		Usuario user = null;
		ResultSet resultados = null;
		List<Usuario> l = new ArrayList<Usuario>();
		try {
			Statement sentencia= ConexionUtil.openStatement();
			synchronized (sentencia) {
				// Cogemos todos los datos de la asignaturas
				resultados = sentencia.executeQuery("SELECT * FROM usuarios");
			}

			while (resultados.next()) {
				user = new Usuario(
						Integer.parseInt(resultados.getString("id")),
						resultados.getString("Nombre"),
						resultados.getString("Apellidos"),
						resultados.getString("email"),
						resultados.getString("username"),
						resultados.getString("password"));
				l.add(user);

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Se cierra resultSet
			if (resultados != null) {
				try {
					resultados.close();
				} catch (SQLException ex) {
					Logger.getLogger(ProductosController.class.getName()).log(
							Level.SEVERE, "No se pudo cerrar el Resulset", ex);
				}
			}
		}
		return l;
	}

	public boolean register(Usuario user) {
		ResultSet resultados = null;
		if (getUserByUsername(user.getUsername()) != null)
			return false;

		try {
			Statement sentencia= ConexionUtil.openStatement();
			synchronized (sentencia) {
				String query = "INSERT INTO usuarios (nombre,apellidos,email,username,password) "
						+ "VALUES ('"
						+ user.getNombre()
						+ "','"
						+ user.getApellidos()
						+ "','"
						+ user.getEmail()
						+ "','"
						+ user.getUsername()
						+ "','"
						+ user.getPassword()
						+ "')";
				sentencia.executeUpdate(query);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// Se cierra resultSet
			if (resultados != null) {
				try {
					resultados.close();
				} catch (SQLException ex) {
					Logger.getLogger(ProductosController.class.getName()).log(
							Level.SEVERE, "No se pudo cerrar el Resulset", ex);
				}
			}
		}
		return true;
	}

}
