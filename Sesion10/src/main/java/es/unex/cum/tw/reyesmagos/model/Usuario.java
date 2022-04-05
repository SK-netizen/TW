package es.unex.cum.tw.reyesmagos.model;

public class Usuario {
	private int id;
	private String nombre;
	private String apellidos;
	private String email;
	private String username;
	private String password;

	public Usuario() {

	}

	public Usuario(int i, String nombre, String apellidos, String email,
			String username, String password) {
		id=i;
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.email = email;
		this.username = username;
		this.password = password;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
