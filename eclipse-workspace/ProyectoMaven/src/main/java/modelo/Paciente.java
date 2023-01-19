package modelo;

public class Paciente {


	private String dni;
	private String nombre;
	private String apellidos;
	private String telefono;
	private String ocupación;
	private Integer sexo;
	private Integer manoDominante;
	private Integer edad;
	
	
	
	public Paciente(String nombre, String apellidos, String dni, String telefono, String ocupación, int sexo,
			int manoDominante, int edad) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.dni = dni;
		this.telefono = telefono;
		this.ocupación = ocupación;
		this.sexo = sexo;
		this.manoDominante = manoDominante;
		this.edad=edad;
	}

	public Integer getEdad() {
		return this.edad;
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

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getOcupación() {
		return ocupación;
	}

	public void setOcupación(String ocupación) {
		this.ocupación = ocupación;
	}

	public int getSexo() {
		return sexo;
	}

	public void setSexo(int sexo) {
		this.sexo = sexo;
	}

	public int getManoDominante() {
		return manoDominante;
	}

	public void setManoDominante(int manoDominante) {
		this.manoDominante = manoDominante;
	}
	public void setEdad(int edad) {
		this.edad=edad;
	}
}
