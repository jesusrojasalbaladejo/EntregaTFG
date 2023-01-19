import controlador.Controlador;
import modelo.Modelo;
import modelo.Paciente;
import modelo.PacienteDAOImp;
import vista.Ventana;

public class Aplicacion {
	private static Ventana v;
	private static Modelo m;
	private static Controlador c;
	

	public static void main(String[] args) {
	v = Ventana.getInstance();
	m = Modelo.getInstance();
	c = new Controlador(v,m);
}	
}
