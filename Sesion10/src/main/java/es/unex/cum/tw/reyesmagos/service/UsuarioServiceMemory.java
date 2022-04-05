package es.unex.cum.tw.reyesmagos.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import es.unex.cum.tw.reyesmagos.model.Usuario;

public class UsuarioServiceMemory implements UsuarioService {
	private static HashMap<Integer, Usuario> lUsuario = null;

	private void buildMap() {
		// Se carga los usuarios del fichero
		if (lUsuario == null) {
			lUsuario= new HashMap<Integer, Usuario>();
		}
	}

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
		if (lUsuario == null)
			buildMap();
		synchronized (lUsuario) {
			Iterator it = lUsuario.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry) it.next();
				// e.getKey() e.getValue());
				user = (Usuario) e.getValue();
				if (user.getUsername().equals(username))
					return user;
			}
		}
		return user;

	}

	public Usuario getUserByUserId(String userId) {
		if (lUsuario == null)
			buildMap();
		Usuario user = null;
		user = lUsuario.get(Integer.parseInt(userId));
		return user;

	}

	public List<Usuario> getListOfUsers() {
		if (lUsuario == null)
			buildMap();
		Usuario user = null;
		List<Usuario> l = new ArrayList<Usuario>();

		synchronized (lUsuario) {
			Iterator it = lUsuario.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry) it.next();
				// e.getKey() e.getValue());
				user = (Usuario) e.getValue();
				l.add(user);
			}
		}
		return l;
	}

	public boolean register(Usuario user) {
		if (lUsuario == null)
			buildMap();
		if (getUserByUsername(user.getUsername()) != null)
			return false;

		synchronized (lUsuario) {
			Integer valor=lUsuario.size() + 1;
			user.setId(valor);
			lUsuario.put(valor, user);
			System.out.println("Anadido : "+ valor);
		}
		return true;
	}

}
