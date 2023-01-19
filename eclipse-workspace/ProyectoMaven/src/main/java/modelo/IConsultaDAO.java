package modelo;

import java.util.List;

public interface IConsultaDAO {

	public List<Consulta> obtenerConsultas(Paciente p);
	public Consulta obtenerConsulta(Paciente p, int id);
	int obtenerUltimaConsulta() ;
	public Consulta crearConsulta(Paciente paciente,String flexMax, String flexMin, String DesvCubMin, String DesvCubMax);
	public Consulta existeMedicion(Paciente p);
	public Consulta obtenerConsulta(int id);
	public void borrarConsulta(String id) ;
	public String borraConsultasPaciente(String dni);
	
}
